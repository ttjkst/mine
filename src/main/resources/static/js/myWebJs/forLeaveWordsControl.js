/**
 * 
 */
var pageSize = 5;
var pageNo = 1;

function bindingQike(){
	$(document).off("click","#leave_list > li > div ~ div > buttom:even");
	$(document).on("click","#leave_list > li > div ~ div > buttom:even",function(){
		var id=$(this).prev().text();
		var key=$(this).text();
	if(key=="未阅读"){
		$(this).slideUp("fast","swing",function(){
			//$(this).slideUp("fast");
			$(this).toggleClass(" btn-danger");
			$(this).toggleClass(" btn-info",true);
			$(this).text("已经阅读");
			$(this).fadeIn("slow","swing",function(){
				$.get(webRoot+"/changHasRead.action",{"id":id,"hasRead":true});
			});
		});
		
		}else{
			$(this).slideUp("fast","swing",function(){
				//$(this).slideUp("fast");
				$(this).toggleClass(" btn-danger",true);
				$(this).toggleClass(" btn-info");
				$(this).text("未阅读");
				$(this).fadeIn("slow","swing",function(){
					$.get(webRoot+"/changHasRead.action",{"id":id,"hasRead":false});
				});
			});
		}
		});
	$(document).off("click","#leave_list > li > div ~ div > buttom:odd");
	$(document).on("click","#leave_list > li > div ~ div > buttom:odd",function(){
		var id=$(this).prev().prev().text();
		
			$.get(webRoot+"delete.action",{"id":id});
			window.location.reload();
				});
}

	$(function(){
	$("#leaveWords").keyup(function(){
			var leaveWords=$("#leaveWords").val().length;
			
			if(leaveWords>=140){
				$("#canInWords").text(140-leaveWords);
			}else{
				$("#canInWords").text(140-leaveWords);
			}
		})
		$("#nextLeaveWordsBtn").click(function(){
			NextleaveWords();
			reloadwebsc();
			bindingQike();
		})
		$("#preLeaveWordsBtn").click(function(){
			preleaveWords();
			reloadwebsc();
			bindingQike();
		})
		$("#subitWords").click(function(){
			$("#preLeaveWordsBtn").parent().find("p").remove();
				var whose =  $("#othersInput").val();
				var mail  =  $("#othersMail").val();
				var qq =  $("#othersQQ").val();
				var sayWhat = $("#leaveWords").val();
				$.post(webRoot+"saveLeaveWords.action",{"whose":whose,"QQ":qq,"email":mail,"sayWhat":sayWhat,"wordId":wordId},function(data){
					if(data!=null){
						getlastleaveWords();
					}
				})
			})
	})

		function reloadwebsc(){
		var scroll_offset = $("#preLeaveWordsBtn").parent().parent().offset();
		 $("body,html").animate({
			   scrollTop:scroll_offset.top  //让body的scrollTop等于pos的top，就实现了滚动
			   },100);
		}
		function loadleaveWords(){
		$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
			if(data.length!=0){
				var temTime = null;
				for(var i=0;i<data.length;i++){
						temTime= new Date(data[i].timeInData);
						var noRead="<h4 style='display: none;' >"+data[i].id+"</h4><buttom class='btn btn-danger'>未阅读</buttom>";
						var hasRead="<h4 style='display: none;' >"+data[i].id+"</h4><buttom class='btn btn-info'>已经阅读</buttom>";
						
						if(data[i].hasRead==false){
							temRead=noRead;
						}else{
							temRead=hasRead;
						}
						$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div><div class='pull-right'>"+temRead+"<buttom class='btn btn-danger'>删除</buttom></div></li><hr/>")
						//alert($("#leave_list > li > div ~ div > buttom").html());
				}
				bindingQike();
			}else{
				$("#preLeaveWordsBtn").after("<p>这么精彩的文章竟然没有人评论！ ╥﹏╥</p>")
			}
			hasNextleaveWords();
			hasPreleaveWords();
			})
			}
		function NextleaveWords(){
			pageNo=pageNo+1;
			$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data.length!=0){
					destoryleaveWords();
					for(var i=0;i<data.length;i++){
						temTime= new Date(data[i].timeInData);
						var noRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-danger'>未阅读</buttom>";
						var hasRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-info'>已经阅读</buttom>";
						
						if(data[i].hasRead==false){
							temRead=noRead;
						}else{
							temRead=hasRead;
						}
						$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div><div class='pull-right'>"+temRead+"<buttom class='btn btn-danger'>删除</buttom></div></li><hr/>")
					}
					hasNextleaveWords();
					hasPreleaveWords();
				}
				})
		}
		function hasNextleaveWords(){
			$.get(webRoot+"leaveWordsHasNextforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data==false){
					$("#nextLeaveWordsBtn").hide();
				}
				else{
					$("#nextLeaveWordsBtn").show();
				}
				})
		}
		function hasPreleaveWords(){
			$.get(webRoot+"leaveWordsHasNextforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data==false){
					$("#preLeaveWordsBtn").hide();
				}
				else{
					$("#preLeaveWordsBtn").show();
				}
				})
		}
		function destoryleaveWords(){
			$("#leave_list").empty();
		}
		function preleaveWords(){
			pageNo=pageNo-1;
			$.get(webRoot+"leaveWordsHasNextforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data.length!=0){
					destoryleaveWords();
					for(var i=0;i<data.length;i++){
						temTime= new Date(data[i].timeInData);
						var noRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-danger'>未阅读</buttom>";
						var hasRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-info'>已经阅读</buttom>";
						
						if(data[i].hasRead==false){
							temRead=noRead;
						}else{
							temRead=hasRead;
						}
						$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div><div class='pull-right'>"+temRead+"<buttom class='btn btn-danger'>删除</buttom></div></li><hr/>")
					}
					hasNextleaveWords();
					hasPreleaveWords();
				}
				})
		}
		function getlastleaveWords(){
			pageNo=1
				$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
					if(data.length!=0){
						destoryleaveWords();
						for(var i=0;i<data.length;i++){
							temTime= new Date(data[i].timeInData);
							var noRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-danger'>未阅读</buttom>";
							var hasRead="<h4 style='display: none;'>"+data[i].id+"</h4><buttom class='btn btn-info'>已经阅读</buttom>";
							
							if(data[i].hasRead==false){
								temRead=noRead;
							}else{
								temRead=hasRead;
							}
							$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div><div class='pull-right'>"+temRead+"<buttom class='btn btn-danger'>删除</buttom></div></li><hr/>")
						}
						hasNextleaveWords();
						hasPreleaveWords();
						reloadwebsc();
					}
			})
		}
	