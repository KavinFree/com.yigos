<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<c:set var='libPath' value='${hostPath}:80/jsLib' />
<c:set var='yigosuiPath' value='${contextPath}/custom/extjs/yigosui' />
<%@ include file="meta.html"%>
<link rel="shortcut icon" href="${contextPath}/favicon.ico" type="image/x-icon" />
<base href="${contextPath}">
<%@ include file="extjs/extjs4.jsp"%>
<script src="${contextPath}/custom/commonsJS/CommonsUtil.js"></script>
<%@ include file="yigosui.jsp"%>
<script>
	CommonsUtil.deviceDisplay = '${deviceDisplay}';
	CommonsUtil.contextPath = '${contextPath}';
    Ext.BLANK_IMAGE_URL = '${contextPath}/custom/images/tree/s.gif';
	Ext.SSL_SECURE_URL  = '${contextPath}/custom/images/tree/s.gif';
	Ext.Loader.setConfig({
		enabled: true,
		scriptCharset: 'UTF-8',
		paths: {'yigosui': 'yigosui_path', 'Ext.ux':'${libPath}/extjs42/ext-ux'}
    });
	
    Ext.onReady(function(){
    	document.onkeypress = CommonsUtil.shieldBackSpaceAndEsc;
    	// 禁止後退鍵、禁用Esc鍵 作用於IE、Chrome
    	document.onkeydown = CommonsUtil.shieldBackSpaceAndEsc;
    	//鼠标右键事件
    	Ext.getDoc().on("contextmenu", function(e){
			//e.stopEvent();
    	});
    });
</script>