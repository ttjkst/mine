<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/loginBar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/controlWeb.js"></script>
	<script type="text/javascript">
		$.ajaxSetup ({
	        cache: false //关闭AJAX缓存
	    });
		var webRoot="${pageContext.request.contextPath}/";
		var webRootNotS="${pageContext.request.contextPath}";
		var webName_global = "${requestScope.modal.webName}";
		var webUser="${requestScope.modal.whoUsed}";
		$(document).ready(function(){
			$.get("${pageContext.request.contextPath}/"+"jsp/littleBar/head.jsp",function(data){
				$("body").prepend(data);
				loginInit();
				webinit();
				loadButtom();
			},"html");
		})
	</script>
</head>
<body class="base_background">
<!--  <div class="container-fluid buttom_background" id="buttom">
<div class="page-header">
<h3>特别感谢：</h3>
<div class="row">


	<div class="col-xs-4 col-md-3">
    <a href="http://www.bootcss.com/ " target="_blank" class="thumbnail text-center">
      <img src="${pageContext.request.contextPath}/img/thinks/bootstrap.jpg" alt="..." class="img-responsive img-rounded">
      	一个强大的前端框架<strong>·bootstrap</strong>
    </a>
  </div>
	<div class="col-xs-4 col-md-3">
    <a href="http://summernote.org/ " target="_blank" class="thumbnail text-center">
      <img src="${pageContext.request.contextPath}/img/thinks/summernote.jpg" alt="..." class="img-responsive img-rounded">
  		    一个强大的前端富文本编辑器<strong>·summernote</strong>
    </a>
  </div>
  <div class="col-xs-4 col-md-3">
    <a href="http://spring.io/ " target="_blank" class="thumbnail text-center">
      <img src="${pageContext.request.contextPath}/img/thinks/spring.jpg" alt="..." class="img-responsive img-rounded">
      	一个强大企业级后台的框架<strong>·spring</strong>
    </a>
  </div>
   <div class="col-xs-4 col-md-3">
    <a href="http://glyphicons.com/ " target="_blank" class="thumbnail text-center">
      <img src="${pageContext.request.contextPath}/img/thinks/font.jpg" alt="..." class="img-responsive img-rounded">
      	感谢其提供的免费的图标<strong>·glyphicons</strong>
    </a>
  </div>
</div>
	<h1 class="text-center text-primary">hellow world!</h1>
</div>
<h5 class="text-center text-primary" ><strong>desiged   by    ttjkst</strong></h5>
</div>-->
</body>
</html>