<%@page contentType="text/html; charset=UTF-8"%>
<%
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<%@ include file="/custom/jsp/includes/meta.html"%>
<script src="${contextPath}/toolkit/dojo/dojo.js" 
    type="text/javascript" data-dojo-config="async:true, parseOnLoad:true" >
</script>
<link href="${contextPath}/toolkit/dijit/themes/tundra/tundra.css" 
    rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">

html, body {
    width: 100%;
    height: 100%;
    margin: 0;
    overflow:hidden;
}

#borderContainerTwo {
    width: 100%;
    height: 100%;
}
</style>    
<script type="text/javascript">
var basePath = '${contextPath}';
var djConfig = {
    isDebug: true, 
    debugAtAllCosts:false,
    preventBackButtonFix: false,
    baseScriptURI : '${contextPath}'+"/toolkit"
};

dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
         
</script>