package com.ttjkst.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ttjkst.bean.Users;
@Repository
public interface UserDAO  extends CrudRepository<Users, Long>{
	
	@Query("select u from Users u where u.username like  ?1")
	public Users getItByUserName(String userName);

}
