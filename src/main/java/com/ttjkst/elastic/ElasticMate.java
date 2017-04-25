//package com.ttjkst.elastic;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class ElasticMate {
//	@Autowired
//	private ElasticProperties properties;
//	
//	private boolean init = false;
//	private boolean hasError = false;
//	private TransportClient client = null;
//	private boolean hasUsed = false;
//	public TransportClient init(){
//			String urls[] = properties.getEsUrl().split(":");
//			try {
//				InetAddress address = InetAddress.getByName(urls[0]);
//				client = extracted()
//				        .addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(urls[1])));
//			} catch (UnknownHostException e) {
//				hasError = true;
//				e.printStackTrace();
//			}
//		
//		return client;
//	}
//	public TransportClient get(){
//		hasUsed = true;
//		if(init){
//			return client;
//		}else{
//			return init();
//		}
//	}
//	public void close(){
//		hasUsed = false;
//	}
//	
//	private PreBuiltTransportClient extracted() {
//		Settings settings = Settings.builder().
//				put("cluster.name", properties.getEsClusterName()).put("client.transport.sniff", false).
//				put("client.transport.ignore_cluster_name",true).build();		      
//		return new PreBuiltTransportClient(settings);
//	}
//	
//}
