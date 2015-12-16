package com.yigos.auth.config.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.config.service.itf.ParamService;
import com.yigos.auth.config.view.SysConfigParamQueryView;
import com.yigos.auth.config.view.SysConfigParamView;
import com.yigos.auth.entity.config.SysConfigParam;
import com.yigos.auth.entity.config.join.SysConfigParamJoinType;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class ParamServiceImpl extends BaseServiceImpl implements ParamService{
	
	@Override
	public void list(SysConfigParamQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysConfigParamJoinType.class, "para");
		//criteria.setFetchMode("sysConfigType", FetchMode.JOIN);
		// 加入查询条件
		buildWhereParam(criteria, view);
		this.dao.findPageByCriteria(criteria, view);
	}

	@Override
	public SysConfigParam findByPK(String id) {
		SysConfigParam sysConfigParam = this.dao.find(SysConfigParam.class, id);
		return sysConfigParam;
	}

	@Override
	public SysConfigParam save(SysConfigParamView view) {
		SysConfigParam sysConfigParam = new SysConfigParam();
		BeanUtils.copyProperties(view, sysConfigParam);
		return  this.dao.save(sysConfigParam);
	}

	@Override
	public SysConfigParam delete(String id) {
		SysConfigParam sysConfigParam = findByPK(id);
		sysConfigParam.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(sysConfigParam);
		return sysConfigParam;
	}

	@Override
	public SysConfigParam update(SysConfigParamView view) {
		SysConfigParam sysConfigParam = this.findByPK(view.getId());
		String[] ignore = {"id","sysConfigType"};
		BeanUtils.copyProperties(view, sysConfigParam, ignore);
		return this.dao.update(sysConfigParam);
	}
	
	protected void buildWhereParam(DetachedCriteria criteria, SysConfigParamQueryView view) {
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
