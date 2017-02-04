package com.ttjkst.elastic;

import java.util.HashMap;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ttjkst.BootApplication;
import com.ttjkst.bean.Word;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=BootApplication.class)
public class ElasticDaoTest {
  
  @Autowired
  private ElasticUitl elasticUitl;
	
  private String index;
  private String type;
  

  @Autowired
  private ElasticDao dao;
  
//  @Test
//  public void presist(){
//	  index = elasticUitl.getProp().getEsIndex();
//	  type  = elasticUitl.getProp().getEsType();
//	  
//	  Word word = new Word();
//	  word.setAuthor("ttjkst");
//	  word.setCreateTime(new Date());
//	  word.setTitle("title 1");
//	  File  file = new File("C:\\Users\\ttjkst\\Desktop\\test\\1.txt");
//	  FileReader fileReader = null;
//	  try {
//		fileReader = new FileReader(file);
//	  } catch (FileNotFoundException e) {
//		e.printStackTrace();
//	  }
//	  BufferedReader bufferedReader = new BufferedReader(fileReader);
//	  String content = bufferedReader.lines().reduce("", (x1,x2)->{
//		  return x1+x2;
//	  });
//	  //mapper.put("content", content);
//	  dao.presist(index, type, new HashMap<>(), mapper->{
//		    mapper.put("content","");
//			mapper.put("author", word.getAuthor());
//			mapper.put("title", word.getTitle());
//			mapper.put("create_time", word.getCreateTime().getTime()+"");
//		  return mapper;
//	  }, then->{
//		  String id = then.get("id").toString();
//		  word.setId(id);
//		  dao.update(index, type, then, id, x->{
//			 Map<String, String> map = new HashMap<>();
//			 map.put("content", "new value");
//			  x.doc(map);
//		  });
//		  return null;
//	  });
//  }
  
  @Test
  public void delete(){
	  index = elasticUitl.getProp().getEsIndex();
	  type  = elasticUitl.getProp().getEsType();
	  dao.search(index, type, new HashMap<>(), base->{
		  base.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		  base.setQuery(QueryBuilders.matchQuery("content", "finibus"));
		  }, mapper->{
		  System.out.println(mapper.getHits().getTotalHits());
		  mapper.getHits().forEach(x->{
			 dao.delete(index, type, x.getId());
		  });
		 return null; 
	  });
	  
	  
  }
  
  
}
