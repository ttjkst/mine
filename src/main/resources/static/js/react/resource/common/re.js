/**
 * 
 * {
 * 
 * }
 * 
 */
var req = function(args,data){
	var oReq = new XMLHttpRequest();
	for(var i in args){
		console.log(i+":"+args[i]);
	}
	oReq.open(
	args.method==undefined?"post":args.method,
	args.url,
	args.isAsync==undefined?false:args.isAsync,
	args.user==undefined?"":args.user,
	args.password==undefined?"":args.password
	)
	for(var i in args.headers){
	oReq.setRequestHeader(i,args.headers[i]);
	}
	for(var i in args.callbacks){
		oReq.addEventListener(i,args.callbacks[i]);
	}
	oReq.send(data);
}
module.exports = req;
