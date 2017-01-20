package com.ttjkst.fileSystem;

import java.io.File;
import java.util.List;

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
//		@Test
//		public  void persist(){
//			File file = new File("C:\\Users\\ttjkst\\Desktop\\test\\1.txt");
//			fileSystem.persist(file, "1");
//			file = new File("C:\\Users\\ttjkst\\Desktop\\test\\2.txt");
//			fileSystem.persist(file, "2");
//			file = new File("C:\\Users\\ttjkst\\Desktop\\test\\3.txt");
//			fileSystem.persist(file, "3");
//			file = new File("C:\\Users\\ttjkst\\Desktop\\test\\4.txt");
//			fileSystem.persist(file, "4");
//			file = new File("C:\\Users\\ttjkst\\Desktop\\test\\5.txt");
//			fileSystem.persist(file, "5");
//			fileSystem.close();
//		}
		@Test
		public void search(){
			List<String> search = fileSystem.search("arcu");
			search.forEach(x->System.out.println(x));
		}
//		@Test
//		public void delete(){
//			fileSystem.delete("HELLOW");
//		}
}
