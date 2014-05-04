package com.yigos.framework.dao.itf;

import java.util.List;
import java.util.Map;

public interface BaseDao {

	<E> E delete(final Class<?> clazz, final Object pk);

	<E> E delete(final E entity);

	List<?> deleteBatch(final Class<?> clazz, final Object[] pks);

	List<?> deleteBatch(final List<?> list);

	<E> E find(final Class<?> clazz, final Object pk);

	List<?> findAll(final Class<?> clazz);

	List<?> findByJpql(final String jpql, final Map<String, Object> condition,
			final Class<?> clazz);

	List<?> findBySql(final String sql, final Map<String, Object> condition,
			final Class<?> clazz);

	Integer queryCountByJpql(final String jpql,
			final Map<String, Object> condition);

	Integer queryCountBySql(final String sql,
			final Map<String, Object> condition);

	List<?> queryPageBySql(final String sql,
			final Map<String, Object> condition, final int currentPage,
			final int pageSize, final Class<?> clazz);

	List<?> queryPageByJpql(final String jpql,
			final Map<String, Object> condition, final int currentPage,
			final int pageSize, final Class<?> clazz);

	<E> E save(final E entity);

	List<?> saveBatch(final List<?> list);

	<E> E update(final E entity);

	List<?> updateBatch(final List<?> list);
	
}
