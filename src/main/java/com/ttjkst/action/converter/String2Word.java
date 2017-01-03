package com.ttjkst.action.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.ttjkst.bean.Words;
import com.ttjkst.service.IWordsService;

public class String2Word implements Converter<String, Words>{

	@Autowired
	private IWordsService service; 
	
	public Words convert(String source) {
		if(source!=null&&!source.isEmpty()){
			return this.service.getItbyId(Long.parseLong(source));
		}
		return null;
	}

	public IWordsService getService() {
		return service;
	}

	public void setService(IWordsService service) {
		this.service = service;
	}
	
	
	
}
