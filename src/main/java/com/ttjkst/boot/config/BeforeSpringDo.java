package com.ttjkst.boot.config;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ttjkst.ElasticMapperHolder;


@Component
public class BeforeSpringDo{
	private static String IK_MODE= "ik_max_word";
	static{
		Map<String, Object> prop = new HashMap<>();
//		Map<String,Map<String,String>> fulltext = new HashMap<>();
//			Map<String, String> _all = new HashMap<>();
//				_all.put("analyzer", IK_MODE);
//				_all.put("search_analyzer", IK_MODE);
//				_all.put("term_vector", "no");
//				_all.put("store", "false");
//			fulltext.put("_all", _all);
//		prop.put("fulltext", fulltext);
		Map<String,Map<String,String>> properties = new HashMap<>();
			Map<String, String> content = new HashMap<>();
				content.put("type", "text");
				content.put("analyzer", IK_MODE);
				content.put("search_analyzer", IK_MODE);
				content.put("include_in_all", "true");
				content.put("boost", "8");
			Map<String, String> author = new HashMap<>();
				author.put("type", "text");
				author.put("analyzer", IK_MODE);
				author.put("search_analyzer", IK_MODE);
				author.put("include_in_all", "true");
				author.put("boost", "8");
			Map<String, String> create_time = new HashMap<>();
				create_time.put("type", "keyword");
				create_time.put("store", "true");
			Map<String, String> title = new HashMap<>();
				title.put("type", "keyword");
				title.put("store", "true");
			Map<String, String> canshow = new HashMap<>();
				canshow.put("type", "keyword");
				canshow.put("store", "true");
		properties.put("content", content);
		properties.put("author", author);
		properties.put("create_time", create_time);
		properties.put("title", title);
		properties.put("canshow", canshow);
		prop.put("properties", properties);
		ElasticMapperHolder.addMapperInfo("ttjkst", "today", prop);
	}
	
	
}
