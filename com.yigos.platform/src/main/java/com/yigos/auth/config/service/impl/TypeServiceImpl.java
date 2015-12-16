package com.yigos.auth.config.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.config.service.itf.TypeService;
import com.yigos.auth.config.view.SysConfigTypeQueryView;
import com.yigos.auth.config.view.SysConfigTypeView;
import com.yigos.auth.entity.config.SysConfigType;
import com.yigos.auth.entity.config.join.SysConfigTypeJoinParam;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class TypeServiceImpl extends BaseServiceImpl implements TypeService{
	
	@Override
	public void list(SysConfigTypeQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysConfigTypeJoinParam.class);
		// 加入查询条件
		buildWhereParam(criteria, view);
		this.dao.findPageByCriteria(criteria, view);
	}
	
	@Override
	public SysConfigType findByPK(String id) {
		SysConfigType sysConfigType = this.dao.find(SysConfigType.class, id);
		return sysConfigType;
	}

	@Override
	public SysConfigType save(SysConfigTypeView view) {
		SysConfigType sysConfigType = new SysConfigType();
		BeanUtils.copyProperties(view, sysConfigType);
		sysConfigType.setSysVersion(1);
		return  this.dao.save(sysConfigType);
	}

	@Override
	public SysConfigType delete(String id) {
		SysConfigType sysConfigType = findByPK(id);
		sysConfigType.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(sysConfigType);
		return sysConfigType;
	}

	@Override
	public SysConfigType update(SysConfigTypeView view) {
		SysConfigType sysConfigType = this.findByPK(view.getId());
		String[] ignore = {"id", "sysVersion"};
		BeanUtils.copyProperties(view, sysConfigType, ignore);
		return this.dao.update(sysConfigType);
	}
	
	protected void buildWhereParam(DetachedCriteria criteria, SysConfigTypeQueryView view) {
		if(null!=view.getQuery()){
			if(StringUtils.isNotBlank(view.getQuery().getCode())){
				criteria.add(Restrictions.like("code", view.getQuery().getCode(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(view.getQuery().getName())){
				criteria.add(Restrictions.like("name", view.getQuery().getName(), MatchMode.ANYWHERE));
			}
		}
	}
}
