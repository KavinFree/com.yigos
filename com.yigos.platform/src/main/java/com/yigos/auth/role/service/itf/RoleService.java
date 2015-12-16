package com.yigos.auth.role.service.itf;

import java.util.List;

import com.yigos.auth.entity.SysRole;
import com.yigos.auth.role.view.RoleQueryView;
import com.yigos.auth.role.view.RoleView;

public interface RoleService {
	void list(RoleQueryView view);
	SysRole findRoleByPK(String id);
	SysRole saveRole(RoleView view);
	SysRole deleteRole(String id);
	SysRole updateRole(RoleView view);
	List<?> loadRoleList();
}
