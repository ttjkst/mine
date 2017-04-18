package com.ttjkst.action.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.ttjkst.bean.Essay;
import com.ttjkst.service.IEssayService;

public class String2Word implements Converter<String, Essay>{

	@Autowired
	private IEssayService service; 
	
	public Essay convert(String source) {
		if(source!=null&&!source.isEmpty()){
			return this.service.getItbyId(source);
		}
		return new Essay();
	}

	public IEssayService getService() {
		return service;
	}

	public void setService(IEssayService service) {
		this.service = service;
	}
	
	
	
}
