/**
 * 
 */	var kindName_global=null;
 	var aboutWhatName_global=null;
 	var wordId=null;
 	var list_kind = null;
 	var list_aboutWhat = null;
 	var title_global=null;
 	function bindindCloseWeb(isNeedFatherWebFursh){
 		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-footer").find("button").text("离开网站");
 		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-footer").find("button").before("<button type='button' class='btn btn-info' style='display: none' >写篇新文章</button>")
 		$(document).on("click","#msgModal > .modal-dialog > .modal-content > .modal-footer > button",function(){
 			var value=$(this).text();
 			$.get(webRoot+"reloadEditweb.action",new Date(),function(data){
 				if(value=="离开网站"){
 	 				window.open(webRoot+"toMain.action","_self"); 
 	 			}else if(value=="写篇新文章"){
 	 				window.location.reload();
 	 			}	
 			});
 		})
 	}
	function showEditTool(){
  		$('#edit').summernote({
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
				/*	callbacks:{
  	  					onImageUpload: function(files) {
         	 		console.log( 'image upload:' , files);
          			alert(files);
          			$("#edit").summernote('insertImage', "../img/test.PNG",function(){
          				
          			});
     		 		}
				}*/

  		
  		
  		});
	}
	//没有修改完善
	function verifyTitle(title){
		if(title_global==null&&aboutWhatName_global==null&&kindName_global==null){
			somehasNothing("#title_id");
		}else{
			$.get(webRoot+"verifyTitle.action",{"title":title,"kindName":kindName_global,"aboutWhat":aboutWhatName_global,"webDoWhat":"edit"},function(data){
				if(data==false){
					somehasError("#title_id");
				}
				else{
					somehasSuccess("#title_id");
					showEditTool();
					$("#show").show();
				}
			})
		}
}
	//没有修改完善
	function verifyKind(title){
				somehasNothing("#newKind_id");
				if(kindName_global==null&&aboutWhatName_global==null){
					somehasError("#newKind_id");
				}else{
					$.get(webRoot+"verify.action",{"msg":kindName_global,"parentMsg":aboutWhatName_global,"doWhat":"verifyKind","webDoWhat":"edit"},function(data){
						if(data==false){
							somehasError("#newKind_id");
						}
						else{
							somehasSuccess("#newKind_id");
							verifyTitle(title);
						}
					})
				}
		}
			
				
	function somehasNothing(selecter){
		$(selecter).parent().find("span").remove();
		$(selecter).parent().toggleClass("has-error",false);
		$(selecter).parent().toggleClass("has-success",false);
	}
	function somehasError(selecter){
		$(selecter).parent().find("span").remove();
		$(selecter).parent().toggleClass(" has-error",true);
		$(selecter).after("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span> <span  class='sr-only'>(error)</span>");
	}
	function somehasSuccess(selecter){
		$(selecter).parent().find("span").remove();
		$(selecter).parent().toggleClass(" has-success",true);
		$(selecter).after("<span class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span> <span id='inputSuccess2Status' class='sr-only'>(success)</span>");
	}
	function verifyNewAboutWhat(parentMsg,msg){
		
	}
	function newAboutWhatRecovery(){
		$("#newOptionGroup > span").remove();
		$("button").removeClass(" disabled");
		$("#newOptionGroup").removeClass(" has-error");
		$("#newWhatAboutGroup").removeClass(" has-success");
		$("#newWhatAboutGroup").removeClass(" has-error");
		$("#newOption_id").removeAttr("disabled");
	}
	function newAboutWhatError(){
		$("#newWhatAboutGroup > span").remove();
		$("#newWhatAboutGroup").append("<span class='glyphicon glyphicon-remove form-control-feedback'  ></span>");
		$("button").addClass(" disabled");
		$("#newWhatAboutGroup").removeClass(" has-success");
		$("#newWhatAboutGroup").addClass(" has-error");
		$("#newOption_id").attr("disabled","disabled");
	} 
	function newAboutWhatSuccess(){
		$("#newOptionGroup > span").remove();
		$("#newOptionGroup").append("<span class='glyphicon glyphicon-ok form-control-feedback'  ></span>");
		$("button").removeClass(" disabled");
		$("#newOptionGroup").removeClass(" has-error");
		$("#newOptionGroup").addClass(" has-success");
	}
