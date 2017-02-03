package com.ttjkst.elastic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ttjkst.elastic.exception.EsRuntimeException;
@Component
public class ElasticDao {
		@Autowired
		private ElasticUitl elasticUitl;

		public <T> T presist(String index,String type,Map<String, Object> ctx,Function<Map<String, String> , Map<String, String>> mapper,Function<Map<String, Object>, T> then){
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
				indexResponse = elasticUitl.getTransportClient().prepareIndex(index,
						type).setSource(source).get();
				ctx.put("id", indexResponse.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
			return then.apply(ctx);
		}
		
		
		public void delete(String index,String type,String id){
			try {
				elasticUitl.getTransportClient().prepareDelete(index,type, id).get();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
		}
		//good
		public void update(String index,String type,Map<String, Object> ctx,String id,Consumer<UpdateRequest> mapper){
			if(ctx==null){
				throw new IllegalArgumentException("ctx must not be null !");
			}
			try {
				UpdateRequest request = new UpdateRequest(index,type,id);
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
		
		public void get(String index,String type,String id,Consumer<GetResponse>  mapper){
			  GetResponse getResponse = elasticUitl.getTransportClient().prepareGet(index, type, id).get();
			 mapper.accept(getResponse);
		}
		
		
		
		
		public Map<String, Object> search(String index,String type,Map<String,Object> ctx,Consumer<SearchRequestBuilder> builder,Function<SearchResponse, Map<String, Object>> resultMapper){
			if(ctx==null){
				throw new IllegalArgumentException("ctx must not be null !");
			}
			try {
				SearchRequestBuilder base = elasticUitl.getTransportClient().prepareSearch(index).setTypes(type);
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
		//not good need update
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
				setting.put("type","string");
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
	
}
