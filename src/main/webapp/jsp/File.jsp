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
		<link href="${pageContext.request.contextPath}/summernote_compile/summernote.css" rel="stylesheet">
		<!--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/myWebJs/re.js"></script>-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/summernote.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/summernote_compile/lang/summernote-zh-CN.js" ></script>
		 
		<title></title>
		<script type="text/javascript">
		var  intorText="";
		var req = function(args,data){
			var oReq = new XMLHttpRequest();
			for(var i in args){
				console.log(i+":"+args[i]);
			}
			oReq.open(
			args.method==undefined?"post":args.method,
			args.url,
			args.isAsync==undefined?false:args.isAsync,
			args.user==undefined?"":args.user,
			args.password==undefined?"":args.password
			)
			for(var i in args.headers)
			oReq.setRequestHeader(i,args.headers[i]);
			//oReq.timeout=args.timeout.value==undefined?0:args.timeout.value;
			for(var i in args.callbacks){
				console.log(i);
				oReq.addEventListener(i,args.callbacks[i]);
			}
			oReq.send(data);
		}
		var uploadBymultipart = function(url,method,isAsync,fromData){
			req({
				url:url,
				method:method,
				isAsync:isAsync,
				callbacks:{
					load:function(result){
						console.log(result)
					},
					error:function(result){
						console.log(result)	
					}
				}
			},fromData)
			
		}
		$(document).ready(function(){
			console.log("你好");
			$("#edit").summernote({
					lang:'zh-CN',
					height: 300,                 // set editor height
					minHeight: null,             // set minimum height of editor
					maxHeight: null,             // set maximum height of editor
					focus: true ,                 // set focus to editable area after initializing
				toolbar: [
					// [groupName, [list of button]]
				 	['style', ['bold', 'italic', 'underline', 'clear']],
				 	['font', ['strikethrough', 'superscript', 'subscript']],
				 	['fontsize', ['fontsize']],
				 	['color', ['color']],
				 	['para', ['ul', 'ol', 'paragraph']],
			     	['height', ['height']],
					['Insert',['picture','link','table','hr']],
				 	['Misc',['codeview','fullscreen']]
				
			],
				focus: true,
				popover: {
						 image: [
		    				['imagesize', 	['imageSize100', 'imageSize50', 'imageSize25']],
						    ['float', 		['floatLeft', 'floatRight', 'floatNone']],
						    ['remove', 		['removeMedia']]
							],
					  link: [
					    	['link', 		['linkDialogShow', 'unlink']]
					  ],
					  air: [
					    ['color', 	['color']],
					    ['font', 	['bold', 'underline', 'clear']],
					    ['para', 	['ul', 'paragraph']],
					    ['table', 	['table']],
					    ['insert', 	['link', 'picture']]
					  ]
					},
				callbacks: {
				    onChange: function(contents, $editable) {
				    	intorText =  contents;
				    }
				  }
		});	
		})
		
		
		$(function(){
			$("#submit").click(function(){
					var form = new FormData();
					var myBlob = new Blob([intorText],{"type":"image/jpeg"});
					form.append("name","ttjkst");
					form.append("file",myBlob);
					var url="${pageContext.request.contextPath}/testFile.action";
					uploadBymultipart(url,"post",true,form);
				
			})	
			
			
			$("#button2").click(function(){
				args={
						"arg1":"你好sss",
						"arg2":"你好sss2"
						}
				
				console.log(args.serialize());
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/testIn.action",
					data:args,
					datatype:"json",
					processData:false,
						error:function(msg){
							console.log(msg)
							//_this.handleError(msg);
						},
						success:function(msg){
							console.log(msg)
							
						}
					})
			})
			
			
		})
		
		
		</script>
	</head>
	  <body class="base_background">
	  		<form action="${pageContext.request.contextPath}/testIn.action"
	  		 method="Post"
	  		 enctype="multipart/form-data" >
	  		 <input type="file" name="file" value=""/>
	  		 <input type="submit" value="提交"/>
	  		 </form>
	  		 <div id="edit">
	  		 </div>
	  		 <button id="submit">测试文件提交</button>
	  		 <button id="button2">测试POST</button>
  </body>
</html>