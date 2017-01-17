package com.ttjkst.fileSystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ttjkst.fileSystem.exception.EsRuntimeException;
@Component
@EnableConfigurationProperties(FileSytemProperties.class)
public class SimpleFileSystem {
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
		public void persist(FileInputStream fileInputStream,String id){
			init();
			if(properties.isEsIgnoreIndex()){
				createIndex();
			}
			Map<String, String> source = new HashMap<>();
			
			source.put("id",id);
			source.put("content", file2Str(fileInputStream,id,properties.getPath()));
			System.out.println(properties.toString());
			client.prepareIndex(properties.getEsIndex(),
					properties.getEsType()).setSource(source).get();
			
			client.close();
			
			
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
				CreateIndexResponse response = client.admin().indices().create(createIndexRequest).actionGet();
				if(!response.isAcknowledged()){
					throw  new EsRuntimeException("create "+properties.getEsIndex()+" error");
				}
			}
		}
		
		private static String file2Str(FileInputStream fileInputStream,String id,String path){
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
