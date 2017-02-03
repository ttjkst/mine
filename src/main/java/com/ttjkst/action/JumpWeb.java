package com.ttjkst.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Word;
import com.ttjkst.service.IWordsService;

@Controller
public class JumpWeb {

	@Autowired
	private IWordsService wordService;
	
	
	//need fixed
	@RequestMapping("/readMore")
	public ModelAndView readMore(@RequestParam("id")String id){
		ModelAndView andView = new ModelAndView("readpage");
		Word words = wordService.getItbyId(id);
		andView.addObject(words);
		return andView;
	}
	
}
