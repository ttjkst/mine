/**
 * 
 */
var pageSize = 5;
var pageNo = 1;
function isEmail(str){ 
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(str); 
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
		})
		$("#preLeaveWordsBtn").click(function(){
			preleaveWords();
			reloadwebsc();
		})
		$("#subitWords").click(function(){
			$("#preLeaveWordsBtn").parent().find("p").remove();
				var key1=true;
				var key2=true;
				var key3=true;
				var key4=true;
				var msg="";
				var whose =  $("#othersInput").val();
				var mail  =  $("#othersMail").val();
				var qq =  $("#othersQQ").val();
				var sayWhat = $("#leaveWords").val();
				
				if(whose==""){
					key1=false;
					msg=msg+"您没有填写尊称<br>";
				}
				
				
				if(sayWhat==""){
					key4=false;
					msg=msg+"发表0个字评论是不道德的<br>"
				}
				if(qq!=""){
					if(!$.isNumeric(qq)){
						key2=false;
						msgqq="qq只能输入数字<br>";
						msg=msg+msgqq;
					}
				}
				if(mail!=""){
					if(!isEmail(mail)){
						key3=false;
						msg=msg+"e-mail格式不对<br>"
					}
				}
				if(!key4&&!key1&&!key2&&key3){
						msgBlockInit(msg,null,false,true,true);
						msgBlockOpen();
					return false;
				}
				else{
					
				$.post(webRoot+"saveLeaveWords.action",{"whose":whose,"QQ":qq,"email":mail,"sayWhat":sayWhat,"wordId":wordId},function(data){
					if(data!=null){
						getlastleaveWords();
					}
				})
				}
			})
	})

		function reloadwebsc(){
		var scroll_offset = $("#preLeaveWordsBtn").parent().parent().offset();
		 $("body,html").animate({
			   scrollTop:scroll_offset.top  //让body的scrollTop等于pos的top，就实现了滚动
			   },0);
		}
		function loadleaveWords(){
		$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
			if(data.length!=0){
				var temTime = null;
				for(var i=0;i<data.length;i++){
					temTime= new Date(data[i].timeInData);
					$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div></li><hr/>")
					
					}
			}else{
				$("#preLeaveWordsBtn").after("<p>这么精彩的文章竟然没有人评论！ ╥﹏╥</p>")
			}
			hasPreleaveWords();
			})
			}
		function NextleaveWords(){
			pageNo=pageNo+1;
			$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data.length!=0){
					destoryleaveWords();
					var temTime = null;
					for(var i=0;i<data.length;i++){
						temTime= new Date(data[i].timeInData);
						$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div></li><hr/>")
					}
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
			$.get(webRoot+"leaveWordsHasPreforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data==false){
					$("#preLeaveWordsBtn").hide();
				}
				else{
					$("#preLeaveWordsBtn").show();
				}
				hasNextleaveWords();
			 })
		}
		function destoryleaveWords(){
			$("#leave_list").empty();
		}
		function preleaveWords(){
			pageNo=pageNo-1;
			$.get(webRoot+"leaveWordsGetCurrentforOthers.action",{"pageSize":pageSize,"pageNo":pageNo,"wordId":wordId,"order":"desc"},function(data){
				if(data.length!=0){
					destoryleaveWords();
					var temTime = null;
					for(var i=0;i<data.length;i++){
						temTime= new Date(data[i].timeInData);
						$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:<small class='pull-right'>"+temTime.toLocaleDateString()+"评论</small></h4>"+data[i].saywhat+"</div></li><hr/>")
					}
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
							$("#leave_list").append("<li class='media'><div class='media-body'><h4>"+data[i].whose+"说:</h4>"+data[i].saywhat+"</div><hr/></li>")
						}
						hasPreleaveWords();
						reloadwebsc();
					}
			})
		}