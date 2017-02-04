package com.ttjkst.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ttjkst.BootApplication;
import com.ttjkst.bean.AboutWhat;
import com.ttjkst.bean.Kind;
import com.ttjkst.bean.Word;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=BootApplication.class)
public class WordServiceTest {

	@Autowired
	private IWordsService wordService;
//	//@Test
//	public void saveit(){
//		File file = new File("C:\\Users\\ttjkst\\Desktop\\test\\1.txt");
//		FileInputStream fileInputStream = null;
//		try {
//			fileInputStream = new FileInputStream(file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Kind kinds = new Kind();
//		AboutWhat aboutWhats = new AboutWhat();
//		aboutWhats.setName("sss");
//		kinds.setName("sasas1");
//		kinds.setAboutwhat(aboutWhats);
//		Word word = new Word();
//		word.setAuthor("ttjkst");
//		word.setCanShow(false);
//		word.setCreateTime(new Date());
//		word.setReadedTimes(0L);
//		word.setKind(kinds);
//		word.setTitle("ttjkst");
//		try {
//			wordService.saveit(word, fileInputStream, null);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
//	@Test
//	public void createData(){
//		for(int i =0;i<100;i++){
//			this.saveit();
//		}
//	}
	
	@Test
	public void search(){
		 Page<Word> page = this.wordService.findall(1, 4, "sss", "finibus", null);
		 System.out.println(page.getTotalPages());
		 page.getContent().forEach(x->{
			 System.out.println(x);
		 });
	}

	
	
//	@Test
//	public void getItbyId(){
//		Word word = this.wordService.getItbyId("AVoGw5Sz_BodVjpyWThz");
//		System.out.println(word.toString());
//	}
	
}
