package com.ttjkst.tools.page.iPage;

import org.hibernate.Criteria;

public interface CriteriaUtil<T> {
	public Criteria toCriteria(Criteria criteria);
}
