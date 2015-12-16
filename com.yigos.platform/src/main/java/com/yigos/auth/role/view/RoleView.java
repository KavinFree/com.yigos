package com.yigos.auth.role.view;

import java.util.List;

import com.yigos.auth.entity.SysRole;

public class RoleView extends SysRole {

	private static final long serialVersionUID = 1L;

	private List<String> ids;
	

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
