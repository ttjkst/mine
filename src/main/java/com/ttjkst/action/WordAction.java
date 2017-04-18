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

import com.ttjkst.bean.Essay;
import com.ttjkst.service.IEssayService;

@Controller
public class WordAction {
	@Autowired
	private IEssayService wordsService; 
	
	
//	@ResponseBody
//	@RequestMapping("/search")
//	public Map<String, Object> search(@RequestParam("pageNo")int pageNo,@RequestParam("pageSize")int pageSize,@RequestParam("searchKey")String searchName){
//		Map<String, Object> info = new HashMap<String, Object>();
//		Page<Essay> page = this.wordsService.findall(pageNo, pageSize,searchName);
//		List<Essay> content = page.getContent();
//		long totalElements = page.getTotalElements();
//		long totalPages = totalElements/pageSize+ (totalElements%pageSize==0?0:1);
//		Iterator<Essay> itera = content.iterator();
//		while(itera.hasNext()){
//			Essay word_i = itera.next();
//			word_i.setLeaveWords(null);
//			//word_i.setwKind(null);
//		}
//		info.put("content", content);
//		info.put("totalPages", totalPages);
//		info.put("totalElements", totalElements);
//		return info;
//	}
	@RequestMapping("/me")
	public ModelAndView home(){
		return new ModelAndView("/home");
	}
	
  @ResponseBody
  @RequestMapping("/getTenWords")
  public List<Essay>  getHotWords(@RequestParam("aboutWhat")String aboutWhat){
	  Page<Essay> myPage =this.wordsService.findItByReadTimes(aboutWhat, 10);
	  List<Essay> content = myPage.getContent();
	  Iterator<Essay> it = content.iterator();
	  return content;
  }
  //as last need fixed
  @RequestMapping("/readAndEdit")
  public ModelAndView  readAndEdit(@RequestParam("id")String id){
	  ModelAndView modelAndView = new ModelAndView("update");
	  modelAndView.addObject("word", this.wordsService.getItbyId(id));
	  System.out.println(this.wordsService.getItbyId(id));
	  return modelAndView;
  }
}
