package com.ttjkst.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ttjkst.BootApplication;
import com.ttjkst.bean.Essay;
import com.ttjkst.service.IEssayService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=BootApplication.class)
public class EssayServiceTest {

	@Autowired
	private IEssayService wordService;
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
	
//	@Test
//	public void search(){
//		 Page<Essay> page = this.wordService.findall(0, 4 ,"dolor");
//		 System.out.println(page.getTotalPages());
//		 page.getContent().forEach(x->{
//			 System.out.println(x);
//		 });
//	}

	
	
	@Test
	public void getItbyId(){
		Essay word = this.wordService.getItbyId("AVuAzfYhBL1kDjz1g7bA");
		System.out.println(word.toString());
	}
	
}
