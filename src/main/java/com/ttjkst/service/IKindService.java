package com.ttjkst.service;

import java.util.List;

import com.ttjkst.bean.Kind;

public interface IKindService {
	public List<Kind> getkindsList(String msg);

	public boolean haskindbyW(String msg, String parentMsg);
	public Kind getkindbyNameAndAb(String msg, String parentMsg);

	public Kind savekinds(Kind kind);
	
}
