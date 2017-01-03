<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关于编程</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<link href="${pageContext.request.contextPath}/summernote_compile/summernote.css" rel="stylesheet">
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/summernote.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/lang/summernote-zh-CN.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/editWord.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/loginBar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/otherhead.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/msgTool.js"></script>
		
		<script type="text/javascript">
		var webRoot="${pageContext.request.contextPath}/";
		var currentWeb="${pageContext.request.contextPath}/";
		var webName_global="null";
		function loadButtom(){
			$.get(webRoot+"jsp/littleBar/buttom.jsp",function(data){
				$("body").append(data);
			})
		}
		
		$.ajaxSetup({
			error:function(jqXHR,textStatus,errorThrown){		
				if(jqXHR.status==400){
					$(document).find("#loginModal").modal('show');
					
				}
				}
			})
		$(document).ready(function(){
			loadButtom();
			
			msgBlockload("",null,false,false,'static');
			
			$.get("${pageContext.request.contextPath}/"+"jsp/littleBar/head.jsp",function(data){
				loginInit();
				$("body").prepend(data);
				loadingEditDATA()
				//有的东西没有写
				
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
				
				$("#title_bar").append("<li role='presentation' class='nav-pills'><a href='#' ><button class='btn btn-info'>编辑</button></a></li>")
			},"html");
		})
		</script>
	</head>
	<body class="base_background">
		<!--
        	作者：791599901@qq.com
        	时间：2016-01-16
        	描述：this  is header
        -->
		<!--<div class="container-fluid">
			<div class="jumbotron head_background">
				 	<h1 style="margin-top: -3%;">ttjkst</h1>
				 	<h4 style="margin-bottom:-3% ;">hard work,try to make mistakes and try to current it!</h4>
			</div>
		</div>-->
		<!--
        	作者：791599901@qq.com
        	时间：2016-01-16
        	描述：this is texttile
        -->
        
       <!--<nav class="nav navbar-default">
       	<div class="container-fluid">
       		<div class="jumbotron head_background">
				 	<h1 style="margin-top: -3%;">ttjkst</h1>
				 	<h4 style="margin-bottom:-3% ;">Hard work,try to make mistakes and try to current it!</h4>
			</div>
       	<div class="navbar-header">
       	<button type="button" class="navbar-toggle" data-toggle="collapse"
       		data-target="#the_navbar">
       		<span class="sr-only">切换导航</span>
       		<span class="icon-bar"></span>
       	 	<span class="icon-bar"></span>
       	 	<span class="icon-bar"></span>
       	 	<span class="icon-bar"></span>
       		
       	</button>
       	</div>
       	<div class="collapse navbar-collapse" id="the_navbar">
       		
       	
       	 <ul class="nav navbar-nav" id="title_bar" >
       	 	<li role="presentation" class=""><a href="#">首页</a></li>
       	 	<li role="presentation" class=""><a href="#">关于本站与我</a></li>
       	 	<li role="presentation" class=""><a href="#">关于生活</a></li>
       	 	<li role="presentation" class="active"><a href="#">关于编程</a></li>
       	 </ul>
       	 </div>
       	</div>
       	</nav>-->
       	
       <!--
       	作者：791599901@qq.com
       	时间：2016-01-17
       	描述：this is context
       -->
  		<div class="container" id="context"> 
       		
       			<div class="thumbnail" style="margin-bottom: 5%;margin-top: 5%;">
       				<div class="caption" style="margin-bottom: 10%;" id="text_page"> 
       					<div class="row">
       						
       						
       						<div class="col-lg-offset-4  col-xs-5">
       							<form class="form-inline" >
       								
       								<div class="form-group" id="AboutWhatGroup">
	       								<label for="AboutWhat_id">AboutWhat:</label>
	       								<select  id="AboutWhat_id" class="form-control">
	       								</select>
	       								<br/>
       								</div>
       								
       								
       								
       								<!--  
       								<div class="form-group has-feedback" id="newWhatAboutGroup">
       									<label for="new_AboutWhat_id">填写新的AboutWhat:</label>
       									<input type="text" id="new_AboutWhat_id" class="form-control" placeholder="系统中没有Any whatAbouts"/>
       									     									
       								</div>
       								-->
       								<br/>
       								
       								<div class="form-group" id="KindGroup">
       									<label for="kind_id">种类</label>
       									<select id="kind_id" class="form-control">
   
       									</select>
       								<br/>
       								</div>
       								
       								<div class="form-group has-feedback" id="newKindGroup">
       									<label for="newKind_id">填写新的种类:</label>
       									<input type="text" id="newKind_id" class="form-control" placeholder="请填写新的种类"  />
       									
       								</div>
       								<br/>
       								
       								<div class="form-group has-feedback" >
       								<label for="title_id">标题:</label>
       								<input type="text" name="title" id="title_id" placeholder="请填写你的标题" 
       								class="form-control"/>
       								</div>
       								<br/>
       								<div class="form-group">
       										
       									<label for="author_id">作者:</label>
										<input type="text" name="title" id="author_id" placeholder="请填写作者名" 
       									class="form-control "/>	
       								</div>
       								</form>
       						</div>
       						
       					</div>
       					
       				
       					<div id="intro">
       					
       					</div>
       					<button id="editIntro" class="btn btn-primary" onclick="editIntro()" type="button">点开写简介</button>
       					<div id="edit">
      						请在这编辑
      					</div>
       					
       					<button id="edit" class="btn btn-primary" onclick="edit()" type="button">开始写文章</button>
       					<button id="show" class="btn btn-primary" onclick="show()" type="button" style="display: none;">展示</button>
       				
					</div>
       			  
       				
       			</div>
       		</div>
  
  
      
      <button id="saveitandshow" class="btn btn-primary"  type="button">同步到网站</button>
	  <button id="saveit" class="btn btn-primary"  type="button">同步到网站不发表</button>
	 <!--   <button id="flush" class="btn btn-primary"  type="button">刷新</button>
     -->
       
       <!--
       	作者：791599901@qq.com
       	时间：2016-01-18
       	描述：this buttom
       -->
	</body>
	
	
</html>