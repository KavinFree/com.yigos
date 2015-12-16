package com.yigos.framework.dao.jpa.itf;

import java.util.List;
import java.util.Map;

public interface JpaBaseDao {

	<E> List<E> findByJpql(final String jpql, 
			final Map<String, Object> condition,
			final Class<E> clazz);

	Integer queryCountByJpql(final String jpql,
			final Map<String, Object> condition);
	
	<E> List<E> queryPageByJpql(final String jpql,
			final Map<String, Object> condition, 
			final int currentPage,
			final int pageSize, final Class<E> clazz);

}
