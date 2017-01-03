package com.ttjkstForTest.dao;

import java.io.File;
import java.io.IOException;

public class TestFile {
	public static void main(String[] args) {
		File file = new File("./你好.text");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(file.getAbsolutePath());
	}
}
