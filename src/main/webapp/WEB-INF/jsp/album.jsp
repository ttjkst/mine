<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

		<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		<script type="text/javascript">
		if (!String.prototype.trim) {
			  String.prototype.trim = function () {
			    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
			  };
			}
		var onSearch = function(){
			var key = $("input[name=key]").val();
			if(key==null){
				window.open('${pageContext.request.contextPath}/album')
			}else{
				$("#searchFrom").submit();
			}
		}
		<c:if test="${not empty user}">
		//has user
		$(function(){
			$("#uploadAblum").click(function(){
				$("#newAlbum").find("form").submit();
			})
			
			$("#uploadUpdate").click(function(){
				$("#updateForm").submit();
			})
			$("img[role='cover']").click(function(){
				var title  = $(this).next().find("[role=title]").text();
				var des    = $(this).next().find("[role=description]").text();
				var id     = $(this).attr("albumid")
				var imageUrl = $(this).attr("src");
				var coverid= $(this).attr("cover");
				$("#previewImage").attr("src",imageUrl);
				$("#previewImage").next().val(coverid.trim())
				$("#updatealbumid").val(id.trim())
				$("#des2").val(des.trim())
				$("#imageTitle2").val(title.trim())
				$.get("${pageContext.request.contextPath}/album/image/go/json/"+id,function(data){
					$("#imageCheckBox").empty();
					var nodes =[];
					$.each(data,function(i,e){
						var id = "${pageContext.request.contextPath}/album/image/load/"+e.id;
						var node  =  createCheckImageDom(id,e.id);
						if(e.album_id!=""){
							
						}
						$("#imageCheckBox").append(node)
					})
				})
				$("#updateImage").modal('show')			
			})
			$(document).on("click","#imageCheckBox > a",function(){
			//	$("#previewImage").attr("src","");
				var imageUrl = $(this).find("img").attr("src");
				var id		 = $(this).find("img").attr("imageid");
				$("#previewImage").next().val(id.trim())
				setTimeout(function(){$("#previewImage").attr("src",imageUrl);},100)
			})
		})
		//has user
		var createCheckImageDom = function(url,id){
			return '<a href="#"><img src="'+url+'" imageid="'+id+'" style="height: 70px;width: 70px;" alt="..." class="img-thumbnail"></a>'
		}
		</c:if>
		</script>
	</head>
	<body style="background-color: rgba(224, 220, 177, 0.95);">
		<!-- Modal -->
		<c:if test="${not empty user}">
			<div class="modal fade" id="newAlbum" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">新建一个相册</h4>
			      </div>
			      <div class="modal-body">
			        <form class="form-group" enctype="multipart/form-data" 
			        action="${pageContext.request.contextPath}/album/save" 
			        method="post">
			      	<label for="cover">请选择一个图片作为封面</label>
			        <input type="file" id="cover" name="cover" placeholder="请选择一个图片作为封面" />
			        <label for="imageTitle">相册的标题</label>
			        <input id="imageTitle" class="form-control" type="text" name="title" />
			        <label for="des">对相册的基本描述</label>
			        <textarea id="des" class="form-control" name="des"></textarea>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
			        <button type="button" class="btn btn-primary" id="uploadAblum">确认</button>
			      </div>
			    </div>
			  </div>
			</div>

			<div class="modal fade" id="updateImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" >更改相册的属性</h4>
			      </div>
			      <div class="modal-body">
			        <form class="form-group"  id="updateForm"  action="${pageContext.request.contextPath}/album/update" 
			        method="post" >
			        <img id="previewImage" src="" imageid="ss" alt="..." class="img-thumbnail">
			        <input type="hidden" name="cover" value="" />
			       <input type="hidden" id="updatealbumid" name="id" value="" />
			         <br />
			      	<label for="cover">请选择一个图片作为封面</label>
			      	<div  id="imageCheckBox" class="pre-scrollable" style="max-height:140px;" >
			        </div>
			        <br />
			        <label for="imageTitle2">相册的标题</label>
			        <input id="imageTitle2" class="form-control" type="text" name="title" />
			        <label for="des2">对相册的基本描述</label>
			        <textarea id="des2" name="des" class="form-control"></textarea>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
			        <button type="button" class="btn btn-primary" id="uploadUpdate">确认</button>
			      </div>
			    </div>
			  </div>
			</div>
</c:if>
		
		
<nav class="navbar" style="background-color: #669e81;">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand"  href="#" style="color: white;">Ttjkst</a>
              </div>
              <p class="navbar-text" style="color: white;" >Hard work and lean,try to make mistakes and try to correct it!</p>
             <c:if test="${not empty user}"><button type="button" class="btn btn-info navbar-btn navbar-left"  data-toggle="modal" data-target="#newAlbum" >新建相册</button></c:if>
                <form class="navbar-form navbar-left" id="searchFrom" action="${pageContext.request.contextPath}/album/search/" method="post">
                  <div class="form-group">
                    <input type="text" class="form-control" name="key"  placeholder="搜你想要的东西" />

                  </div>
                    <button type="button" class="btn btn-default" onclick="onSearch()" >Go!</button>
                </form>
              </div>
      </nav>
      <!--<div class="container">
    	<div class="row">
    		<div class="col-sm-6 col-md-4">
			    <div class="thumbnail">
			      <img src="img/noImage.jpg" alt="...">
			      <div class="caption">
			        <h3>默认相册编号1</h3>
			        <p>至少留下什么东西。。。。</p>
			        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
			      </div>
			    </div>
			  </div>	
      </div>
		-->
		<div class="container">
		<c:if test="${empty albums}">
			   <h1>啊！竟然啥也没有！</h1>
		</c:if>
    	<div class="row">
    	<c:forEach items="${albums}" var="album">
    		<div class="col-sm-6 col-md-4">
			    <div class="thumbnail">
			      <img src="${pageContext.request.contextPath}/album/image/load/${album.cover.id}" cover="${album.cover.id}" albumid="${album.id}" role="cover" alt="...">
			      <div class="caption">
			        <h3 role="title">${album.title}</h3>
			        <p role="description">${album.des}</p>
			        <p><a href="${pageContext.request.contextPath}/album/image/go/${album.id}" class="btn btn-primary" role="button">更多</a>	 <c:if test="${not empty user}"><a href="${pageContext.request.contextPath}/album/delete/${album.id}" class="btn btn-primary" role="button">删除</a></c:if></p>
			      </div>
			    </div>
			  </div>
		
    	</c:forEach>	  
      </div> <!--row-->
     </div><!-- end with container-->
	</body>
</html>
