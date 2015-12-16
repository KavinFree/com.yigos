package com.yigos.auth.menu.view;

import java.util.List;

import com.yigos.auth.entity.SysMenu;

public class MenuView extends SysMenu {

	private static final long serialVersionUID = 1L;
	private boolean root = false;
	private transient List<MenuView> children;
	private List<String> ids;
	
	public boolean isRoot() {
		return root;
	}
	public void setRoot(boolean root) {
		this.root = root;
	}
	public List<MenuView> getChildren() {
		return children;
	}
	public void setChildren(List<MenuView> children) {
		this.children = children;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
