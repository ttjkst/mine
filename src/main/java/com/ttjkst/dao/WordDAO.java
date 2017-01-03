package com.ttjkst.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ttjkst.bean.Words;
@Repository
public interface WordDAO extends CrudRepository<Words, Long>,JpaSpecificationExecutor<Words>{
	@Query("SELECT count(w) FROM Words w WHERE w.wTitle like ?1 "
			+ " and w.wKind.kName like ?2"
			+ " and w.wKind.aboutwhat.name like ?3")
	public Integer countWordTitle(String title, String kindName,
			String aboutWhatName);
	
	
}
