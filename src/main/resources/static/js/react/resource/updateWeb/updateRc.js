/**
 * 
 */
/**
 * 
 */
var React = require("react");
var ReactDOM = require("react-dom");


//get HeadBoxRight component
var HeaderBoxRight = require("../common/head_com.js")
//get LoginFrom component
var LoginFrom =require("../modal/modal_com.js")
//get Modal component
// can not  var Modal = require("./modal.js")
var LoginModal = require("../modal/LoginModal.js");

// get req tool
var req = require("../common/re.js")
//get Head componenti

var MsgModal = require("../modal/modalMsg.js");
var HeaderBox = require("../common/head.js")
//get pusher-ober
var msgManger = require("../common/msg.js")

var CommentNotEditBox = require("../comment/comment.js")

var fromPusher = msgManger.getPublisher();
var InfoPusher = msgManger.getPublisher();

//for words save
var wordText = "";
//for introduce ;
var intorText= "";

//for words-src
var srcWordText="";
//for introduce -src;
var srcIntorText ="";


var uploadBymultipart = function(url,method,isAsync,fromData,_this){
	
	req({
		url:url,
		method:method,
		isAsync:isAsync,
		callbacks:{
			load:function(result){
				console.log("你好")
				console.log(_this);
				_this.handleSuccess("上次成功请离开（提啊示自己请按流程行事，开发不完全）");
			}.bind(_this),
			error:function(result){
				console.log("不好")
				_this.handleError("上次失败请自己查看后台（提啊示自己请按流程行事，开发不完全）");
			}.bind(_this)
		}
	},fromData)
	
}

var handleSumbit = function(title,author,kindName,aboutWhat,isNeedCreate,isNeedPush,_this){
	var args = {"abName":aboutWhat,"kindName":kindName,"msg":isNeedCreate};
	console.log("msg:"+args.msg);
	var form = new FormData();
	var word = null;
	var intor = null;
	
	if(srcWordText!=wordText){
		word = new Blob([wordText],{"type":"text"});
	}
	if(srcIntorText != intorText){
		intor = new Blob([intorText],{"type":"text"});
	}
	form.append("wordId",wordId);
	form.append("wordText",word);
	form.append("intor",intor);
	form.append("author",author);
	form.append("canShow",isNeedPush);
	form.append("abName",aboutWhat);
	form.append("kindName",kindName);
	form.append("msg",isNeedCreate);
	form.append("title",title);
	var url=webRoot+"/update.action";
	uploadBymultipart(url,"post",true,form,_this);
}


var Info = React.createClass({
	getInitialState:function(){
		return {msg:"",ober:msgManger.getOber()}
	}
	,
	handleClose:function(){
		MsgModal.close();
	},
	handleSuccess:function(msg){
		this.props.father.changeTitle("success√","text-info");
		this.setState({msg:msg});
		MsgModal.open();
	},
	handleError:function(msg){
		this.props.father.changeTitle("后台出现错误！","text-danger");
		this.setState({msg:msg});
		MsgModal.open();
	},
	componentDidMount:function(){
		var ober = this.state.ober;
		ober.setDo(function(msg){
			this.props.father.changeTitle("后台正在处理：","");
			handleSumbit(
					msg.title,
					msg.author,
					msg.kindName,
					msg.aboutWhatName,
					msg.isNeedCreate,
					msg.isNeedPush,
				
					this
					);
		}.bind(this))
		fromPusher.subscribed(this.state.ober);
	},
	componentWillUnmount:function(){
		fromPusher.unsubscribed(this.state.ober);
	},
	render:function(){
		return (<div>{this.state.msg}</div>);
	}
})


var inputTitlePusher = msgManger.getPublisher()
var submitPuhser = msgManger.getPublisher()

var HeaderLi=React.createClass({
	render:function(){
		return  <li role="presentation"  className={this.props.css}><a  href="Javascript:void(0)">{this.props.text}</a></li>
	}
})

