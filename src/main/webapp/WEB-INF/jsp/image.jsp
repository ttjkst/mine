<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

		<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
		
		<link href="//cdn.bootcss.com/imageviewer/0.5.1/viewer.min.css" rel="stylesheet">
		<script src="//cdn.bootcss.com/imageviewer/0.5.1/viewer.min.js"></script>
		<style>
		#imagePreview {
			filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
		}
		</style>
		<script type="text/javascript">
		if (!String.prototype.trim) {
			  String.prototype.trim = function () {
			    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
			  };
			}
				$(function(){
					setTimeout(function(){$("div[class='alert alert-danger']").hide()},5000);
				})
		<c:if test="${empty user}">
				$(function(){
					$("a[myRole=image]").find("img").viewer();
				})
		</c:if>
				<c:if test="${not empty user}">
				$(function(){
					$("a[myRole=image]").click(function(){
						var url = $(this).find("img").attr("src");
						$("#imagePreview2").attr("src",url);
						var title= $(this).next().text().trim();
						var id = $(this).find("img").attr("imageid").trim();
						var isPrimary = $(this).find("img").attr("isprimary");
						$("#isprimary").attr("checked",isPrimary=="false"?false:true);
						$("#imageTitle2").val(title);
						$("#imageId").val(id);
						$("#updateImage").modal('show')
					})
					
					$("a[myRole=image]").mouseover(function(){
						$(this).next().collapse('show')
					})
					$("a[myRole=image]").mouseleave(function(){
						$(this).next().collapse('hide')
					}) 
				})
				
				
					var loadImageFile = (function () {
					if (window.FileReader) {
						var	oPreviewImg = null, oFReader = new window.FileReader(),
							rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
				
						oFReader.onload = function (oFREvent) {
							if (!oPreviewImg) {
								var newPreview = document.getElementById("imagePreview");
								$(newPreview).attr("src",oFREvent.target.result)
							}
						};
				
						return function () {
							var aFiles = document.getElementById("imageIput").files;
							if (aFiles.length === 0) { return; }
							if (!rFilter.test(aFiles[0].type)) { alert("You must select a valid image file!"); return; }
							oFReader.readAsDataURL(aFiles[0]);
						}
				
					}
					if (navigator.appName === "Microsoft Internet Explorer") {
						return function () {
				
						}
					}
				})();
					
				
				var loadImageFile2 = (function () {
					if (window.FileReader) {
						var	oPreviewImg = null, oFReader = new window.FileReader(),
							rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
				
						oFReader.onload = function (oFREvent) {
							if (!oPreviewImg) {
								var newPreview = document.getElementById("imagePreview2");
								$(newPreview).attr("src",oFREvent.target.result)
							}
						};
				
						return function () {
							var aFiles = document.getElementById("imageIput2").files;
							if (aFiles.length === 0) { return; }
							if (!rFilter.test(aFiles[0].type)) { alert("You must select a valid image file!"); return; }
							oFReader.readAsDataURL(aFiles[0]);
						}
				
					}
					if (navigator.appName === "Microsoft Internet Explorer") {
						return function () {
				
						}
					}
				})();
					
				
				var uploadImage = function(){
					$("#newImage").find("form").submit();
				}
				var uploadImage2 = function(){
					$("#updateImage").find("form").submit();
				}
			</c:if>
		</script>
	</head>
	<body>
	<c:if test="${not empty user}">
	 	<div class="modal fade" id="updateImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">上传一张图片</h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-group" enctype="multipart/form-data" 
        action="${pageContext.request.contextPath}/album/image/update" 
        method="post">
        		<input type="hidden" name="album_id" value="${album.id}">
        		<input id="imageId" type="hidden" name="id" value="">
		      	<label for="imageIput">请选择要上传的图片</label>
		        <input type="file" id="imageIput2" name="image"  onchange="loadImageFile2()" placeholder="请选择一个图片作为封面" />
		        <br/>
		        <img id="imagePreview2" alt="..."   class="img-thumbnail">
		        <label for="imageTitle">图片标题</label>
		        <input id="imageTitle2" class="form-control" type="text" name="title" />
		        <input  id="isprimary" type="checkbox" name="isPrimary" value="yes"> 作为隐私图片？
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
		        <button type="button" class="btn btn-primary" onclick="uploadImage2()" >确认</button>
		      </div>
		    </div>
		  </div>
		</div>
	 </c:if>
	
	
	
	 <c:if test="${not empty user}">
	 	<div class="modal fade" id="newImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">上传一张图片</h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-group" enctype="multipart/form-data" 
        action="${pageContext.request.contextPath}/album/image/save" 
        method="post">
        		<input type="hidden" name="album_id" value="${album.id}">
		      	<label for="imageIput">请选择要上传的图片</label>
		        <input type="file" id="imageIput" name="image"  onchange="loadImageFile()" placeholder="请选择一个图片作为封面" />
		        <br/>
		        <img id="imagePreview" alt="..."   class="img-thumbnail">
		        <label for="imageTitle">图片标题</label>
		        <input id="imageTitle" class="form-control" type="text" name="title" />
		        <input type="checkbox" name="isPrimary" value="yes"> 作为隐私图片？
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
		        <button type="button" class="btn btn-primary" onclick="uploadImage()" >确认</button>
		      </div>
		    </div>
		  </div>
		</div>
	 </c:if>
	 <c:if test="${not empty error}">
	 <div class="alert alert-danger" role="alert">${error}</div>
	 </c:if>
		<nav class="navbar" style="background-color: #669e81;">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand"  href="#" style="color: white;">Ttjkst</a>
              </div>
              <p class="navbar-text" style="color: white;" >I hope every picture here will give you a different feeling.</p>
              <c:if test="${not empty user}">
              <button type="button" class="btn btn-info navbar-btn navbar-left"  data-toggle="modal" data-target="#newImage" >上传一个新的图片</button>
              </c:if>
              </div>
     </nav>
     	<div class="container">
     	<c:if test="${empty images}">
			   <h1>啊！竟然啥也没有！</h1>
		</c:if>
     		<div class="row">
     		<c:forEach items="${images}" var="image">
     			<div class="col-xs-6 col-md-3">
				    <a href="#" class="thumbnail" myRole="image">
				      <img src="${pageContext.request.contextPath}/album/image/load/${image.id}" isprimary="${image.isPrimary}" imageid="${image.id}" alt="...">
				    </a>
				    <div class="collapse">
					  <div class="well" role="title">${image.title}<c:if test="${empty image.title}">没有标题</c:if></div>
					</div>
				    <c:if test="${not empty user}">
				    <a href="${pageContext.request.contextPath}/album/image/delete/${image.album_id}/${image.id}">
				    	<button class=" center-block  btn btn-default" >删除</button>
				    </a>
				    </c:if>
				  </div>
			</c:forEach>
     		</div><!--row-->
     	</div> <!--container-->
    <c:if test="${empty user}">
     <div class="panel container">
     	<div class="panel-heading" style="background-color: #669e81;color: white;"  >关于图片感想</div>
	  <div class="panel-body">
	    ${album.thinking}
		    <c:if test="${empty album.thinking}">
				   啊！竟然啥也没有!
			</c:if>	   
	  </div>
	</div>
	</c:if>
	 <c:if test="${not empty user}">
	 	 <div class="panel container">
	 	 <form action="${pageContext.request.contextPath}/album/changeThinking" method="post">
	     	<div class="panel-heading form-group" style="background-color: #669e81;color: white;"  >
	     		<div class="panel-heading" style="background-color: #669e81;color: white;"  >关于图片感想</div>
	     	</div>
		  <div class="panel-body form-group">
		  	<input type="hidden" name="album_id" value="${album.id}">
		    <textarea class="form-control" rows="6" name="thinking" >${album.thinking}<c:if test="${empty album.thinking}">啊！竟然啥也没有!</c:if></textarea>
		    <br>
		    <button type="submit" class="btn btn-default pull-right" >更改</button>
		  </div>
		  </form>
		</div>
	 </c:if>
	</body>
</html>
