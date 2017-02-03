package com.ttjkst.service.impl;


import java.io.InputStream;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
import com.ttjkst.elastic.ElasticDao;
import com.ttjkst.elastic.ElasticUitl;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;

@Service
@Transactional
public class WordService implements IWordsService{
	
	
	@Autowired
	private ElasticUitl elasticUitl;
	@Autowired
	private WordDAO dao;
	
	@Autowired
	private KindDAO kDao;
	
	@Autowired
	private ABDAO  abDao;
	
	
	private String separator = "/";
	
	
	@Autowired
	private ElasticDao elastic;
	
	private static String filePath = null;

	public void detele(String id) throws ServiceException {
		//elastic delete
	   elastic.delete(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), id);
	   dao.delete(id);
	}

	// need fixed
	public boolean hasWordByTitle(String title, String kindName,
			String aboutWhatName) {
		Integer result = 0;
		return result.equals(0)?false:true;
	}

	//need fixed
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
	
    //not finished
	public Word update(Word word, InputStream srcWord, InputStream srcIntor)
			throws ServiceException {
		elastic.update(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), new HashMap<>(), word.getId(), x->{
			
		});
		
		dao.save(word);
		return word;
	}
	//need fixed
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

	
	//not finished
	public Word getItbyId(String id) {
		Word data =  dao.findOne(id);
		elastic.get(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), id, x->{
			data.getAuthor();	
		});
		//from dataSource
		return dao.findOne(id);
	}
	
	
	//not finished
	public Word saveit(Word word, InputStream srcWord, InputStream srcIntor) throws ServiceException {
		elastic.presist(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), new HashMap<>(), mapper->{
			return mapper;
		}, then->{
			String id = then.get("id").toString();
			return word;
		});
		this.dao.save(word);
		
		return word;
	}

	
	
}
