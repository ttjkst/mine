//react.js
var React = require("react");
var ReactDOM = require('react-dom');

var msgManger = require("./msg.js");

//var Modal = require("./modal.js");
var Modal = require("../modal/LoginModal.js")
//get LoginFrom component
var LoginFrom =require("../modal/modal_com.js")




var	headPusher = msgManger.getPublisher();
var HeaderBoxRight= React.createClass({
				statics:{
					getMsgPusher:function(){
						return headPusher
					}
				},
				getInitialState:function(){
					return {key:false,ober:msgManger.getOber()}
				},
				componentDidMount:function(){
					var ober =  this.state.ober;
					ober.setDo(function(msg){
						this.setState({key:msg.isLogin})
					}.bind(this))
					LoginFrom.getMsgPusher().subscribed(ober);
				},
				componentWillUnmount:function(){
					LoginFrom.getMsgPusher().unsubscribed(this.state.ober)
  				},
				hindleLogout:function(){
					headPusher.pushIt("logout")
					
					$.get(webRoot+"logOut.action",function(msg){
						LoginFrom.getMsgPusher().pushIt({isLogin:false})
					}.bind(this));
				},
				hindleClick:function(){	
					Modal.open();
					headPusher.pushIt("login")
				},
				render: function(){
					if(this.state.key==null){
						return null
					}else if(this.state.key==true){
						return (
						<p className="navbar-text navbar-right" style={{marginBottom: "-2%"}} >
						<a href={webRoot+"toEdit.action"}>管理员已经登入</a>
						<button onClick={this.hindleLogout}>退出</button>
						</p>
					)
					}
					return (<button className="btn btn-primary btn-sm navbar-btn navbar-right" style={{marginBottom:"-2%"}} onClick={this.hindleClick}>管理员登入入口</button>)
				}
			})

module.exports = HeaderBoxRight;
