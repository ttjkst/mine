/**
 * 
 */
function webinit(){
	var selector="";
	$.getScript(webRoot+"js/myWebJs/head.js");
	$.get(webRoot+"getWebName.action",function(data){
		webName_global=data.webName;
		webUser=data.whoUsed;
		if(data.webName=="首页"||data.webName=="关于本站与我"){
			if(data.webName=="首页"){
				selector="#fir";
				var loadurl="html/thiscontext/main.html";
			}else{
				selector="#sec";
				var loadurl="html/thiscontext/aboutMeAndWeb.html";
			}
			$.get(webRoot+loadurl,function(data){
				$("#head_bar").after(data);
				addClassForHead(selector);
			},"html")
		}else{
			if(data.webName=="关于生活"){
				selector="#thir";
			}
			else{
				selector="#fou";
			}
			$.getScript(webRoot+"js/myWebJs/showWeb.js",function(){
				addClassForHead(selector);
			});
		}
		$("title").text(data.webName);
		//alert(data.webName);
	})
}
function webload(){
	$("#buttom").hide();
	$("#head_bar").next().remove();
	$.get(webRoot+"getWebName.action",function(data){
		webName_global=data.webName;
		webUser=data.whoUsed;
		if(data.webName=="首页"||data.webName=="关于本站与我"){
			$("script[src='"+webRoot+"js/showWeb.js']").remove();
			if(data.webName=="首页"){
				var loadurl="html/thiscontext/main.html";
			}else{
				var loadurl="html/thiscontext/aboutMeAndWeb.html";
			}
			$.get(webRoot+loadurl,function(data){
				$("#head_bar").after(data);
				$("#buttom").show();
			},"html")
		}else{
			$.getScript(webRoot+"js/myWebJs/showWeb.js",function(data){
				
			});
		}
	})
}

function loadbuttom(){
	$.get(webRoot+"jsp/littleBar/buttom.jsp",function(data){
		$("body").append(data);
	},"html")
}
function destroyMiddle(){
	$("#head_bar + div").remove();
}

function loadButtom(){
	$.get(webRoot+"jsp/littleBar/buttom.jsp",function(data){
		$("body").append(data);
	})
}
