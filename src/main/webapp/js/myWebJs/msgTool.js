/**
 * 
 */

function msgBlockload(dom,title,isNeedKey,isButtonNeed,isNeedKeyboard){
	$.get(webRoot+"jsp/littleBar/msgBlock.jsp",function(data){
		$("body").prepend(data);

			$("#msgModal").modal({ 
				backdrop:isNeedKeyboard ,
				show:false
				})
		if(isNeedKey){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find("button").eq(0).show();
		}
		if(isButtonNeed){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-footer").find("button").show();
		}
		if(title!=null){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find(".modal-title").append(title);
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find(".modal-title").show();
		}
		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-body").append(dom);
	});
}

function msgBlockInit(dom,title,isNeedKey,isButtonNeed,isNeedKeyboard){

		if(isNeedKey){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find("button").eq(0).show();
		}
		$("#msgModal").modal({ 
			backdrop:isNeedKeyboard ,
			show:false
			})
		if(title!=null){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find(".modal-title").append(title);
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-header").find(".modal-title").show();
		}
		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-body").empty();
		$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-body").append(dom);
		
		if(isButtonNeed){
			$("#msgModal").find(".modal-dialog").find(".modal-content").find(".modal-footer").find("button").show();
		}
}

function msgBlockOpen(){
	$('#msgModal').modal({show:true});
}
function msgBlockClose(){
	$('#msgModal').modal({show:false});
}
function msgBlockDestory(){
	$('#msgModal').remove();
}
