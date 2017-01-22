package com.ttjkst.service;

import java.io.InputStream;

import org.springframework.data.domain.Page;

import com.ttjkst.bean.Word;
import com.ttjkst.service.exception.ServiceException;

public interface IWordsService {
	public Word saveit(Word word,InputStream srcWord,InputStream srcIntor) throws ServiceException;

	public void detele(Long id) throws ServiceException;

	public boolean hasWordByTitle(String title, String kindName,
			String abouWhatName);

	public Page<Word> findall(int pageNo, int pageSize,
			final String aboutWhatName, final String searchName,
			final Boolean isNoPrcess);

	public Word update(Word word,InputStream srcWord,InputStream srcIntor) throws ServiceException;

	public Page<Word> findItByReadTimes(final String aboutWhatName, int size);

	public Word getItbyId(Long id);

}