var Li = React.createClass({
	handle:function(e){
		this.props.fatherhandle(this.props.name)	
	},
	render:function(){
		return <li><a onClick={this.handle} href="Javascript:void(0)">{this.props.name}</a></li>	
	}
})
var FromAboutEdit = React.createClass({
	getInitialState:function(){
		return {author:author,
			title:title,
			opArray1:[],
			opArray2:[],
			selected1:aboutWhat,
			selected2:kindName,
			newKind:"",
			ober:msgManger.getOber(),
				}
	},
	handleTitle:function(e){
		(this.state.selected2=="选择小分类"&&(this.state.selected2=="新的分类"&&this.state.newKind==""))?
				"":this.setState({title:e.target.value});
		inputTitlePusher.pushIt($.trim(e.target.value));
	},
	handleAuthor:function(e){
		this.setState({author:e.target.value});
	},
	componentDidMount:function(){
		$.get(webRoot+"getAboutwhat.action",function(data){
			this.setState({opArray1:data});
			if(data==null){
				return;
			}
		}.bind(this))
		var arg = {"msg":this.state.selected1}
		$.get(webRoot+"getKindsByMsg.action",arg,function(data){
			this.setState({opArray2:data,newKind:""});
			inputTitlePusher.pushIt(this.state.title);
		}.bind(this))
		
		var ober = this.state.ober;
		ober.setDo(function(msg){
			args ={
					"id":wordId,
					"title":$.trim(this.state.title),
					"kindName":this.state.selected2=="新的分类"?$.trim(this.state.newKind):this.state.selected2,
					"aboutWhatName":this.state.selected1,
					"webDoWhat":"edit"
						}
			$.get(webRoot+"verifyTitle.action",args,
				function(data){
				args.result = data;
				args.author=this.state.author;
				args.isNeedCreate=this.state.selected2=="新的分类"?"one":"null";
				args.isNeedPush=(this.msg=="发表"?true:false);
				fromPusher.pushIt(args);
			}.bind(this,msg))
			
			
		}.bind(this))
		
		submitPuhser.subscribed(ober);
	},
	componentWillUnmount:function(){
		submitPuhser.unsubscribed(ober);
	},
	handleOpsAboutWhat:function(msg){
		var arg = {"msg":msg}
		this.setState({selected1:msg})
		$.get(webRoot+"getKindsByMsg.action",arg,function(data){
			this.setState({opArray2:data,selected2:"选择小分类",newKind:""});
			inputTitlePusher.pushIt("");
		}.bind(this))
	},
	handleOpsKind:function(msg){		
			this.setState({selected2:msg,newKind:""});
			msg=="选择小分类"||msg=="新的分类"?inputTitlePusher.pushIt(""):inputTitlePusher.pushIt($.trim(this.state.title));
	},
	handleNewKind:function(e){
		if(this.state.selected2=="选择小分类"){
			return;
		}
		this.setState({newKind:e.target.value});
		if(e.target.value==""){
			inputTitlePusher.pushIt("");
		}else{
			inputTitlePusher.pushIt($.trim(this.state.title));
		}
	},
	render:function(){
		var opsAboutWhats = this.state.opArray1.map(function(op){
			return (
					<Li  key={op.aid} fatherhandle={this.handleOpsAboutWhat} name={op.name}></Li>
					)
		}.bind(this))
		
		
		var opsKinds = this.state.opArray2.map(function(op){
			return (
					<Li  key={op.kId} fatherhandle={this.handleOpsKind} name={op.kName}></Li>
					)
		}.bind(this))
		//judge whether the input for "newkind" can work or not 
		var newKindInput = this.state.selected2=="新的分类"?
			(
			<input type="text" value={this.state.newKind} onChange={this.handleNewKind}
		className="form-control" placeholder="请填写新的种类"  />
		)
		:
		(
		<input type="text" value={this.state.newKind} onChange={this.handleNewKind}
		className="form-control" placeholder={this.state.selected2=="选择小分类"?"此时输入框不能使用":"此时不需要输入框"} disabled/>
		)		
		//judge whether the input for title can work or not 
		var titleInput = (this.state.selected2=="选择小分类"||this.state.selected2=="新的分类"&&this.state.newKind=="")?
				(
				<div className="input-group has-feedback">
					<span className="input-group-btn">
						<button className="btn btn-default disabled" type="button" href="Javascript:void(0)">
							<span className="glyphicon glyphicon-certificate"></span>
						</button>
					</span>	
					<input type="text" name="title" 
						value={(this.state.selected2=="选择小分类"||this.state.selected2=="新的分类"&&this.state.newKind=="")?"":this.state.title} 
						onChange={this.handleTitle}  id="title_id" placeholder="此时不能使用输入框" 
						className="form-control"  disabled />
				</div>
				)
				:
				(
				<div className="input-group has-feedback">
					<span className="input-group-btn">
						<button className="btn btn-default" type="button" href="Javascript:void(0)">
							<span className="glyphicon glyphicon-certificate"></span>
						</button>
					</span>	
					<input type="text" name="title" value={(this.state.selected2=="选择小分类"||this.state.selected2=="新的分类"&&this.state.newKind=="")?"":this.state.title} onChange={this.handleTitle}  id="title_id" placeholder="请填写你的标题" 
					className="form-control"/>
				</div>
				)
		return ( 
					<form className="form-inline">
					<div className="row">
					<div className="col-md-2 col-md-offset-1">
					<div className="input-group ">
						<div className="input-group-btn">
							<button className ="btn btn-default dropdown-toggle" type="button" 
								id ="dropbutton" data-toggle="dropdown" 
									aria-haspopup = "true" aria-expended="true">
								{this.state.selected1}
							<span className="caret"></span>
							</button>
							<ul className="dropdown-menu dropdown-menu-right" aria-labelledby="dropbutton">
							   {opsAboutWhats}
							</ul>
						</div>
					</div>
				</div>
					
					
				<div className="col-md-3">
					<div className="input-group input-group">
						<div className="input-group-btn">
							<button className = 
							 {this.state.selected1=="选择大分类"?"btn btn-default dropdown-toggle disabled"
									:"btn btn-default dropdown-toggle"} 
							type="button" 	id ="dropbutton" data-toggle="dropdown" 
									aria-haspopup = "true" aria-expended="true">
								{this.state.selected2}
								<span className="caret"></span>
							
							</button>
							<ul className="dropdown-menu dropdown-menu-right" aria-labelledby="dropbutton">
								<Li   fatherhandle={this.handleOpsKind} name={"新的分类"}></Li>	
								{opsKinds}
							</ul>
						</div>
						{newKindInput}
					</div>
				</div>
				
				<div className="col-md-3">
							{titleInput}
						
				</div>
					
					<div className="col-md-3">
						<div className="input-group">
							<span className="input-group-btn">
									<button className="btn btn-default" type="button" href="Javascript:void(0)">
										<span className="glyphicon glyphicon-user"></span>
									</button>
							</span>
							<input type="text" name="author" value={this.state.author} onChange={this.handleAuthor} id="author_id" placeholder="请填写作者名" 
								className="form-control "/>	
						</div>
						</div>
						
						</div>
					</form>
					
		)
	}
})
var HeaderBoxList=React.createClass({
	getInitialState: function() {
		var array=[
			{"key":1,text:"首页",			css:" "},
			{"key":2,text:"关于本站与我",	css:" "},
			{"key":3,text:"关于生活",		css:" "},
			{"key":4,text:"关于编程",		css:" "}
			]
    	return {array: array,text:wordId==""?"编辑新的文章":"修改文章"}; 
  	},
	render:function(){
		var headerList = this.state.array.map(function(liDOM){
			return <HeaderLi key={liDOM.key} text={liDOM.text} css={" "} />
			})
		return <ul className="nav navbar-nav" style={{marginTop:"-2%"}}>
						{headerList}
						<li role="presentation" className="nav-pills">
							<a href="#" >
								<button className="btn btn-info">{this.state.text}</button>
							</a>
						</li>
					</ul>
	}
})
//introduce
var EditBox2 = React.createClass({
	destoryEdit:function(){
		$(this.refs.root).summernote('destroy');
	},
	showEdit:function(){
		$(this.refs.root).summernote({
			lang:'zh-CN',
			height:50,                 // set editor height
			minHeight: null,             // set minimum height of editor
			maxHeight: null,             // set maximum height of editor
			focus: true ,                 // set focus to editable area after initializing
		toolbar: [
			// [groupName, [list of button]]
		 	['style', ['bold', 'italic', 'underline', 'clear']],
		 	['font', ['strikethrough', 'superscript', 'subscript']],
		 	['fontsize', ['fontsize']],
		 	['color', ['color']],
		 	['para', ['ul', 'ol', 'paragraph']],
	     	['height', ['height']],
			['Misc',['codeview','fullscreen']]
		
	],
		focus: true,
		popover: {
			  link: [
			    	['link', 		['linkDialogShow', 'unlink']]
			  ],
			  air: [
			    ['color', 	['color']],
			    ['font', 	['bold', 'underline', 'clear']],
			    ['para', 	['ul', 'paragraph']],
			    ['table', 	['table']],
			    ['insert', 	['link', 'picture']]
			  ]
			},
			callbacks: {
			    onChange: function(contents, $editable) {
			    	intorText =  contents;
			    }
			  }
	});
	},
	componentWillReceiveProps:function(nextProps){
		nextProps.isOpen?this.showEdit():this.destoryEdit();
		
	},
	componentDidMount:function(){
		$.get(webRoot+intorPath,function(data){
			//$(this.refs.root).summernote('insertText', data);
			this.props.isOpen?this.showEdit():this.destoryEdit();
			srcIntorText = data
			 this.refs.root.innerHTML = data;
			
		}.bind(this))	
	},
	render:function(){
		return <div ref="root" className="bg-warning" >
		</div>
	}
	
})







