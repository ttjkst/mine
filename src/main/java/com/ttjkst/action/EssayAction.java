package com.ttjkst.action;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Essay;
import com.ttjkst.service.IEssayService;
import com.ttjkst.service.exception.ServiceException;

@RestController
@RequestMapping("/essay")
public class EssayAction{

	@RequestMapping("/me")
	public ModelAndView home(){
		return new ModelAndView("/home");
	}
	
	@Autowired
	public IEssayService essayService;

	private HttpHeaders responseHeaders = new HttpHeaders();
	@RequestMapping("/ik")
	public ModelAndView toIk(){
		return new ModelAndView("ik");
	}
	@ResponseBody
	@RequestMapping("/ik/analyze")
	public List<AnalyzeToken> ikTest(@RequestParam("content")String content){
		return essayService.ikTest(content);
	}
	@RequestMapping("/save")
	public ResponseEntity<Boolean> saveEassy(@RequestParam("content")String content,@RequestParam("title")String title,@RequestParam("author")String author,@RequestParam(value="tags",required=false)String tags){
		Essay essay = new Essay();
		if(tags!=null&&!tags.isEmpty()){
			Arrays.asList(tags.split(",")).forEach(x->{
				if(!x.trim().isEmpty()){
					essay.getTags().add(x);
				}
			});
		}
		essay.setAuthor(author);
		essay.setContent(content);
		essay.setTitle(title);
		try {
			this.essayService.saveit(essay);
			return new ResponseEntity<Boolean>(true, responseHeaders,HttpStatus.OK);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, responseHeaders,HttpStatus.BAD_GATEWAY);
	}
	@ResponseBody
	@RequestMapping(path={"/search"})
	public ResponseEntity<Map<String, Object>> search(@RequestParam("pageNo")int pageNo,
			@RequestParam("pageSize")int pageSize,@RequestParam(value="searchKey",required=false)String searchName){
		//返回结果的容器
		Map<String, Object> info = new HashMap<String, Object>();
		Page<Essay> page = this.essayService.findall(pageNo-1, pageSize,searchName);
		List<Essay> content = page.getContent();
		long totalElements = page.getTotalElements();
		long totalPages = totalElements/pageSize+ (totalElements%pageSize==0?0:1);
		info.put("content", content);
		info.put("totalPages", totalPages);
		info.put("totalElements", totalElements);
		return new ResponseEntity<>(info,responseHeaders,HttpStatus.OK);
	}
	//高亮结果
	@ResponseBody
	@RequestMapping(path={"/searchWithHighlighter"})
	public ResponseEntity<Map<String, Object>> searchWithHighlighter(@RequestParam("pageNo")int pageNo,
			@RequestParam("pageSize")int pageSize,@RequestParam(value="searchKey",required=false)String searchName){
		//返回结果的容器
		Map<String, Object> info = new HashMap<String, Object>();
		Page<Essay> page = this.essayService.findallWithHighlighter(pageNo-1, pageSize,searchName);
		List<Essay> content = page.getContent();
		long totalElements = page.getTotalElements();
		long totalPages = totalElements/pageSize+ (totalElements%pageSize==0?0:1);
		info.put("content", content);
		info.put("totalPages", totalPages);
		info.put("totalElements", totalElements);
		return new ResponseEntity<>(info,responseHeaders,HttpStatus.OK);
	}
	@RequestMapping("/load/{id}")
	public ResponseEntity<Essay> loadEssay(@PathVariable("id")String id){
		Essay body = this.essayService.getItbyId(id);
		return new ResponseEntity<Essay>(body, responseHeaders, HttpStatus.OK);
	}
	@RequestMapping("/update")
	public ResponseEntity<?> updateEssay(
			@RequestParam("id")		String id,
			@RequestParam("author")	String author,
			@RequestParam("content")String content,
			@RequestParam("title")	String title,
			@RequestParam("tags")	String tags){
		Essay essay = new Essay();
		essay.setId(id);
		essay.setAuthor(author);
		essay.setContent(content);
		essay.setTitle(title);
		if(tags!=null&&!tags.isEmpty()){
			Arrays.asList(tags.split(",")).forEach(x->{
				if(!x.trim().isEmpty()){
					essay.getTags().add(x);
				}
			});
		}
		Object body = null;
		try {
			this.essayService.update(essay);
		} catch (ServiceException e) {
			e.printStackTrace();
			body = e.getMessage();
		}
		if(body==null){
			body = true;
		}
		return new ResponseEntity<>(body, responseHeaders,body.equals(true)?HttpStatus.OK
				:HttpStatus.BAD_REQUEST);	
	}
	
	@RequestMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id")String id){
		assertNotNull(id);
		if(id.equals(null)){
			throw new IllegalArgumentException("the args must has a  id,but it do not has one");
		}
		try {
			this.essayService.detele(id);
		} catch (ServiceException e) {
			
			e.printStackTrace();
			return new ResponseEntity<String>("",responseHeaders,HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<String>("",responseHeaders,HttpStatus.OK);
	}
	@RequestMapping("/manager")
	public ModelAndView gotoManger(){
		return new ModelAndView("manager");
	}
}
