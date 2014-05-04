package com.yigos.framework.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.yigos.framework.dao.itf.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public <E> E delete(final Class<?> clazz, final Object pk) {
		Object entity = manager.find(clazz, pk);
		if (entity != null) {
			manager.remove(entity);
		}
		return (E) entity;
	}

	@Override
	public <E> E delete(final E entity) {
		manager.remove(entity);
		return (E) entity;
	}

	@Override
	public List<?> deleteBatch(final Class<?> clazz, final Object[] pks) {
		List<Object> list = new ArrayList<Object>();
		if (pks != null && pks.length > 0) {
			for (Object id : pks) {
				Object entity = manager.find(clazz, id);
				if (entity != null) {
					manager.remove(entity);
					list.add(entity);
				}
			}
		}
		return list;
	}

	@Override
	public List<?> deleteBatch(final List<?> list) {
		if (list != null && list.size() > 0) {
			for (int i = list.size(); i > 0; i--) {
				Object entity = list.get(i - 1);
				if (entity != null) {
					manager.remove(entity);
				}
			}
		}
		return list;
	}

	@Override
	public <E> E find(final Class<?> entityClazz, final Object pk) {
		return (E) this.manager.find(entityClazz, pk);
	}

	@Override
	public List<?> findAll(final Class<?> clazz) {
		String className = clazz.getSimpleName();
		StringBuffer jpql = new StringBuffer("select o from ");
		jpql.append(className).append(" o ");
		return manager.createQuery(jpql.toString()).getResultList();
	}

	@Override
	public List<?> findByJpql(final String jpql,
			final Map<String, Object> condition, final Class<?> clazz) {
		Query query = manager.createQuery(jpql, clazz);
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		return query.getResultList();
	}

	@Override
	public List<?> findBySql(final String sql,
			final Map<String, Object> condition, final Class<?> clazz) {
		Query query = manager.createNativeQuery(sql, clazz);
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		return query.getResultList();
	}

	@Override
	public Integer queryCountByJpql(final String jpql,
			final Map<String, Object> condition) {
		Query query = manager.createQuery(jpql);
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		return Integer.valueOf(query.getSingleResult().toString());
	}

	@Override
	public Integer queryCountBySql(final String sql,
			final Map<String, Object> condition) {
		Query query = manager.createNativeQuery(sql);
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		return Integer.valueOf(query.getSingleResult().toString());
	}

	@Override
	public List<?> queryPageByJpql(final String jpql,
			final Map<String, Object> condition, final int currentPage,
			final int pageSize, final Class<?> clazz) {
		Query query = manager.createQuery(jpql, clazz);

		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		query.setFirstResult((currentPage - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public List<?> queryPageBySql(final String sql,
			final Map<String, Object> condition, final int currentPage,
			final int pageSize, final Class<?> clazz) {
		Query query = manager.createNativeQuery(sql, clazz);
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		query.setFirstResult((currentPage - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public <E> E save(final E entity) {
		this.manager.persist(entity);
		return (E) entity;
	}

	@Override
	public List<?> saveBatch(final List<?> list) {
		if (list != null && list.size() > 0) {
			for (int i = list.size(); i > 0; i--) {
				Object entity = list.get(i - 1);
				manager.persist(entity);
			}
		}
		return list;
	}

	@Override
	public <E> E update(final E entity) {
		return (E) manager.merge(entity);
	}

	@Override
	public List<?> updateBatch(final List<?> list) {
		if (list != null && list.size() > 0) {
			for (int i = list.size(); i > 0; i--) {
				manager.merge(list.get(i - 1));
			}
		}
		return list;
	}
}
