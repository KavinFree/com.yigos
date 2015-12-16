package tree.test;

import java.util.ArrayList;
import org.apache.commons.collections4.map.LinkedMap;
import java.util.List;
import java.util.Map;
import tree.util.BuiltTreeAuthUtil;
import tree.view.MenuView;
import com.google.gson.Gson;

public class TreeTest {
	
	public static void main(String[] args) throws Exception{
		List<MenuView> treeViewList = initAlData();
		Map<String,String> authDataMap = initAuthData();
		List<MenuView> authTreeList = BuiltTreeAuthUtil.processAuthTreeData(treeViewList, authDataMap);

		Gson gson = new Gson();
		System.out.printf(gson.toJson(authTreeList));
	}
	
	public static List<MenuView> initAlData(){
		List<MenuView> treeViewList = new ArrayList<MenuView>();

		MenuView root = createMenuView("", "", "根目录");
		treeViewList.add(root);
		
		MenuView lv_1 = createMenuView("lv_1", root.getId(), "目录lv_1");
		//lv_1.setAuthFlag(true);
		treeViewList.add(lv_1);

		MenuView lv_2 = createMenuView("lv_2", root.getId(), "目录lv_2");
		treeViewList.add(lv_2);
		MenuView lv_2_2 = createMenuView("lv_2_2", lv_2.getId(), "目录lv_2_2");
		treeViewList.add(lv_2_2);
		MenuView lv_2_1 = createMenuView("lv_2_1", lv_2.getId(), "目录lv_2_1");
		treeViewList.add(lv_2_1);

		MenuView lv_2_1_1 = createMenuView("lv_2_1_1", lv_2_1.getId(), "目录lv_2_1_1");
		treeViewList.add(lv_2_1_1);

		

		MenuView lv_3 = createMenuView("lv_3", root.getId(), "目录lv_3");
		treeViewList.add(lv_3);

		MenuView lv_3_1 = createMenuView("lv_3_1", lv_3.getId(), "目录lv_3_1");
		treeViewList.add(lv_3_1);

		return treeViewList;
	}

	public static Map<String,String> initAuthData(){
		Map<String,String> authDataMap = new LinkedMap<String, String>();
		authDataMap.put("lv_2", "");
		authDataMap.put("lv_2_2", "");
		authDataMap.put("lv_3", "");
		authDataMap.put("lv_2_1_1", "");
		return authDataMap;
	}

	public static MenuView createMenuView(String id, String pid, String text){
		MenuView menu = new MenuView();
		menu.setId(id);
		menu.setPid(pid);
		menu.setText(text);
		return menu;
	}

}
