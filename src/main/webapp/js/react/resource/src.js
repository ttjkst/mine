//react.js
var React = require('react');
var ReactDOM = require('react-dom');

var msgManger = require("./msg.js");
//get HeaderBox component
var HeaderBox = require("./head.js");
//get HeaderBoxRight component
var HeaderBoxRight = require("./head_com.js");
//get Modal component
var LoginModal = require("./LoginModal.js");
var LoginFrom = require("./modal_com.js");
var pusher=msgManger.getPublisher();
var headerClick="首页";


var HeaderLi=React.createClass({
	hindleClick:function(){
		//while it is itself, no action.
		if(this.props.css==" active"){
			
			return null;
		}
		//tell father component
		this.props.father.hindleChildrenMsg(this.props.text);
	},
	render:function(){
		return  <li role="presentation"  className={this.props.css}><a onClick={this.hindleClick} href="Javascript:void(0)">{this.props.text}</a></li>
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
	hindleChildrenMsg:function(msg){
		headerClick=msg;
		//while it is clicked,tell other components(include ShowWordBox ShowWeb )
		pusher.pushIt(msg);
		this.forceUpdate();
	
	},
	render:function(){
		var headerList=this.state.array.map(function(liDOM){
			var css=" ";
			if(headerClick==liDOM.text){
				css=" active";
			}	
			return <HeaderLi key={liDOM.key} text={liDOM.text} css={css} father={this}/>
		}.bind(this))
		
		return <ul className="nav navbar-nav" style={{marginTop:"-2%"}}>
						{headerList}
					</ul>
	}
})
        	var isLogin = false;
        	var pageSize=6;
        	var size=5;
        	var searchInfoPusher= msgManger.getPublisher();
	var MainWeb = React.createClass({
				 	render: function(){
				 
				 		return (
				 		<div className="-fluid"> 
						<div className="jumbotron base_background">
				       				<h1>程序员</h1>
				       				<p>我们是一群让人难以理解的人</p>
				       				<p>人们不知道我们有能力改变世界</p>
				       				<p>我们却正在改变世界</p>
				       				
				       			</div>
				       		</div>
				       		)
				 	}
				 })
				 
			var AboutMEandWeb = React.createClass({
				render: function(){
					return 	(
					<div className="container">
       					<div className="thumbnail" style={{marginTop:"1%"}}>
       						<div className="caption" style={{marginBottom:"10%"}}>
       							<h1 className="text-center">关于本站与我</h1>
       							<div className="text-center">
       								<span className="glyphicon glyphicon-user">:ttjkst&nbsp;</span>
       								<span className="glyphicon glyphicon-time">:1991-11-22</span>
       							</div>
       							<p className="p1"><big>我是一名大学生，这是我的第一个网站。</big></p>
       							<p className="p1"><big>其实我也不知道说些什么。</big></p>
       							<p className="p1"><big>还是说说我最喜欢的栏目吧---"关于IT",这里我会放一些我自己学习记录。</big></p>
       							<p className="p1"><big>好了就这些。。以下是联系方式：</big></p>
       							<p className="p1"><big>我的邮箱：791599901@qq.com(请不要加qq联系我)</big></p>
       						</div>
       					</div>
       			</div>
					)
				}
			})
			var ClickNo= React.createClass({
				hindleClick:function(){
					var clickNo=$(this.refs.a).text();
					if(jQuery.isNumeric(clickNo)){
						
						this.props.father.hindleClick(clickNo);
					}else{
						console.log("error something")
					}
				},
				render:function(){
					return <li className={this.props.css} onClick={this.hindleClick}><a ref="a">{this.props.value}</a></li>
				}
			})
			var ShowPageButton=React.createClass({
				createPagetagList:function(total,currNo){
					var key = parseInt(""+((currNo)/size+ ((currNo)%size == 0 ? -1 : 0))+"")
					var list=[];
					var e=null;
					var beginNo = key*size+1;
					var endNo = key*size+ size;
					console.log(beginNo+":"+endNo+":"+key);
					if(endNo>total){
						endNo = total
					}
					
					if(beginNo>1){
						e={"key":total+1,"css":"disabled","value":"..."};
						list.push(e);	
					}
							
					for(var i=beginNo;i<=endNo;i++){
						e={"key":i,"css":"","value":i};
						list.push(e);
					}
							
					if(total>endNo){
						e={"key":total+2,"css":"disabled","value":"..."};
						list.push(e);	
					}
							
					if(total>endNo){
						e={"key":total,"css":"","value":total};
						list.push(e);
					}
					return list;
				},
				getInitialState:function(){
					return ({prevCss:"none",nextCss:"none",pageNoList:[]});
				},
				componentDidMount:function(){
					this.setState({
						pageNoList:this.createPagetagList(this.props.total,this.props.currNo)
					})
				},
				componentWillReceiveProps:function(nextProps){
					this.setState({
						pageNoList:this.createPagetagList(nextProps.total,nextProps.currNo)
					})
				},
				hindleClick:function(msg){
					var curr=parseInt(msg);
					this.props.father.hindleClick(msg);
				},
				hindlePrev:function(){
					var curr = this.props.currNo;
					this.props.father.hindleChildrenAction("prev",curr);
				},
				hindleNext:function(){
					var curr = this.props.currNo;
					this.props.father.hindleChildrenAction("next",curr);
				},
				render:function(){	
					var nextCss = this.props.currNo==this.props.total?"disappear":"";
					var prevCss = this.props.currNo==1?"disappear":"";
					
					var pageNoList = this.state.pageNoList.map(function(no){
						var css=no.css;
						if(this.props.currNo==parseInt(no.value)){
							css= " active";
						}
						return <ClickNo css={css} value={no.value} key={no.key} father={this} ></ClickNo>
					}.bind(this))
					
					return (	
					<div className="text-center">
								<nav>
								  <ul className="pagination">
								    <li id="parentPrevious">
								      <a href="#" aria-label="Previous" onClick={this.hindlePrev} className={prevCss}>
								        <span aria-hidden="true">«</span>
								      </a>
								    </li>
								   		{pageNoList}
								    <li id="parentNext">
								      <a href="#" aria-label="Next"  onClick={this.hindleNext} className={nextCss}>
								        <span aria-hidden="true">»</span>
								      </a>
								    </li>
								  </ul>
							</nav>
							</div>
						)
				}
			})
			var ShowWordDesControlButton=React.createClass({
				getDefaultProps: function(){
					return {canShow: false}
				},
				render:function(){
					//优化
					var result = <button type='button'  className={this.props.css} style={{float:"right" }}>{this.props.buttonName}</button>
					if(this.props.canShow==false){
						result = <button type='button'  className={this.props.css} style={{display: "none"}} >{this.props.buttonName}</button>
					}
					return result;
				}
			})
			var ShowWordDes=React.createClass({
				getInitialState: function() {
					var display=null;
					if(isLogin){
						display="";
					}else{
						display="none";
					}
    				return {canShow:isLogin,display:display,ober:msgManger.getOber(),des:"正在查询中。。。"};
  				},
				componentDidMount:function(){
					var ober = this.state.ober;
					ober.setDo(function(msg){
						if(msg.isLogin){
							this.setState({canShow:true,display:""})
						}else{
							this.setState({canShow:false,display:"none"})
						}
					}.bind(this))
					$.get(webRoot+this.props.url,function(desword){
						this.refs.des.innerHTML=desword;
					
					}.bind(this))
					LoginFrom.getMsgPusher().subscribed(ober)
					//this.state.ober = ober;
				},
				componentWillUnmount:function(){
					LoginFrom.getMsgPusher().unsubscribed(this.state.ober)
  				},
				render:function(){

					var buttonName = LoginFrom.getMsgPusher().msg.isLogin?"更改文章":"阅读全文"
					
					var morebutton = (
					<ShowWordDesControlButton 
						canShow={this.state.canShow}
						 css={"btn btn-default btn-danger"}
						 buttonName={"删除"}
						  style={this.state.display}/>
						  )
					
					return (
					<div className='thumbnail'>
						<div className='caption'>
							<h3>{this.props.title}</h3>
							<p ref="des" >{this.state.des}</p>
							<a href={this.props.href} >
								<button type='button'  className='btn btn-default' >{buttonName}</button>
							</a>
							{morebutton}
								
						</div>
					</div>
					)
				}
			})
			var ShowNewWordComments = React.createClass({
				getInitialState: function() {
    				return {num:0};
  				},
  				hindleClick:function(){
  				},
  				componentDidMount:function(){
					$.get(webRoot+"search.action",{pageNo:1,pageSize:1,aboutWhat:null,searchName:null,isNoProcess:true},function(data){
						this.setState({num:data.totalElements})
					}.bind(this))
					
				},
				render:function(){
					return (
					<button className='btn btn-primary'
						 type='button' onClick={this.hindleClick} > 新的评论 <span className='badge' >{this.state.num}</span>
					</button>
					)
				}
			})
			var ShowWordBoxRightHotWord = React.createClass({
				render:function(){
					return (
						<div className='container-fluid'>
							<h6 className='glyphicon glyphicon-pencil pull-left'></h6>
							<h5>
								<a href={this.props.href}>
							<span className='pull-left'>{this.props.title}</span>
							</a>
							</h5>
							<h5>
							<span className='pull-right'>{this.props.readedTimes}</span>
							</h5>
						</div>
				)
				}
			})
			var ShowWordBoxRightInfo = React.createClass({
				getInitialState:function(){
					//get modal information about whether user is logined or not
					var title =  LoginFrom.getMsgPusher().msg.isLogin?"新的评论的数量":"热门文章排行榜"
					return {data:[],title:title,ober:msgManger.getOber()};
				},
				componentWillReceiveProps:function(newProps){
					$.get(webRoot+"getTenWords.action",{"aboutWhat":newProps.aboutWhat},function(data){
						this.setState({
							data:data
						})
					}.bind(this))
				
				 },
				componentDidMount:function(){
					/*
					*loading date about hot words
					*/
					var ober = this.state.ober;
					ober.setDo(function(msg){
						$.get(webRoot+"getTenWords.action",{"aboutWhat":this.props.aboutWhat},function(data){
						if(this.isMounted()){
							this.setState({
								data:data
							})
						}
						}.bind(this))
					}.bind(this));
					$.get(webRoot+"getTenWords.action",{"aboutWhat":this.props.aboutWhat},function(data){
						this.setState({
							data:data
						})
					}.bind(this))
					LoginFrom.getMsgPusher().subscribed(ober)
				},
				componentWillUnmount:function(){
					LoginFrom.getMsgPusher().unsubscribed(this.state.ober)
  				},
				render:function(){
					/*
					*what shows in "ShowWordBoxRightInfo"
					*/
					var block = LoginFrom.getMsgPusher().msg.isLogin?
					<ShowNewWordComments></ShowNewWordComments>:
					this.state.data.map(function(word){
						console.log("not block");
						return (
						<ShowWordBoxRightHotWord 
							key = {word.wId}
							href={webRoot+"readMore.action?id="+word.wId} 
							title={word.wTitle} 
							readedTimes={word.readedTimes}
							/>
							)
					});
					
					
				//return
				return (
					<div className="panel panel-default">
							<h3>{this.state.title}</h3>
							<div className="panel-body">
								{block}
							</div>
						
					</div>
					)
				}
			})
			
			var ShowWordBoxRightSearchBox = React.createClass({
				getInitialState:function(){
					return {value:""};
				},
				hindleClick: function(){
					//while searchBox is clicked , tell other components( include ShowWordBox ShowWordDes)
					searchInfoPusher.pushIt(this.state.value);
					this.setState({value:this.state.value});
				},
				hindleChange: function(e){
					this.setState({value: e.target.value});
				},
				render:function(){
					//return
					return (
							<div className="thumbnail">
								<div className="caption">
								<h3>搜索</h3>
									<div className="form-group form-inline ">
										<input type="text"  placeholder="写下你想搜索的东西吧！" className="form-control" value={this.state.value} onChange={this.hindleChange}/>
										<button className="btn btn-info btn-xs" onClick={this.hindleClick}>确认</button>
									</div>
								</div>
							</div>
							)
				}
			})
			var ShowWordBox=React.createClass({
				getInitialState:function(){
					return {data:[],ober:msgManger.getOber(),searchName:"",currNo:0,totalPageNum:null};
				},
				componentWillUnmount:function(){
					searchInfoPusher.unsubscribed(this.state.ober)
  				},
				hindleClick:function(msg){
						var title=this.props.aboutWhat;
						var currNo=parseInt(msg);
						var args = {"pageSize":pageSize,"pageNo":parseInt(msg),"aboutWhat":title,"searchName":this.state.searchName,isNoProcess:null};
						var url  = "search.action";
						$.get(webRoot+url,args,function(data){
								this.setState({
									data:data.content,
									totalPageNum:data.totalPages,
									currNo:currNo
								})		
					}.bind(this))
						$("body,html").animate({
							   scrollTop:0  //让body的scrollTop等于pos的top，就实现了滚动
							   },1000);
					
				},
				hindleChildrenAction:function(msg,currNo){
					var title=this.props.aboutWhat;
					
					if(msg=="next"){
						
					var args = {"pageSize":pageSize,"pageNo":currNo+1,"aboutWhat":title,"searchName":this.state.searchName,isNoProcess:null};
					var url  = "search.action";
					$.get(webRoot+url,args,function(data){
								this.setState({
									data:data.content,
									totalPageNum:data.totalPages,
									currNo:currNo+1
								})			
						}.bind(this))
					}
					if(msg=="prev"){
						var args = {"pageSize":pageSize,"pageNo":currNo-1,"aboutWhat":title,"searchName":this.state.searchName,isNoProcess:null};
						var url  = "search.action";
						$.get(webRoot+url,args,function(data){
								this.setState({
									data:data.content,
									totalPageNum:data.totalPages,
									currNo:currNo-1
								})	
						}.bind(this))
					}
					$("body,html").animate({
							   scrollTop:0  //让body的scrollTop等于pos的top，就实现了滚动
							   },1000);
				},
				componentWillReceiveProps:function(newProps){
					var args = {"pageNo":1,"pageSize":pageSize,"searchName":"","aboutWhat":newProps.aboutWhat,isNoProcess:null};
					var url  = "search.action";	
					$.get(webRoot+url,args,function(data){
						this.setState({
							data:data.content,
							totalPageNum:data.totalPages,
							currNo:1
						})	
					}.bind(this))
				 },
				componentDidMount:function(){
					var ober = this.state.ober;
					//response searchButton ,finish action
					//ober.subscribe(searchInfoPusher);				
					ober.setDo(function(msg){
					var args = {"pageNo":1,"pageSize":pageSize,"searchName":msg,"aboutWhat":this.props.aboutWhat,isNoProcess:null};
					var url  = "search.action";	
					$.get(webRoot+url,args,function(data){
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages,
								currNo:1
							})					
					    }.bind(this));
					}.bind(this))
					searchInfoPusher.subscribed(ober)
					
					//first loading ............
					var args = {"pageNo":1,"pageSize":pageSize,"searchName":"","aboutWhat":this.props.aboutWhat,isNoProcess:null};
					var url  = "search.action";	
					$.get(webRoot+url,args,function(data){
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages,
								currNo:1
							})					
				    	}.bind(this));
				},
				render:function(){
					var wordDesList=this.state.data.map(function(word){
						return (
						<ShowWordDes
								key		=	{word.wId}
								title   = 	{word.wTitle}
								url		=	{word.introductionPath} 
							 	href	=	{webRoot+"readMore.action?id="+word.wId} 
							  	canShow = {false}
							   />
							   )
					})
					if(this.state.totalPageNum==null){
							console.log("no render..")
						return null;
					}
					return 	<div>		
									{wordDesList}
									<ShowPageButton total={this.state.totalPageNum} currNo={this.state.currNo}  father={this}></ShowPageButton>
							</div>
				}
			})
				var ShowWordContext = React.createClass({
					render:function(){
						return (<div className="row">
							<div className="col-lg-1">
								
							</div>
							<div className="col-lg-8" >
								<ShowWordBox aboutWhat={this.props.aboutWhat} />
							</div>
															
								<div className="col-lg-3">
									<ShowWordBoxRightSearchBox></ShowWordBoxRightSearchBox>
									<ShowWordBoxRightInfo aboutWhat={this.props.aboutWhat} />
								</div>
							
							
			       		
			       	</div>)
					}
				})
			
			var ShowWeb=React.createClass({
        		
        		getInitialState:function(){
        				return {msg:"首页",ober:msgManger.getOber()}
        			},
        			componentDidMount:function(){
				 		var ober = this.state.ober;
				 		ober.setDo(function(msg){
				 			this.setState({
				 				msg:msg
				 			})
				 		}.bind(this))
				 		pusher.subscribed(ober);
				 },
				 passValue:function(){
				 	return this.state.msg;
				 },
				componentWillUnmount:function(){
  					pusher.unsubscribed(this.state.msg);
  				},
        		render:function(){
					console.log("father render...")	
        			var msg = this.state.msg;
        			var dom=null;
        			if(msg == "首页"){
        				dom = <MainWeb/>;
        			}else if(msg == "关于本站与我"){
        				dom = <AboutMEandWeb/>;
        			}else{
        				dom = <ShowWordContext aboutWhat={this.state.msg} />/*需要修改，控制精准渲染*/
        				
        			}
        			return (
        			<div>{dom}</div>
        			)
        		}
        	})
        	var DomContext = React.createClass({
        		render: function(){
        				
	        			return <ShowWeb />
	        		
        		}
        	})
        	
/// render DOM
ReactDOM.render(
		<LoginModal  content={LoginFrom}/>,
		document.getElementById('modal')
);
ReactDOM.render(
  <DomContext />,
  document.getElementById('context')
);
ReactDOM.render(
	<HeaderBox headerBoxRight={HeaderBoxRight} headerBoxList={HeaderBoxList}/>,
	document.getElementById('head')
) 