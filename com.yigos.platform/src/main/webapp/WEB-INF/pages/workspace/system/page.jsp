<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>yigos yigos.com</title>
<script src="${contextPath}/custom/extjs/auth/menu/auth.TreeMenuPanel.js"></script>
<script src="${contextPath}/custom/extjs/workspace/system/auth.WorkSpacePanel.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	new auth.WorkSpacePanel({
		configParam:{
			urlsPath:{
				list:          '${contextPath}/auth/menu/menu/list.json'
			}
		}
	})
});
</script>
</head>
<body></body>
</html>