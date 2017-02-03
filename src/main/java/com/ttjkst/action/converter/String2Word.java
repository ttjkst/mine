package com.ttjkst.action.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.ttjkst.bean.Word;
import com.ttjkst.service.IWordsService;

public class String2Word implements Converter<String, Word>{

	@Autowired
	private IWordsService service; 
	
	public Word convert(String source) {
		if(source!=null&&!source.isEmpty()){
			return this.service.getItbyId(source);
		}
		return new Word();
	}

	public IWordsService getService() {
		return service;
	}

	public void setService(IWordsService service) {
		this.service = service;
	}
	
	
	
}
