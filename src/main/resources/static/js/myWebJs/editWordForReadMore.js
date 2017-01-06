/**
 * 
 */	var kindName_global="";
 	var aboutWhatName_global="";
 	var isWordChange
 	function bindindCloseWeb(isNeedFatherWebFursh){
 		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-footer").find("button").text("离开网站");
 		$(document).on("click","#msgModal > .modal-dialog > .modal-content > .modal-footer > button",function(){
 			$.get(webRoot+"reloadEditweb.action",new Date());
 			 window.opener.location.reload();
 			 window.close();
 		})
 	}
 	function updateKandA(args,isKindNew){
 		//args={"abName":parentValue,"kName":childValue,"msg":"both"};
 		loadingStripeShow("正在检验aboutWhat与种类是否要更改",false);
 		var updateArgsJson ={};
 		var new_abName = null;
 		var new_kName = null; 
 		if(args.abName != aboutWhatName_global_server){
 			updateArgsJson.abName =  args.abName;
 		}else{
 			updateArgsJson.abName =  aboutWhatName_global_server;
 		}
 		if(args.kName !=kindName_global_server){
 			updateArgsJson.KindName = args.kName;
 		}
 		if(updateArgsJson.KindName!=null){
 			loadingStripeShow("正在更改....",false);
 			$.ajax({
 				type:"post",
 				url:webRoot+"updateKAndW.action",
 				data:updateArgsJson,
 				datatype:"json",
 				error:function(){
 					loadingStripeError("错误!!!");
 				},
 				success: function(msg) {
 					loadingStripeSuccess("成功！！！");
 					},
 				}); 		
 			}else{
 				loadingStripeShow("无需更改",true);
 			}
 	}
 	function updateTAAndShow(title,author,canShow){
 		//args={"abName":parentValue,"kName":childValue,"msg":"both"};
 		loadingStripeInit("正在检验作者、标题和是否展示选项要更改",false);
 		var updateArgsJson = {};
 		if(title != title_global_server){
 			updateArgsJson.key="";
 			updateArgsJson.title = title;
 		}
 		if(author != author_global_server){
 			updateArgsJson.key="";
 			updateArgsJson.author = author;
 		}
 		if(canShow != canShow_global_server){
 			updateArgsJson.key="";
 			updateArgsJson.canShow = canShow;
 		}
 		
 		
 		if(updateArgsJson.key!=null){
 			loadingStripeShow("正在更改....",false);
 			$.ajax({
 				type:"post",
 				url:webRoot+"updateTAAndShow.action",
 				data:updateArgsJson,
 				datatype:"json",
 				error:function(){
 					loadingStripeError("错误!!!");
 				},
 				success: function(msg) {
 					loadingStripeSuccess("成功！！！");
 					
 					},
 				});
 		}else{
 			
 			loadingStripeShow("无需更改",true);
 		}
 	}
 	function updateText(who,text){
 		var srcText;
 		var destText;
 		var doWhat;
 		if(who =="introduce"){
 			loadingStripeInit("正在检验简介是否要更改");
 			srcText=$.md5(introduceText_global_server);
 			destText=$.md5($('#intro').summernote('code'));
 			doWhat="updateIntroduce";
 		}
 		else if(who == "words"){
 			loadingStripeInit("正在检验正文是否要更改",false);
 			srcText=$.md5(editText_global_server);
 			destText=$.md5($('#edit').summernote('code'));
 			doWhat = "updateWord";
 		}
 		//alert(who+":"+"srcText:"+srcText+""+"destText:"+destText+""+"they are same:"+srcText==destText);
 		if(srcText!=destText){
 			loadingStripeShow("正在更改...",false);
 		$.ajax({
			type:"post",
			url:webRoot+doWhat,
			data:text,
			datatype:"json",
			processData:false,
			error:function(){
				loadingStripeError("错误！！！");
			},
			success: function(msg) {
				loadingStripeSuccess("成功！！");
				},
			});
 		}else{
 			loadingStripeShow("无需更改",true);
 		}
 	//	$('#edit').summernote('destroy');
	//	$('#intro').summernote('destroy');
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
	function bindChangeNewKindId(){
							
	}
	function loadingStripeInit(showValue){
			setTimeout(function(){
				msgBlockClose();
				var dom = "<div class='progress' ><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0'aria-valuemax='100' style='width:100%'>"+showValue+"</div></div>";
				msgBlockInit(dom,null,false,false,"static");
				msgBlockOpen();
				},1000);

	}
	function loadingStripeShow(showValue,isCanClose){		
		setTimeout(function(){
			msgBlockClose();
			var dom = "<div class='progress'><div class='progress-bar progress-bar-info progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0'aria-valuemax='100' style='width:100%'>"+showValue+"</div></div>";
			msgBlockInit(dom,null,false,isCanClose,"static");
			msgBlockOpen();
			},1000);

	}
	function loadingStripeSuccess(showValue){
		setTimeout(function(){
			msgBlockClose();
			bindindCloseWeb(true);
			var dom = "<div class='progress'  ><div class='progress-bar progress-bar-success progress-bar-striped ' role='progressbar' aria-valuenow='60' aria-valuemin='0'aria-valuemax='100' style='width:100%'>"+showValue+"</div></div>";
			msgBlockInit(dom,null,false,true,"static");
			msgBlockOpen();

			},1000);

	}
	function loadingStripeError(showValue){
		setTimeout(function(){
			msgBlockClose();
			var dom = "<div class='progress' ><div class='progress-bar progress-bar-danger ' role='progressbar' aria-valuenow='60' aria-valuemin='0'aria-valuemax='100' style='width:100%'>"+showValue+"</div></div>";
			msgBlockInit(dom,null,false,false,"static");
			msgBlockOpen();
			},1000);

	}
	function verifyTitle(title){
		somehasNothing("#title_id");
		if(title==""){
			somehasError("#title_id");
		}else{
			$.get(webRoot+"verifyTitle.action",{"title":title,"kindName":kindName_global,"aboutWhat":aboutWhatName_global},function(data){
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
	function verifyKind(title){
				somehasNothing("#newKind_id");
				if(kindName_global==""){
					somehasError("#newKind_id");
				}else{
					$.get(webRoot+"verify.action",{"msg":kindName_global,"parentMsg":aboutWhatName_global,"doWhat":"verifyKind","webDoWhat":"readMore"},function(data){
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
$(document).ready(function(){
				$("#title_id").val(title_global_server);
				$("#author_id").val(author_global_server);
				
				if(canShow_global_server=="true"){
					$("#canShow").val("展示");
					}
				else{
					$("#canShow").val("不展示");
				}
				$.get(webRoot+"getAboutwhat.action",function(data){
				if(data.length!=0){
					$("#newOptionGroup").hide();
					$("#newWhatAboutGroup").hide();
					for(var i=0;i<data.length;i++){
					$("#AboutWhat_id").append("<option>"+data[i].name+"</option>");
					}
					$("#AboutWhat_id").val(aboutWhatName_global_server );
					var msg=$("#AboutWhat_id").val();
					$.get(webRoot+"getKindsByMsg.action",{"msg":msg},function(data){
						if(data.length!=0){
							$("#kind_id").append("<option>"+"New Kind"+"</option>");
								for(var i=0;i<data.length;i++){
									$("#kind_id").append("<option>"+data[i].kName+"</option>");
								}
							$("#kind_id").val(kindName_global_server);	
							$("#newKindGroup").hide();
							}else{
								alert("链接出现错误");
								}
						bindChangeNewKindId();	
					},"json");
				}else{
					alert("链接出现错误");
				}
				
			},"json")
			
		

		});
		
		
			
		
		
		
		
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
			
		/*	$("#flush").click(function(){
				$.post(webRoot+"reloadEditweb.do",new Date());
				location.reload();
			})*/
			
			$("#AboutWhat_id").change(function(){
				$("#kind_id").empty();
				var msg=$("#AboutWhat_id").val();
				
				
				$.get(webRoot+"getKindsByMsg.action",{"msg":msg},function(data){
					if(data.length!=0){
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
			//update
			$("#updateIt").click(function(){
				
				var parentValue=$("#AboutWhat_id").val();
				var childValue=$("#kind_id").val();
				var msg="";
				var args="";
				if(childValue==null){
					childValue="New Kind";
				}
				
				if(parentValue!="New AboutWhat"){
					if(childValue!="New Kind"){
						
						args={"abName":parentValue,"kName":childValue,"msg":"null"};
						updateKandA(args, false);
					}else {
						childValue=$.trim($("#newKind_id").val());
						msg="one";
						args={"abName":parentValue,"kName":childValue,"msg":msg};
						updateKandA(args, true);
					}
				}else if(parentValue=="New AboutWhat"){
					parentValue=$.trim($("#new_AboutWhat_id").val());
					childValue=$.trim($("#newKind_id").val());
					args={"abName":parentValue,"kName":childValue,"msg":"both"};
				}
				var author = $.trim($("#author_id").val());
				var title  = $.trim($("#title_id").val());
				var canShow  = $("#canShow").val();
				var cov_canShow;
				if(canShow == "不展示"){
					cov_canShow="false";
				}
				else{
					cov_canShow="true";
				}
				if(author==""||title=="" ){
					alert("作者栏或者标题栏没写");
				}else{
				updateTAAndShow(title, author, cov_canShow);
				}
				updateText("introduce",$('#intro').summernote('code'));
				//修改过
				updateText("words",$('#edit').summernote('code'));
				});
		})
			//anthor button
				
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
		
		var show = function(){
  		$('#edit').summernote('destroy');
  		$("#show").hide();
		};