var EditBox = React.createClass({
	destoryEdit:function(){
		$(this.refs.root).summernote('destroy');
	},
	showEdit:function(){
		$(this.refs.root).summernote({
			lang:'zh-CN',
			height: 300,                 // set editor height
			minHeight: null,             // set minimum height of editor
			maxHeight: null,             // set maximum height of editor
			focus: true ,                 // set focus to editable area after initializing
		toolbar: [
			// [groupName, [list of button]]
		 	['style', ['bold', 'italic', 'underline', 'clear']],
		 	['font', ['strikethrough', 'superscript', 'subscript']],
		 	['fontsize', ['fontsize']],
		 	['color', ['color']],
		 	['para', ['ul', 'ol', 'paragraph']],
	     	['height', ['height']],
			['Insert',['picture','link','table','hr']],
		 	['Misc',['codeview','fullscreen']]
		
	],
		focus: true,
		popover: {
				 image: [
    				['imagesize', 	['imageSize100', 'imageSize50', 'imageSize25']],
				    ['float', 		['floatLeft', 'floatRight', 'floatNone']],
				    ['remove', 		['removeMedia']]
					],
			  link: [
			    	['link', 		['linkDialogShow', 'unlink']]
			  ],
			  air: [
			    ['color', 	['color']],
			    ['font', 	['bold', 'underline', 'clear']],
			    ['para', 	['ul', 'paragraph']],
			    ['table', 	['table']],
			    ['insert', 	['link', 'picture']]
			  ]
			},
			callbacks: {
			    onChange: function(contents, $editable) {
			    	wordText =  contents;
			    }
			  }
	});
	},
	componentWillReceiveProps:function(nextProps){
		nextProps.isOpen?this.showEdit():this.destoryEdit();
		
	},
	componentDidMount:function(){
		$.get(webRoot+wordPath,function(data){
			//$(this.refs.root).summernote('insertText', data);
			this.props.isOpen?this.showEdit():this.destoryEdit();
			srcWordText = data
			this.refs.root.innerHTML = data;
			
		}.bind(this))
	},
	render:function(){
		return <div ref="root">
		</div>
	}
})

