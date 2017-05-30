package com.ttjkst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttjkst.bean.Users;
import com.ttjkst.dao.UserDAO;
import com.ttjkst.service.IUserService;

@Transactional
@Service
public class UserService implements IUserService{

	@Autowired
	private UserDAO dao;

	public Users login(String userName, String passWord) {
		Users user = dao.getItByUserName(userName);
		if(user!=null&&user.getPassword().equals(passWord)){
		return user;
		}
		return new Users();
	}

	public Users createUser(String userName, String passWord, Integer permition) {
		return dao.save(new Users(userName,passWord,permition));
	}
	
	
	
}
