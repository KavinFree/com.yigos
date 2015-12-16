package tree.view;

import java.util.List;
import tree.view.base.SysMenu;

public class MenuView extends SysMenu {

	private List<MenuView> children;
	private boolean authFlag = false;

	public List<MenuView> getChildren() {
		return children;
	}
	public void setChildren(List<MenuView> children) {
		this.children = children;
	}

	public boolean isAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(boolean authFlag) {
		this.authFlag = authFlag;
	}
}
