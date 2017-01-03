/**
 * 
 */
function bindingIsUserON(){
	$(document).ready(function(){
		$.get(webRoot+"isLogin.action",function(data){
			if(data==true){
				$("#loginShow").replaceWith("<p class='navbar-text navbar-right' style='margin-bottom: -2%;' id='controlInfo'><a href='"+webRoot+"toEdit.action"+"'>管理员已经登入</a><button id='logout'>退出</button></p>");
				bindinglogOut();
			}else{
				
				brindloginButton();
				bindinglogin();
			}
		})
	})
}

function loginInit(){
		$.get(webRoot+"jsp/littleBar/login.jsp",function(data){
			$("body").prepend(data);
			$('#loginModal').modal('hide');
			bindingIsUserON();
		});
	}
function loginShow(title){
	$("#modalWaring").html(title);
	$('#loginModal').modal();
}
function destorylogin(){
	$(document).off("loginButton","click");
	$('#loginModal').remove();
}
function brindloginButton(){
$("#loginButton").click(function(){
	var password=$.trim($("#password").val());
	var username=$.trim($("#username").val());
	$.get(webRoot+"loginAndNotJump.action",{"password":password,"username":username},function(data){
		if(data==false){
			$("#modalWaring").html("密码或者账号错误");
			$("#modalWaring").addClass(" text-danger");
		}else{
	
			$("#loginShow").replaceWith("<p class='navbar-text navbar-right' style='margin-bottom: -2%;' id='controlInfo'><a href='"+webRoot+"toEdit.action"+"'>管理员已经登入</a><button id='logout'>退出</button></p>");
			bindinglogOut();
			$('#loginModal').modal('hide');
			webUser="user";
			if(webName_global=="关于生活"||webName_global=="关于编程"){
				
				renderPages(url_from_sh,data_from_sh,args_from_sh);	
			}
		}
		
	})
})
}
function bindinglogin(){
	$("#loginShow").click(function(){
	loginShow("请管理员登入");
	})
}
function bindinglogOut(){
$("#logout").click(function(){
	$.get(webRoot+"logOut.action",function(){
		if(webName_global=="关于生活"||webName_global=="关于编程"){
			webUser="others";
			renderPages(url_from_sh,data_from_sh,args_from_sh);	
		}
	});
	$("#controlInfo").replaceWith("<button class='btn btn-primary btn-sm navbar-btn navbar-right' style='margin-bottom: -2%;' id='loginShow'>管理员登入入口</button>");
	bindinglogin();
})
}
