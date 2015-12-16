package com.yigos.framework.dao.jpa.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.collections.MapUtils;
import com.yigos.framework.dao.jpa.itf.JpaBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaBaseDaoImpl implements JpaBaseDao{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public <E> List<E> findByJpql(final String jpql,
			final Map<String, Object> condition, final Class<E> clazz) {
		Query query = null;
		if(clazz!=null){
			query = manager.createQuery(jpql, clazz);
		}else{
			query = manager.createQuery(jpql);
		}
		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		@SuppressWarnings("unchecked")
		List<E> list = query.getResultList();
		return list;
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
	public <E> List<E> queryPageByJpql(final String jpql,
			final Map<String, Object> condition, final int currentPage,
			final int pageSize, final Class<E> clazz) {
		Query query = manager.createQuery(jpql, clazz);

		if (MapUtils.isNotEmpty(condition)) {
			for (String key : condition.keySet()) {
				Object value = condition.get(key);
				query.setParameter(key, value);
			}
		}
		query.setFirstResult(currentPage);
		query.setMaxResults(pageSize);
		
		@SuppressWarnings("unchecked")
		List<E> list = query.getResultList();
		return list;
	}

}
