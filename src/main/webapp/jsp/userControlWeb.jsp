<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>用户管理</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/loginBar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/otherhead.js"></script>
		<script type="text/javascript">
		$.ajaxSetup ({
	        cache: false //关闭AJAX缓存
	    });
		var webRoot="${pageContext.request.contextPath}/";
		var webRootNotS="${pageContext.request.contextPath}";
		var webName_global = "首页";
		var webUser="others";
		var currentWebBartool = null;
		var hasProcess = true;
		var pageSize = 8;
		var searchName="";
		function bingPAndN(who,text,how,selecter){
			
			$(document).off("click",selecter);
			$(document).on("click",selecter,function(){	
				if(how=="pre"){
					prePage("words",text);
				}else{
					nextPage("words",text);
				}	
			
			})
			
		
	}
		
		function bartool(totalPages){
			this.pageLength = 5;
			this.beginPageNo = 1;
			this.currentPageNo = 1;
			this.totalPagesNo = totalPages;
			this.endPagesNo = 5-1;

			this.hasNextPage = function (){
				if(this.currentPageNo==this.totalPagesNo){
					return false;
				}else{
					return true;
				}
			}
			
			this.setPageLength = function(len){
				this.pageLength = len;
				if(len>this.totalPagesNo){
					this.endPagesNo=this.totalPagesNo;
				}else{
				this.endPagesNo = this.beginPageNo+this.pageLength-1;
				}
			}
			
			this.hasPrePage = function(){
				
				if(this.currentPageNo == 1){
					return false;
				}else{
					return true;
				}
			}
			
			this.nextPage = function(){
				if(this.hasNextPage()!=false){
					if(this.currentPageNo==this.endPagesNo){
						this.beginPageNo=this.endPagesNo+1;
						if(this.endPagesNo+this.pageLength-1>=this.totalPagesNo){
							this.endPagesNo=this.totalPagesNo;
						}else if(this.endPagesNo+this.pageLength-1<this.totalPagesNo){
							this.endPagesNo=this.beginPageNo+this.pageLength-1;
						}
					}else{
						
					}
					this.currentPageNo++;
				}
			}
			
			this.prePage = function(){
				if(this.hasPrePage()!= false){
					if(this.currentPageNo==this.beginPageNo){
					this.endPagesNo=this.beginPageNo-1;
						if(this.beginPageNo-this.pageLength<1){
							this.beginPageNo=this.beginPageNo-this.pageLength;
						}else{
							this.beginPageNo=1;
						}
					}
					this.currentPageNo--;
				}
			}
			
			this.rePostionall = function(totalPage_o,currentPage_o){
				this.totalPagesNo = totalPage_o;
				this.currentPageNo = currentPage_o;
			}
			
			this.rePostion = function(totalPage_o){
				this.totalPagesNo = totalPage_o;
			}
			
			this.initRander= function(){
					$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
					$("#Next").show();
					$("#Previous").show();
			}
			
			this.render = function(){
				if(this.beginPageNo>1){
					$("#Next").parent().before("<li class='disabled'><a>...</a></li>")
					
				}
				
				if(this.currentPageNo==1){
					$("#Previous").hide();
				}else{
					$("#Previous").show();
				}
					
				if(this.currentPageNo==this.totalPagesNo){
					$("#Next").hide();
					
				}else{
					$("#Next").show();
				}
				for(var i = this.beginPageNo;i<=this.endPagesNo;i++){
					$("#Next").parent().before("<li><a>"+i+"</a></li>");
				}
				if(this.totalPagesNo>this.endPagesNo){
					$("#Next").parent().before("<li class='disabled'><a>...</a></li>");
				}
				$("#Next").parent().prevAll(":contains("+this.currentPageNo+")").addClass(" active");
				if(this.currentPageNo == 1){
					$("#Previous").hide();
				}
			}
			
			this.destoryRender = function(){
				$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
			}
		}
		function bingRepostion(key1,who){
			var args={};
			$(document).off("click",".pagination > li");
			$(document).on("click",".pagination > li",function(){	
				var key=$(this).find("a").text();
				if(jQuery.isNumeric(key)){
					if(hasProcess==null){
						args={"who":"user","doWhat":"click","pageNo":parseInt(key),"pagSize":pageSize,"searchName":searchName};
					}else{
						args={"who":"user","doWhat":"click","pageNo":parseInt(key),"pagSize":pageSize,"hasProcess":hasProcess,"searchName":searchName};
					}
					$.get(webRoot+"getPageWords.action",args,function(data){
						$("#resultTable").empty();
						for(var i =0; i<data.length;i++){
							renderResult(who, data[i]);
						}
						$.get(webRoot+"getTotalPagesNo.action",function(data){
							console.log("total"+data);
							currentWebBartool.rePostionall(data,key);
							currentWebBartool.destoryRender();
							currentWebBartool.render();
							rendertableTitle(who);
						})
					})
				}
				
			return false;
			})
		}
		/*function renderPagetools(args){
			$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
			if(args=="null"){
			//	$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
				$("#Next").show();
				$("#Previous").show();
			}else{
					$("#Next").show();
					$("#Previous").show();
				//	$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
					
					$.get(webRoot+"brtool.do",args,function(data){
						
								if(data.beginPageNo>1){
									$("#Next").parent().before("<li class='disabled'><a>...</a></li>")
									
								}
								
								for(var i=data.beginPageNo;i<=data.endPageNo;i++){
									$("#Next").parent().before("<li><a>"+i+"</a></li>");
								}
								
								if(data.totalPages>data.endPageNo){
									$("#Next").parent().before("<li class='disabled'><a>...</a></li>");
								}
								
								if(data.totalPages>data.endPageNo){
									$("#Next").parent().before("<li ><a>"+data.totalPages+"</a></li>");
								}
								$("#Next").parent().prevAll(":contains("+data.currentPageNo+")").addClass(" active")
								if(data.currentPageNo==data.totalPages){
									$("#Next").hide();	
								}
								if(data.currentPageNo==1){
									$("#Previous").hide();
								}
				})
			}
		}
		*/
		
		function nextPage(who,text){
			var args={};
			if(who=="words"){
				if(hasProcess==null){
					args={"who":"user","doWhat":"next","pagSize":pageSize,"searchName":searchName};
				}else{
					args={"who":"user","doWhat":"next","pagSize":pageSize,"hasProcess":hasProcess,"searchName":searchName};
				}
			$.get(webRoot+"getPageWords.action",args,function(data){
				if(data!=null){	
					$("#resultTable").empty();
					for(var i=0;i<data.length;i++){
						renderResult("words", data[i]);
					}
					$.get(webRoot+"getTotalPagesNo.action",function(data){						
						currentWebBartool.rePostion(data);
						currentWebBartool.destoryRender();
						currentWebBartool.nextPage();
						currentWebBartool.render();
						rendertableTitle("words");
					})
				}else{
				}
			})
			}
			return false;
		}
		function prePage(who,text){
			var args={};
				if(who=="words"){
					if(hasProcess==null){
						args={"who":"user","doWhat":"pre","pagSize":pageSize,"searchName":searchName};
					}else{
						args={"who":"user","doWhat":"pre","pagSize":pageSize,"hasProcess":hasProcess,"searchName":searchName};
					}
			$.get(webRoot+"getPageWords.action",args,function(data){
				if(data!=null){	
					$("#resultTable").empty();
					for(var i=0;i<data.length;i++){
						renderResult("words", data[i]);
					}
					$.get(webRoot+"getTotalPagesNo.action",function(data){						
						currentWebBartool.rePostion(data);
						currentWebBartool.destoryRender();
						currentWebBartool.prePage();
						currentWebBartool.render();
						rendertableTitle("words");
					})
				}else{
				}
			})
		}
			return false;
		}
		function rendertableTitle(who){
			if(who=="words")
			$("#resultTable").prepend("<tr><th>序列号</th><th>标题</th><th>作者</th><th>上线时间</th><th>一级分类</th><th>二级分类</th><th>评论数</th><th>未阅评论数</th><th>公开</th><th class='text-center'>管理</th></tr>");
			else if(who=="leaveWords")
				$("#resultTable").prepend("<tr><th>序列号</th><th>评论人</th><th>QQ号</th><th>邮箱</th><th>对什么文章评论的</th><th>是否阅读</th><th>##</th></tr>");	
		}
		function renderResult(who,data){
			var leaveWordsNum;
			var NohasRead;
			var id=data.wId;
			if(who=="words"){
				var tempTime = new Date(data.wTimeOfInData);
				$.get(webRoot+"getHasReadNum.action",{"wordId":id},function(e1){
					if(data!=null){
						NohasRead = e1;
							$.get(webRoot+"getReadNum.action",{"wordId":id},function(e2){
								leaveWordsNum = e2;
								$("#resultTable").append("<tr><td>"+data.wId+"</td><td>"+data.wTitle+"</td><td>"+data.wAuthor+"</td><td>"+tempTime.toLocaleDateString()+"</td><td>"+data.wKind.aboutwhat.name+"</td><td>"+data.wKind.kName+"</td><td>"+leaveWordsNum+"</td><td>"+NohasRead+"</td><td>"+data.canShow+"</td><td><botton class='btn btn-info'>更多</botton><botton class='btn btn-danger'>删除</botton></td></tr>");
							})
						
					}
					
				})
			}else if(who=="leaveWords"){
				$("#resultTable").append("<tr><td>"+data.id+"</td><td>"+data.whose+"</td><td>"+data.qq+"</td><td>"+data.email+"</td><td>"+data.leaveFor+"</td><td>"+data.hasRead+"</td><td><button>删除</button><button>查看文章</button></td></tr>");
			}
		}
		
		
		/*$("#search").click(function(){
				var key=$.trim($("#searchText").val());
				if(key!=""){
					var args = {"doWhat":"null"};
					var data = {"pageNo":1,"pageSize":6,"doWhat":"getSearch","key":key};
					var url  = "search.do";
					args_from_sh=args;
					data_from_sh=data;
					url_from_sh=url;
					renderPages(url,data,args);
				}else{
					alert("搜索栏为空！");
				}
			})
			$(document).on("click",".pagination > li",function(){	
				var key=$(this).find("a").text();
				if(jQuery.isNumeric(key)){
					var args = {"doWhat":"click"};
					var data = {"doWhat":"click","clickNo":key};
					var url  = "getPageBeans.do";
					args_from_sh=args;
					data_from_sh=data;
					url_from_sh=url;
					renderPages(url,data,args);
				}
				
			})
	})*/
		
		$(document).ready(function(){
			$.get("${pageContext.request.contextPath}/"+"jsp/littleBar/head.jsp",function(data){
				$("body").prepend(data);
				loginInit();
				
				$("#title_bar").empty();
				var navdata={"navs":[{"navName":"首页","goWhere":"#","bomId":"main"},
				                     {"navName":"关于本站与我","goWhere":"#","bomId":"aboutMe"},
				                     {"navName":"关于生活","goWhere":"#","bomId":"aboutLeft"},
				                     {"navName":"关于编程","goWhere":"#","bomId":"aboutCode"}],"index":"#title_bar"}
								addNav(navdata);
								$(document).on("click","#main",function(){
									$.get(webRoot+"setWebName.action",{"where":"首页"},function(data){
										
										window.open(webRoot+"toMain.action","_self");
										
									})
									
								})
								$(document).on("click","#aboutMe",function(){
									
									$.get(webRoot+"setWebName.action",{"where":"关于本站与我"},function(data){
										window.open(webRoot+"toMain.action","_self");
											
									})
									
								})
								$(document).on("click","#aboutLeft",function(){
									
									$.get(webRoot+"setWebName.action",{"where":"关于生活"},function(data){
										window.open(webRoot+"toMain.action","_self");
											
									})
									
								})
								$(document).on("click","#aboutCode",function(){
									
									$.get(webRoot+"setWebName.action",{"where":"关于编程"},function(data){
										window.open(webRoot+"toMain.action","_self");
									})
									
								})
				$("#title_bar").append("<li role='presentation' class='nav-pills'><a href='#' ><button class='btn btn-info'>管理页面</button></a></li>")
				
			},"html");
			
			
			$.get(webRoot+"search.action",{"pageSize":pageSize,"pageNo":1,"hasProcess":hasProcess},function(data){
				if(data!=null){					
					for(var i=0;i<data.length;i++){
						renderResult("words", data[i]);
					}
					$.get(webRoot+"getMaxPageNum.action",function(data){						
						currentWebBartool = new bartool(data);
						currentWebBartool.setPageLength(5);
						currentWebBartool.initRander();
						currentWebBartool.render();
						rendertableTitle("words");
						bingRepostion(null, "words");
						bingPAndN("words",null,"pre","#Previous");
						bingPAndN("words",null,"next","#Next");
					})
				}else{
				}
			})
		})
		$(function(){
			//还没写
			
			$("#showAll").click(function(){
				$("#resultTable").empty();
				hasProcess = null;
				searchName="";
				$.get(webRoot+"search.action",{"pageSize":pageSize,"pageNo":1},function(data){
				if(data!=null){					
					for(var i=0;i<data.length;i++){
						renderResult("words", data[i]);
					}
					$.get(webRoot+"getTotalPagesNo.action",function(data){						
						currentWebBartool = new bartool(data);
						currentWebBartool.setPageLength(5);
						currentWebBartool.initRander();
						currentWebBartool.render();
						rendertableTitle("words");
						bingRepostion(null, "words");
						bingPAndN("words",null,"pre","#Previous");
						bingPAndN("words",null,"next","#Next");
					})
				}else{
				}
			})
		})
		$("#showNoProcess").click(function(){
			hasProcess=true;
			searchName="";
			$("#resultTable").empty();
			$.get(webRoot+"search.action",{"pageSize":pageSize,"pageNo":1,"hasProcess":hasProcess},function(data){
				if(data!=null){					
					for(var i=0;i<data.length;i++){
						renderResult("words", data[i]);
					}
					$.get(webRoot+"getTotalPagesNo.action",function(data){						
						currentWebBartool = new bartool(data);
						currentWebBartool.setPageLength(5);
						currentWebBartool.initRander();
						currentWebBartool.render();
						rendertableTitle("words");
						bingRepostion(null, "words");
						bingPAndN("words",null,"pre","#Previous");
						bingPAndN("words",null,"next","#Next");
					})
				}else{
				}
			})
		})
			
			
			$(document).on("click","#resultTable >tbody > tr >td > botton",function(){
				 var value = $(this).text();
					 var id=$(this).parent().prevAll().eq(8).text();
				 if(value=="更多"){
					 window.open(webRoot+"readMoreAndControl.action?id="+id);
				 }else if(value=="删除"){
					 var thisDoM = $(this).parent().parent();
					 $.post(webRoot+"delete.action",{"id":id},function(){
					 thisDoM.fadeOut('normal',function(){
							$(this).remove();
						})
					 thisDoM.nextAll().fadeOut("slow",function(){
							$(this).fadeIn('slow');
						})
							 
					 });
				 }
			})
			$("#searchBtn").click(function(data){
				var text=$.trim($("#searchText").val());
				var args ={};
				if(text==""){
					
					alert("搜索栏不能为空！");
					return false;
				}else{
					$("#resultTable").empty();
					searchName = text;
					if(hasProcess==null){
						args={"pageSize":pageSize,"pageNo":1,"searchName":searchName};
					}else{
						args={"pageSize":pageSize,"pageNo":1,"hasProcess":hasProcess,"searchName":searchName};
					}
					$.get(webRoot+"search.action",args,function(data){
						for(var i=0;i<data.length;i++){
							renderResult("words", data[i]);
						}
						$.get(webRoot+"getTotalPagesNo.action",function(data){						
							currentWebBartool = new bartool(data);
							currentWebBartool.setPageLength(5);
							currentWebBartool.initRander();
							currentWebBartool.render();
							rendertableTitle("words");
							bingRepostion(text, "words");
							bingPAndN("words",text,"pre","#Previous");
							bingPAndN("words",text,"next","#Next");
						})
					})
				}
				
			})
			
			
			
			
				
			$("#dropdown_title").find("li").find("a").click(function(){
				var value=$(this).text();
				$("#dropdown_title").parent().find("button").text(value);
				$("#dropdown_title").parent().find("button").append("<span class='caret'></span>");
			})
		})
		
	</script>
	</head>
	<body class="base_background">
	<br/>
	<div class="container-fluid ">
	    <div class="row">
		    <div class="col-md-8">
				<div class="panel panel-default">
					  <div class="panel-heading">
					    <h3 class="panel-title">工具栏</h3>
					  </div>
					  <div class="panel-body" id="result">
					    <table class="table" id="resultTable">
					    	
					    </table>
				<div class="text-center" id="pages">
					<nav >
					  <ul class="pagination">
					    <li id="parentPrevious">
					      <a href="#" aria-label="Previous" id="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					   
					    <li id="parentNext">
					      <a href="#" aria-label="Next" id="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					  </ul>
				</nav>
				</div>
					  </div>
				</div>
		    </div>
		    <div class="col-md-4">
		    	<div class="panel panel-default">
					  <div class="panel-heading">
					    	管理对象：
					   		<div class="btn-group btn-group-xs">
							  <button type="button" class="btn btn-default  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="keyOfswitch">
							    Action<span class="caret"></span>
							  </button>
							  <ul class="dropdown-menu" id="dropdown_title">
							    <li><a  id="wordsControl">文章管理</a></li>
							    <li><a  id="leaveWordsControl">评论管理</a></li>
							  </ul>
							</div>
					  </div>
					  <div class="panel-body container">
					  	<div class="form-group form-inline">
					  		<input type="text" placeholder="写下你要搜索的东西" class="form-control" id="searchText"/>
					  		<button class="btn btn-info" id="searchBtn">搜索</button>
					  	</div>
					  	
					  	<button type="button" class="btn btn-info" id="showAll" >管理所有的文章</button>
					  	<br/>
					  	<br/>
					  	<button type="button" class="btn btn-info" id="showNoProcess" >管理所有的文章（评价没有处理的）</button>
					  
					  </div>
				</div>
		    </div>
 		</div>
      </div>
	</body>
</html>