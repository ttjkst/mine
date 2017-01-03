//react.js
var React = require("react");
var ReactDOM = require("react-dom");

var msgManger = require("../common/msg.js");
var ModalBox= React.createClass({
	getInitialState:function(){
		//$(this.refs.root).modal({show:false})
		return {ober:msgManger.getOber(),title:"正在调整。。。",titleClassName:""}
	},
	changeTitle:function(title,className){
		this.setState({title:title,titleClassName:className})
	},
	handleOpen:function(){
		
		$(this.refs.root).modal('show');
		//$(document).find("#modalRoot").modal('show');
		
	},
	handleClose:function(){
		
		$(this.refs.root).modal('hide');
		console.log("close");
		//$(document).find("#modalRoot").modal('hide');
	},
	componentDidMount:function(){
		var ober = this.state.ober;
		$(this.refs.root).modal('hide');
		ober.setDo(function(msg){
			if(msg=="open"){
				this.handleOpen();
			}else if(msg=="close"){
				this.handleClose();
			}else{
				
			}
		}.bind(this))
		this.props.pusher.subscribed(ober)
		
	
	},
	componentWillUnmount:function(){
			this.props.pusher.unsubscribed(this.state.ober)
		},
	render:function(){
		var Content = this.props.content;
		return   (
			
				<div className="modal fade" id="modalRoot" ref="root">
				  <div className="modal-dialog">
				    <div className="modal-content">
				      <div className="modal-header">
				        <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 className={this.state.titleClassName} >{this.state.title}</h4>
				      </div>
				      <div className="modal-body">
				      	<Content father={this}/>
				      </div>		
				      <div className="modal-footer">
				        <button type="button" className="btn btn-default" data-dismiss="modal">关闭</button>
				        
				      </div>
				    </div>
				  </div>
		</div>
				)
	}
})
	
module.exports=ModalBox;