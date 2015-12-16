<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>403 没有权限!</title>
<script type="text/javascript">
Ext.onReady(function() {
    //403 没有权限!
    Ext.Msg.alert("提示","没有权限!"); 
});
</script>
</head>
<body>


</body>
</html>
