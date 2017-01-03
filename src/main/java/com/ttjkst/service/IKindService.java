package com.ttjkst.service;

import java.util.List;

import com.ttjkst.bean.Kinds;

public interface IKindService {
	public List<Kinds> getkindsList(String msg);

	public boolean haskindbyW(String msg, String parentMsg);
	public Kinds getkindbyNameAndAb(String msg, String parentMsg);

	public Kinds savekinds(Kinds kind);
	
}
