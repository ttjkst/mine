package com.ttjkst.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.AboutWhat;
import com.ttjkst.bean.Kind;
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
		hasCreate=true;
		elastic.createIndex(elasticUitl.getProp().getEsIndex(), 
				elasticUitl.getProp().getEsType());
	}

	public void detele(String id) throws ServiceException {
		//elastic delete
	   elastic.delete(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), id);
	   dao.delete(id);
	}

	// need fixed question search mysql or elastic ?
	public boolean hasWordByTitle(String title, String kindName,
			String aboutWhatName) {
		Integer result = 0;
		return result.equals(0)?false:true;
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
	//need fixed
	public Page<Essay> findItByReadTimes(final String aboutWhatName, int size) {
		Pageable pageable = new PageRequest(0, size);
		Specification<Essay> specification = new Specification<Essay>() {
			
			public Predicate toPredicate(Root<Essay> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if(aboutWhatName!=null&&!aboutWhatName.isEmpty()){
					Path<Kind> kindPath = root.get("wKind");
					Path<AboutWhat> abPath = kindPath.get("aboutwhat");
					Expression<String> nameEx = abPath.get("name");
					return cb.equal(nameEx, aboutWhatName);
				}else{
					throw new IllegalArgumentException("Argument 'aboutWhatName' must not be null!");
				}
			}
		};
		return dao.findAll(specification, pageable);
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
			System.out.println(author);
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
				System.out.println(x.getFields());
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
		//
		private Page<Essay>  map2page(Map<String, Object> map){
			Page<Essay> result = new Page<Essay>() {
				
				Pageable pageable = (Pageable)map.get("pageable");
				@SuppressWarnings("unchecked")
				List<Essay> content = (List<Essay>)map.get("content");
				
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
