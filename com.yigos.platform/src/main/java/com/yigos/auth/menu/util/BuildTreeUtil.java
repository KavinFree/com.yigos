package com.yigos.auth.menu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.yigos.auth.menu.view.MenuView;

public class BuildTreeUtil {
	
	public static List<MenuView> treeResult(List<MenuView> treeViewList)
			throws Exception {
		List<MenuView> treeData = new ArrayList<MenuView>();
		List<MenuView> others = new ArrayList<MenuView>();
		for (MenuView mmv : treeViewList) {
			if (StringUtils.isBlank(mmv.getPid()) 
				|| StringUtils.equals(mmv.getPid(), "")
				|| StringUtils.equals(mmv.getPid(), "0")) {
				MenuView result = mmv;
				result.setChildren(new ArrayList<MenuView>());
				treeData.add(result);
			}else{
				others.add(mmv);
			}
		}
		buildTree(treeData, others);
		return treeData;
	}

	protected static void buildTree(List<MenuView> parents,
		List<MenuView> others) throws Exception {
		List<MenuView> record = new ArrayList<MenuView>();
		for (Iterator<MenuView> it = parents.iterator(); it.hasNext();) {
			MenuView view = it.next();
			for (Iterator<MenuView> otherIt = others.iterator(); otherIt.hasNext();) {
				MenuView viewChildren = otherIt.next();
				if (StringUtils.isNotBlank(viewChildren.getId())
						&& StringUtils.isNotBlank(viewChildren.getPid())
						&& StringUtils.equals(view.getId(), viewChildren.getPid())) {
					if (null == view.getChildren()) {
						view.setChildren(new ArrayList<MenuView>());
					}
					view.getChildren().add(viewChildren);
					record.add(viewChildren);
					otherIt.remove();
				}
			}
		}
		if (others.size() == 0) {
			return;
		} else {
			buildTree(record, others);
		}
	}
}
