package com.yigos.auth.menu.view;

import com.yigos.framework.model.PageModel;

public class MenuQueryView extends PageModel{
	private String pid;
	private String roleId;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
