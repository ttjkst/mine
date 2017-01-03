package com.ttjkst.service;

import org.springframework.data.domain.Page;

import com.ttjkst.bean.LeaveWords;

public interface ILeaveWordsService {
	public void detele(Long id);

	public void save(LeaveWords leaveWord);
	
	public LeaveWords findItById(Long id);

	public Long getlwNumByIdAndReaded(Long wordId,boolean isGetAll);

	public Page<LeaveWords> getAll(int pageSize, int pageNo,
			final Long wordId, String order, Boolean isNoProcess);

	public void updateHasRead(boolean hasRead, Long id);
}
