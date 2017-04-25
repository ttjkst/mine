package com.ttjkst.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ttjkst.bean.Album;
import com.ttjkst.bean.Image;

public interface ImageDAO extends JpaRepository<Image, String>{

	@Query("select image from Image image where image.id like ?1%")
	Image getImageByID(String id);
	
	@Query("select image from Image image where image.album_id like ?1")
	List<Image> getImagesByAblum(String album_id);
}
