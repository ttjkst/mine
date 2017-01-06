//react.js
var React = require('react');
var ReactDOM = require('react-dom');

var msgManger = require("../common/msg.js")
var loginMsgPusher = msgManger.getPublisher();
var LoginForm = React.createClass({
				statics:{
					getMsgPusher:function(){
						return loginMsgPusher
					}
				
				},
				getInitialState: function() {
    				return {
						password:"",
						username:"",
						hasCommit:false
					};
  				},
				hindlePassword:function(e){
					this.setState({password:e.target.value})
				}
				,
				hindleUsername:function(e){
					this.setState({username:e.target.value})
				},
				componentDidMount:function(){
					$.post(webRoot+"isLogin.action",function(result){
						loginMsgPusher.pushIt({isLogin:result});
						if(result){
							this.props.father.changeTitle("登入界面","")
						}else{
							this.props.father.changeTitle("登入界面","")
						}
					}.bind(this))
					
				},
				hindleLogin:function(){
					isLogin=true;
					if(this.state.hasCommit){
						return;
					}
					var args={username:this.state.username,password:this.state.password}
					this.state.hasCommit = true;
					$.post(webRoot+"loginAndNotJump.action",args,function(result){
						loginMsgPusher.pushIt({isLogin:result});
						if(result){
							this.props.father.handleClose();
						}else{
							this.props.father.changeTitle("错误","text-danger")
						}
						this.state.hasCommit = false;
					}.bind(this))	
				},
				render:function(){
					return 	(
					<div className="modal-body">
							    <form>
								      <div className="form-group">
									      	<label  className="control-label"  >用户名：</label>
									        <input  type="text" value={this.state.username} placeholder="请输入用户名"  onChange={this.hindleUsername} className="form-control"/>
								       </div>
								       <div className="form-group">
									       	<label className="control-label" >密码：</label>
									       	<input  type="password" value={this.state.password} placeholder="请输入密码" onChange={this.hindlePassword}  className="form-control" />
								     	</div>
								     	<button type="button" className="btn btn-primary" onClick={this.hindleLogin} >登入</button>
							     </form>  	
							      </div>
							     
								
							     )
				}
		})			
module.exports = LoginForm;	
