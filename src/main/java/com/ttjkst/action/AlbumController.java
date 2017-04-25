package com.ttjkst.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Album;
import com.ttjkst.bean.Image;
import com.ttjkst.service.IAlbumService;

@Controller
@RequestMapping("/album")
public class AlbumController {

	
	@Autowired
	private IAlbumService service;
	@RequestMapping(path={"/",""})
	public ModelAndView home(){
	List<Album> loadAll = service.loadAll();
	ModelAndView andView = new ModelAndView("album");
	andView.addObject("albums", loadAll);
	return andView;	
	}
	@RequestMapping("/search/")
	public ModelAndView search(@RequestParam("key")String key){
		List<Album> search = service.search(key);
		ModelAndView andView = new ModelAndView("album");
		andView.addObject("albums", search);
		return andView;
	}
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView save(@RequestParam("title")String title,@RequestParam("des")String des,
			@RequestParam("cover")MultipartFile file){
		Image image = new Image();
		try {
			image.setBlob(new SerialBlob(file.getBytes()));
			image.setId(file.getOriginalFilename());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		Album album = new Album();
		album.setCover(image);
		album.setDes(des);
		album.setTitle(title);
		service.save(album);
		return new ModelAndView("redirect:/album/");
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("title")String title,@RequestParam("des")String des,
			@RequestParam("cover")String cover_id,@RequestParam("id")String id){
		Image image = new Image();
		image.setId(cover_id);
		Album album = new Album();
		album.setCover(image);
		album.setDes(des);
		album.setId(id);
		album.setTitle(title);
		service.update(album);
		return new ModelAndView("redirect:/album/");
	}
	@RequestMapping(value="/changeThinking",method=RequestMethod.POST)
	public ModelAndView changeThinking(@RequestParam("thinking")String thinking,
			@RequestParam("album_id")String album_id){
		service.updateThinking(album_id, thinking);
		return new ModelAndView("redirect:/album/image/go/"+album_id);
	}
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id")String id){
		service.delete(id);
		return new ModelAndView("redirect:/album/");
	}
}
