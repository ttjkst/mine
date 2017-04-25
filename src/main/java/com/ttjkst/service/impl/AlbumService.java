package com.ttjkst.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttjkst.bean.Album;
import com.ttjkst.dao.AlbumDAO;
import com.ttjkst.dao.ImageDAO;
import com.ttjkst.service.IAlbumService;
import com.ttjkst.service.Util.IdBuilder;
@Service
@Transactional
public class AlbumService implements IAlbumService{

	@Autowired
	private AlbumDAO dao;
	
	@Autowired
	private ImageDAO imageDao;
	@Override
	public void save(Album album) {
		album.setId(IdBuilder.stringId());
		album.getCover().setId(IdBuilder.stringId()+album.getCover().getId());
		album.getCover().setIsPrimary(false);
		album.getCover().setTitle(album.getId());
		imageDao.save(album.getCover());
		dao.save(album);
		album.getCover().setAlbum_id(album.getId());
		imageDao.save(album.getCover());
	}
	@Override
	public List<Album> loadAll() {
		return dao.getAll();
	}
	@Override
	public List<Album> search(String key) {
		List<Album> findByConditions = dao.findByConditions(key);
		return findByConditions;
	}
	@Override
	public Album get(String id) {
		return dao.findOne(id);
	}
	@Override
	public void updateThinking(String id, String thinking) {
		Album findOne = dao.findOne(id);
		findOne.setThinking(thinking);
		dao.save(findOne);
	}
	@Override
	public void update(Album album) {
		dao.updateIt(album.getTitle(), album.getDes(), album.getCover().getId(), album.getId());
	}
	@Override
	public void delete(String id) {
		Album album = dao.findOne(id);
		List<String> images = new ArrayList<>();
		album.getImages().forEach(x->images.add(x.getId()));
		dao.delete(id);
		images.forEach(x->imageDao.delete(x));
	}
	
}
