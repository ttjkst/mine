package com.ttjkst.fileSystem;

public class FileSytemFactory {
		private String esUrl = "";
		private String filePath = "";
		public SimpleFileSystem getSimpleFileSystem(){
			return new SimpleFileSystem();
		}
		
		
		
}
