package com.ttjkst.boot.config;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ttjkst.ElasticMapperHolder;


@Component
public class BeforeSpringDo{
	static{
		System.out.println("do.....");
		Map<String, Object> source = new HashMap<>();
		Map<String, String> setting = new HashMap<>();
		setting.put("type", "string");
		setting.put("store", "true");
		setting.put("index", "not_analyzed");
		source.put("author", setting);
		source.put("title", setting);
		source.put("create_time", setting);
		source.put("canshow", setting);
		source.put("hasNoProcessLw", setting);
		Map<String, Object> prop = new HashMap<>();
		prop.put("properties", source);
		ElasticMapperHolder.addMapperInfo("ttjkst", "today", prop);
	}
	
	
}
