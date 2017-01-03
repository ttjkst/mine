<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${requestScope.title}</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/loginBar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/controlWeb.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/forLeaveWords.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/otherhead.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/msgTool.js"></script>
		<script type="text/javascript">
		$.ajaxSetup ({
	        cache: false //关闭AJAX缓存
	    });
		var webRoot="${pageContext.request.contextPath}/";
		var webRootNotS="${pageContext.request.contextPath}";
		var webName_global = "";
		var webUser="others";
		var wordId = ${word.wId};
		var fatherTitle = window.opener==null? null:$(window.opener.document).find("title").text();
		
		var win = window;
		$(function(){
		/*$("#leaveWords").keydown(function(e){
			var leaveWords=$("#leaveWords").val().length;
			if(e.keyCode == 8){
				console.log("error");
				return true;
			}
			if(leaveWords>=155){
				console.log("出错");
				return false;
			}else{
				return true;
			}
		})*/
			$("#leaveWords").keyup(function(){
			var leaveWords=$("#leaveWords").val().length;
			
			if(leaveWords>=155){
				$("#canInWords").text(140-leaveWords);
			}else{
				$("#canInWords").text(140-leaveWords);
			}
		})
			
		})
		
		$(document).ready(function(){
			loadButtom();
			$.get("${pageContext.request.contextPath}/"+"jsp/littleBar/head.jsp",function(data){
				$("body").prepend(data);
				loginInit();
				//webinit();
				loadleaveWords();
				msgBlockload("",null,false,true,true);
				$("#title_bar").empty();
				var navdata={"navs":[{"navName":"首页","goWhere":"#","bomId":"main"},
				                     {"navName":"关于本站与我","goWhere":"#","bomId":"aboutMe"},
				                     {"navName":"关于生活","goWhere":"#","bomId":"aboutLeft"},
				                     {"navName":"关于编程","goWhere":"#","bomId":"aboutCode"}],"index":"#title_bar"}
				addNav(navdata);
								
								
			$("#title_bar").append("<li role='presentation' class='nav-pills'><a href='#' ><button class='btn btn-info'>您正在阅读文章</button></a></li>")					
				
			},"html");
		})
		$(document).ready(function(){	
			$("#word").load("${pageContext.request.contextPath}"+"${requestScope.word.wPath}",function(){
			$("#word").prepend("<br/>");	
			$("#word").prepend("<div class='text-center'><h4>${requestScope.word.wTitle}</h4><span class='glyphicon glyphicon-user'>:${requestScope.word.wAuthor}&nbsp;</span><br><span class='glyphicon glyphicon-time'>:${requestScope.word.wTimeOfInData}</span></div>");
			$("#word").prepend("<h1 class='text-center'>"+"${requestScope.title}"+"</h1>");
			});	
		})
		$(function(){
			
			$(document).on("click","#main",function(){
				$.get(webRoot+"setWebName.action",{"where":"首页"},function(data){
					window.open(webRoot+"toMain.action","_self");
					if(fatherTitle!=null){
						window.opener.close();
					}
				})
				
			})
			$(document).on("click","#aboutMe",function(){
				
				$.get(webRoot+"setWebName.action",{"where":"关于本站与我"},function(data){
					window.open(webRoot+"toMain.action","_self");
					if(fatherTitle!=null){
						window.opener.close();
					}
				})
				
			})
			$(document).on("click","#aboutLeft",function(){
				$.get(webRoot+"setWebName.action",{"where":"关于生活"},function(data){
					window.open(webRoot+"toMain.action","_self");
					if(fatherTitle!=null){
						window.opener.close();
					}
					
				})
				
			})
			$(document).on("click","#aboutCode",function(){
				
				$.get(webRoot+"setWebName.action",{"where":"关于编程"},function(data){
					window.open(webRoot+"toMain.action","_self");
					if(fatherTitle!=null){
						window.opener.close();
					}
					
				})
				
			})
			
		})
		
		</script>
	</head>
	<body class="base_background">  
       <div class="container"> 
       		<div id="context">
       			<br />
       			<div class="thumbnail" style="margin-bottom: 0%;">
       				<div class="caption" style="margin-bottom: 10%;" id ="word">
       				    				
       				</div>
       			</div>
       		</div>
       </div >
       
       <div class="container">
       <br/>
       <div class="panel panel-default">
       		<div class="panel-heading"><h5>评论</h5>
       		</div>
       		
	       	<div class="panel-body"> 
	       	<button class="btn btn-block btn-info" id="preLeaveWordsBtn" style="display: none" >之前评论</button>
		     	<ul class="media-list" id="leave_list">
		     	</ul>  
		     		<button class="btn btn-block btn-info" style="display: none"  id="nextLeaveWordsBtn">更多评论</button>
	     	</div>
     	</div>
       <div class="row">
       <div class="col-xs-4">
       <label for="othersInput">称呼</label>
       <input type="text" value="" class="form-control" id="othersInput" placeholder="请写上您的尊称"/>
         <label for="othersMail">邮箱</label>
       <input type="text" value="" class="form-control" id="othersMail" placeholder="格式:xxx@xxx.com"/>
         <label for="othersQQ">QQ</label>
       <input type="text" value="" class="form-control" id="othersQQ" placeholder="只允许输入数字"/>
       </div>
       </div>
       
       <br/>
       <div class="from-group" id="textareaF">
       <textarea  class="form-control" rows="6" id="leaveWords" maxlength="140"></textarea>
       <p class="floatLeft">你还能输入
       	<strong  id="canInWords">140</strong>
       		个字
       </p>
       <button class="btn btn-default floatRight" id="subitWords">提交</button>
       </div>
        </div>
	</body>
</html>
