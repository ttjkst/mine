package com.ttjkst.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Words;
import com.ttjkst.service.ILeaveWordsService;
@SessionAttributes("word")
@Controller
public class LeaveWordAction {
	
	@Autowired
	private ILeaveWordsService service;
	
	
	@RequestMapping("/saveLeaveWord")
	public ResponseEntity<Object> saveLeaveWords(@RequestParam("word")Words word,
			@RequestParam("qq")String qq,
			@RequestParam("sayWhat")String sayWhat,
			@RequestParam("email")String email,
			@RequestParam("whose")String name){
		LeaveWords le = new LeaveWords();
		le.setEmail(email);
		le.setHasRead(false);
		le.setSaywhat(sayWhat);
		le.setQq(qq);
		le.setTimeInData(new Date());
		le.setWhose(name);
		le.setLeaveFor(word);
		service.save(le);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	@ResponseBody
	@RequestMapping("/currentleaveWordsInfo")
	public Map<String, Object>  getCurrent(@RequestParam("pageSize")int pageSize,
			@RequestParam("pageNo")int pageNo,@RequestParam("wordId")long wordId,
			@RequestParam("order")String order){
		Page<LeaveWords> page = this.service.getAll(pageSize,
				pageNo - 1, wordId, order, null);
		boolean hasNext = page.hasNext();
		boolean hasPre  = page.hasPrevious();
		//long total = page.getTotalPageNum();-> no need 
		List<LeaveWords> content = page.getContent();
		Iterator<LeaveWords> itera = content.iterator();
		while(itera.hasNext()){
			itera.next().setLeaveFor(null);
		}
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("hasNext", hasNext);
		info.put("hasPre", hasPre);
		info.put("content", content);
		return info;
	}
	
	
	@RequestMapping("/changReadState")
	public HttpStatus changReadState(@RequestParam("id")long id,@RequestParam("readState")boolean readState){
		this.service.updateHasRead(readState, id);
		return HttpStatus.OK;
	}
	
	@RequestMapping("/deleteComment")
	public ResponseEntity<Object>  deleteComment(@RequestParam("id")Long id){
		this.service.detele(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping("/lwSInfo")
	public long getLwNumByreadState(@RequestParam("wordId")long id,@RequestParam("readState")Boolean readState){

		return this.service.getAll(1, 1, id, "asc", readState).getTotalElements(); 
	}
	@ResponseBody
	@RequestMapping("/getLwsNum")
	public Callable<Map<String, Long>> getLwsNum(@RequestParam("id")final Long id){
		return new Callable<Map<String, Long>>() {
			
			public Map<String, Long> call() throws Exception {
				Map<String, Long> info = new HashMap<String, Long>();
				info.put("hasNoRead", service.getlwNumByIdAndReaded(id, false));
				info.put("all", service.getlwNumByIdAndReaded(id, true));
				return info;
			}
		};
	}
	
	@ResponseBody
	@RequestMapping("/getLwsNumNoAsync")
	public Map<String, Long> getLwsNumNoAsync(@RequestParam("id")Long id){
			Map<String, Long> info = new HashMap<String, Long>();
				info.put("hasNoRead", service.getlwNumByIdAndReaded(id, false));
				info.put("all", service.getlwNumByIdAndReaded(id, true));
				return info;	
	}
	
}
