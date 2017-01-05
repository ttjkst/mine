package com.ttjkst.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.AboutWhats;

public interface ABDAO extends CrudRepository<AboutWhats, Long>{
		
		@Query("select count(a) from AboutWhats a where a.name like ?1")
		public int getCountByName(String name);
		
		@Query("select a from AboutWhats a where a.name like ?1")
		public AboutWhats getItByName(String name);
}
