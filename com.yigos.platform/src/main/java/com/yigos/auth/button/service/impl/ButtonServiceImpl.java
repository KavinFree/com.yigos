package com.yigos.auth.button.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.button.service.itf.ButtonService;
import com.yigos.auth.button.view.SysButtonQueryView;
import com.yigos.auth.button.view.SysButtonView;
import com.yigos.auth.entity.SysButton;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class ButtonServiceImpl extends BaseServiceImpl implements ButtonService{

	@Override
	public void list(SysButtonQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysButton.class);
		// 加入查询条件
		buildWhereParam(criteria, view);
		this.dao.findPageByCriteria(criteria, view);
	}
	
	@Override
	public SysButton findByPK(String id) {
		SysButton sysButton = this.dao.find(SysButton.class, id);
		return sysButton;
	}

	@Override
	public SysButton save(SysButtonView view) {
		SysButton sysButton = new SysButton();
		BeanUtils.copyProperties(view, sysButton);
		sysButton.setSysVersion(1);
		return  this.dao.save(sysButton);
	}

	@Override
	public SysButton delete(String id) {
		SysButton sysButton = findByPK(id);
		sysButton.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(sysButton);
		return sysButton;
	}

	@Override
	public SysButton update(SysButtonView view) {
		SysButton sysButton = this.findByPK(view.getId());
		String[] ignore = {"id", "sysVersion"};
		BeanUtils.copyProperties(view, sysButton, ignore);
		return this.dao.update(sysButton);
	}
	
	protected void buildWhereParam(DetachedCriteria criteria, SysButtonQueryView view) {
		if(null!=view.getQuery()){
			if(StringUtils.isNotBlank(view.getQuery().getName())){
				criteria.add(Restrictions.like("name", view.getQuery().getName(), MatchMode.ANYWHERE));
			}
		}
	}
	
}
