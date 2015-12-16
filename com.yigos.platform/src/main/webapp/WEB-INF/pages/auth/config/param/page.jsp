<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>config-param</title>
<script>
Ext.onReady(function () {
	new config.ParamPanel({
		configParam:{
			urlsPath:{
				list:        '${contextPath}/auth/config/param/list.json',
				findParam:   '${contextPath}/auth/config/param/find-param.json',
				updateParam: '${contextPath}/auth/config/param/update-param.json',
				saveParam:   '${contextPath}/auth/config/param/save-param.json',
				deleteParam: '${contextPath}/auth/config/param/delete-param.json',
				categoryList:'${contextPath}/auth/config/type/list.json',
			},
			i18n:{
				searchButton:'查询',
				addButton:'新增',
				modifyButton:'编辑',
				deleteButton:'删除',
				displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
	            emptyMsg: '没有数据',
				title:'参数明细管理',
				gridNo:'行号',
				id:'id',
				categoryId:'分类',
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
<script src="${contextPath}/custom/extjs/auth/config/param/config.ParamPanel.js"></script>
</head>
<body></body>
</html>