package com.ttjkst.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletInputStream;

/**
 * @author ttjkst
 *
 */
public class FileTools {
	public void saveInAdress(String dest, BufferedReader br) throws IOException {
		OutputStreamWriter osw = null;
		BufferedWriter bfw = null;
		File file = new File(dest);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}

		osw = new OutputStreamWriter(new FileOutputStream(dest), "UTF-8");
		bfw = new BufferedWriter(osw);
		char[] b = new char[512];
		int len;
		while ((len = br.read(b)) != -1) {
			bfw.write(b, 0, len);
			bfw.flush();
		}

		bfw.close();

		br.close();

	}

	public void saveItAdressForInPutStream(String dest, ServletInputStream sis)
			throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File file = new File(dest);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}

		bis = new BufferedInputStream(sis);
		bos = new BufferedOutputStream(new FileOutputStream(dest));
		byte[] b = new byte[1024];
		int len;
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
			bos.flush();
		}
		bos.close();
		bis.close();
	}

	public void movefile(String src, String dest) throws FileToolsException {
		File srcFile = new File(src);
		File destFile = new File(dest);
		if (!destFile.exists()) {
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
		}
		System.out.println(src + "  moveTo->  " + dest);
		try {

			if (!srcFile.renameTo(destFile)) {
				throw new FileToolsException("file move fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBack(String src, String dest) throws FileToolsException {
		File srcFile = new File(src);
		File destFile = new File(dest);
		if (!destFile.renameTo(srcFile)) {
			throw new FileToolsException("file move fail:rollbak");
		}
	}

	public boolean detelefile(String dest) {
		File fi = new File(dest);

		return fi.delete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */

}
