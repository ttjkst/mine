/**
*control.js
*/
var webpack = require("webpack");
module.exports ={
	plugins:[
		new webpack.optimize.CommonsChunkPlugin('common.js')
	],
	entry:{
		index:['./updateRc.js','../common/foo.js']
	},
	output:{
		path:__dirname+"/../../built/",
		filename:'updatePage.js'
	},
	module:{
		loaders: [
				{test: /\.js$/, loader: "babel-loader"}
			]
	},
	resolve:{
			extensions:['','.js','json','.jsx'],
	}
}