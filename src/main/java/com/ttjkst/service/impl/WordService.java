package com.ttjkst.service.impl;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.criteria.Subquery;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
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
import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Word;
import com.ttjkst.dao.ABDAO;
import com.ttjkst.dao.KindDAO;
import com.ttjkst.dao.WordDAO;
import com.ttjkst.elastic.ElasticDao;
import com.ttjkst.elastic.ElasticUitl;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;



@Service
@Transactional
public class WordService implements IWordsService{
	
	
	@Autowired
	private ElasticUitl elasticUitl;
	@Autowired
	private WordDAO dao;
	
	@Autowired
	private KindDAO kDao;
	
	@Autowired
	private ABDAO  abDao;
	
	
	
	
	@Autowired
	private ElasticDao elastic;
	

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
	public Page<Word> findall(int pageNo, int pageSize,final String aboutWhatName,
			final String searchName, final Boolean isNoPrcess) {
		Pageable page = new  PageRequest(pageNo, pageSize);
		if(isNoPrcess==null){
		Map<String, Object> map =	elastic.search(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(),
					new HashMap<>(), (x)->{
						
						  x.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
						  x.setFrom(page.getOffset());
						  x.setSize(page.getPageSize());
						  x.setQuery(QueryBuilders.multiMatchQuery(searchName, "content","author","title"));
						  BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
						  List<QueryBuilder> musts = queryBuilder.must();
						  musts.add(QueryBuilders.matchQuery("kindName", searchName));
						  musts.add(QueryBuilders.matchQuery("aboutWhatName", aboutWhatName));
						  x.setPostFilter(queryBuilder);
						  x.addStoredField("title");
						  x.addStoredField("author");
						  x.addStoredField("create_time");
						}, 
					resultMapper);
				map.put("pageable", page);
		return this.map2page(map);
		}
		
		
		
		//not finished
		Specification<Word> specification  = new Specification<Word>() {

			public Predicate toPredicate(Root<Word> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate all = null;
					//all this is common use
					Path<Kind> kindPath = root.get("wKind");
					Path<AboutWhat> aboutWhatPath = kindPath.get("aboutwhat");
					Expression<String> nameEx = aboutWhatPath.get("name");
					
					Expression<Long> idEx  =  root.get("wId");
					
					// different
					if(searchName!=null&&!searchName.isEmpty()){	
					String searchParam = "%"+searchName+"%";
					Expression<String> titleEx = root.get("wTitle");
					Predicate p1 = cb.like(titleEx,searchParam);
					Expression<String> kNameEx = kindPath.get("kName"); 
					Predicate p2 =  cb.like(kNameEx, searchParam);
					Predicate p3 = cb.like(nameEx, searchParam);
					all = cb.or(p1,p2,p3);
					}
					//different
					if(aboutWhatName!=null&&!aboutWhatName.isEmpty()){
						Predicate p1 = cb.like(nameEx, aboutWhatName);
						all = cb.and(p1);
					}
					//subQuery exists
					if(isNoPrcess!=null){
						Subquery<LeaveWords> subquery = cb.createQuery().subquery(LeaveWords.class);
						Root<LeaveWords> subRoot = subquery.from(LeaveWords.class);
						Path<Word> WordSubPath = subRoot.get("leaveFor");
						Expression<Boolean> hasReadEx = subRoot.get("hasRead");
						Predicate p1 = cb.equal(hasReadEx, !isNoPrcess);
						Expression<Long> subWordIdEx = WordSubPath.get("wId");
						Predicate p2 = cb.equal(idEx, subWordIdEx);
						all = cb.and(cb.exists(subquery.select(subRoot).where(cb.and(p1,p2))));
					}
				
				
				return all;
			}
			
		};
		Page<Word> pageResult = this.dao.findAll(specification, page);
		//initialize
//		pageResult.getContent();
//		for(Words w:pageResult.getContent()){
//			w.getwKind().getkName();
//			w.getwKind().getAboutwhat().getName();
//		}
		return pageResult;
	}
	
	
	
