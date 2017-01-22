package com.ttjkst.fileSystem;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ttjkst.fileSystem.exception.EsRuntimeException;
@Component
@EnableConfigurationProperties(FileSytemProperties.class)
public class SimpleFileSystem implements Closeable {
		private FileSytemProperties properties;
		private TransportClient client = null;
		private InetAddress address = null;
		private static String separator = "/";
		
		
		public SimpleFileSystem(FileSytemProperties properties) {
			super();
			this.properties = properties;
		}

		private boolean inited = false;
		public void init(){
			if(inited){
				return;
			}
			String urls[] = properties.getEsUrl().split(":");
			try {
				address = InetAddress.getByName(urls[0]);
				client = extracted()
				        .addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(urls[1])));
				inited = true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		private PreBuiltTransportClient extracted() {
			Settings settings = Settings.builder().
					put("cluster.name", properties.getEsClusterName()).put("client.transport.sniff", false).
					put("client.transport.ignore_cluster_name",true).build();		      
			return new PreBuiltTransportClient(settings);
		}
		public void persist(File file,String id){
			init();
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				this.persist(fileInputStream, id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		public void persist(InputStream fileInputStream,String id){
			init();
			if(properties.isEsIgnoreIndex()){
				createIndex();
			}
			Map<String, String> source = new HashMap<>();
			
			source.put("id",id);
			source.put("content", stream2Str(fileInputStream,id,properties.getPath()));
			client.prepareIndex(properties.getEsIndex(),
					properties.getEsType()).setSource(source).get();
			
			
			
		}
		
		
		public long delete(String id){
			init();
			File delFile = new File(properties.getPath()+separator+id);
			
			BulkIndexByScrollResponse response = DeleteByQueryAction.
					INSTANCE.newRequestBuilder(client).filter(QueryBuilders.matchQuery("id", id)).source(properties.getEsIndex()).get();
			long delId = response.getDeleted();
			if(delFile.exists()){
				delFile.delete();
			}else{
				
			}
			return delId;
		}
		
		public void update(InputStream inputStream,String id){
			init();
			//先是获取文档中的id属性（非doc中的_id,这里的id是自己定义的id）
			SearchResponse response = client.prepareSearch(properties.getEsIndex()).
					setTypes(properties.getEsType()).setSearchType(SearchType.QUERY_THEN_FETCH).
					setPostFilter(QueryBuilders.matchQuery("id", id)).addStoredField("id").get();
			List<String> result = new ArrayList<>();
			response.getHits().forEach(x->{
				result.add( x.getId());
			});
			String docId = result.get(0);
			//然后通过id属性定位文档，对其进行更新，相应的本地存储也会更新
			UpdateRequest request = new UpdateRequest(properties.getEsIndex(),properties.getEsType(),docId);
			Map< String, String> source = new HashMap<>();
			source.put("id", id);
			source.put("content", stream2Str(inputStream, id, properties.getPath()));
			request.doc(source);
			try {
				client.update(request).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		public List<String> search(String key){
			if(!inited){
				init();
			}
			SearchResponse response = client.prepareSearch(properties.getEsIndex()).
					setTypes(properties.getEsType()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).
					setQuery(QueryBuilders.termQuery("content", key)).addStoredField("id").get();
			SearchHits hits = response.getHits();
			List<String> result = new ArrayList<>();
			hits.forEach(x->{
				result.add( x.getFields().get("id").getValues().get(0).toString());
			});
			return result;
		}
		
		private boolean isIndexExists(){
			IndicesExistsRequest request = new IndicesExistsRequest(properties.getEsIndex());
			IndicesExistsResponse response = null;
			try {
				response = client.admin().indices().exists(request).actionGet();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return response.isExists();
		}
		private void createIndex(){
			if(!isIndexExists()){
				CreateIndexRequest createIndexRequest = new CreateIndexRequest(properties.getEsIndex());
				Map<String, Object> source = new HashMap<>();
				Map<String, String> idSettings = new HashMap<>();
				idSettings.put("type", "string");
				idSettings.put("store", "true");
				source.put("id", idSettings);
				Map<String, Object> prop = new HashMap<>();
				prop.put("properties", source);
				createIndexRequest.mapping(properties.getEsType(), prop);
				CreateIndexResponse response = client.admin().indices().create(createIndexRequest).actionGet();
				if(!response.isAcknowledged()){
					throw  new EsRuntimeException("create "+properties.getEsIndex()+" error");
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
		@Override
		public void close(){
			this.client.close();
		}
		
}
