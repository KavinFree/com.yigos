package com.yigos.auth.menu.service.itf;

import com.yigos.auth.entity.SysMenu;
import com.yigos.auth.menu.view.MenuQueryView;
import com.yigos.auth.menu.view.MenuView;

public interface MenuService {
	void list(MenuQueryView view) throws Exception;
	void roleMenuList(MenuQueryView view) throws Exception;
	SysMenu findByPK(String id) throws Exception;
	SysMenu findByPid(String pid) throws Exception;
	SysMenu save(MenuView view) throws Exception;
	SysMenu delete(String id) throws Exception;
	SysMenu update(MenuView view) throws Exception;
}
