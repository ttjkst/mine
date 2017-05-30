package com.ttjkst.service;

import com.ttjkst.bean.Users;

public interface IUserService {
	public Users login(String userName, String passWord);

	public Users createUser(String userName, String passWord, Integer permition);

}
