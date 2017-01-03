package com.ttjkst.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ttjkst.bean.Kinds;

@Repository
public interface KindDAO extends CrudRepository<Kinds, Long>{
	
	@Query("select count(k) from Kinds k where k.kName like ?1 and k.aboutwhat.name like ?2")
	public int getCoutBy2Name(String kName,String abName);
	
	@Query("select k from Kinds k where k.aboutwhat.name like ?1")
	public List<Kinds> findByAbName(String name);
	
	@Query("select k from Kinds k where k.kName like ?1 and k.aboutwhat.name like ?2")
	public Kinds getItBy2Name(String kName,String abName);

}
