package com.ttjkst.elastic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ttjkst.elastic.exception.EsRuntimeException;
@Component
public class ElasticAction {
		private static String separator = "/";
		@Autowired
		private ElasticUitl elasticUitl;

		public void persist(File file,String id){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				this.persist(fileInputStream, id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		public void persist(InputStream fileInputStream,String id){
			try {
				if(elasticUitl.getProp().isEsIgnoreIndex()){
					createIndex();
				}
				Map<String, String> source = new HashMap<>();
				
				source.put("id",id);
				source.put("content", stream2Str(fileInputStream,id,elasticUitl.getProp().getPath()));
				elasticUitl.getTransportClient().prepareIndex(elasticUitl.getProp().getEsIndex(),
						elasticUitl.getProp().getEsType()).setSource(source).get();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				elasticUitl.close();
			}
		}
		
		
		public void delete(String id){
			try {
				File delFile = new File(elasticUitl.getProp().getPath()+separator+id);
				
				DeleteByQueryAction.INSTANCE.
					newRequestBuilder(elasticUitl.getTransportClient()).
					filter(QueryBuilders.matchQuery("id", id)).
					source(elasticUitl.getProp().getEsIndex()).get();
				
				if(delFile.exists()){
					delFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
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
				source.put("content", stream2Str(inputStream, id, elasticUitl.getProp().getPath()));
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
		public List<String> search(String key){
			try {
				SearchResponse response = elasticUitl.getTransportClient().prepareSearch(elasticUitl.getProp().getEsIndex()).
						setTypes(elasticUitl.getProp().getEsType()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).
						setQuery(QueryBuilders.termQuery("content", key)).addStoredField("id").get();
				SearchHits hits = response.getHits();
				List<String> result = new ArrayList<>();
				hits.forEach(x->{
					result.add( x.getFields().get("id").getValues().get(0).toString());
				});
				return result;
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
				Map<String, String> idSettings = new HashMap<>();
				idSettings.put("type", "string");
				idSettings.put("store", "true");
				source.put("id", idSettings);
				Map<String, Object> prop = new HashMap<>();
				prop.put("properties", source);
				createIndexRequest.mapping(elasticUitl.getProp().getEsType(), prop);
				CreateIndexResponse response = elasticUitl.getTransportClient().admin().indices().create(createIndexRequest).actionGet();
				if(!response.isAcknowledged()){
					throw  new EsRuntimeException("create "+elasticUitl.getProp().getEsIndex()+" error");
				}
			}
		}
		
		private static String stream2Str(InputStream fileInputStream,String id,String path){
			ByteArrayOutputStream arrayOutputStream = null;
			File destDir = new File(path);
			File dest = null;
			FileOutputStream dOutputStream = null;
			if(destDir.exists()){
				if(!destDir.isDirectory()){
					throw new RuntimeException("destDir is not a directory!");
				}
			}
			dest = new File(path+separator+id);
			if(dest.exists()){
				dest.delete();
			}else{
				try {
					dest.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				dOutputStream = new FileOutputStream(dest);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			int len =-1;
			byte[] b = new byte[1024];
			try {
				arrayOutputStream = new ByteArrayOutputStream(fileInputStream.available());
			    while((len=fileInputStream.read(b))!=-1){
			    	arrayOutputStream.write(b, 0, len);
			    	dOutputStream.write(b, 0, len);
			    }
			    arrayOutputStream.flush();
			    dOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fileInputStream!=null){
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(dOutputStream!=null){
					try {
						dOutputStream.close();
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
