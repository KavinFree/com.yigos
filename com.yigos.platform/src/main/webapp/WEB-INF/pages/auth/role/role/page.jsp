<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>role</title>
<script>
Ext.onReady(function () {
	new auth.RolePanel({
		configParam:{
			urlsPath:{
				list:      '${contextPath}/auth/role/role/list.json',
				findRole:  '${contextPath}/auth/role/role/find-role.json',
				updateRole:'${contextPath}/auth/role/role/update-role.json',
				saveRole:  '${contextPath}/auth/role/role/save-role.json',
				deleteRole:'${contextPath}/auth/role/role/delete-role.json'
			},
			i18n:{
				searchButton:'查询',
				addButton:'新增',
				modifyButton:'编辑',
				deleteButton:'删除',
				title:'角色管理',
				gridNo:'行号',
				id:'id',
				pid:'pid',
				code:'编号',
				name:'名称',
				orderFlag:'排序',
				sysDelFlag:'状态'
			}
		}
	});
});
</script>
<script src="${contextPath}/custom/extjs/yigosui/form/tree/TreePicker.js"></script>
<script src="${contextPath}/custom/extjs/auth/role/auth.RolePanel.js"></script>
</head>
<body></body>
</html>