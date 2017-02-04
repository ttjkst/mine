package com.ttjkst.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Kind;

public interface KindDAO extends CrudRepository<Kind, Long>{
	
	@Query("select count(k) from Kind k where k.name like ?1 and k.aboutwhat.name like ?2")
	public int getCoutBy2Name(String kName,String abName);
	
	@Query("select k from Kind k where k.aboutwhat.name like ?1")
	public List<Kind> findByAbName(String name);
	
	@Query("select k from Kind k where k.name like ?1 and k.aboutwhat.name like ?2")
	public Kind getItBy2Name(String kName,String abName);

}
