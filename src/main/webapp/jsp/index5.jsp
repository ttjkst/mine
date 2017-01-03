<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/react/built/common.js"></script>
		<title></title>
		<script type="text/javascript">
		var webRoot = "${pageContext.request.contextPath}/";
		</script>
	</head>
	  <body class="base_background">
	  	<div id="modal_login"></div>
	  	<div id="modal_msg"></div>
    	<div id="head"></div>
    	<br/>
    	<div id="context"></div>
    	<div id="button"></div>
  </body>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/react/built/controlPage.js"></script>
</html>