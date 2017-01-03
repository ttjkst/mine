/**
 * 
 */
$("#fir").click(function(){
	$("#fir").parent().addClass(" active");
	if(webName_global=="首页"){
		return false;
	}else{
		webName_global="首页"
		$.post(webRoot+"setWebName.action",{"where":webName_global},function(data){
			addClassForHead("#fir");
			$("title").text("首页");
			webload();
		})
	}
})
$("#sec").click(function(){
	$("#sec").parent().addClass(" active");
	if(webName_global=="关于本站与我"){
		return false;
	}else{
		webName_global="关于本站与我"
			$.post(webRoot+"setWebName.action",{"where":webName_global},function(data){
				addClassForHead("#sec");
				$("title").text("关于本站与我");
				webload();
			})
	}
})
$("#thir").click(function(){
	$("#thir").parent().addClass(" active");
	if(webName_global=="关于生活"){
		return false;
	}else{
		webName_global="关于生活"
			$.post(webRoot+"setWebName.action",{"where":webName_global},function(data){
				addClassForHead("#thir");
				$("title").text("关于生活");
				webload();
			})
	}
})
$("#fou").click(function(){
	$("#fou").parent().addClass(" active");
	if(webName_global=="关于编程"){
		return false;
	}else{
		webName_global="关于编程"
			$.post(webRoot+"setWebName.action",{"where":webName_global},function(data){
				addClassForHead("#fou");
				$("title").text("关于编程");
				webload();
			})
	}
})

function addClassForHead(selector){
	console.log("sl"+selector);
	$(selector).parent().toggleClass(" active",true);
	$(selector).parent().parent().find("li").children().not(selector).parent().toggleClass("active",false);
}
/*data{"name":"xxxx","goWhere":"xxxx","index":"xxxx","addValue":"xxx"};
 * 
 * 
 * */
function loadButtom(){
	$.get(webRoot+"jsp/littleBar/buttom.jsp",function(data){
		$("body").append(data);
	})
}