var SubmitButton = React.createClass({
	 getInitialState:function(){ 
		 return {
			 clickText:"发表",
			 canDo:false,
			 ober:msgManger.getOber()
			 }
	},
	handleClick:function(msg){
		this.setState({clickText:msg});
	},
	handleSubmit:function(e){
		this.state.canDo?submitPuhser.pushIt(this.state.clickText):"";
	},
	componentDidMount:function(){
	
	},
	componentWillMount:function(){
		var ober = this.state.ober;
		ober.setDo(function(msg){
			this.setState({canDo:msg==""?false:true})
		}.bind(this))
		inputTitlePusher.subscribed(ober);	
	},
	componentWillUnmount:function(){
		inputTitlePusher.unsubscribed(this.state.ober);
	},
	render:function(){
		return (
		<div className="input-group">
		<div className="input-group-btn">
		<button className={this.state.canDo?"btn btn-primary":"btn btn-primary disabled"} 
		type="button" onClick={this.handleSubmit} >同步到网站</button>	
		
		<button className ="btn btn-default dropdown-toggle" type="button" 
			id ="dropbutton" data-toggle="dropdown" 
				aria-haspopup = "true" aria-expended="true">
			{this.state.clickText}
			<span className="caret"></span>
		</button>
		<ul className="dropdown-menu dropdown-menu-right" aria-labelledby="dropbutton">
			<Li   fatherhandle={this.handleClick} name={"发表"}></Li>	
			<Li   fatherhandle={this.handleClick} name={"不发"}></Li>
		</ul>
	</div>
	</div>
	)
	}
})
var ContextBox = React.createClass({
	getInitialState:function(){
		return {
			isOpen:false,
			buttonName:"展开",
			buttonCss:"btn btn-primary"
				}
	},
	handleToggle:function(e){
	this.setState({
		isOpen:!this.state.isOpen,
		buttonName:!this.state.isOpen?"预览":"展开",
		buttonCss:!this.state.isOpen?"btn btn-info":"btn btn-primary"
		})	
	setTimeout(
			$("body,html").animate({
		   scrollTop:0  //让body的scrollTop等于pos的top，就实现了滚动
		   },1000)
		   ,2000);
	},
	render:function(){
		
		return (
				<div className="container">
				<div className="thumbnail">
					<div className ="caption" style={{marginBottom: "10%"}} id="text_page"> 
						<div className="row">
							<FromAboutEdit />
						</div>
							<EditBox2 isOpen={this.state.isOpen}/>
						    <EditBox  isOpen={this.state.isOpen}/>
							<div className="row">
								<div className="col-md-1">
									<button   className={this.state.buttonCss} onClick={this.handleToggle} type="button">{this.state.buttonName}</button>
								</div>
								<div className="col-md-2">
								<SubmitButton father={this}/>
									</div>
							
						</div>
					</div>
				</div>
				<CommentNotEditBox wordId={wordId} needDelete ={true}/>
			</div>
				)
	}
})
//test


//render
ReactDOM.render(
		<HeaderBox headerBoxRight={HeaderBoxRight} headerBoxList={HeaderBoxList}/>,
document.getElementById('head')
);
ReactDOM.render(
		<LoginModal  content={LoginFrom}/>,
		document.getElementById('modal_login')
);
ReactDOM.render(
		<MsgModal  content={Info}/>,
		document.getElementById('modal_msg')
);
ReactDOM.render(
	<ContextBox />,
 	document.getElementById('context')
);