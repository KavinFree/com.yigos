<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404 找不到頁面！</title>
<script type="text/javascript">
Ext.onReady(function() {
	//提示404 找不到頁面！
    Ext.Msg.alert("<spring:message code='message.prompt' text='message.prompt' />",
    "<spring:message code='exception.page_404' text='exception.page_404' />");
});
</script>
</head>
<body>
</body>
</html>
