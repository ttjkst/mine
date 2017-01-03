package com.ttjkst.service;

import java.io.InputStream;

import org.springframework.data.domain.Page;

import com.ttjkst.bean.Words;
import com.ttjkst.service.exception.ServiceException;

public interface IWordsService {
	public Words saveit(Words word,InputStream srcWord,InputStream srcIntor) throws ServiceException;

	public void detele(Long id) throws ServiceException;

	public boolean hasWordByTitle(String title, String kindName,
			String abouWhatName);

	public Page<Words> findall(int pageNo, int pageSize,
			final String aboutWhatName, final String searchName,
			final Boolean isNoPrcess);

	public Words update(Words word,InputStream srcWord,InputStream srcIntor) throws ServiceException;

	public Page<Words> findItByReadTimes(final String aboutWhatName, int size);

	public Words getItbyId(Long id);

}
