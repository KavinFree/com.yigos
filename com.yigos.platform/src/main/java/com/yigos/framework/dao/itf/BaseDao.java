package com.yigos.framework.dao.itf;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import com.yigos.framework.model.PageModel;

public interface BaseDao{

	/**
	 * 保存新记录
	 * @param entity
	 * @return
	 */
	<E> E save(final E entity);
	
	/**
	 * 更新记录
	 * @param entity
	 * @return
	 */
	<E> E update(final E entity);
	
	/**
	 * 删除记录
	 * 注意：此方式是物理删除，永久无法恢复
	 * 如果是想标志下记录为“删除状态”，请使用update方法
	 * @param entity
	 * @return
	 */
	<E> E delete(final E entity);
	
	/**
	 * 根据id值查找记录
	 * @param clazz
	 * @param pk
	 * @return
	 */
	<E> E find(final Class<E> clazz, final Serializable pk);
	
	/**
	 * 
	 * @param dc
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	void findPageByCriteria(final DetachedCriteria dc, 
			final PageModel page);
	
	<E> List<?> findByCriteria(final DetachedCriteria criteria);
}
