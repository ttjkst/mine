package com.ttjkst.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.AboutWhat;
import com.ttjkst.dao.ABDAO;
import com.ttjkst.service.IAboutWhatService;



@Service
@Transactional
public class ABService implements IAboutWhatService{

	
	@Autowired
	private ABDAO dao;
	
	public List<AboutWhat> getAllAboutWhat() {
		List<AboutWhat> abs = new ArrayList<AboutWhat>();
		Iterator<AboutWhat> itera = dao.findAll().iterator();
		itera.forEachRemaining(x->abs.add(x));//java 8 
		return abs;
	}

	public boolean hasthis(String msg) {
		int result = dao.getCountByName(msg);
		return result==0?false:true;
	}

	public AboutWhat newAboutWhat(AboutWhat abWhats) {
		return dao.save(abWhats);
	}

	public AboutWhat getItByName(String name) {
		return dao.getItByName(name);
	}

	
		//getter and setter
	public ABDAO getDao() {
		return dao;
	}

	public void setDao(ABDAO dao) {
		this.dao = dao;
	}
	

}
