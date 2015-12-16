package com.yigos.auth.user.service.itf;

import java.util.List;

import com.yigos.auth.entity.SysUser;
import com.yigos.auth.user.view.UserQueryView;
import com.yigos.auth.user.view.SysUserView;

public interface UserService {
	void list(UserQueryView view);
	SysUser findUserByPK(String id);
	SysUser saveUser(SysUserView view);
	SysUser deleteUser(String id);
	SysUser updateUser(SysUserView view);
	List<?> loadUserList();
	List<?> findUserByCode(String code);
}
