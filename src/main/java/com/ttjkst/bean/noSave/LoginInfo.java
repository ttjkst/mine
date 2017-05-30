package com.ttjkst.bean.noSave;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class LoginInfo {
	private String id;
	
	private List<Date> loginTime = new Vector<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Date> getLoginTime() {
		return loginTime;
	}
	public void addLoginTime(Date date){
		Date date2 = loginTime.get(loginTime.size()-1);
		int ms = (int)((date.getTime()-date2.getTime())/1000);
		if(Math.abs(ms)<=60){
			loginTime.add(date);
		}else{
			loginTime.clear();
		}
	}
	public boolean isCanLogin(){
		if(loginTime.size()>30){
		return false;
		}
		return true;
	}
}
