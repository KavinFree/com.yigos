package com.yigos.auth.role.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.entity.SysRole;
import com.yigos.auth.role.service.itf.RoleService;
import com.yigos.auth.role.view.RoleQueryView;
import com.yigos.auth.role.view.RoleView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Override
	public void list(RoleQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRole.class);
		criteria.addOrder(Order.asc("orderFlag"));
		// 加入查询条件
		criteria.add(Restrictions.eq("pid", StringUtils.trimToEmpty(view.getPid())));
		view.setStart(0);
		this.dao.findPageByCriteria(criteria, view);
	}

	@Override
	public SysRole findRoleByPK(String id) {
		SysRole role = this.dao.find(SysRole.class, id);
		return role;
	}

	@Override
	public SysRole saveRole(RoleView view) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(view, role);
		if(StringUtils.isBlank(role.getPid())){
			role.setPid("0");
		}
		return  this.dao.save(role);
	}

	@Override
	public SysRole deleteRole(String id) {
		SysRole role = findRoleByPK(id);
		role.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(role);
		return role;
	}

	@Override
	public SysRole updateRole(RoleView view) {
		SysRole role = this.findRoleByPK(view.getId());
		String[] ignore = {"id","version"};
		BeanUtils.copyProperties(view, role, ignore);
		return this.dao.update(role);
	}

	@Override
	public List<?> loadRoleList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRole.class);
		return this.dao.findByCriteria(criteria);
	}
	
}
