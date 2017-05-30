package com.ttjkst.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Album;

public interface AlbumDAO extends CrudRepository<Album, String>{
	@Query(value="select a from Album a")
	List<Album> getAll();
	
	@Query(value="select a from Album a where a.title like %?1% or a.des like %?1%")
	List<Album> findByConditions(String key);
	@Modifying
	@Query("update Album a set a.title=?1,a.des=?2,a.cover.id=?3 where a.id=?4")
	void updateIt(String title,String des,String cover_id,String id);
}
