package com.yigos.auth.roleuser.service.itf;

import com.yigos.auth.entity.SysRoleUser;
import com.yigos.auth.roleuser.view.RoleUserQueryView;
import com.yigos.auth.roleuser.view.RoleUserView;

public interface RoleUserService {
	void list(RoleUserQueryView view);
	SysRoleUser findRoleUserByPK(String id);
	SysRoleUser saveRoleUser(RoleUserView view);
	SysRoleUser deleteRoleUser(RoleUserView view);
	SysRoleUser updateRoleUser(RoleUserView view);
}
