package com.ttjkst.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Album;
import com.ttjkst.bean.Image;
import com.ttjkst.service.IAlbumService;
import com.ttjkst.service.IimageService;
import com.ttjkst.service.exception.ServiceException;

@Controller
@RequestMapping("/album/image")
public class IMageController {
	
	@Autowired
	private IimageService service;
	
	@Autowired
	private IAlbumService albumService;
	
	@RequestMapping(path={"/go/{album_id}"})
	private ModelAndView lookeImages(@PathVariable("album_id")String album_id){
		ModelAndView andView = new ModelAndView("image");
		List<Image> images = service.getImages(album_id);
		Album album = albumService.get(album_id);
		andView.addObject("album", album);
		andView.addObject("images", images);
		return andView;
	}
	@ResponseBody
	@RequestMapping(path={"/go/json/{album_id}"},produces="application/json")
	private List<Image> lookeImagesFojson(@PathVariable("album_id")String album_id,HttpSession session){
		List<Image> images = service.getImages(album_id);
		return images;
	}
	@ResponseBody
	@RequestMapping("/load/{id}")
	public ResponseEntity<byte[]> loadIMage(@PathVariable("id")String id){
		Image image = service.get(id);
		InputStream binaryStream = null;
		byte[] bytes = null;
		try {
			binaryStream = image.getBlob().getBinaryStream();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			bytes = new byte[binaryStream.available()];
			binaryStream.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(bytes, bytes==null?HttpStatus.BAD_REQUEST:HttpStatus.OK);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView save(@RequestParam("title")String title,
			@RequestParam("image")MultipartFile file,
			@RequestParam(value="isPrimary",required=false)String isPrimary,
			@RequestParam("album_id")String album_id){
		Image image = new Image();
		image.setAlbum_id(album_id);
		try {
			image.setBlob(new SerialBlob(file.getBytes()));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		image.setIsPrimary(isPrimary==null?false:true);
		image.setTitle(title);
		service.save(image, file.getOriginalFilename());
	return new ModelAndView("redirect:/album/image/go/"+album_id);	
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("title")String title,
			@RequestParam("image")MultipartFile file,
			@RequestParam(value="isPrimary",required=false)String isPrimary,
			@RequestParam("album_id")String album_id,@RequestParam("id")String id,HttpSession session){
		Image image = new Image();
		image.setAlbum_id(album_id);
		image.setId(id);
		try {
			image.setBlob(new SerialBlob(file.getBytes()));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		image.setTitle(title);
		image.setIsPrimary(isPrimary==null?false:true);
		service.updateIt(image);
		return new ModelAndView("redirect:/album/image/go/"+album_id);
	}
	@RequestMapping("/delete/{album_id}/{id}")
	public ModelAndView deleteImage(@PathVariable("album_id")String album_id,@PathVariable("id")String id,HttpSession session){
		session.setAttribute("error", "");
		try {
			service.delete(id);
		} catch (ServiceException e) {
			session.setAttribute("error", e.getMessage());
		}
		return new ModelAndView("redirect:/album/image/go/"+album_id);
	}
}
