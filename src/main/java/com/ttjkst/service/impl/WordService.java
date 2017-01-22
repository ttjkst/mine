package com.ttjkst.service.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.AboutWhats;
import com.ttjkst.bean.Kinds;
import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Word;
import com.ttjkst.dao.ABDAO;
import com.ttjkst.dao.KindDAO;
import com.ttjkst.dao.WordDAO;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;
import com.ttjkst.tools.MD5Tool;

@Service
@Transactional
public class WordService implements IWordsService{
	
	@Autowired
	private WordDAO dao;
	
	@Autowired
	private KindDAO kDao;
	
	@Autowired
	private ABDAO  abDao;
	
	
	private String separator = "/";
	
	private static String filePath = null;

	public void detele(Long id) throws ServiceException {
		Word w = this.getItbyId(id);
		if(filePath==null){
		filePath = System.getProperty("filePath");
		}
		boolean deletePath = false;
		boolean deleteIntor = false;
		if(filePath==null||filePath.isEmpty()){
				throw new ServiceException("the filePath is not defined");
		
		}
		try {
		  deleteIntor =	FileUtils.deleteQuietly(new File(filePath+w.getIntroductionPath()));
		  deletePath  =	FileUtils.deleteQuietly(new File(filePath+w.getwPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dao.delete(id);	
		}
		
	}

	public boolean hasWordByTitle(String title, String kindName,
			String aboutWhatName) {
		Integer result = dao.countWordTitle(title, kindName, aboutWhatName);
		return result.equals(0)?false:true;
	}

	
	@Transactional(readOnly=true)
	public Page<Word> findall(int pageNo, int pageSize,final String aboutWhatName,
			final String searchName, final Boolean isNoPrcess) {
		
		Pageable page = new  PageRequest(pageNo, pageSize);
		Specification<Word> specification  = new Specification<Word>() {

			public Predicate toPredicate(Root<Word> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate all = null;
					//all this is common use
					Path<Kinds> kindPath = root.get("wKind");
					Path<AboutWhats> aboutWhatPath = kindPath.get("aboutwhat");
					Expression<String> nameEx = aboutWhatPath.get("name");
					
					Expression<Long> idEx  =  root.get("wId");
					
					// different
					if(searchName!=null&&!searchName.isEmpty()){	
					String searchParam = "%"+searchName+"%";
					Expression<String> titleEx = root.get("wTitle");
					Predicate p1 = cb.like(titleEx,searchParam);
					Expression<String> kNameEx = kindPath.get("kName"); 
					Predicate p2 =  cb.like(kNameEx, searchParam);
					Predicate p3 = cb.like(nameEx, searchParam);
					all = cb.or(p1,p2,p3);
					}
					//different
					if(aboutWhatName!=null&&!aboutWhatName.isEmpty()){
						Predicate p1 = cb.like(nameEx, aboutWhatName);
						all = cb.and(p1);
					}
					//subQuery exists
					if(isNoPrcess!=null){
						Subquery<LeaveWords> subquery = cb.createQuery().subquery(LeaveWords.class);
						Root<LeaveWords> subRoot = subquery.from(LeaveWords.class);
						Path<Word> WordSubPath = subRoot.get("leaveFor");
						Expression<Boolean> hasReadEx = subRoot.get("hasRead");
						Predicate p1 = cb.equal(hasReadEx, !isNoPrcess);
						Expression<Long> subWordIdEx = WordSubPath.get("wId");
						Predicate p2 = cb.equal(idEx, subWordIdEx);
						all = cb.and(cb.exists(subquery.select(subRoot).where(cb.and(p1,p2))));
					}
				
				
				return all;
			}
			
		};
		Page<Word> pageResult = this.dao.findAll(specification, page);
		//initialize
//		pageResult.getContent();
//		for(Words w:pageResult.getContent()){
//			w.getwKind().getkName();
//			w.getwKind().getAboutwhat().getName();
//		}
		return pageResult;
	}
	

	public Word update(Word word, InputStream srcWord, InputStream srcIntor)
			throws ServiceException {
		if(filePath==null){
			filePath = System.getProperty("LocalWebRoot");
		}
		if(filePath==null||filePath.isEmpty()){
			throw new ServiceException("the loacalWebRoot is not defined");
		}
		try {
			Word srcEnitity = this.dao.findOne(word.getwId());
			String aboutName = word.getwKind().getAboutwhat().getName();
			String kindName  = word.getwKind().getkName();
			
			Kinds kind = this.kDao.getItBy2Name(kindName, aboutName);
			if(kind==null){
				AboutWhats aboutWhat = this.abDao.getItByName(aboutName);
				if(aboutWhat==null){
					aboutWhat = this.abDao.save(new AboutWhats(null, aboutName));
				}
				kind = new Kinds(null, kindName, aboutWhat);
				kind = this.kDao.save(kind);
			}
			word.setwKind(kind);
			word.setIntroductionPath(srcEnitity.getIntroductionPath());
			word.setwPath(srcEnitity.getwPath());
			word  = this.dao.save(word);
			if(srcWord!=null){
				FileUtils.copyInputStreamToFile(srcWord, new File(filePath+separator+word.getwPath()));
			}
			if(srcIntor!=null){
				FileUtils.copyInputStreamToFile(srcIntor,new File(filePath+separator+word.getIntroductionPath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return word;
	}

	public Page<Word> findItByReadTimes(final String aboutWhatName, int size) {
		Pageable pageable = new PageRequest(0, size);
		Specification<Word> specification = new Specification<Word>() {
			
			public Predicate toPredicate(Root<Word> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if(aboutWhatName!=null&&!aboutWhatName.isEmpty()){
					Path<Kinds> kindPath = root.get("wKind");
					Path<AboutWhats> abPath = kindPath.get("aboutwhat");
					Expression<String> nameEx = abPath.get("name");
					return cb.equal(nameEx, aboutWhatName);
				}else{
					throw new IllegalArgumentException("Argument 'aboutWhatName' must not be null!");
				}
			}
		};
		return dao.findAll(specification, pageable);
	}

	public Word getItbyId(Long id) {
		return dao.findOne(id);
	}
	
	
	private String getPathByMd5(Word word){
		String path = 
				 "html"
				+ separator
				+ word.getwKind().getAboutwhat()
						.getName()
				+ separator
				+ word.getwKind().getkName()
				+ separator
				+ (new MD5Tool().MD5("" + word.getwTitle()
						+ word.getwTimeOfInData().toString())) + ".html";
		return path;
	}
	private String getIntorByMd5(Word word){
		String intorPath = 
				 "html"
				+ separator
				+ word.getwKind().getAboutwhat()
						.getName()
				+ separator
				+ word.getwKind().getkName()
				+ separator
				+ "introduction"
				+ separator
				+ (new MD5Tool().MD5("" + word.getwTitle()
						+ word.getwTimeOfInData().toString())) + ".html";
		return intorPath;
	}
	public Word saveit(Word word, InputStream srcWord, InputStream srcIntor) throws ServiceException {
		//init
		if(filePath==null){
			filePath = System.getProperty("LocalWebRoot");
		}
		if(filePath==null||filePath.isEmpty()){
			throw new ServiceException("the loacalWebRoot is not defined");
		}
		
		
		String aboutName = word.getwKind().getAboutwhat().getName();
		String kindName  = word.getwKind().getkName();
		
		Kinds kind = this.kDao.getItBy2Name(kindName, aboutName);
		if(kind==null){
			AboutWhats aboutWhat = this.abDao.getItByName(aboutName);
			if(aboutWhat==null){
				aboutWhat = this.abDao.save(new AboutWhats(null, aboutName));
			}
			kind = new Kinds(null, kindName, aboutWhat);
			kind = this.kDao.save(kind);
		}
		word.setwKind(kind);
		// where is the artist  located in
		String pathFilePath  = getPathByMd5(word);
		// where is the intor located in
		String intorFilePath = getIntorByMd5(word);
		 try {
		    FileUtils.copyInputStreamToFile(srcWord, new File(filePath+separator+pathFilePath));
			FileUtils.copyInputStreamToFile(srcIntor,new File(filePath+separator+intorFilePath));
			
			word.setIntroductionPath(intorFilePath);
			word.setwPath(pathFilePath);
			this.dao.save(word);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
