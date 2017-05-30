package com.ttjkst.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.Essay;
import com.ttjkst.dao.EssayDAO;
import com.ttjkst.elastic.ElasticDao;
import com.ttjkst.elastic.ElasticUitl;
import com.ttjkst.service.IEssayService;
import com.ttjkst.service.exception.ServiceException;





@Service
@Transactional
public class EssayService implements IEssayService{
	
	private static boolean hasCreate = false;
	@Autowired
	private ElasticUitl elasticUitl;
	@Autowired
	private EssayDAO dao;
	
	
	@Autowired
	private ElasticDao elastic;
	private void init(){
		if(!hasCreate){
		hasCreate=true;
		elastic.createIndex(elasticUitl.getProp().getEsIndex(), 
				elasticUitl.getProp().getEsType());
		}
	}

	public void detele(String id) throws ServiceException {
		//elastic delete
	   elastic.delete(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), id);
	   dao.delete(id);
	}

	//need fixed
	@Transactional(readOnly=true)
	public Page<Essay> findall(int pageNo, int pageSize,
			final String searchName) {
		Pageable page = new  PageRequest(pageNo, pageSize);
		System.out.println(page);
		Map<String, Object> map =	elastic.search(elasticUitl.getProp().getEsIndex(), 
				elasticUitl.getProp().getEsType(), (x)->{
						
						  x.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
						  x.setFrom(page.getOffset());
						  x.setSize(page.getPageSize());
						  if(searchName==null||searchName.isEmpty()){
							  x.setQuery(QueryBuilders.matchAllQuery());
						  }else{
							  x.setQuery(QueryBuilders.matchQuery("content", searchName));
						  }
						}, 
					resultMapper);
				if(map==null){
					map = new HashMap<>();
				}
				map.put("pageable", page);
		return this.map2page(map);
	}
	@Override
	public Page<Essay> findallWithHighlighter(int pageNo, int pageSize, String searchName) {
		Pageable page = new  PageRequest(pageNo, pageSize);
		System.out.println(page);
		Map<String, Object> map =	elastic.search(elasticUitl.getProp().getEsIndex(), 
				elasticUitl.getProp().getEsType(), (x)->{
						
						  x.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
						  x.setFrom(page.getOffset());
						  x.setSize(page.getPageSize());
						  if(searchName==null||searchName.isEmpty()){
							  x.setQuery(QueryBuilders.matchAllQuery());
						  }else{
							  x.setQuery(QueryBuilders.matchQuery("content", searchName));
							  HighlightBuilder builder = new HighlightBuilder();
							  builder.preTags("<p class='text-primary'>").postTags("</p>").field("content");
							  x.highlighter(builder);
						  }
						 
						}, 
				resultMapperWithHighligher);
				if(map==null){
					map = new HashMap<>();
				}
				map.put("pageable", page);
		return this.map2page(map);
	}
	
	
    //not finished  kind and aboutWhat
	public Essay update(Essay word)
			throws ServiceException {
		List<Essay> itByTitle = dao.getItByTitle2(word.getId(),word.getTitle());
		if(itByTitle.size()!=0){
			throw new ServiceException("title is not unique");
		}
		elastic.update(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(),
				new HashMap<>(), word.getId(), x->{
				Map<String, String> map = new HashMap<>();
				map.put("author", word.getAuthor());
				map.put("title", word.getTitle());
				map.put("content", word.getContent());
				x.doc(map);
		});
				
		dao.save(word);
		return word;
	}

	@Override
	public List<AnalyzeToken> ikTest(String content){
		TransportClient transportClient = elasticUitl.getTransportClient();
		AnalyzeResponse actionGet = transportClient.admin().indices().prepareAnalyze(content).setAnalyzer("ik_smart").execute().actionGet();
		return actionGet.getTokens();
	}
	
	//finished  ?
	public Essay getItbyId(String id) {
		//from dataSource
		Essay data =  dao.findOne(id);
		elastic.get(elasticUitl.getProp().getEsIndex(),
				elasticUitl.getProp().getEsType(), id, x->{
			Map<String, Object> source = x.getSource();
			String title = source.get("title").toString();
			String content = source.get("content").toString();
			String author  = source.get("author").toString();
			data.setAuthor(author);
			data.setContent(content);
			data.setTitle(title);
			data.setId(x.getId());
		});
		return data;
	}
	
	
	private void checkWord(Essay word){
		if(word.getTitle()==null){
			throw new IllegalArgumentException("word title must not be null!");
		}
		if(word.getAuthor()==null){
			throw new IllegalArgumentException("word author must not be null!");
		}
	}
	
	//finished ?
	public Essay saveit(Essay word) throws ServiceException {
		init();
		checkWord(word);
		List<Essay> itByTitle = dao.getItByTitle(word.getTitle());
		if(itByTitle.size()!=0){
			throw new ServiceException("title is not unique");
		}
		elastic.presist(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), mapper->{
			Map<String, Object> source = new HashMap<>();
			source.put("title", word.getTitle());
			source.put("author", word.getAuthor());
			source.put("content", word.getContent());
			mapper.setSource(source);
		}, then->{
			word.setId(then.getId());
		});
		this.dao.save(word);
		
		return word;
	}

	// tools
	// mapper
		private  Function<SearchResponse, Map<String, Object>> resultMapper = x1->{
			List<Essay> list = new ArrayList<>();
			x1.getHits().forEach(x->{
				Essay word = new Essay();
				word.setId(x.getId());
				Map<String, Object> source = x.getSource();
				String title = source.get("title").toString();
				String content = source.get("content").toString();
				String author  = source.get("author").toString();
				word.setAuthor(author);
				if(content.length()<=300){
					word.setContent(content);
				}else{
				word.setContent(content.substring(0, 300));
				}
				word.setTitle(title);
				word.setId(x.getId());
				list.add(word);
			});
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", list);
			map.put("total", x1.getHits().getTotalHits());
			return map;
		};
		private  Function<SearchResponse, Map<String, Object>> resultMapperWithHighligher = x1->{
			List<Essay> list = new ArrayList<>();
			x1.getHits().forEach(x->{
				Essay word = new Essay();
				word.setId(x.getId());
				Map<String, Object> source = x.getSource();
				String title = source.get("title").toString();
				String content = null;
				Map<String, HighlightField> highlightFields = x.getHighlightFields();
				if(highlightFields.isEmpty()){
					content = source.get("content").toString();
					if(content.length()<=300){
						word.setContent(content);
					}else{
					word.setContent(content.substring(0, 300));
					}
				}else{
					Text[] fragments = x.getHighlightFields().get("content").fragments();
					content = Arrays.asList(fragments).stream().
							reduce(new Text(""), (t1,t2)->new Text(t1.toString()+t2.toString())).toString();
					word.setContent(content);
				}
				String author  = source.get("author").toString();
				word.setAuthor(author);
				word.setTitle(title);
				word.setId(x.getId());
				list.add(word);
				//待优化
				
			});
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", list);
			map.put("total", x1.getHits().getTotalHits());
			return map;
		};
		//
		private Page<Essay>  map2page(Map<String, Object> map){
			Page<Essay> result = new Page<Essay>() {
				
				Pageable pageable = (Pageable)map.get("pageable");
				@SuppressWarnings("unchecked")
				List<Essay> content = map.containsKey("content")?(List<Essay>)map.get("content"):new ArrayList<>();
				
				long total = (long)map.get("total");
				@Override
				public Iterator<Essay> iterator() {
					
					return content.iterator();
				}
				
				@Override
				public Pageable previousPageable() {
					return pageable.previousOrFirst();
				}
				
				@Override
				public Pageable nextPageable() {
					return pageable.next();
				}
				
				@Override
				public boolean isLast() {
					
					return getTotalPages()==pageable.getPageNumber();
				}
				
				@Override
				public boolean isFirst() {
					return 0==pageable.getPageNumber();
				}
				
				@Override
				public boolean hasPrevious() {
					
					return !isFirst();
				}
				
				@Override
				public boolean hasNext() {
					return !isLast();
				}
				
				@Override
				public boolean hasContent() {
					return !content.isEmpty();
				}
				
				@Override
				public Sort getSort() {
					throw new UnsupportedOperationException("no override");
				}
				
				@Override
				public int getSize() {
					
					return pageable.getPageSize();
				}
				
				@Override
				public int getNumberOfElements() {
					return content.size();
				}
				
				@Override
				public int getNumber() {
					return pageable.getPageNumber();
				}
				
				@Override
				public List<Essay> getContent() {
					return content;
				}
				
				@Override
				public <S> Page<S> map(Converter<? super Essay, ? extends S> converter) {
					throw new UnsupportedOperationException("no override");
				}
				
				@Override
				public int getTotalPages() {
					int totalpages = ((int)total/pageable.getPageSize())+((int)total)%pageable.getPageSize()==0?0:1;
					return totalpages;
				}
				
				@Override
				public long getTotalElements() {
					return total;
				}
			};
			
			return result;
		}
	
	
}
