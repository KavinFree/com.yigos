package com.yigos.auth.menu.service.impl;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.entity.SysMenu;
import com.yigos.auth.menu.service.itf.MenuService;
import com.yigos.auth.menu.view.MenuQueryView;
import com.yigos.auth.menu.view.MenuView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Override
	public void list(MenuQueryView view) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysMenu.class);
		criteria.addOrder(Order.asc("orderFlag"));
		// 加入查询条件
		criteria.add(Restrictions.eq("pid", StringUtils.trimToEmpty(view.getPid())));
		view.setStart(0);
		this.dao.findPageByCriteria(criteria, view);
	}
	
	@Override
	public void roleMenuList(MenuQueryView view) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysMenu.class);
		criteria.addOrder(Order.asc("orderFlag"));
		// 加入查询条件
		//criteria.add(Restrictions.eq("pid", StringUtils.trimToEmpty(view.getPid())));
		view.setStart(0);
		this.dao.findPageByCriteria(criteria, view);
	}

	@Override
	public SysMenu findByPK(String id) throws Exception {
		SysMenu sysMenu = this.dao.find(SysMenu.class, id);
		return sysMenu;
	}
	
	@Override
	public SysMenu findByPid(String pid) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysMenu.class);
		// 加入查询条件
		criteria.add(Restrictions.eq("pid", pid));
		List<?> list = this.dao.findByCriteria(criteria);
		if(!CollectionUtils.sizeIsEmpty(list)){
			list.get(0);
		}
		return null;
	}
	
	@Override
	public SysMenu save(MenuView view) throws Exception {
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(view, sysMenu);
		if(StringUtils.isBlank(sysMenu.getPid())){
			sysMenu.setPid("0");
		}
		return  this.dao.save(sysMenu);
	}

	@Override
	public SysMenu delete(String id) throws Exception {
		SysMenu sysMenu = this.findByPK(id);
		sysMenu.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(sysMenu);
		return sysMenu;
	}

	@Override
	public SysMenu update(MenuView view) throws Exception {
		SysMenu sysMenu = this.findByPK(view.getId());
		String[] ignore = {"id","sysConfigType"};
		BeanUtils.copyProperties(view, sysMenu, ignore);
		return this.dao.update(sysMenu);
	}

}
