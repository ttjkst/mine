package com.ttjkst.action;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.ttjkst.bean.AboutWhat;
import com.ttjkst.bean.Kind;
import com.ttjkst.bean.Word;
import com.ttjkst.service.IAboutWhatService;
import com.ttjkst.service.IKindService;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;

@Controller
public class Edit implements ServletContextAware{

	
	@Autowired
	public IWordsService wordsService;

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
	
	
	//need fixed
	@ResponseBody
	@RequestMapping(value="/saveWord")
	public Boolean saveWord(@RequestParam("wordText")MultipartFile wordText,@RequestParam("intor")MultipartFile inrotext,
			@RequestParam("author")String author,@RequestParam("title")String title,
			@RequestParam("canShow")String canShow,
			@RequestParam("abName")String aboutWhatName,@RequestParam("kindName")String kindName,
			@RequestParam("msg")String msg){
		
		
		Kind kind = new Kind();
		kind.setName(kindName);
		AboutWhat aboutWhat = new AboutWhat();
		aboutWhat.setName(aboutWhatName);
		kind.setAboutwhat(aboutWhat);
		Word word = new Word();
							//id    title
			 word = new Word();
			 
			 try {
				this.wordsService.saveit(word, wordText.getInputStream(), inrotext.getInputStream());
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}finally{
				if(wordText!=null){
					try {
						wordText.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(inrotext!=null){
					try {
						inrotext.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			 
			 
		return true;
	}
	
	
	//need fixed
	@ResponseBody
	@RequestMapping(value="/verifyTitle")
	public Boolean verifyTitle(@RequestParam("id")String id,@RequestParam("title")String title,@RequestParam("kindName")String kindName,@RequestParam("aboutWhatName")String  aboutWhatName,@RequestParam("webDoWhat")String webDoWhat){
		if(id==null){
			return !this.wordsService.hasWordByTitle(title, kindName, aboutWhatName);
		}else{
			Word word = wordsService.getItbyId(id);
			if(word.getTitle()!=null){
				return word.getTitle().equals(title);
			}
		}
		return false;
	}
	//need fixed
	@ResponseBody
	@RequestMapping("/update")
	public Boolean update(@RequestParam("wordId")Long id,@RequestParam("wordText")MultipartFile wordText,@RequestParam("intor")MultipartFile inrotext,
			@RequestParam("author")String author,@RequestParam("title")String title,
			@RequestParam("canShow")String canShow,
			@RequestParam("abName")String aboutWhatName,@RequestParam("kindName")String kindName,
			@RequestParam("msg")String msg){
		
		
		
		Kind kind = new Kind();
		kind.setName(kindName);
		AboutWhat aboutWhat = new AboutWhat();
		aboutWhat.setName(aboutWhatName);
		kind.setAboutwhat(aboutWhat);
		Word word = new Word();
							//id    title
			 word = new Word();
			 
			 try {
				this.wordsService.update(word, wordText.getSize()==0?null:wordText.getInputStream(), inrotext.getSize()==0?null:inrotext.getInputStream());
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}finally{
				if(wordText!=null){
					try {
						wordText.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(inrotext!=null){
					try {
						inrotext.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			 
			 
		return true;
	}
	@RequestMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam("id")String id){
		assertNotNull(id);
		if(id.equals(null)){
			throw new IllegalArgumentException("the args must has a  id,but it do not has one");
		}
		try {
			this.wordsService.detele(id);
		} catch (ServiceException e) {
			
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//inject ServletContext 
	//init webRoot
	public void setServletContext(ServletContext servletContext) {
		System.out.println(servletContext.getRealPath("/"));
	 System.setProperty("LocalWebRoot", servletContext.getRealPath("/"));	
	}	
	
}
