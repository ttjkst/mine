package com.ttjkst.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Word;
import com.ttjkst.service.IWordsService;

@Controller
public class WordAction {
	@Autowired
	private IWordsService wordsService; 
	
	
	@ResponseBody
	@RequestMapping("/search")
	public Map<String, Object> search(@RequestParam("pageNo")int pageNo,@RequestParam("pageSize")int pageSize,
			@RequestParam("aboutWhat")String aboutWhat,@RequestParam("searchName")String searchName,@RequestParam("isNoProcess")Boolean isNoProcess){
		Map<String, Object> info = new HashMap<String, Object>();
		Page<Word> page = this.wordsService.findall(pageNo-1, pageSize, aboutWhat, searchName, isNoProcess);
		List<Word> content = page.getContent();
		long totalElements = page.getTotalElements();
		long totalPages = totalElements/pageSize+ (totalElements%pageSize==0?0:1);
		Iterator<Word> itera = content.iterator();
		while(itera.hasNext()){
			Word word_i = itera.next();
			word_i.setLeaveWords(null);
			//word_i.setwKind(null);
		}
		info.put("content", content);
		info.put("totalPages", totalPages);
		info.put("totalElements", totalElements);
		return info;
	}
  @ResponseBody
  @RequestMapping("/getTenWords")
  public List<Word>  getHotWords(@RequestParam("aboutWhat")String aboutWhat){
	  Page<Word> myPage =this.wordsService.findItByReadTimes(aboutWhat, 10);
	  List<Word> content = myPage.getContent();
	  Iterator<Word> it = content.iterator();
		while (it.hasNext()) {
			Word word_i = it.next();
			word_i.setLeaveWords(null);
			word_i.setwKind(null);
		}
	  return content;
  }
  //as last need fixed
  @RequestMapping("/readAndEdit")
  public ModelAndView  readAndEdit(@RequestParam("id")long id){
	  ModelAndView modelAndView = new ModelAndView("update");
	  modelAndView.addObject("word", this.wordsService.getItbyId(id));
	  System.out.println(this.wordsService.getItbyId(id));
	  return modelAndView;
  }
}
