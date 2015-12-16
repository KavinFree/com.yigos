package com.yigos.auth.roleuser.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yigos.auth.entity.SysRole;
import com.yigos.auth.entity.SysRoleUser;
import com.yigos.auth.entity.SysUser;
import com.yigos.auth.entity.join.SysRoleUserJoin;
import com.yigos.auth.roleuser.service.itf.RoleUserService;
import com.yigos.auth.roleuser.view.RoleUserQueryView;
import com.yigos.auth.roleuser.view.RoleUserView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class RoleUserServiceImpl extends BaseServiceImpl implements
		RoleUserService {

	@Override
	public void list(RoleUserQueryView view) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRoleUserJoin.class, "para");
		// 加入查询条件
		//
		this.dao.findPageByCriteria(criteria, view);
	}

	@Override
	public SysRoleUser findRoleUserByPK(String id) {
		SysRoleUser roleUser = this.dao.find(SysRoleUser.class, id);
		return roleUser;
	}

	@Override
	public SysRoleUser saveRoleUser(RoleUserView view) {
		SysRoleUser roleUser = new SysRoleUser();
		BeanUtils.copyProperties(view, roleUser);
		return  this.dao.save(roleUser);
	}

	@Override
	public SysRoleUser deleteRoleUser(RoleUserView view) {
		SysRoleUser roleUser = findRoleUserByPK(view.getId());
		roleUser.setSysDelFlag(DataDict.SYS_DEL_FLAG_UNABLE);
		this.dao.update(roleUser);
		return roleUser;
	}

	@Override
	public SysRoleUser updateRoleUser(RoleUserView view) {
		SysRoleUser roleUser = this.findRoleUserByPK(view.getId());
		String[] ignore = {"id"};
		BeanUtils.copyProperties(view, roleUser, ignore);
		return this.dao.update(roleUser);
	}
	
	protected List<?> copylist(List<SysRoleUserJoin> list){
		List<RoleUserView> cl = new ArrayList<RoleUserView>();
		for(SysRoleUserJoin s : list){
			RoleUserView view = new RoleUserView();
			BeanUtils.copyProperties(s, view);
			SysRole sysRoleCopy = s.getSysRole();
			if(null!=sysRoleCopy){
				view.setIdRole(sysRoleCopy.getId());
				view.setRoleCode(sysRoleCopy.getCode());
				view.setRoleName(sysRoleCopy.getName());
				view.setRoleRemark(sysRoleCopy.getRemark());
			}
			SysUser sysUserCopy = s.getSysUser();
			if(null!=sysUserCopy){
				view.setIdUser(sysUserCopy.getId());
				view.setUserCode(sysUserCopy.getCode());
				view.setUserName(sysUserCopy.getName());
				view.setUserRemark(sysUserCopy.getRemark());
			}
			cl.add(view);
		}
		return cl;
	}
	
	protected String buildWhereParam(Map<String, Object> condition, RoleUserQueryView view) {
		String where = "";
		if(StringUtils.isNotBlank(view.getUserCode())){
			where = where + " and o.sysUser.code like :userCode ";
			condition.put("userCode", "%"+view.getUserCode()+"%");
		}
		if(StringUtils.isNotBlank(view.getUserName())){
			where = where + " and o.sysUser.name like :userName ";
			condition.put("userName", "%"+view.getUserName()+"%");
		}
		if(StringUtils.isNotBlank(view.getUserPhone())){
			where = where + " and o.sysUser.phone like :userPhone ";
			condition.put("userPhone", "%"+view.getUserPhone()+"%");
		}
		
		if(StringUtils.isNotBlank(view.getRoleCode())){
			where = where + " and o.sysRole.code like :roleCode ";
			condition.put("roleCode", "%"+view.getRoleCode()+"%");
		}
		if(StringUtils.isNotBlank(view.getRoleName())){
			where = where + " and o.sysRole.name like :roleName ";
			condition.put("roleName", "%"+view.getRoleName()+"%");
		}
		
		if(view.getSysDelFlag()!=null){
			where = where + " and o.sysDelFlag = :sysDelFlag ";
			condition.put("sysDelFlag", view.getSysDelFlag());
		}
		return where;
	}

}
