package com.ttjkst.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/basicImage")
public class BasicImageController {
	
	private String dir = "C:/Users/ttjkst/Desktop/fileData";
	@RequestMapping("/save")
	public ResponseEntity<String> save(@RequestParam("file") MultipartFile file){
		String name =  UUID.randomUUID().toString()+file.getOriginalFilename();
		File dest = new File(dir, name);
		try {
			FileCopyUtils.copy(file.getBytes(), dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<String>(name, responseHeaders, HttpStatus.OK);	
	}
	@RequestMapping("/get")
	public ResponseEntity<byte[]> getFile(@RequestParam("name") String name){
		File neededReturn = new File(dir, name);
		FileInputStream stream =null;
		try {
			stream = new FileInputStream(neededReturn);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		byte[] b = null;
		try {
			b = new byte[stream.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(b,HttpStatus.OK);
	}
	
}
