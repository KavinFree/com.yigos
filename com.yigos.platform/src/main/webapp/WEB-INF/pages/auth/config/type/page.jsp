<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>config-type</title>
<script>
Ext.onReady(function () {
	new config.TypePanel({
		configParam:{
			urlsPath:{
				list:      '${contextPath}/auth/config/type/list.json',
				findType:  '${contextPath}/auth/config/type/find-type.json',
				updateType:'${contextPath}/auth/config/type/update-type.json',
				saveType:  '${contextPath}/auth/config/type/save-type.json',
				deleteType:'${contextPath}/auth/config/type/delete-type.json'
			},
			i18n:{
				searchButton:'查询',
				addButton:'新增',
				modifyButton:'编辑',
				deleteButton:'删除',
				displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
	            emptyMsg: '没有数据',
				title:'分类管理',
				gridNo:'行号',
				id:'id',
				code:'编号',
				name:'名称',
				sysDelFlag:'状态'
			}
		}
	});
});
</script>
<script src="${contextPath}/custom/extjs/auth/config/type/config.TypePanel.js"></script>
</head>
<body></body>
</html>