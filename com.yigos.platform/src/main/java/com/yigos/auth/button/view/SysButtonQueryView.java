package com.yigos.auth.button.view;

import com.yigos.framework.model.PageModel;

public class SysButtonQueryView extends PageModel{
	private SysButtonView query;
	private String menuId;
	
	public SysButtonView getQuery() {
		return query;
	}

	public void setQuery(SysButtonView query) {
		this.query = query;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
