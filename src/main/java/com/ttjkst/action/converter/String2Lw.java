package com.ttjkst.action.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.ttjkst.bean.LeaveWords;
import com.ttjkst.service.ILeaveWordsService;

public class String2Lw implements Converter<String, LeaveWords>{

	@Autowired
	private ILeaveWordsService service;
	
	public LeaveWords convert(String source) {
		if(source!=null&&!source.isEmpty()){
			return this.service.findItById(Long.parseLong(source));
		}
		return null;
	}

	public ILeaveWordsService getService() {
		return service;
	}

	public void setService(ILeaveWordsService service) {
		this.service = service;
	}
	
	
	

}
