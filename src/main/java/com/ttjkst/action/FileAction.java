package com.ttjkst.action;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileAction {
	@Value(value = "my.file.path")
	private  String local;
	
	@PostConstruct
	public void valid(){
		System.out.println("localPath:"+local);
	}

	
}
