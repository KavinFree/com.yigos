package tree.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import tree.view.MenuView;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class BuiltTreeAuthUtil extends BuildTreeUtil{
	
	public static List<MenuView> processAuthTreeData(List<MenuView> treeViewList, 
    	Map<String,String> authDataMap) throws Exception{
    	Map<String,MenuView> allMap = allTreeMap(treeViewList);
    	Map<String,String> authTreeMap = new LinkedMap<String, String>();
        findParent(allMap, authDataMap, authTreeMap);
        List<MenuView> authTreeList = new ArrayList<MenuView>();
        for(Map.Entry<String, MenuView> entry:allMap.entrySet()){
        	if(authTreeMap.containsKey(allMap.get(entry.getKey()).getId())){
        		authTreeList.add(allMap.get(entry.getKey()));
        	}
        }
        List<MenuView> treeDataList = BuildTreeUtil.processTree(authTreeList);
        return treeDataList;
    }
    
    private static void findParent(Map<String,MenuView> allMap, 
    	Map<String,String> authDataMap, Map<String,String> authTreeMap){
    	Map<String,String> authListTemp = new LinkedMap<String,String>();
        for (Map.Entry<String, String> entry : authDataMap.entrySet()){
        	String id = entry.getKey();
            if (allMap.containsKey(id)){
                MenuView temp = allMap.get(id);
                authTreeMap.put(id, "true");
                if (allMap.containsKey(temp.getPid())){
                    MenuView parent = allMap.get(temp.getPid());
                    if(StringUtils.isBlank(parent.getPid()) 
                    	|| StringUtils.equals(parent.getPid(), "0")){

                    }else{
                        authListTemp.put(parent.getId(), "");
                    }
                    //if(!authTreeMap.containsKey(parent.getId())){
                        authTreeMap.put(parent.getId(), "false");
                    //}
                }
            }
        }
        authDataMap = authListTemp;
        if (CollectionUtils.sizeIsEmpty(authDataMap)) {
            return;
        } else {
            findParent(allMap, authDataMap, authTreeMap);
        }
    }

}
