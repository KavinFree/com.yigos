<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yigos.framework.exception.ErrorInfo"%>
<!DOCTYPE html>
<html>
<%
	ErrorInfo info = null;
	info = (ErrorInfo) request.getAttribute("errorInfo");
%>
<head>
<%@ include file="/custom/jsp/includes/extjs.jsp"%>
<title>Error</title>
<%
    if (info.isbAlert()) {
        out.println("<script>");
        out.println("Ext.onReady(function () {");
        out.println("Ext.Msg.alert('"+info.getMsgTitle()+"', '"+ info.getAlertInfo() + "');");
        out.println("});");
        out.println("</script>");
    }
%>
</head>
</html>
