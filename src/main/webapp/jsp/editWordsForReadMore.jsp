<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${word.wTitle}</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<link href="${pageContext.request.contextPath}/summernote_compile/summernote.css" rel="stylesheet">
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/summernote.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/lang/summernote-zh-CN.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/editWordForReadMore.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/MD5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/loginBar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/controlWeb.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/forLeaveWordsControl.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/otherhead.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/msgTool.js"></script>
		<script type="text/javascript">
		var webRoot = "${pageContext.request.contextPath}/";
		var currentWeb = "${pageContext.request.contextPath}/";
		var id_global_server="${word.wId}";
		var wordId=id_global_server;
		var title_global_server = "${word.wTitle}";
		var author_global_server = "${word.wAuthor}";
		var kindName_global_server = "${word.wKind.kName}";
		var aboutWhatName_global_server = "${word.wKind.aboutwhat.name}";
		var canShow_global_server = "${word.canShow}"
		var editText_global_server;
		var introduceText_global_server;
		var fatherTitle = $(window.opener.document).find("title").text();
		
		$.ajaxSetup ({
	         cache: false //关闭AJAX缓存
	     });
		$.ajaxSetup({
			error:function(jqXHR,textStatus,errorThrown){		
				if(jqXHR.status==400){
					$(document).find("#loginModal").modal('show');
					
				}
				}
			})
		$(document).ready(function(){
			loginInit();
			loadleaveWords();
			$.post(webRoot+"init.action",{"id":id_global_server},function(data){
			});
			$.get("${pageContext.request.contextPath}/"+"jsp/littleBar/head.jsp",function(data){
				$("body").prepend(data);
				
				loadbuttom();
				msgBlockload("",null,false,false,'static');
			$("#title_bar").empty();
			var navdata={"navs":[{"navName":"首页","goWhere":"#","bomId":"main"},
			                     {"navName":"关于本站与我","goWhere":"#","bomId":"aboutMe"},
			                     {"navName":"关于生活","goWhere":"#","bomId":"aboutLeft"},
			                     {"navName":"关于编程","goWhere":"#","bomId":"aboutCode"}],"index":"#title_bar"}
			addNav(navdata);
							$(document).on("click","#main",function(){
								$.get(webRoot+"setWebName.action",{"where":"首页"},function(data){
									
									window.close();
									if(fatherTitle!="首页"){
									window.opener.close();
									window.open(webRoot+"toMain.action");
									}
								})
								
							})
							$(document).on("click","#aboutMe",function(){
								
								$.get(webRoot+"setWebName.action",{"where":"关于本站与我"},function(data){
									window.close();
									if(fatherTitle!="关于本站与我"){
									window.open(webRoot+"toMain.action");
										window.opener.close();
										}
								})
								
							})
							$(document).on("click","#aboutLeft",function(){
								
								$.get(webRoot+"setWebName.action",{"where":"关于生活"},function(data){
									window.close();
									if(fatherTitle!="关于生活"){
									window.open(webRoot+"toMain.action");
										window.opener.close();
										}
								})
								
							})
							$(document).on("click","#aboutCode",function(){
								
								$.get(webRoot+"setWebName.action",{"where":"关于编程"},function(data){
									window.close();
									if(fatherTitle!="关于编程"){
										window.open(webRoot+"toMain.action");
										window.opener.close();
										}
									
								})
								
							})
							$("#title_bar").append("<li role='presentation' class='nav-pills'><a href='#' ><button class='btn btn-danger'>您正在更改文章</button></a></li>")
				
			},"html");
			
			
			
			
			$("#edit").load("${pageContext.request.contextPath}"+"${word.wPath}"+"?time="+(new Date()).getUTCDate(),function(data){
				editText_global_server = data;
			});	
			$("#intro").load("${pageContext.request.contextPath}"+"${word.introductionPath}"+"?time="+(new Date()).getUTCDate(),function(data){
				introduceText_global_server = data;
			});
			
			
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
       								<br/>
       								<div class="form-group">
       										
       									<label for="canShow">是否在页面展示：</label>
										 <select id="canShow" class="from-control">
										 <option>不展示</option>
										 <option>展示</option>
										 </select>
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
       			  		<br/>
       			  		<br/>
					</div>
       				
       			</div>
       		</div>
  	
  
      
      <button id="updateIt" class="btn btn-primary"  type="button">同步到网站</button>
       
           <div class="container">
       <br/>
       <div class="panel panel-default">
       		<div class="panel-heading"><h5>评论</h5>
       		</div>
       		
	       	<div class="panel-body"> 
	       	<button class="btn btn-block btn-info" id="preLeaveWordsBtn" style="display: none">之前评论</button>
		     	<ul class="media-list" id="leave_list">
		     	</ul>  
		     		<button class="btn btn-block btn-info" id="nextLeaveWordsBtn" style="display: none">更多评论</button>
	     	</div>
     	</div>
        </div>
       
       <!--
       	作者：791599901@qq.com
       	时间：2016-01-18
       	描述：this buttom
       -->
  
	</body>
	
	
</html>