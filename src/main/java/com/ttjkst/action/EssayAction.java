package com.ttjkst.action;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

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
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.AboutWhat;
import com.ttjkst.bean.Kind;
import com.ttjkst.bean.Essay;
import com.ttjkst.service.IAboutWhatService;
import com.ttjkst.service.IKindService;
import com.ttjkst.service.IEssayService;
import com.ttjkst.service.exception.ServiceException;

@RestController
@RequestMapping("/essay")
public class EssayAction{

	
	@Autowired
	public IEssayService essayService;

	private String allow = "*";
	private HttpHeaders responseHeaders = new HttpHeaders();
	{
		responseHeaders.add("Access-Control-Allow-Origin", "*");
	}
	@Autowired
	public IAboutWhatService aboutWhatService;
	@Autowired
	public IKindService kindService ;
	@ResponseBody
	@RequestMapping(value="/getAboutwhat")
	public List<AboutWhat> getAbouts(){
		return this.aboutWhatService.getAllAboutWhat();
	}
	@RequestMapping(value="/init")
	public void init(){
		
	}
	@ResponseBody
	@RequestMapping("/getKindsByMsg")
	public List<Kind> getKind(@RequestParam("msg")String msg){
		return this.kindService.getkindsList(msg);
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
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		try {
			this.essayService.saveit(essay);
			return new ResponseEntity<Boolean>(true, responseHeaders,HttpStatus.OK);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, responseHeaders,HttpStatus.BAD_GATEWAY);
	}
	@ResponseBody
	@RequestMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestParam("pageNo")int pageNo,
			@RequestParam("pageSize")int pageSize,@RequestParam(value="searchKey",required=false)String searchName){
		//返回结果的容器
		Map<String, Object> info = new HashMap<String, Object>();
		Page<Essay> page = this.essayService.findall(pageNo-1, pageSize,searchName);
		List<Essay> content = page.getContent();
		long totalElements = page.getTotalElements();
		long totalPages = totalElements/pageSize+ (totalElements%pageSize==0?0:1);
		Iterator<Essay> itera = content.iterator();
		while(itera.hasNext()){
			Essay word_i = itera.next();
			word_i.setLeaveWords(null);
			//word_i.setwKind(null);
		}
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
	
	//need fixed
//	@ResponseBody
//	@RequestMapping(value="/saveWord")
//	public Boolean saveWord(@RequestParam("wordText")MultipartFile wordText,@RequestParam("intor")MultipartFile inrotext,
//			@RequestParam("author")String author,@RequestParam("title")String title,
//			@RequestParam("canShow")String canShow,
//			@RequestParam("abName")String aboutWhatName,@RequestParam("kindName")String kindName,
//			@RequestParam("msg")String msg){
//		
//		
//		Kind kind = new Kind();
//		kind.setName(kindName);
//		AboutWhat aboutWhat = new AboutWhat();
//		aboutWhat.setName(aboutWhatName);
//		kind.setAboutwhat(aboutWhat);
//		Essay word = new Essay();
//							//id    title
//			 word = new Essay();
//			 
//			 try {
//				this.wordsService.saveit(word, wordText.getInputStream(), inrotext.getInputStream());
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}finally{
//				if(wordText!=null){
//					try {
//						wordText.getInputStream().close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				
//				if(inrotext!=null){
//					try {
//						inrotext.getInputStream().close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			 
//			 
//		return true;
//	}
	
	
	//need fixed
	@ResponseBody
	@RequestMapping(value="/verifyTitle")
	public Boolean verifyTitle(@RequestParam("id")String id,@RequestParam("title")String title,@RequestParam("kindName")String kindName,@RequestParam("aboutWhatName")String  aboutWhatName,@RequestParam("webDoWhat")String webDoWhat){
		if(id==null){
			return !this.essayService.hasWordByTitle(title, kindName, aboutWhatName);
		}else{
			Essay word = essayService.getItbyId(id);
			if(word.getTitle()!=null){
				return word.getTitle().equals(title);
			}
		}
		return false;
	}
	//need fixed
//	@ResponseBody
//	@RequestMapping("/update")
//	public Boolean update(@RequestParam("wordId")Long id,@RequestParam("wordText")MultipartFile wordText,@RequestParam("intor")MultipartFile inrotext,
//			@RequestParam("author")String author,@RequestParam("title")String title,
//			@RequestParam("canShow")String canShow,
//			@RequestParam("abName")String aboutWhatName,@RequestParam("kindName")String kindName,
//			@RequestParam("msg")String msg){
//		
//		
//		
//		Kind kind = new Kind();
//		kind.setName(kindName);
//		AboutWhat aboutWhat = new AboutWhat();
//		aboutWhat.setName(aboutWhatName);
//		kind.setAboutwhat(aboutWhat);
//		Essay word = new Essay();
//							//id    title
//			 word = new Essay();
//			 
//			 try {
//				this.wordsService.update(word, wordText.getSize()==0?null:wordText.getInputStream(), inrotext.getSize()==0?null:inrotext.getInputStream());
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}finally{
//				if(wordText!=null){
//					try {
//						wordText.getInputStream().close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				
//				if(inrotext!=null){
//					try {
//						inrotext.getInputStream().close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			 
//			 
//		return true;
//	}
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
