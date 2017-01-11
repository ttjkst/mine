package com.ttjkst.fileSystem;

import java.io.ByteArrayInputStream;
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

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class SimpleFileSystem {
		private String esUrl = "";
		private String path = "";
		private TransportClient client = null;
		private InetAddress address = null;
		private static String separator = "/";
		public void init(){
			String urls[] = esUrl.split(":");
			
			try {
				address = InetAddress.getByName(urls[0]);
				client = extracted()
				        .addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(urls[1])));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		private PreBuiltTransportClient extracted() {
			return new PreBuiltTransportClient(Settings.EMPTY);
		}
		public void persist(File file,String id){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				this.persist(fileInputStream, id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		public void persist(FileInputStream fileInputStream,String id){
			IndexRequest indexRequest = new IndexRequest();
			Map<String, String> source = new HashMap<>();
			
			source.put("id",id);
			source.put("content", file2String(fileInputStream,id,path));
			indexRequest.source(source);
			client.index(indexRequest);
			
		}
		
		private static String file2String(FileInputStream fileInputStream,String id,String path){
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
