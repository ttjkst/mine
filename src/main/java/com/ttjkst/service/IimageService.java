package com.ttjkst.service;

import java.util.List;

import com.ttjkst.bean.Image;
import com.ttjkst.service.exception.ServiceException;

public interface IimageService {
	Image get(String id);
	
	List<Image> getImages(String album_id);
	
	void delete(String id) throws ServiceException;
	void save(Image image,String orgFileName);
	
	void updateIt(Image image);
}
