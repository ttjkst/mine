//package com.ttjkst.elastic;
//
//import java.util.Map;
//
//import org.elasticsearch.action.Action;
//import org.elasticsearch.action.ActionFuture;
//import org.elasticsearch.action.ActionListener;
//import org.elasticsearch.action.ActionRequest;
//import org.elasticsearch.action.ActionRequestBuilder;
//import org.elasticsearch.action.ActionResponse;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteRequestBuilder;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.explain.ExplainRequest;
//import org.elasticsearch.action.explain.ExplainRequestBuilder;
//import org.elasticsearch.action.explain.ExplainResponse;
//import org.elasticsearch.action.fieldstats.FieldStatsRequest;
//import org.elasticsearch.action.fieldstats.FieldStatsRequestBuilder;
//import org.elasticsearch.action.fieldstats.FieldStatsResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetRequestBuilder;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.get.MultiGetRequest;
//import org.elasticsearch.action.get.MultiGetRequestBuilder;
//import org.elasticsearch.action.get.MultiGetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexRequestBuilder;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.ClearScrollRequest;
//import org.elasticsearch.action.search.ClearScrollRequestBuilder;
//import org.elasticsearch.action.search.ClearScrollResponse;
//import org.elasticsearch.action.search.MultiSearchRequest;
//import org.elasticsearch.action.search.MultiSearchRequestBuilder;
//import org.elasticsearch.action.search.MultiSearchResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchScrollRequest;
//import org.elasticsearch.action.search.SearchScrollRequestBuilder;
//import org.elasticsearch.action.termvectors.MultiTermVectorsRequest;
//import org.elasticsearch.action.termvectors.MultiTermVectorsRequestBuilder;
//import org.elasticsearch.action.termvectors.MultiTermVectorsResponse;
//import org.elasticsearch.action.termvectors.TermVectorsRequest;
//import org.elasticsearch.action.termvectors.TermVectorsRequestBuilder;
//import org.elasticsearch.action.termvectors.TermVectorsResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateRequestBuilder;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.AdminClient;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.threadpool.ThreadPool;
//
//public class ClientWapper implements Client{
//
//	private Client realClient;
//	
//	public ClientWapper() {
//		super();
//		this.realClient = xxx.createClient();
//	}
//
//	//需要修补
//	@Override
//	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> void execute(
//			Action<Request, Response, RequestBuilder> arg0, Request arg1, ActionListener<Response> arg2) {
//		 realClient.execute(arg0,arg1,arg2);
//		
//	}
//	
//	//所有接口的方法都安装上面的方法进行修复
//
//	
//}
