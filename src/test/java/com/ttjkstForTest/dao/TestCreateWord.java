package com.ttjkstForTest.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttjkst.bean.Kinds;
import com.ttjkst.bean.Word;
import com.ttjkst.service.IKindService;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.exception.ServiceException;

public class TestCreateWord {
	
	private ApplicationContext ctx = null;
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for(String e:beanDefinitionNames){
			System.out.print(e+"  ");
		}
	}
	@Test
	public void test(){
		
		IWordsService wordsService = ctx.getBean(IWordsService.class); 
		IKindService kindService = ctx.getBean(IKindService.class);
		Word w = new Word(); 
		List<Kinds> k = kindService.getkindsList("编程与思考");
		System.out.println("k"+k);
		List<Kinds> k2 = kindService.getkindsList("关于生活");
		System.out.println("k2"+k2);
		System.setProperty("LocalWebRoot","src/main/webapp/");
		for(int i=0; i<=300 ; i++){
			w = new Word();
			w.setCanShow(true);
			w.setwAuthor("ttjkst");
			w.setwTimeOfInData(new Date());
			if(i>100){
			w.setwTitle("标题：编程与思考"+i);
			w.setwKind(k.get(0));
			}else{
				w.setwTitle("标题：关于生活"+i);
				w.setwKind(k2.get(0));
			}
			try {
				wordsService.saveit(w, new FileInputStream(new File("src/main/webapp/html/关于编程/测试/00E303E556D7A5639C5B803CB96B0813.html")),
						new FileInputStream(new File("src/main/webapp/html/关于编程/测试/introduction/00E303E556D7A5639C5B803CB96B0813.html")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
