package com.ttjkst.service;

import java.util.List;

import com.ttjkst.bean.AboutWhat;

public interface IAboutWhatService {
	public List<AboutWhat> getAllAboutWhat();

	public boolean hasthis(String msg);

	public AboutWhat newAboutWhat(AboutWhat abWhats);
	
	public AboutWhat getItByName(String name);
}
