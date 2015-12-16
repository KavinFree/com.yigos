<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>User</title>
<script>
Ext.onReady(function () {
	new auth.UserPanel({
		configParam:{
			urlsPath:{
				list:      '${contextPath}/auth/user/user/list.json',
				findUser:  '${contextPath}/auth/user/user/find-user.json',
				updateUser:'${contextPath}/auth/user/user/update-user.json',
				saveUser:  '${contextPath}/auth/user/user/save-user.json',
				deleteUser:'${contextPath}/auth/user/user/delete-user.json'
			},
			i18n:{
				searchButton:'查询',
				addButton:'新增',
				modifyButton:'编辑',
				deleteButton:'删除',
				displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
	            emptyMsg: '没有数据',
				title:'用户管理',
				gridNo:'行号',
				id:'id',
				code:'编号',
				name:'名称',
				email:'电邮',
				phone:'电话',
				sysDelFlag:'状态'
			}
		}
	});
});
</script>
<script src="${contextPath}/custom/extjs/auth/user/auth.UserPanel.js"></script>
</head>
<body></body>
</html>