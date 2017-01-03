package com.ttjkst.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Words;
import com.ttjkst.service.IWordsService;

@Controller
public class JumpWeb {

	@Autowired
	private IWordsService wordService;
	
	@RequestMapping("/readMore")
	public ModelAndView readMore(@RequestParam("id")Long id){
		ModelAndView andView = new ModelAndView("readpage");
		Words words = wordService.getItbyId(id);
		String wpath = words.getwPath().replaceAll("\\\\", "/");
		words.setwPath(wpath);
		andView.addObject(words);
		return andView;
	}
	
}
