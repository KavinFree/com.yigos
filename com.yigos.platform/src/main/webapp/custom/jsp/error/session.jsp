<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用戶會話已超時，請重新登錄!</title>
<script type="text/javascript">
Ext.onReady(function() {
	Ext.Msg.alert("提示",
    		"用戶會話已超時，請重新登錄!",
    		function(){
    	         parent.location.href = CommonsUtil.contextPath + "/system/manager/login.html";
            }
    );
});
</script>
</head>
<body>
</body>
</html>
