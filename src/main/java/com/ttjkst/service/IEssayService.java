package com.ttjkst.service;


import org.springframework.data.domain.Page;

import com.ttjkst.bean.Essay;
import com.ttjkst.service.exception.ServiceException;

public interface IEssayService {
	public Essay saveit(Essay word) throws ServiceException;

	public void detele(String id) throws ServiceException;

	public boolean hasWordByTitle(String title, String kindName,
			String abouWhatName);

	public Page<Essay> findall(int pageNo, int pageSize,
				final String searchName);

	public Essay update(Essay word) throws ServiceException;

	public Page<Essay> findItByReadTimes(final String aboutWhatName, int size);

	public Essay getItbyId(String id);

}
