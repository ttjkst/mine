package com.ttjkst.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Word;

public interface WordDAO extends CrudRepository<Word, String>,JpaSpecificationExecutor<Word>{

	// this method will be overrided by elastic
	//	@Query("SELECT count(w) FROM Word w WHERE w.title like ?1 "
//			+ " and w.wKind.kName like ?2"
//			+ " and w.wKind.aboutwhat.name like ?3")
//	public Integer countWordTitle(String title, String kindName,
//			String aboutWhatName);
	
	
}
