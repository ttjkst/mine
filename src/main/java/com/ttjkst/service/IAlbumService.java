package com.ttjkst.service;


import java.util.List;


import com.ttjkst.bean.Album;

public interface IAlbumService {
	void save(Album album);
	List<Album> loadAll();
	
	List<Album> search(String key);
	
	Album get(String id);
	void updateThinking(String id,String thinking);
	
	void update(Album album);
	void delete(String id);
}
