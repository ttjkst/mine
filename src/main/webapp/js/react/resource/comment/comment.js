/**
 * 
 */
var React = require("react");
var ReactDOM = require("react-dom");


var Comment = React.createClass({
	handleDelete:function(e){
		$.post(webRoot+"deleteComment.action",{"id":this.props.id},function(){
			this.props.father.reloadForDelete();
		}.bind(this))
	},
	render:function(){
		
		var buttonDom = !this.props.needDelete?"":<button type='button' className="btn btn-danger pull-right" onClick={this.handleDelete}>删除此评论</button>
		
		return (<li className='media'>
		<div className='media-body'>
		<h4>{this.props.who==null||this.props.who==""?"匿名":this.props.who}<small className='pull-right'>{this.props.time.toLocaleDateString()}评论</small></h4>
		{this.props.context}
		{buttonDom}
		<hr/>
		</div>
		</li>
		)
	}
})

var CommentUpBox = React.createClass({
	getInitialState:function(){
		return {array:[],pageSize:5,pageNo:1,NextCssValue:null,PreCssValue:null};
	},
	componentWillReceiveProps:function(nextProps){		
			var url="currentleaveWordsInfo.action";
			var args={"pageSize":this.state.pageSize,"pageNo":1,"wordId":this.props.wordId,"order":"desc"};
			$.get(url,args,function(result){
				this.setState({
					array:result.content,
					PrevCssValue:result.hasPre?"":"none",
					NextCssValue:result.hasNext?"":"none",
					pageNo:1
				})			
			}.bind(this))//end with get
	},
	handlePrev:function(e){
		var url="currentleaveWordsInfo.action";
		var args={"pageSize":this.state.pageSize,"pageNo":this.state.pageNo-1,"wordId":this.props.wordId,"order":"desc"};
		$.get(url,args,function(result){
			this.setState({
				array:result.content,
				PrevCssValue:result.hasPre?"":"none",
				NextCssValue:result.hasNext?"":"none",
				pageNo:this.state.pageNo-1
				})
			var top = $(this.refs.listRoot).offset().top;
			$("body,html").animate({
					   scrollTop:top //让body的scrollTop等于pos的top，就实现了滚动
					   },1000);
			})
	},
	reloadForDelete:function(){
		//if this array is zore,that mean that take previous page,
		//but it when the pageNo is not 1,(the pageNo is not little than 1)
		if(this.state.array.length == 1&&this.state.pageNo != 1){
				this.state.pageNo--;
		}
		var url="currentleaveWordsInfo.action";		
		var args={"pageSize":this.state.pageSize,"pageNo":this.state.pageNo,"wordId":this.props.wordId,"order":"desc"};
		$.get(url,args,function(result){
			this.setState({
				array:result.content,
				PrevCssValue:result.hasPre?"":"none",
				NextCssValue:result.hasNext?"":"none"
		})
	}.bind(this))
				
	},
	handleNext:function(e){
		var url="currentleaveWordsInfo.action";
		var args={"pageSize":this.state.pageSize,"pageNo":this.state.pageNo+1,"wordId":this.props.wordId,"order":"desc"};
		$.get(url,args,function(result){
			this.setState({
				array:result.content,
				PrevCssValue:result.hasPre?"":"none",
				NextCssValue:result.hasNext?"":"none",
				pageNo:this.state.pageNo+1
				})
				var top = $(this.refs.listRoot).offset().top;
				console.log(top);
				$("body,html").animate({
					scrollTop:top //让body的scrollTop等于pos的top，就实现了滚动
			    },1000);
		}.bind(this))
	},
	componentDidMount:function(){
		var url="currentleaveWordsInfo.action";
		var args={"pageSize":this.state.pageSize,"pageNo":this.state.pageNo,"wordId":this.props.wordId,"order":"desc"};
		$.get(url,args,function(result){
			this.setState({
				array:result.content,
				PrevCssValue:result.hasPre?"":"none",
				NextCssValue:result.hasNext?"":"none"
				})			
		}.bind(this))
	},
	render:function(){
		if(this.state.PrevCssValue==null||this.state.NextCssValue==null){
			return null;
		}
		var comments = this.state.array.length==0?<p>这么精彩的文章竟然没有人评论！ ╥﹏╥</p>:this.state.array.map(function(comment){
			var timeInData = new Date(comment.timeInData);
			return <Comment father={this} needDelete={this.props.needDelete} id={comment.id} key={comment.id} who={comment.whose}  time = {timeInData} context= {comment.saywhat} />
			}.bind(this))
			
			return (
					<div className="panel panel-default">
		       		<div className="panel-heading"><h5>评论</h5>
		       		</div>
			       	<div className="panel-body" ref="listRoot" > 
			       			<button className="btn btn-block btn-info" onClick={this.handlePrev} style={{display:this.state.PrevCssValue}} >之前评论</button>
				     	<ul className="media-list">
				     		{comments}
				     	</ul>  
				     		<button className="btn btn-block btn-info" onClick={this.handleNext} style={{display:this.state.NextCssValue}}>更多评论</button>
			     	</div>
		     	</div>
				)
	}
})

module.exports=CommentUpBox