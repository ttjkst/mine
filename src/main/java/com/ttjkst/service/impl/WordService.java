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
import com.ttjkst.bean.Words;
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
	
	private static String localWebRoot = null;

	public void detele(Long id) throws ServiceException {
		Words w = this.getItbyId(id);
		if(localWebRoot==null){
		localWebRoot = System.getProperty("LocalWebRoot");
		}
		boolean deletePath = false;
		boolean deleteIntor = false;
		if(localWebRoot==null||localWebRoot.isEmpty()){
				throw new ServiceException("the loacalWebRoot is not defined");
		
		}
		try {
		  deleteIntor =	FileUtils.deleteQuietly(new File(localWebRoot+w.getIntroductionPath()));
		  deletePath  =	FileUtils.deleteQuietly(new File(localWebRoot+w.getwPath()));
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
	public Page<Words> findall(int pageNo, int pageSize,final String aboutWhatName,
			final String searchName, final Boolean isNoPrcess) {
		
		Pageable page = new  PageRequest(pageNo, pageSize);
		Specification<Words> specification  = new Specification<Words>() {

			public Predicate toPredicate(Root<Words> root,
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
						Path<Words> WordSubPath = subRoot.get("leaveFor");
						Expression<Boolean> hasReadEx = subRoot.get("hasRead");
						Predicate p1 = cb.equal(hasReadEx, !isNoPrcess);
						Expression<Long> subWordIdEx = WordSubPath.get("wId");
						Predicate p2 = cb.equal(idEx, subWordIdEx);
						all = cb.and(cb.exists(subquery.select(subRoot).where(cb.and(p1,p2))));
					}
				
				
				return all;
			}
			
		};
		Page<Words> pageResult = this.dao.findAll(specification, page);
		//initialize
//		pageResult.getContent();
//		for(Words w:pageResult.getContent()){
//			w.getwKind().getkName();
//			w.getwKind().getAboutwhat().getName();
//		}
		return pageResult;
	}
	

	public Words update(Words word, InputStream srcWord, InputStream srcIntor)
			throws ServiceException {
		if(localWebRoot==null){
			localWebRoot = System.getProperty("LocalWebRoot");
		}
		if(localWebRoot==null||localWebRoot.isEmpty()){
			throw new ServiceException("the loacalWebRoot is not defined");
		}
		try {
			Words srcEnitity = this.dao.findOne(word.getwId());
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
				FileUtils.copyInputStreamToFile(srcWord, new File(localWebRoot+separator+word.getwPath()));
			}
			if(srcIntor!=null){
				FileUtils.copyInputStreamToFile(srcIntor,new File(localWebRoot+separator+word.getIntroductionPath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Page<Words> findItByReadTimes(final String aboutWhatName, int size) {
		Pageable pageable = new PageRequest(0, size);
		Specification<Words> specification = new Specification<Words>() {
			
			public Predicate toPredicate(Root<Words> root, CriteriaQuery<?> query,
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

	public Words getItbyId(Long id) {
		return dao.findOne(id);
	}
	
	
	private String getPathByMd5(Words word){
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
	private String getIntorByMd5(Words word){
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
	public Words saveit(Words word, InputStream srcWord, InputStream srcIntor) throws ServiceException {
		//init
		if(localWebRoot==null){
			localWebRoot = System.getProperty("LocalWebRoot");
		}
		if(localWebRoot==null||localWebRoot.isEmpty()){
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
		    FileUtils.copyInputStreamToFile(srcWord, new File(localWebRoot+separator+pathFilePath));
			FileUtils.copyInputStreamToFile(srcIntor,new File(localWebRoot+separator+intorFilePath));
			
			word.setIntroductionPath(intorFilePath);
			word.setwPath(pathFilePath);
			this.dao.save(word);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//getter and setter
	public WordDAO getDao() {
		return dao;
	}

	public void setDao(WordDAO dao) {
		this.dao = dao;
	}

	public KindDAO getkDao() {
		return kDao;
	}

	public void setkDao(KindDAO kDao) {
		this.kDao = kDao;
	}

	public ABDAO getAbDao() {
		return abDao;
	}

	public void setAbDao(ABDAO abDao) {
		this.abDao = abDao;
	}

	
	
	
	
}
