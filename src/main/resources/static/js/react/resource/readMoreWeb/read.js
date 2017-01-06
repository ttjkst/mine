/*
 * read more..
 * 
 *
 */



//react.js
var React = require("react");
var ReactDOM = require("react-dom");


var validiation = require("../common/validiation.js");
//get HeadBoxRight component
var HeaderBoxRight = require("../common/head_com.js")
//get LoginFrom component
var LoginFrom =require("../modal/modal_com.js")
//get Modal component
var LoginModal = require("../modal/LoginModal.js");

var HeaderBox = require("../common/head.js")
// get CommentUpBox component
var CommentUpBox = require("../comment/comment.js")
//head
var HeaderLi=React.createClass({
	render:function(){
		
		 return <li role="presentation"  className={this.props.css}><a  href="Javascript:void(0)">{this.props.text}</a></li>
	}
})
var HeaderBoxList=React.createClass({
	getInitialState: function() {
		var array=[
			{"key":1,text:"首页",			css:" "},
			{"key":2,text:"关于本站与我",	css:" "},
			{"key":3,text:"关于生活",		css:" "},
			{"key":4,text:"编程与思考",		css:" "}
			]
    	return {array: array}; 
  	},
	render:function(){
		var headerList = this.state.array.map(function(liDOM){
			return <HeaderLi key={liDOM.key} text={liDOM.text} css={" "} />
			})
		return <ul className="nav navbar-nav" style={{marginTop:"-2%"}}>
						{headerList}
						<li role="presentation" className="nav-pills">
							<a href="#" >
								<button className="btn btn-info" disabled>您正在阅读文章</button>
							</a>
						</li>
					</ul>
	}
})



//word
var Words = React.createClass({
	getInitialState:function(){
		return {title:"title",author:"author","time":new Date().toDateString(),word:"文章正在加载"}
	},
	componentDidUpdate:function(pre,next){
	//	console.log("componentDidUpdate");
	},
	componentDidMount:function(){
		this.refs.w.innerHTML="文章正在加载"
		var readWordPath = wordPath.replace("\\","/");
		console.log(readWordPath)
		setTimeout(
			$.post(webRoot+readWordPath,function(data){
				this.refs.w.innerHTML=data
			}.bind(this))
		,1000)
		
	},
	render:function(){
		//return 
		return ( 
				<div className="container"> 
   		<div id="context">
			<br />
			<div className="thumbnail" style={{marginBottom:"0%"}}>
				<div className="caption" style={{marginBottom:"10%"}}>
					<div className='text-center'>
						<h4>{this.state.title}</h4>
						<span className='glyphicon glyphicon-user'>{this.state.author}&nbsp;</span>
						<br/>
						<span className='glyphicon glyphicon-time'>:{this.state.time}</span>
					</div>
				    	<div ref="w">
				    	</div>
				</div>
			</div>
		</div>
</div >)
	}
})

//the context down
var isQQ = true;
var isEmail = true;
var CommentDownBox = React.createClass({
	getInitialState:function(){
		return {QQvalue:"",mail:"",name:"",textarea:"",totalChars:140,submitCss:"btn btn-default floatRight"}
	},
	getSubmitCss:function(){
		return isQQ&&isEmail?"btn btn-default floatRight":"btn btn-default floatRight disabled" 
	},
	handleSubmit:function(e){
		if(isQQ&&isEmail){
			if(this.state.textarea==""){
				return;
			}
			$.post(webRoot+"saveLeaveWord.action",{
				word:	this.props.wordId,
				qq:		this.state.QQvalue,
				whose:	this.state.name,
				sayWhat:this.state.textarea,
				email:	this.state.mail
				},function(result){
					this.props.father.reload();
				}.bind(this))//end with post
			
		}else{
			return;
		}
	},
	componentWillReceiveProps:function(){
	   this.setState({
		   QQvalue:"",mail:"",name:"",textarea:"",totalChars:140,submitCss:"btn btn-default floatRight"
	   })	
	  // this.refs.textarea.innerHtml="";
	},
	handleTextArea:function(e){
		this.setState({textarea:e.target.value,totalChars:(140-e.target.value.length)})
	},
	handleQQ:function(e){
		if(validiation.isQQ(e.target.value)){
			isQQ = true;
			this.setState({QQvalue:e.target.value,submitCss:this.getSubmitCss()})
		}else{
			isQQ = false;
		}
		this.setState({QQvalue:e.target.value,submitCss:this.getSubmitCss()})
	},
	handleMail:function(e){
		if(validiation.isEmail(e.target.value)){
			isEmail = true;
		}else{
			isEmail = false;
		}
		this.setState({mail:e.target.value,submitCss:this.getSubmitCss()})
	},
	handleName:function(e){
		this.setState({name:e.target.value})
	},
	render:function(){
		return (
				<div>
				   <div className="row">
				       <div className="col-xs-4">
				       	 <label htmlFor="othersInput">称呼</label>
				       	 <input type="text" value={this.state.name} className="form-control" onChange={this.handleName} placeholder="请写上您的尊称"/>
				         <label htmlFor="othersMail">邮箱</label>
				         <input type="text" value={this.state.mail} className="form-control" onChange={this.handleMail} placeholder="格式:xxx@xxx.com"/>
				         <label htmlFor="othersQQ">QQ</label>
				         <input type="text" value={this.state.QQvalue} className="form-control" onChange={this.handleQQ} placeholder="只允许输入数字"/>
				       </div>
			       </div>
			       <br />
			       <div className="from-group" id="textareaF">
			       <textarea  className="form-control" rows="6" id="leaveWords" value={this.state.textarea} maxLength="140" onChange={this.handleTextArea} ></textarea>
			       <p className="floatLeft">你还能输入
			       	<strong  id="canInWords">{this.state.totalChars}</strong>
			       		个字
			       </p>
			       <button className={this.state.submitCss} onClick={this.handleSubmit} >提交</button>
			       </div>
			       </div>
				)
	}
})
var ContextBox = React.createClass({
	reload:function(){
		this.forceUpdate();
	},	
	render:function(){
		return (
				<div>
				<Words />
				<br />
				<div className="container" >
					<CommentUpBox wordId={wordId} needDelete={false}> </CommentUpBox>
					<CommentDownBox wordId={wordId} father={this}> </CommentDownBox>
				</ div>
				</div>
				)
	}
})
//render
ReactDOM.render(
		<HeaderBox headerBoxRight={HeaderBoxRight} headerBoxList={HeaderBoxList}/>,
document.getElementById('head')
);
ReactDOM.render(
		<LoginModal  content={LoginFrom}/>,
		document.getElementById('modal')
);
ReactDOM.render(
	<ContextBox />,
 	document.getElementById('context')
);