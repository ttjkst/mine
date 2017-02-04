package com.ttjkst;

import java.util.HashMap;
import java.util.Map;

public class ElasticMapperHolder {
	
	private ElasticMapperHolder(){
		
	}
  private  static Map<String, Map<String, Object>>  source = new HashMap<>();
  public  static Map<String, Object> getMapperInfo(String index,String type){
	  int len1 = index.length();
	  int len2 = type.length();
	return source.get(len1+len2+index+type);
  }
  public static void addMapperInfo(String index,String type,Map<String,Object> map){
	  int len1 = index.length();
	  int len2 = type.length();
	  source.put(len1+len2+index+type, map);
  }
  
}
