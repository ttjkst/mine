/**
 * 
 */

//react.js
var React = require("react");
var ReactDOM = require('react-dom');

var msgManger = require("../common/msg.js");

//var Modal = require("./modal.js");
var Modal = require("./modal_reBuild.js");

var factory = React.createFactory(Modal);
var modalPusher = msgManger.getPublisher();
var LoginModal = React.createClass({
	render:function(){
		return <Modal pusher={modalPusher} content={this.props.content}/>
	}
})
module.exports=  LoginModal;
module.exports.open=function(){
	modalPusher.pushIt("open")
},
module.exports.close=function(){
	modalPusher.pushIt("close")
}