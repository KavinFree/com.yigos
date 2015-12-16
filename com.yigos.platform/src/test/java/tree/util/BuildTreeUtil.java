package tree.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import tree.view.MenuView;

public class BuildTreeUtil {
	
	protected static List<MenuView> processTree(List<MenuView> treeViewList) throws Exception {
		if(CollectionUtils.sizeIsEmpty(treeViewList)){
			return null;
		}
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

		recursionBuildTree(treeData, others);
		return treeData;
	}

	/**
	 * 递归循环树数据
	 * @param parents
	 * @param others
	 * @throws Exception
	 */
	private static void recursionBuildTree(List<MenuView> parents, List<MenuView> others) 
		throws Exception {
		List<MenuView> record = new ArrayList<MenuView>();
		for (Iterator<MenuView> it = parents.iterator(); it.hasNext();){
			MenuView view = it.next();
			for (Iterator<MenuView> otherIt = others.iterator(); 
					otherIt.hasNext();) {
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
			recursionBuildTree(record, others);
		}
	}
	
	protected static Map<String, MenuView> allTreeMap(List<MenuView> treeViewList){
        Map<String,MenuView> allMap = new LinkedMap<String,MenuView>();
        for (MenuView view : treeViewList){
            allMap.put(view.getId(), view);
        }
        return allMap;
    }

}
