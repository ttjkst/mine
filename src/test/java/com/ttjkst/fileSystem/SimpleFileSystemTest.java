package com.ttjkst.fileSystem;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ttjkst.BootApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=BootApplication.class)
public class SimpleFileSystemTest {
	
		@Autowired
		private SimpleFileSystem fileSystem;
		@Test
		public  void persist(){
			File file = new File("C:\\Users\\ttjkst\\Desktop\\test\\maven r.html");
			fileSystem.persist(file, "HELLOW");
		}
}
