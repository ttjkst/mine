<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		
	</head>
	<style>
		#ikResult > div{
			display: inline-block!important;
		}
		
	</style>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/sampleJs/jquery.min.js"></script>
		 <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
 		<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<body>
		<div class="container">
			<label for="ik"><h1>填写你的需要分词的语句</h1></label>
			<div class="input-group input-group-lg">
				<input class="form-control" id="ik" type="text" name="content" />
				<span class="input-group-btn" ><button class="btn btn-primary" onclick="onIk()" type="button">分词!</button></span>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<h1 id="ikResult">
					</h1>
				</div>
			</div>
		</div>
		<script>
		var webRoot="${pageContext.request.contextPath}";
			var createTags = function(text){
				var romdomResource = [
				"label-default","label-primary","label-success","label-info",
				"label-warning","label-danger"
									]
				var  romdomNumInt = parseInt(Math.random()*romdomResource.length);
				console.info(romdomNumInt)
				var className = "label "+ romdomResource[romdomNumInt];
				return "<div class='"+className+"' >"+text+"</div>";
			}
			var onIk = function(){
				$("#ikResult").empty();
				var iktext = $("#ik").val();
				$.get(webRoot+"/essay/ik/analyze",{content:iktext},function(data){
					if(Array.isArray(data)){
						data.forEach(function(e){
							$("#ikResult").append(createTags(e.term));	
						})
					}else{
						alert("no result");
					}
				})
			}

			
			
		</script>
	</body>
</html>
           