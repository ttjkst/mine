package com.ttjkst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.Kinds;
import com.ttjkst.dao.KindDAO;
import com.ttjkst.service.IKindService;
@Transactional
@Service
public class KindsService implements IKindService{
	
	@Autowired
	private KindDAO dao;
	

	public List<Kinds> getkindsList(String msg) {
		
		return dao.findByAbName(msg);
	}

	public boolean haskindbyW(String msg, String parentMsg) {
		int result = dao.getCoutBy2Name(msg, parentMsg);
		return result==0?false:true;
	}

	public Kinds getkindbyNameAndAb(String msg, String parentMsg) {
		return dao.getItBy2Name(msg, parentMsg);
	}

	public Kinds savekinds(Kinds kind) {
		
		return dao.save(kind);
	}
	
	
	//getter and setter
	public KindDAO getDao() {
		return dao;
	}

	public void setDao(KindDAO dao) {
		this.dao = dao;
	}
	

}
