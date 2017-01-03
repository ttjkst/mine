/**
 * 
 */

//	$.ajaxSetup({
//			  contentType:"UTF-8",
//			});
var args_from_sh = null;
var data_from_sh = null;
var url_from_sh  = null;
var searchName ="";
var totalPageNum = null;
var size =  5;
var currNo = null;
var pageSize = 6;
function renderPageHotWords(){
	console.log("webUser : "+ webUser)
	$("#hotWords").empty();
	$("#hotWords").parent().find("h3").text("");
	if(webUser=="user"){
		$("#hotWords").parent().find("h3").text("");
		$.get(webRoot+"getNewLeaveWordsNum.action",{"webName":webName_global},function(data){
			$("#hotWords").append("<button class='btn btn-primary' type='button' id='NumOfNewLW'> 新的评论 <span class='badge'>"+data+"</span></button>");
			
		})
	}else if(webUser=="others"){
		$("#hotWords").parent().find("h3").text("热门文章");
	$.get(webRoot+"getTenWords.action",{"aboutWhat":webName_global},function(data){
		if(data.length==0){
			$("#hotWords").append("<p>没有文章</p>");
		}else{
			for(var i=0;i<data.length;i++){
			$("#hotWords").append("<div class='container-fluid'><h6 class='glyphicon glyphicon-pencil pull-left'></h6><h5><a href='"+webRoot+"readmore.action?key="+data[i].wId+"'><span class='pull-left'>"+data[i].wTitle+"</span></a></h5><h5><span class='pull-right'>"+data[i].readedTimes+"</span></h5></div>");
			}
		}
	})
	}
}
	function doubleSeparator2oneS(arg){
		var arr="";
		arr=arg.split("\\\\");
		var reValue="";
		for(var i=0;i<arr.length;i++){
			reValue=reValue+arr[i];
			if(i!=arr.length-1){
				reValue=reValue+"\\";
			}
		}
		return reValue;
	}	
	
	function renderPages(url,data){
			
			
			$.ajax({
				type:"post",
				url:webRoot+url,
				data:data,
				datatype:"json",
				error:function(){
					console.log("网站服务器发生错误请通知网站作者");
					renderPagetools("");
				},
				success: function(msg) {
					totalPageNum = msg.totalPageNum;
					$("#buttom").hide();
					$("#pages").prevAll().remove();
					if(msg.length==0){
						renderPagetools();
					}else {
						for(var i=0;i<msg.length;i++){
							if(webUser=="others"){
							$("#pages").before("<div class='thumbnail'><div class='caption'><h3>"+msg[i].wTitle+"</h3><p id='p"+i+"'></p><a href='"+webRoot+"readmore.action?id="+msg[i].wId+"' target='_blank'><button type='button'  class='btn btn-default'>阅读更多</button></a></div></div>");	
							}else if(webUser=="user"){
							$("#pages").before("<div class='thumbnail'><div class='caption'><h3>"+msg[i].wTitle+"</h3><p id='p"+i+"'></p><a href='"+webRoot+"readMoreAndControl.action?id="+msg[i].wId+"' target='_blank'><button type='button'  class='btn btn-default'>进一部管理</button></a><p style='display:none' >"+msg[i].wId+"</p><button type='button'  class='btn btn-default btn-danger' style='float: right'>删除</button></div></div>");
							}
							$('#p'+i).load(webRootNotS+doubleSeparator2oneS(msg[i].introductionPath));
//							$.post(geturl,function(data){
//								$('#p'+i).append(data);
//							}),'html'
							$("#buttom").show();
							}
						renderPagetools();
						renderPageHotWords();
					}  
                },
			});
		}
		
		function renderPagetools(){
			$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
			$.get(webRoot+"/getMaxPageNum.action",function(data){
				totalPageNum = data;
				if(data==0){
					$("#Next").hide();
					$("#Previous").hide();	
				}else{
				$("#Next").show();
				$("#Previous").show();
			//	$(".pagination").find("li").not($(".pagination").find("li").eq(-1)).not($(".pagination").find("li").eq(0)).remove();
				
							console.log("-------------end----------------------------")
							var key = currNo/size + (currNo%size == 0 ? -1 : 0);
							key = parseInt(""+key+"")
							var beginNo = key*size+1;
							console.log("searchName is :"+searchName);
							var endNo = key*size+ size;
							if(endNo>totalPageNum){
								endNo = totalPageNum
							}
							if(beginNo>1){
								$("#Next").parent().before("<li class='disabled'><a>...</a></li>")
								
							}
							
							for(var i=beginNo;i<=endNo;i++){
								$("#Next").parent().before("<li><a>"+i+"</a></li>");
							}
							
							if(totalPageNum>endNo){
								$("#Next").parent().before("<li class='disabled'><a>...</a></li>");
							}
							
							if(totalPageNum>endNo){
								$("#Next").parent().before("<li ><a>"+totalPageNum+"</a></li>");
							}
							$("#Next").parent().prevAll(":contains("+currNo+")").addClass(" active")
							if(currNo==totalPageNum){
								$("#Next").hide();	
							}
							if(currNo==1){
								$("#Previous").hide();
							}
							
							console.log("------------------end-----------------------")
				}
				
			})
			
					
			
		}
		
		
		$(document).ready(function(){
			
			$.get(webRoot+"html/thiscontext/baseShow.html",function(data){
				$("#head_bar").after(data);
				$(document).off("click","#Previous");
				$(document).on("click","#Previous",function(){
					console.log("#pre");
					var data = {"doWhat":"pre","clickNo":0,"aboutWhat":webName_global,"searchName":searchName,"who":webUser};
					var url  = "getPageWords.action";
					data_from_sh=data;
					url_from_sh=url;
					currNo = currNo-1;
					renderPages(url,data);
				})
				$(document).off("click","#Next");
				$(document).on("click","#Next",function(){
					console.log("#Next");
					var data = {"doWhat":"next","clickNo":0,"aboutWhat":webName_global,"searchName":searchName,"who":webUser};
					var url  = "getPageWords.action";
					data_from_sh=data;
					url_from_sh=url;
					currNo = currNo+1;
					renderPages(url,data);
				})
				$(document).off("click","#search");
				$(document).on("click","#search",function(){
					var key=$.trim($("#searchText").val());
					if(key!=""){
						var data = {"pageNo":1,"pageSize":pageSize,"doWhat":"getSearch","searchName":key,"aboutWhat":webName_global,"who":webUser};
						var url  = "search.action";
						data_from_sh=data;
						url_from_sh=url;
						currNo = 1;
						searchName =  key;
						renderPages(url,data);
						
					}else{
						alert("搜索栏为空！");
					}
				})
				$(document).off("click",".pagination > li");
				$(document).on("click",".pagination > li",function(){
					
					var key=$(this).find("a").text();
					
					if(jQuery.isNumeric(key)){
						currNo = parseInt(key);
						var args = {"doWhat":"click"};
						var data = {"doWhat":"click","pageNo":key,"aboutWhat":webName_global,"searchName":searchName,"who":webUser};
						var url  = "getPageWords.action";
						args_from_sh=args;
						data_from_sh=data;
						url_from_sh=url;
						renderPages(url,data);
						$("body,html").animate({
							   scrollTop:0  //让body的scrollTop等于pos的top，就实现了滚动
							   },10);
					}
					
				})	
				
				//begin
				beginNo = 1;
				endNo = 5;
				currNo = 1;
				var args = {"doWhat":"null"};
				var data = {"pageNo":1,"pageSize":pageSize,"doWhat":"getAll","searchName":searchName,"aboutWhat":webName_global,"who":webUser};
				var url  = "search.action";
				args_from_sh=args;
				data_from_sh=data;
				url_from_sh=url;
				renderPages(url,data);
			})
		})
		
	$(function(){
		$(document).on("click","#NumOfNewLW",function(){
			window.open(webRoot+"userControl.action","_self");
		})
		$(document).on("click","#showRelust>div>div>button",function(){
			var key=$(this).prev().text();
			var dome = $(this);
			var showDome=dome.parent().parent().nextAll();
			$.post(webRoot+"delete.action",{"id":key},function(){
				dome.parent().parent().fadeOut('normal',function(){
					dome.remove();
				})
				showDome.fadeOut("slow",function(){
					showDome.fadeIn('slow');
				})
				
			});
		})
		
	})