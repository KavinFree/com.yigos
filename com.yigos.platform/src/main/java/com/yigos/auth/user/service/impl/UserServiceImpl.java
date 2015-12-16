package com.yigos.auth.user.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.entity.SysUser;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.UserQueryView;
import com.yigos.auth.user.view.SysUserView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	@Override
	public void list(UserQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		// 加入查询条件
		buildWhereParam(criteria, view);
		this.dao.findPageByCriteria(criteria, view);
	}
	
	@Override
	public SysUser findUserByPK(String id) {
		SysUser user = this.dao.find(SysUser.class, id);
		return user;
	}

	@Override
	public SysUser saveUser(SysUserView view) {
		SysUser user = new SysUser();
		BeanUtils.copyProperties(view, user);
		if(null==user.getSysDelFlag()){
			user.setSysDelFlag(DataDict.SYS_DEL_FLAG_ENABLE);
		}
		this.dao.save(user);
		return user;
	}

	@Override
	public SysUser deleteUser(String id) {
		SysUser user = findUserByPK(id);
		user.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(user);
		return user;
	}

	@Override
	public SysUser updateUser(SysUserView view) {
		SysUser user = this.findUserByPK(view.getId());
		String[] ignore = null;
		if(StringUtils.isBlank(view.getPassword())){
			ignore = new String[3];
			ignore[0] = "password";
			ignore[1] = "code";
			//ignore[2] = "version";
		}else{
			ignore = new String[2];
			ignore[0] = "code";
			//ignore[1] = "version";
		}
		BeanUtils.copyProperties(view, user, ignore);
		if(null==user.getSysDelFlag()){
			user.setSysDelFlag(DataDict.SYS_DEL_FLAG_ENABLE);
		}
		return this.dao.update(user);
	}

	@Override
	public List<?> loadUserList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		return this.dao.findByCriteria(criteria);
	}
	
	@Override
	public List<?> findUserByCode(String code){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		// 加入查询条件
		criteria.add(Restrictions.eq("code", code));
		return this.dao.findByCriteria(criteria);
	}
	
	protected void buildWhereParam(DetachedCriteria criteria, UserQueryView view) {
		if(StringUtils.isNotBlank(view.getCode())){
			criteria.add(Restrictions.like("code", view.getCode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(view.getName())){
			criteria.add(Restrictions.like("name", view.getName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(view.getPhone())){
			criteria.add(Restrictions.like("phone", view.getPhone(), MatchMode.ANYWHERE));
		}
		if(view.getSysDelFlag()!=null){
			criteria.add(Restrictions.eq("sysDelFlag", view.getSysDelFlag()));
		}
	}

}
