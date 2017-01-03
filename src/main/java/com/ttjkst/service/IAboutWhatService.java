package com.ttjkst.service;

import java.util.List;

import com.ttjkst.bean.AboutWhats;

public interface IAboutWhatService {
	public List<AboutWhats> getAllAboutWhat();

	public boolean hasthis(String msg);

	public AboutWhats newAboutWhat(AboutWhats abWhats);
	
	public AboutWhats getItByName(String name);
}
