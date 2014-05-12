<%@page contentType="text/html; charset=UTF-8"%>
<%
	pageContext.setAttribute("contextPath", request.getContextPath());
	//claro()、nihilo()、soria()、tundra(灰色)
	pageContext.setAttribute("dojoTheme", "soria");//
%>
<%@ include file="/custom/jsp/includes/meta.html"%>
<link rel="stylesheet" href="${contextPath}/toolkit/dijit/themes/claro/document.css"/>
<link href="${contextPath}/toolkit/dijit/themes/${dojoTheme}/${dojoTheme}.css" rel="stylesheet" />

<script type="text/javascript">
var basePath = '${contextPath}';
var dojoConfig = {
    isDebug: true,
    parseOnLoad: true,
    debugAtAllCosts:false,
    preventBackButtonFix: false,
    baseScriptURI : '${contextPath}'+"/toolkit"
};
</script>
<script src="${contextPath}/toolkit/dojo/dojo.js" type="text/javascript"></script>
<style type="text/css">
body{
margin: 0;
padding: 0;
width: 100%; height: 100%;
overflow: hidden;
}
</style>