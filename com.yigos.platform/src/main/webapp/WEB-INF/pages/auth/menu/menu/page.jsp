<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>Menu</title>
<script>
Ext.onReady(function () {
	new auth.MenuPanel({
		configParam:{
			urlsPath:{
				list:      '${contextPath}/auth/menu/menu/list.json',
				findMenu:  '${contextPath}/auth/menu/menu/find-menu.json',
				updateMenu:'${contextPath}/auth/menu/menu/update-menu.json',
				saveMenu:  '${contextPath}/auth/menu/menu/save-menu.json',
				deleteMenu:'${contextPath}/auth/menu/menu/delete-menu.json'
			},
			i18n:{
				searchButton:'查询',
				addButton:'新增',
				modifyButton:'编辑',
				deleteButton:'删除',
				title:'功能管理',
				id:'id',
				pid:'pid',
				iconCls:'图标',
				text:'名称',
				link:'链接',
				orderFlag:'排序',
				sysDelFlag:'状态'
			}
		}
	});
});
</script>
<script src="${contextPath}/custom/extjs/yigosui/form/tree/TreePicker.js"></script>
<script src="${contextPath}/custom/extjs/auth/menu/auth.MenuPanel.js"></script>
</head>
<body></body>
</html>