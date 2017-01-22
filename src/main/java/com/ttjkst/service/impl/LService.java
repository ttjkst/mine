package com.ttjkst.service.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Word;
import com.ttjkst.dao.LWDAO;
import com.ttjkst.service.ILeaveWordsService;
@Service
@Transactional
public class LService implements ILeaveWordsService{
	
	
	@Autowired
	private LWDAO dao;

	public void detele(Long id) {
		dao.delete(id);
	}

	public void save(LeaveWords leaveWord) {
		dao.save(leaveWord);
	}

	public LeaveWords findItById(Long id) {
		
		return dao.findOne(id);
	}

	

	public Long getlwNumByIdAndReaded(Long wordId, boolean isGetAll) {
		return isGetAll?this.dao.getCount(wordId):this.dao.getCountByReaded(wordId);
	}

	public Page<LeaveWords> getAll(int pageSize, int pageNo,final Long wordId,
		final	String order, final Boolean isNoProcess) {
		
		Pageable pageable = new PageRequest(pageNo, pageSize);
		Specification<LeaveWords> specification = new Specification<LeaveWords>() {
			
			public Predicate toPredicate(Root<LeaveWords> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate all = null;
				if(wordId!=null){
					Path<Word> wordPath = root.get("leaveFor");
					Expression<Long> wordIdEx = wordPath.get("wId");
					Predicate p1 = cb.equal(wordIdEx, wordId);
					all = cb.and(p1);
				}
				
				if(isNoProcess!=null){
					Expression<Boolean> hasReadEx = root.get("hasRead");
					Predicate p1 = cb.equal(hasReadEx, isNoProcess);
					all = cb.and(p1);
				}
				if(order!=null&&!order.isEmpty()){
					Expression<Date> timeInDateEx = root.get("timeInData");
					if(order.equals("asc")){
						Order order = cb.asc(timeInDateEx);
					    query.orderBy(order);
					}else if(order.equals("desc")){
						Order order = cb.desc(timeInDateEx);
					    query.orderBy(order);
					}
				}
				
				return all;
			}
		};
		return dao.findAll(specification, pageable);
	}

	public void updateHasRead(boolean hasRead, Long id) {
		this.dao.updateHasRead(hasRead, id);
		
	}
	//getter and setter
	public LWDAO getDao() {
		return dao;
	}

	public void setDao(LWDAO dao) {
		this.dao = dao;
	}
	

}