    //not finished  kind and aboutWhat
	public Word update(Word word, InputStream srcWord, InputStream srcIntor)
			throws ServiceException {
		if(srcWord==null){
			elastic.update(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), new HashMap<>(), word.getId(), x->{
				Map<String, String> map = new HashMap<>();
				map.put("kindName", word.getKind().getName());
				map.put("aboutWhatName", word.getKind().getAboutwhat().getName());
				map.put("author", word.getAuthor());
				map.put("title", word.getTitle());
				map.put("hasNoProcessLw", word.isHasNoProcessLw()+"");
				map.put("canshow", word.isCanShow()+"");
			});
		}else{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(srcWord));
			String content = bufferedReader.lines().reduce("", (x1,x2)->{return x1+x2;});
			elastic.update(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), new HashMap<>(), word.getId(), x->{
				Map<String, String> map = new HashMap<>();
				map.put("kindName", word.getKind().getName());
				map.put("aboutWhatName", word.getKind().getAboutwhat().getName());
				map.put("author", word.getAuthor());
				map.put("title", word.getTitle());
				map.put("content", content);
				map.put("hasNoProcessLw", word.isHasNoProcessLw()+"");
				map.put("canshow", word.isCanShow()+"");
			});
		}		
		dao.save(word);
		return word;
	}
	//need fixed
	public Page<Word> findItByReadTimes(final String aboutWhatName, int size) {
		Pageable pageable = new PageRequest(0, size);
		Specification<Word> specification = new Specification<Word>() {
			
			public Predicate toPredicate(Root<Word> root, CriteriaQuery<?> query,
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

	
	//not finished
	public Word getItbyId(String id) {
		//from dataSource
		Word data =  dao.findOne(id);
		elastic.get(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), id, x->{
			data.getAuthor();
			Map<String, Object> map = x.getSourceAsMap();
			String title = map.get("title").toString();
			String author = map.get("author").toString();
			String createTimeStr = map.get("create_time").toString();
			String content = map.get("content").toString();
			data.setAuthor(author);
			data.setCreateTime(new Date(Long.parseLong(createTimeStr)));
			data.setTitle(title);
			data.setContent(content);
		});
		return data;
	}
	
	
	private void checkWord(Word word){
		if(word.getTitle()==null){
			throw new IllegalArgumentException("word title must not be null!");
		}
		if(word.getAuthor()==null){
			throw new IllegalArgumentException("word author must not be null!");
		}
		if(word.getCreateTime()==null){
			throw new IllegalArgumentException("word createTime must not be null!");
		}
	}
	
	//finished ?
	public Word saveit(Word word, InputStream srcWord, InputStream srcIntor) throws ServiceException {
		checkWord(word);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(srcWord));
		String content = bufferedReader.lines().reduce("", (x1,x2)->{return x1+x2;});
		elastic.presist(elasticUitl.getProp().getEsIndex(), elasticUitl.getProp().getEsType(), new HashMap<>(), mapper->{
			mapper.put("kindName", word.getKind().getName());
			mapper.put("aboutWhatName", word.getKind().getAboutwhat().getName());
			mapper.put("author", word.getAuthor());
			mapper.put("title", word.getTitle());
			mapper.put("content", content);
			mapper.put("hasNoProcessLw", word.isHasNoProcessLw()+"");
			mapper.put("canshow", word.isCanShow()+"");
			return mapper;
		}, then->{
			String id = then.get("id").toString();
			word.setId(id);
			return word;
		});
		String aboutWhatName = word.getKind().getAboutwhat().getName();
		String kName = word.getKind().getName();
		Kind kind = kDao.getItBy2Name(kName, aboutWhatName);
		if(kind==null){
			AboutWhat aboutWhat = abDao.getItByName(aboutWhatName);
			if(aboutWhat==null){
				aboutWhat = new AboutWhat();
				aboutWhat.setName(aboutWhatName);
				aboutWhat = abDao.save(aboutWhat);
			}
			kind = new Kind();
			kind.setName(kName);
			kind.setAboutwhat(aboutWhat);
			kind = kDao.save(kind);
		}
		word.setKind(kind);
		this.dao.save(word);
		
		return word;
	}

	// tools
	// mapper
		private  Function<SearchResponse, Map<String, Object>> resultMapper = x1->{
			List<Word> list = new ArrayList<>();
			x1.getHits().forEach(x->{
				Word word = new Word();
				word.setId(x.getId());
				
				String title = x.getFields().get("title").getValues().get(0).toString();
				String author = x.getFields().get("author").getValues().get(0).toString();
				String create_time = x.getFields().get("create_time").getValues().get(0).toString();
				String canshowStr   = x.getFields().get("canshow").getValues().get(0).toString();
				String hasNoProcessStr = x.getFields().get("hasNoProcess").getValues().get(0).toString();
				word.setTitle(title);
				word.setAuthor(author);
				word.setCreateTime(new Date(Long.parseLong(create_time)));
				word.setCanShow(Boolean.parseBoolean(canshowStr));
				word.setHasNoProcessLw(Boolean.parseBoolean(hasNoProcessStr));
				list.add(word);
			});
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", list);
			map.put("total", x1.getHits().getTotalHits());
			return map;
		};
		//
		private Page<Word>  map2page(Map<String, Object> map){
			Page<Word> result = new Page<Word>() {
				
				Pageable pageable = (Pageable)map.get("pageable");
				@SuppressWarnings("unchecked")
				List<Word> content = (List<Word>)map.get("content");
				
				long total = (long)map.get("total");
				@Override
				public Iterator<Word> iterator() {
					
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
				public List<Word> getContent() {
					return content;
				}
				
				@Override
				public <S> Page<S> map(Converter<? super Word, ? extends S> converter) {
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
