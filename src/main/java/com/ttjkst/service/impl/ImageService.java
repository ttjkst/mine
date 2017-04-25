package com.ttjkst.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttjkst.bean.Album;
import com.ttjkst.bean.Image;
import com.ttjkst.dao.AlbumDAO;
import com.ttjkst.dao.ImageDAO;
import com.ttjkst.service.IimageService;
import com.ttjkst.service.Util.IdBuilder;
import com.ttjkst.service.exception.ServiceException;
@Service
public class ImageService implements IimageService{

	@Autowired
	private ImageDAO dao;
	@Autowired
	private AlbumDAO albumDao;
	@Override
	public Image get(String id) {
		Image findOne = dao.getImageByID(id);
		findOne.getBlob();
		return findOne;
	}
	@Override
	public List<Image> getImages(String album_id) {
		return dao.getImagesByAblum(album_id);
	}
	@Override
	public void delete(String id) throws ServiceException {
		Image findOne = dao.getImageByID(id);
		Album findOne2 = albumDao.findOne(findOne.getAlbum_id());
		if(findOne2.getCover().getId().startsWith(id)){
			throw new ServiceException("This image is cover,Do not delete it");
		}
		dao.delete(findOne);
	}
	@Override
	public void save(Image image,String orgFileName) {
		image.setId(IdBuilder.stringId()+orgFileName);
		dao.save(image);
	}
	@Override
	public void updateIt(Image image) {
		Image orgImage = dao.findOne(image.getId());
		try {
			if(image.getBlob().length()==0){
				image.setBlob(orgImage.getBlob());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dao.save(image);
	}

	
	
}
