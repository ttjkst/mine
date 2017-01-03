package com.ttjkst.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ttjkst.bean.AboutWhats;

@Repository
public interface ABDAO extends CrudRepository<AboutWhats, Long>{
		
		@Query("select count(a) from AboutWhats a where a.name like ?1")
		public int getCountByName(String name);
		
		@Query("select a from AboutWhats a where a.name like ?1")
		public AboutWhats getItByName(String name);
}
