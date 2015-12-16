<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>roleuser</title>
<script src="${contextPath}/custom/extjs/auth/menu/auth.TreeMenuPanel.js"></script>
<script src="${contextPath}/custom/extjs/auth/roleuser/auth.RoleUserPanel.js"></script>
<script>
Ext.onReady(function () {
	new auth.RoleUserPanel({
		configParam:{
			urlsPath:{
				roleList:   '${contextPath}/auth/role/role/list.json',
				roleMenuList:   '${contextPath}/auth/menu/menu/role-menu-list.json',
				menuButtonList: '${contextPath}/auth/button/button/menu-button-list.json',
				findMenu:  '${contextPath}/auth/roleuser/role-user/find-menu.json',
				updateMenu:'${contextPath}/auth/roleuser/role-user/update-menu.json',
				saveMenu:  '${contextPath}/auth/roleuser/role-user/save-menu.json',
				deleteMenu:'${contextPath}/auth/roleuser/role-user/delete-menu.json'
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
</head>
<body></body>
</html>