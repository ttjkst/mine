<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	  <div class="modal fade" id="loginModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalWaring">权限不足！</h4>
	      </div>
	      <div class="modal-body">
	      	<form>
	      
		      <div class="form-group">
			      	<label for="username" class="control-label" >用户名：</label>
			        <input type="text" value="" placeholder="请输入用户名" id="username" class="form-control"/>
		       </div>
		       <div class="form-group">
			       	<label for="password">密码：</label>
			       	<input type="password" value="" placeholder="请输入密码" id="password" class="form-control" />
		     	</div>
	     	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="loginButton">登入</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->	