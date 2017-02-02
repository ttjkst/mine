package com.ttjkst.elastic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ttjkst.bean.Word;
import com.ttjkst.elastic.exception.EsRuntimeException;
@Component
public class WordElasticDao {
		private static String separator = "/";
		@Autowired
		private ElasticUitl elasticUitl;

		public void persist(File file,String id){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		//test
		public Word presist(InputStream fileInputStream,Word word){
				this.presist(new HashMap<>(),
						//begin
						x->{
							x.put("content", stream2Str(fileInputStream));
							x.put("author", word.getAuthor());
							x.put("title", word.getTitle());
							x.put("create_time", word.getCreateTime().getTime()+"");
							return x;
						}
						,
						//after result 
						ctx->{
							word.setId(ctx.get("id").toString());
							return word;
						});
			
			return word;
		}
		public <T> T presist(Map<String, Object> ctx,Function<Map<String, String> , Map<String, String>> mapper,Function<Map<String, Object>, T> then){
			if(ctx==null){
				throw new IllegalArgumentException("ctx must not be null !");
			}
			if(elasticUitl.getProp().isEsIgnoreIndex()){
				createIndex();
			}
			Map<String, String> source = new HashMap<>();
			source = mapper.apply(source);
			IndexResponse indexResponse;
			try {
				indexResponse = elasticUitl.getTransportClient().prepareIndex(elasticUitl.getProp().getEsIndex(),
						elasticUitl.getProp().getEsType()).setSource(source).get();
				ctx.put("id", indexResponse.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
			return then.apply(ctx);
		}
		
		
		public void delete(String id){
			try {
				elasticUitl.getTransportClient().prepareDelete(elasticUitl.getProp().getEsIndex(),
						elasticUitl.getProp().getEsType(), id);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
		}
		//test
		public void update(InputStream inputStream,String id){
			try {
				//先是获取文档中的id属性（非doc中的_id,这里的id是自己定义的id）
				SearchResponse response = elasticUitl.getTransportClient().prepareSearch(elasticUitl.getProp().getEsIndex()).
						setTypes(elasticUitl.getProp().getEsType()).setSearchType(SearchType.QUERY_THEN_FETCH).
						setPostFilter(QueryBuilders.matchQuery("id", id)).addStoredField("id").get();
				List<String> result = new ArrayList<>();
				response.getHits().forEach(x->{
					result.add( x.getId());
				});
				String docId = result.get(0);
				//然后通过id属性定位文档，对其进行更新，相应的本地存储也会更新
				UpdateRequest request = new UpdateRequest(elasticUitl.getProp().getEsIndex(),elasticUitl.getProp().getEsType(),docId);
				Map< String, String> source = new HashMap<>();
				source.put("id", id);
				source.put("content", stream2Str(inputStream));
				request.doc(source);

				elasticUitl.getTransportClient().update(request).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
			
		}
		//good
		public void update(Map<String, Object> ctx,String id,Consumer<UpdateRequest> mapper){
			if(ctx==null){
				throw new IllegalArgumentException("ctx must not be null !");
			}
			try {
				UpdateRequest request = new UpdateRequest(elasticUitl.getProp().getEsIndex(),elasticUitl.getProp().getEsType(),id);
				mapper.accept(request);
				elasticUitl.getTransportClient().update(request).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
			
		}
		
		
		
		
		public Map<String, Object> search(Map<String,Object> ctx,Consumer<SearchRequestBuilder> builder,Function<SearchResponse, Map<String, Object>> resultMapper){
			if(ctx==null){
				throw new IllegalArgumentException("ctx must not be null !");
			}
			try {
				SearchRequestBuilder base = elasticUitl.getTransportClient().prepareSearch(elasticUitl.getProp().getEsIndex()).
						setTypes(elasticUitl.getProp().getEsType());
				builder.accept(base);
				return resultMapper.apply(base.get());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
			return null;
		}
		
		private boolean isIndexExists(){
			IndicesExistsRequest request = new IndicesExistsRequest(elasticUitl.getProp().getEsIndex());
			IndicesExistsResponse response = null;
			try {
				response = elasticUitl.getTransportClient().admin().indices().exists(request).actionGet();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return response.isExists();
		}
		private void createIndex(){
			if(!isIndexExists()){
				CreateIndexRequest createIndexRequest = new CreateIndexRequest(elasticUitl.getProp().getEsIndex());
				Map<String, Object> source = new HashMap<>();
				Map<String, String> setting = new HashMap<>();
				setting.put("type", "string");
				setting.put("store", "true");
				setting.put("index", "not_analyzed");
				source.put("author", setting);
				source.put("title", setting);
				setting.put("type","long");
				source.put("create_time", setting);			
				Map<String, Object> prop = new HashMap<>();
				prop.put("properties", source);
				createIndexRequest.mapping(elasticUitl.getProp().getEsType(), prop);
				CreateIndexResponse response = elasticUitl.getTransportClient().admin().indices().create(createIndexRequest).actionGet();
				if(!response.isAcknowledged()){
					throw  new EsRuntimeException("create "+elasticUitl.getProp().getEsIndex()+" error");
				}
			}
		}
		//不是很好的方法
		private static String stream2Str(InputStream inputStream){
			ByteArrayOutputStream arrayOutputStream = null;
			int len =-1;
			byte[] b = new byte[1024];
			try {
				arrayOutputStream = new ByteArrayOutputStream(inputStream.available());
			    while((len=inputStream.read(b))!=-1){
			    	arrayOutputStream.write(b, 0, len);
			    }
			    arrayOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(inputStream!=null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(arrayOutputStream!=null){
					try {
						arrayOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			return arrayOutputStream.toString();
		}
		
}
