/**
 * control Web page
 */



//react.js
var React = require("react");
var ReactDOM = require("react-dom");


//var validiation = require("./validiation.js");
//get HeadBoxRight component
var HeaderBoxRight = require("../common/head_com.js")
//get LoginFrom component
var LoginFrom =require("../modal/modal_com.js")
//get Modal component
var Modal = require("../modal/modal_reBuild.js")

var HeaderBox = require("../common/head.js")


var msgManger = require("../common/msg.js")


var searchInfoPusher = msgManger.getPublisher();

var pageSize = 10;
var pageNo   = 0;
var size	 = 5;

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
								<button className="btn btn-primary">您正在进行后台管理</button>
							</a>
						</li>
					</ul>
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
				componentWillReceiveProps:function(nextProps){
					this.setState({
						pageNoList:this.createPagetagList(nextProps.total,nextProps.currNo)
					})
				},
				componentDidMount:function(){
					this.setState({
						pageNoList:this.createPagetagList(this.props.total,this.props.currNo)
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
		
			var ShowWordBoxRightInfo = React.createClass({
				getInitialState:function(){
					return {title:"工具栏",ober:msgManger.getOber(),isLogin:false};
				},
				hindleClickAllWords:function(e){
					this.props.father.handleChildrenClick(null);
				},
				hindleClickNoWords:function(e){
					this.props.father.handleChildrenClick(false);
				},
				componentDidMount:function(){
					var ober = this.state.ober;
					ober.setDo(function(msg){
						this.setState({isLogin:msg.isLogin});
					}.bind(this))	
				},
				componentWillUnmount:function(){
					LoginFrom.getMsgPusher().unsubscribed(this.state.ober)
  				},
				render:function(){

				return (
					<div className="panel panel-default">
							<h3>{this.state.title}</h3>
							<div className="panel-body">
							
								
									<div className="btn-group">
										<button type="button"
										 onClick = {this.hindleClickAllWords}	className="btn btn-primary"  disabled={this.state.isLogin}
										>管理所有的文章</button>
									</div>
							</div>
							<div className="panel-body">
									<div className="btn-group">
										<button type="button" 
											onClick = {this.hindleClickNoWords}	className="btn btn-primary" disabled={this.state.isLogin}
										>管理所有的文章（评价没有处理的）</button>
									</div>
								
							</div>
						
						
					</div>
					)
				}
			})
			
			var ShowWordBoxRightSearchBox = React.createClass({
				getInitialState:function(){
					return {value:this.props.searchName};
				},
				hindleClick: function(){
					this.props.father.handleChildrenSearch(this.state.value);
				},
				hindleChange: function(e){
					this.setState({value: e.target.value});
				},
				render:function(){
					//return
					return (
							<div className="panel panel-default">
								<div className="panel-body">
									<h3>搜索</h3>
									<div className="input-group">
										
											<input type="text"  placeholder="写下你想搜索的东西吧！" className="form-control" value={this.state.value} onChange={this.hindleChange}/>
											<span className="input-group-btn">
												<button className="btn btn-primary" onClick={this.hindleClick}>确认</button>
											</span>
										
									</div>	
								</div>
							</div>
							)
				}
			})
			var Tr = React.createClass({
				getDefaultProps: function(){
					return {canShow: false,wId:null,wTitle:"正在载入",wTimeOfInData:0,wAuthor:"正在载入"}
				},
				getInitialState:function(){
					return {hasNoRead:0,all:0}
				},
				componentDidMount:function(){
					 var url = "getLwsNumNoAsync.action";
					 var arg ={"id":this.props.wId};
					 $.get(webRoot+url,arg,function(info){
						 this.setState({
							 hasNoRead:info.hasNoRead,
							 all:info.all
						 })
					 }.bind(this))
				},
				handleClickforDelete:function(e){
					$.post(webRoot+"delete.action",{id:this.props.wId},function(){
						this.props.father.reloadForDelete();
					}.bind(this))
				},
				handleLookDetials:function(e){
					window.open(webRoot+"readAndEdit.action?id="+this.props.wId,'_self');
				},
				render:function(){
					return(
						<tr>
							<td className="text-center">{this.props.wId}</td>
							<td className="text-center">{this.props.wTitle}</td>
							<td className="text-center">{this.props.wAuthor}</td>
							<td className="text-center">{new Date(this.props.wTimeOfInData).toDateString()}</td>
							<td className="text-center">{this.props.aboutWhat}</td>
							<td className="text-center">{this.props.kind}</td>
							<td className="text-center">{this.state.all}</td>
							<td className="text-center">{this.state.hasNoRead}</td>
							<td className="text-center">{this.props.canShow?"公开":"隐藏"}</td>
							<td className="text-center">
								<div className="btn-toolbar">
									<div className="btn-group btn-group-xs">
										<button className="btn btn-primary"  onClick={this.handleLookDetials}>查看文章</button>
									</div>
									<div className="btn-group btn-group-xs">
										<button  className="btn btn-danger" onClick={this.handleClickforDelete}>删除</button>
									</div>
								</div>
							</td>
						</tr>
						) 
				}
			}) 
			var ShowWordBox=React.createClass({
				getInitialState:function(){
					return {data:[],ober:msgManger.getOber(),currNo:0,totalPageNum:null};
				},
				componentWillUnmount:function(){
					searchInfoPusher.unsubscribed(this.state.ober)
  				},
  				reloadForDelete:function(){
  					//if this data is 1,that mean that take previous page,
  					//but it when the pageNo is not 1,(the pageNo is not little than 1)
  					if(this.state.data.length == 1 && this.state.currNo != 1){
  							this.state.currNo--;
  					}
					var args = {
							"pageSize":pageSize,
							"pageNo":this.state.currNo,
							"aboutWhat":null,
							"searchName":this.props.searchName,
							"isNoProcess":this.props.isNoProcess
							};
					
					var url  = "search.action";
					$.get(webRoot+url,args,function(data){
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages
							})
					}.bind(this))
  				},
				hindleClick:function(msg){
					var currNo=parseInt(msg);
					var args = {"pageSize":pageSize,"pageNo":parseInt(msg),"aboutWhat":null,"searchName":this.props.searchName,"isNoProcess":this.props.isNoProcess};
					var url  = "search.action";
					$.get(webRoot+url,args,function(data){
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages,
								currNo:currNo
							})		
					$("body,html").animate({
						   scrollTop:0  //让body的scrollTop等于pos的top，就实现了滚动
						   },1000);
					}.bind(this))					
				},
				hindleChildrenAction:function(msg,currNo){				
					if(msg=="next"){
					var args = {"pageNo":currNo+1,"pageSize":pageSize,"doWhat":"getAll","searchName":this.props.searchName,"aboutWhat":null,"isNoProcess":this.props.isNoProcess};
					var url  = "search.action";	
					$.get(webRoot+url,args,function(data){
						console.log("getdata");
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages,
								currNo:currNo+1
							})
					}.bind(this))
						
					}
					if(msg=="prev"){
						var args = {"pageNo":currNo-1,"pageSize":pageSize,"doWhat":"getAll","searchName":this.props.searchName,"aboutWhat":null,"isNoProcess":this.props.isNoProcess};
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
				},componentWillReceiveProps:function(nextProps){
					var args = {"pageNo":1,"pageSize":pageSize,"doWhat":"getAll","searchName":nextProps.searchName,"aboutWhat":null,"isNoProcess":nextProps.isNoProcess};
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
							
					//first loading ............
					var args = {"pageNo":1,"pageSize":pageSize,"doWhat":"getAll","searchName":this.props.searchName,"aboutWhat":null,"isNoProcess":this.props.isNoProcess};
					var url  = "search.action";	
					$.get(webRoot+url,args,function(data){
							this.setState({
								data:data.content,
								totalPageNum:data.totalPages,
								currNo:1
							})
					}.bind(this))
				},
				render:function(){
					var trList=this.state.data.map(function(word){
						return (
								<Tr wId={word.wId} wTitle={word.wTitle} wAuthor={word.wAuthor} canShow={word.canShow} key={word.wId}
								wTimeOfInData={word.wTimeOfInData} aboutWhat={word.wKind.aboutwhat.name} 
								kind={word.wKind.kName} father={this} />
							   )
					}.bind(this))
					console.log(trList.length)
					return 	<div className = "panel panel-default">
								<div className="panel-body">
					          <table className="table">
					          	<tbody>
									<tr>
											<th className="text-center">序列号</th>
											<th className="text-center">标题</th>
											<th className="text-center">作者</th>
											<th className="text-center">上线时间</th>
											<th className="text-center">一级分类</th>
											<th className="text-center">二级分类</th>
											<th className="text-center">评论数</th>
											<th className="text-center">未阅评论数</th>
											<th className="text-center">公开与否</th>
											<th className='text-center'>管理</th>
									</tr>
									{trList}
								</tbody>	
							   </table>
							 </div>
							   <ShowPageButton total={this.state.totalPageNum} currNo={this.state.currNo}  father={this}></ShowPageButton>
						   
							 </div>
						   
				}
			})
			
			///采用信息集中方式进行管理
				var ShowWordContext = React.createClass({
					getInitialState:function(){
						return {searchName:"",isNoProcess:null}
					},
					handleChildrenSearch:function(msg){
						console.log("searchName:"+msg);
						this.setState({searchName:msg})
					},
					handleChildrenClick:function(msg){
						this.setState({isNoProcess:msg})
					},
					render:function(){
						return (<div className="row">
							<div className="col-lg-1">
								
							</div>
							<div className="col-lg-8" >
								<ShowWordBox searchName={this.state.searchName} isNoProcess={this.state.isNoProcess} />
							</div>
															
								<div className="col-lg-3">
									<ShowWordBoxRightSearchBox searchName={this.state.searchName} father={this} ></ShowWordBoxRightSearchBox>
									<ShowWordBoxRightInfo isNoProcess={this.state.isNoProcess} aboutWhat={this.props.aboutWhat} father={this}/>
								</div>			       		
			       	</div>)
					}
				})













//render
ReactDOM.render(
		<HeaderBox headerBoxRight={HeaderBoxRight} headerBoxList={HeaderBoxList}/>,
document.getElementById('head')
);
ReactDOM.render(
		<ShowWordContext />,
document.getElementById('context')
);