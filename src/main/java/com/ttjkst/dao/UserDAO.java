package com.ttjkst.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Users;
public interface UserDAO  extends CrudRepository<Users, Long>{
	
	@Query("select u from Users u where u.username like  ?1")
	public Users getItByUserName(String userName);

}
