var webpack = require("webpack");
module.exports ={
	plugins:[
		new webpack.optimize.CommonsChunkPlugin('common.js')
	],
	entry:{
		index:['./editRc.js','./foo.js']
	},
	output:{
		path:__dirname+"/../built/",
		filename:'editPage.js'
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