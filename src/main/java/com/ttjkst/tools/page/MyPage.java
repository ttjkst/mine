package com.ttjkst.tools.page;

import java.util.ArrayList;
import java.util.List;


public class MyPage<T> {
	private long totalElementsNum;
	private int pageNo;
	private int pageSize;
	private List<T> context = new ArrayList<T>();
	public MyPage(long totalElementsNum, int pageNo, int pageSize,
			List<T> context) {
		super();
		if(pageNo<0){
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}
		this.totalElementsNum = totalElementsNum;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.context = context;
	}
	/**
	 * @return the totalElementsNum
	 */
	
	public boolean hasNextPage(){
		int endPageNo = (int) (this.totalElementsNum/this.pageSize +(this.totalElementsNum%this.pageSize==0?-1:0));
		if(endPageNo>this.pageNo){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasPrePage(){
		if(pageNo<=0){
			return false;
		}else{
			return true;
		}
	}
	public long getTotalElementsNum() {
		return totalElementsNum;
	}
	/**
	 * @return the context
	 */
	public List<T> getContext() {
		return context;
	}
	public List<T> getContent() {
		
		return context;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public PageRquest getNextPageRequest() {
		if(this.hasNextPage()){
			this.pageNo = pageNo+1;
			return new PageRquest(pageNo, pageSize);
		}
		return new PageRquest(pageNo, pageSize);
	}
	
	public PageRquest getPrePageRequest() {
		if(this.hasPrePage()){
			this.pageNo = this.pageNo-1;
			return new PageRquest(pageNo, pageSize);
		}
		return new PageRquest(pageNo, pageSize);
	}
	
	public Integer getPageNo() {
		
		return pageNo;
	}
	public boolean isLastPage() {
		int endPageNo = (int) (this.totalElementsNum/this.pageSize +(this.totalElementsNum%this.pageSize==0?-1:0));
		if(pageNo+1==endPageNo){
			return true;
		}
		return false;
	}
	public Integer getTotalPageNum(){
		System.out.println("allbeanNum:"+ this.totalElementsNum);
		System.out.println("sas:"+(int) (this.totalElementsNum/this.pageSize +(this.totalElementsNum%this.pageSize==0?0:1)));
		return (int) (this.totalElementsNum/this.pageSize +(this.totalElementsNum%this.pageSize==0?0:1));
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MyPage [pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
}
