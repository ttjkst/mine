package com.ttjkst.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.AboutWhats;
import com.ttjkst.dao.ABDAO;
import com.ttjkst.service.IAboutWhatService;



@Service
@Transactional
public class ABService implements IAboutWhatService{

	
	@Autowired
	private ABDAO dao;
	
	public List<AboutWhats> getAllAboutWhat() {
		List<AboutWhats> abs = new ArrayList<AboutWhats>();
		Iterator<AboutWhats> itera = dao.findAll().iterator();
		while(itera.hasNext()){
			abs.add(itera.next());
		}
		return abs;
	}

	public boolean hasthis(String msg) {
		int result = dao.getCountByName(msg);
		return result==0?false:true;
	}

	public AboutWhats newAboutWhat(AboutWhats abWhats) {
		return dao.save(abWhats);
	}

	public AboutWhats getItByName(String name) {
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
