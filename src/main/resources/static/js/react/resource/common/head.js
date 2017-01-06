//react.js
var React = require('react');
var ReactDOM = require('react-dom');

//default component equals "null"
var DefalutCom =React.createClass({
	render:function(){
		return <span>无组件</span>
	}
})
var HeaderBox = React.createClass({
		  	render: function() {
		  		var HeaderBoxList = DefalutCom;
		  		if(this.props.headerBoxList!=null){
		  			HeaderBoxList=this.props.headerBoxList;
		  		}
		  		var HeaderBoxRight = this.props.headerBoxRight//this.props.headBoxRight;
		  		if(this.props.headerBoxRight==null){
		  			HeaderBoxRight= DefalutCom;
		  		}
		    return (
			      <nav className="nav navbar-default head_background">
			      	<div className="container-fluid">
				       	
				       		<div className="page-header">
								 	<h1>ttjkst</h1>
								 	<h4>Hard work,try to make mistakes and try to current it!</h4>
							</div>  	
				       	<div className="navbar-header " >
				      	 	<div className="collapse navbar-collapse">
				       			<HeaderBoxList/>
				      	 	</div>
				       	 </div>
				       		<HeaderBoxRight/>
				       </div>
	       		</nav>
		      )
		  }
		 })

module.exports=HeaderBox;