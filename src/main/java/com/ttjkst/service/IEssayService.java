package com.ttjkst.service;


import java.util.List;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.springframework.data.domain.Page;

import com.ttjkst.bean.Essay;
import com.ttjkst.service.exception.ServiceException;

public interface IEssayService {
	public Essay saveit(Essay word) throws ServiceException;

	public void detele(String id) throws ServiceException;

	public Page<Essay> findall(int pageNo, int pageSize,
				final String searchName);
	public Page<Essay> findallWithHighlighter(int pageNo, int pageSize,
			final String searchName);

	public Essay update(Essay word) throws ServiceException;

	public Essay getItbyId(String id);

	List<AnalyzeToken> ikTest(String content);

}
