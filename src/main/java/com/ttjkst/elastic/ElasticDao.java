package com.ttjkst.elastic;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ttjkst.ElasticMapperHolder;
import com.ttjkst.elastic.exception.EsRuntimeException;
@Component
public class ElasticDao {
		@Autowired
		private ElasticUitl elasticUitl;

		public <T> void presist(String index,String type,Consumer<IndexRequestBuilder> mapper,Consumer<IndexResponse> then){
			if(elasticUitl.getProp().isEsIgnoreIndex()){
				createIndex(index,type);
			}
			IndexRequestBuilder prepareIndex= null;
			
			try {
				prepareIndex = elasticUitl.getTransportClient().prepareIndex(index,
						type);
				 mapper.accept(prepareIndex);
				 IndexResponse indexResponse = prepareIndex.get();
				 then.accept(indexResponse); 
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
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
		
		
		
		
		public Map<String, Object> search(String index,String type,Consumer<SearchRequestBuilder> builder,Function<SearchResponse, Map<String, Object>> resultMapper){

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
		
		private boolean isIndexExists(String index,String type){
			IndicesExistsRequest request = new IndicesExistsRequest(index);
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
		private void createIndex(String index,String type){
			if(!isIndexExists(index,type)){
				CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);			
				Map<String, Object> prop = ElasticMapperHolder.getMapperInfo(index, type);
				createIndexRequest.mapping(type, prop);
				CreateIndexResponse response = elasticUitl.getTransportClient().admin().indices().create(createIndexRequest).actionGet();
				if(!response.isAcknowledged()){
					throw  new EsRuntimeException("create "+index+" error");
				}
			}
		}
	
}
