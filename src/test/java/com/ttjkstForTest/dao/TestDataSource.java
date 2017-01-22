package com.ttjkstForTest.dao;


import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ttjkst.bean.AboutWhats;
import com.ttjkst.bean.Kinds;
import com.ttjkst.bean.LeaveWords;
import com.ttjkst.bean.Word;
import com.ttjkst.dao.LWDAO;
import com.ttjkst.dao.WordDAO;
import com.ttjkst.service.ILeaveWordsService;

public class TestDataSource {
	
	private ApplicationContext ctx = null;
	{
		
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
//	@Test
//	public void test() {
//		DataSource dataSource = ctx.getBean(DataSource.class);
//		tetSystem.out.println(dataSource);
//	}
//	@Test
//	public void test() {
//		LWDAO dao = ctx.getBean(LWDAO.class);
//		System.out.println(dao.getCountByReaded(1L));
//	}
	
//	@Test
//	public void test2(){
//		LWDAO dao = ctx.getBean(LWDAO.class);
//		dao.updateHasRead(false, 1L);
////		LeaveWords entity = new LeaveWords();
////		entity.setEmail("11111");
////		entity.setHasRead(true);
////		entity.setTimeInData(new Date());
////		
////		dao.save(entity);
//	}
	
	@Test
	public void test3(){
		ILeaveWordsService service2 = ctx.getBean(ILeaveWordsService.class);
		Page<LeaveWords> page = service2.getAll(1, 0, 1L, "asc", false);
		System.out.println(page.getContent());
	}

}
