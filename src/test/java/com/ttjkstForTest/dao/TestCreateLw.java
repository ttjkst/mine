package com.ttjkstForTest.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Word;
import com.ttjkst.service.ILeaveWordsService;
import com.ttjkst.service.IWordsService;
import com.ttjkst.service.impl.LService;
import com.ttjkst.service.impl.WordService;

public class TestCreateLw {
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
		ILeaveWordsService lService = ctx.getBean(ILeaveWordsService.class);
		IWordsService wService = ctx.getBean(IWordsService.class);
		Word word = wService.getItbyId(17L);
		for(int i=0;i<=50;i++){
			LeaveWords leaveWords = new LeaveWords();
			leaveWords.setEmail("sss@qq.com");
			leaveWords.setQq("111111");
			leaveWords.setSaywhat("模拟评论水水水水水");
			leaveWords.setHasRead(true);
			leaveWords.setLeaveFor(word);
			leaveWords.setTimeInData(new Date());
			lService.save(leaveWords);
		}
	}
	
	
}
