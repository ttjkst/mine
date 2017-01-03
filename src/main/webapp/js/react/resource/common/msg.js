 function obeser(){
				this.hindle= function(msg){
        			if(typeof this.callback === 'function'){     				
		                this.callback(msg);
		           	}
        		}
        		this.callback=function()
        		{};
        		this.index;
        		this.setHinder = function(obej){
        			this.hinder=obej        			
        		}
        		this.setDo= function(callback){
        			this.callback=callback;
        		}
        		this.unsubscribe = function(puer){
        		}
        	}
function publisher(){
        		this.obs = [];
        		this.index=-1;
        		this.pushIt = function(messge){
					this.msg=messge;
        			for(var i=0;i<this.obs.length;i++){
        				this.obs[i].hindle(messge);
        			} 
        		}
        		this.subscribed = function(obe){
        			this.obs.push(obe);
        			this.index++;
        			obe.index=this.index;
        		}
        		this.unsubscribed=function(obe){
        			this.obs.splice(this.obs.indexOf(obe),1);
        			this.index--;//2016 9 3 add
        		}
        	}
 
module.exports.getOber= function(){return  new obeser()};
module.exports.getPublisher = function(){return new publisher()};