function loadingEditDATA(){
			
				$.get(webRoot+"getAboutwhat.action",function(data){
					list_aboutWhat = data;
					
				if(list_aboutWhat.length!=0){
					$("#newOptionGroup").hide();
					$("#newWhatAboutGroup").hide();
					for(var i=0;i<list_aboutWhat.length;i++){
					$("#AboutWhat_id").append("<option>"+list_aboutWhat[i].name+"</option>");
					}
					var msg = $("#AboutWhat_id").val();
					$.get(webRoot+"getKindsByMsg.action",{"msg":msg},function(data){
						list_kind = data;
						if(list_kind.length!=0){
							$("#kind_id").append("<option>"+"New Kind"+"</option>");
								for(var i=0;i<list_kind.length;i++){
									$("#kind_id").append("<option>"+list_kind[i].kName+"</option>");
								}
							
							}else if(list_kind == null){
									$("#KindGroup").hide();
									$("#newKindGroup").show();
								}	
					},"json");
				}else if(list_aboutWhat == null){
					$("#AboutWhatGroup").hide();
					$("#KindGroup").hide();	
				}
				
			},"json")
		
		}
		
		
			
		
		
		
		
		/*
			Here is for kinds; 
		*/
//		$("#newKind_id").change(function(){
//			var msg=$.trim($("#newKind_id").val());
//			var parentMsg=$("#AboutWhat_id").val();
//			
//			
//			$.get(webRoot+"verify.do",{"msg":msg,"parentMsg":parentMsg,"doWhat":"verifyKind"},function(data){
//			if(data==false){
//				somehasError("#newKind_id");
//			}
//			else{
//				somehasError("#newKind_id");	
//			}
//			
//		})
//	});
	
		$(function(){
			
			//$("#flush").click(function(){
				//$.post(webRoot+"reloadEditweb.do",new Date());
				//location.reload();
			//})
			$("#AboutWhat_id").change(function(){
				$("#kind_id").empty();
				var msg=$("#AboutWhat_id").val();
				aboutWhatName_global=msg;
				
				$.get(webRoot+"getKindsByMsg.action",{"msg":msg},function(data){
					if(data.length !=0){
						$("#kind_id").append("<option>"+"New Kind"+"</option>");
							for(var i=0;i<data.length;i++){
								$("#kind_id").append("<option>"+data[i].kName+"</option>");
							}
						
						}else{
								$("#KindGroup").hide();
								$("#newKindGroup").show();
							}
					
				},"json");
			});
			
			/*
				not denfinded
			*/
				$("#kind_id").change(function(){
					$("#newKind_id").val("");
					somehasNothing("#newKind_id");
					var kindValue=$("#kind_id").val();
					
					if(kindValue!="New Kind"){
						kindName_global = null;
						$("#newKindGroup").hide();
//						$(document).unbind("change","#newKind_id");
					}else{
						$("#newKindGroup").show();
					}
					
				})
				$("#title_id").change(function(){
					var msg=$("#title_id").val();
					if(msg==""){
						somehasNothing("#title_id");
					}
				})
			//upload words
			$("#saveitandshow").click(function(){
				var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>正在上传标题，作者等信息</div></div>"
					msgBlockInit(dom,null,false,false,"static");
					msgBlockOpen();
					
				var parentValue=$("#AboutWhat_id").val();
				var childValue=$("#kind_id").val();
				var msg="";
				var args="";
				if(childValue==null){
					childValue="New Kind";
				}
				
				if(parentValue!="New AboutWhat"){
					if(childValue!="New Kind"){
						
						args={"abName":"","kindName":childValue,"msg":"null"};
					}else {
						childValue=$.trim($("#newKind_id").val());
						msg="one";
						args={"abName":parentValue,"kindName":childValue,"msg":msg};
					}
				}else if(parentValue=="New AboutWhat"){
					parentValue=$.trim($("#new_AboutWhat_id").val());
					childValue=$.trim($("#newKind_id").val());
					args={"abName":parentValue,"kindName":childValue,"msg":"both"};
				}
				//修改过
				$.get(webRoot+"saveKindAndAbout.action",args,function(data){
					if(data==true){
						var author = $.trim($("#author_id").val());
						var title  =$.trim($("#title_id").val());
						if(author==""||title=="" ){
							alert("作者栏或者标题栏没写");
						}else{
						
						
						var args2={"author":author,"title":title,"canShow":true,"time":new Date()};
						
						msgBlockClose();
						$.ajax({
							type:"post",
							url:webRoot+"saveDataAsDataBase.action",
							data:args2,
							datatype:"json",
							error:function(){
								var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
									msgBlockInit(dom,null,false,false,"static");
								msgBlockOpen();
							},
							success: function(msg) {
								msgBlockClose();
								var words=$("#edit").summernote('code');
								
								var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>正在上传主体文章</div></div>"
									msgBlockInit(dom,null,false,false,"static");
								msgBlockOpen();
								$.ajax({
									type:"post",
									url:webRoot+"saveWordTxt",
									data:words,
									datatype:"json",
									processData:false,
									error:function(){
										var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
											msgBlockInit(dom,null,false,false,"static");
										msgBlockOpen();
									},
									success: function(msg) {
										msgBlockClose();
										var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>正在上传简介</div></div>"
											msgBlockInit(dom,null,false,false,"static");
										msgBlockOpen();
										var intor = $('#intro').summernote('code');
										$.ajax({
											type:"post",
											url:webRoot+"saveIntroduce",
											data:intor,
											datatype:"json",
											processData:false,
											error:function(){
													msgBlockClose();
												var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
													msgBlockInit(dom,null,false,false,"static");
													msgBlockOpen();
											},
											success: function(msg) {
												msgBlockClose();
												var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-success progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传成功</div></div>"
													bindindCloseWeb(true);
													msgBlockInit(dom,null,false,true,"static");
													msgBlockOpen();
												},
											});
										},
									});	
								},
						});	
						}
					}
					else{
						msgBlockClose();
						var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>后台出现错误！</div></div>"
							msgBlockInit(dom,null,false,false,"static");
							msgBlockOpen();
					}
					
				});
			
				$('#edit').summernote('destroy');
				});
			
			//anthor button
				$("#saveit").click(function(){
					$("#progress_br_info").show();
					var parentValue=$("#AboutWhat_id").val();
					var childValue=$("#kind_id").val();
					var msg="";
					var args="";
					if(childValue==null){
						childValue="New Kind";
					}
					
					if(parentValue!="New AboutWhat"){
						if(childValue!="New Kind"){
							
							args={"abName":"","kindName":childValue,"msg":"null"};
						}else {
							childValue=$.trim($("#newOption_id").val());
							msg="one";
							args={"abName":parentValue,"kindName":childValue,"msg":msg};
						}
					}else if(parentValue=="New AboutWhat"){
						parentValue = $.trim($("#new_AboutWhat_id").val());
						childValue 	= $.trim($("#newOption_id").val());
						args={"abName":parentValue,"kindName":childValue,"msg":"both"};
					}
					//修改过
					$.get(webRoot+"saveKindAndAbout.action",args,function(data){
						if(data==true){
							var args2={"author":$.trim($("#author_id").val()),"title":$.trim($("#title_id").val()),"canShow":false,"time":new Date()};
								$.ajax({
									type:"post",
									url:webRoot+"saveDataAsDataBase.action",
									data:args2,
									datatype:"json",
									error:function(){
										var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
											msgBlockInit(dom,null,false,false,"static");
											msgBlockOpen();
									},
									success: function(msg) {
										var words=$("#edit").summernote('code');
										$.ajax({
											type:"post",
											url:webRoot+"saveWordTxt",
											data:words,
											datatype:"json",
											processData:false,
											error:function(){
												var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
													msgBlockInit(dom,null,false,false,"static");
													msgBlockOpen();
											},
											success: function(msg) {
													msgBlockClose();
												var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>正在上传简介</div></div>"
													msgBlockInit(dom,null,false,false,"static");
												var intor = $('#intro').summernote('code');
												
												$.ajax({
													type:"post",
													url:webRoot+"saveIntroduce",
													data:intor,
													datatype:"json",
													processData:false,
													error:function(){
															msgBlockClose();
														var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传出现错误</div></div>"
															msgBlockInit(dom,null,false,false,"static");
															msgBlockOpen();
													},
													success: function(msg) {
															msgBlockClose();
														var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-success progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>上传成功</div></div>"
															bindindCloseWeb(true);
															msgBlockInit(dom,null,false,true,"static");
															msgBlockOpen();
														},
												});
											},
										});	
									},
								});	
						}else{ 
							
							msgBlockClose();
							var dom = "<div class='progress'  style='margin-top: 1%;'><div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:100%'>后台出现错误！</div></div>"
								msgBlockInit(dom,null,false,false,"static");
								msgBlockOpen();
						}
						
					});
				
					$('#edit').summernote('destroy');
					});
			
		})
		//验证title
/*		$("#title_id").change(function(){
	//		var title=
		//});
		*/
		
		var editIntro = function() {
	  		$('#intro').summernote({
	  			lang:'zh-CN',
	  				height: 200,                 // set editor height
	  				minHeight: null,             // set minimum height of editor
	  				maxHeight: null,             // set maximum height of editor
	  				focus: true ,                 // set focus to editable area after initializing
					toolbar: false,
					focus: true,
	  			
	  		});
		}
		
		var edit = function() {
			var aboutWhat=$("#AboutWhat_id").val();
			var kind=$("#kind_id").val();
			var newkind="";
			var title=$.trim($("#title_id").val());
			kindName_global=kind;
			aboutWhatName_global=aboutWhat;
			if(kind=="New Kind"){
				newkind=$.trim($("#newKind_id").val());
				kindName_global=newkind;
				verifyKind(title);
			}else {
				verifyTitle(title);
			}

		};
		
		var show = function() {	 
	  		$(document).find("#edit").summernote('destroy');
	  		$(document).find("#show").hide();
		};