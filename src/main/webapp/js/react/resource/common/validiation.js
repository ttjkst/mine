/**
 * 
 */


module.exports.isEmail = function(str){ 
	var reg = /^([\w_-])+@([\w_-])+.([\w_-])+$/; 
	return str==""||reg.test(str); 
}
module.exports.isQQ = function(str){
	var reg=/^\d{5,12}$/;
	return str==""||reg.test(str);
}