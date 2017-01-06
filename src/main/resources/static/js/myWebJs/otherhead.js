/**
 * 
 */

function addNav(data){
	var selector = data.index;
	var classValue= "";
	var bomId="";
	for(var i=0 ;i<data.navs.length;i++){
		if(data.navs[i].classValue!=null){
			classValue = data.navs[i].classValue;
		}else{
			classValue = "";
		}
		if(data.navs[i].bomId!=null){
			bomId ="id='" + data.navs[i].bomId+"'";
		}else{
			bomId = "";
		}
		$(selector).append( "<li role='presentation' class='"+classValue+"'><a href='"+data.navs[i].goWhere+"' "+bomId+">"+data.navs[i].navName+"</a></li>");
	}
}