package com.ttjkst.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Essay;

public interface EssayDAO extends CrudRepository<Essay, String>,JpaSpecificationExecutor<Essay>{

	@Query("select e from Essay e where e.title=?1")
	List<Essay> getItByTitle(String title);
	
	@Query("select e from Essay e where e.id not like ?1 and e.title=?2")
	List<Essay> getItByTitle2(String id,String title);
}
