package com.yigos.framework.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.yigos.framework.dao.itf.BaseDao;
import com.yigos.framework.model.PageModel;

@Repository
public class BaseDaoImpl implements BaseDao {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private HibernateTemplate template;

	@Override
	public <E> E save(final E entity) {
		this.template.save(entity);
		return (E) entity;
	}

	@Override
	public <E> E update(final E entity) {
		this.template.update(entity);
		return (E) entity;
	}

	@Override
	public <E> E delete(E entity) {
		this.template.delete(entity);
		return (E) entity;
	}

	@Override
	public <E> E find(final Class<E> entityClazz, final Serializable pk) {
		return (E) this.template.get(entityClazz, pk);
	}

	@Override
	public void findPageByCriteria(final DetachedCriteria dc, 
			final PageModel page) {
		//采用getCurrentSession()创建的session会绑定到当前线程中，而采用openSession()创建的session则不会
		//采用getCurrentSession()创建的session在commit或rollback时会自动关闭，而采用openSession()创建的session必须手动关闭。
		Session session = this.template.getSessionFactory().getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		Long totalCount = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
		//1、
		page.setCounts(totalCount.intValue());
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		Collection<?> items = criteria.setFirstResult(page.getStart()).setMaxResults(page.getLimit()).list();
		//2、
		this.resetStart(page);
		//3、
		page.setItems(items);
		//return items;
	}
	
	@Override
	public <E> List<?> findByCriteria(DetachedCriteria criteria){
		return this.template.findByCriteria(criteria);
	}
	
	/**
     * 重新设置分页的页码
     * 注意：
     * 1、先设置view.setCount(total);
     * 2、接着super.resetStart(view); 
     * 3、最后设置view.setItems(list);
     * @param model
     * @since 2013年5月6日  
    */ 
	private void resetStart(PageModel model) {
		int limit = model.getLimit();
		int counts = model.getCounts();
		if ((model.getPage() - 1) * limit + 1 > counts) {
			int page = counts / limit;
			model.setPage(page == 0 ? 1 : page);
		}
	}
}
