package com.ttjkst.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.AboutWhat;

public interface ABDAO extends CrudRepository<AboutWhat, Long>{
		
		@Query("select count(a) from AboutWhat a where a.name like ?1")
		public int getCountByName(String name);
		
		@Query("select a from AboutWhat a where a.name like ?1")
		public AboutWhat getItByName(String name);
}
