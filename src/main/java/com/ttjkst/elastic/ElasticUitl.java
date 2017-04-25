package com.ttjkst.elastic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.ttjkst.elastic.exception.EsRuntimeException;

public class ElasticUitl {
	
	private static ThreadLocal<TransportClient> threadloacl = new ThreadLocal<>();
	private static TransportClient client = null;
	private ElasticProperties properties;
//	public TransportClient getTransportClient(){
//		TransportClient client = null;
//		if(threadloacl.get()==null){
//			String urls[] = properties.getEsUrl().split(":");
//			try {
//				InetAddress address = InetAddress.getByName(urls[0]);
//				client = extracted()
//				        .addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(urls[1])));
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			}
//			threadloacl.set(client);
//		}
//		return threadloacl.get();
//	}
	public TransportClient getTransportClient(){
		if(client==null){
			String urls[] = properties.getEsUrl().split(":");
			try {
				InetAddress address = InetAddress.getByName(urls[0]);
				client = extracted()
				        .addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(urls[1])));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
	public ElasticUitl(ElasticProperties properties) {
		super();
		this.properties = properties;
	}


	private PreBuiltTransportClient extracted() {
		Settings settings = Settings.builder().
				put("cluster.name", properties.getEsClusterName()).put("client.transport.sniff", false).
				put("client.transport.ignore_cluster_name",true).build();		      
		return new PreBuiltTransportClient(settings);
	}
	public void close(){
		if(threadloacl.get()==null){
			//throw new EsRuntimeException("closed elasticSearch error");
		}else{
			//threadloacl.get().close();
		}
	}
	public void destory(){
		System.out.println("close.........");
		if(threadloacl.get()==null){
			throw new EsRuntimeException("closed elasticSearch error");
		}else{
			threadloacl.get().close();
		}
	}
	
	public ElasticProperties getProp(){
		return properties;
	}
}
