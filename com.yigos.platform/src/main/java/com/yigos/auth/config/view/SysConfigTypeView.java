package com.yigos.auth.config.view;

import java.util.List;

import com.yigos.auth.entity.config.SysConfigType;

public class SysConfigTypeView extends SysConfigType{

	private static final long serialVersionUID = 1L;
	private List<String> ids;
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
