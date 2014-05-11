<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<%@ include file="/custom/jsp/includes/dojo.jsp"%>
<script type="text/javascript" src="${contextPath}/custom/js/workspace.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/custom/style/workspace.css" />
<title>worspace</title>
<script type="text/javascript">
	require(["dojo/parser", 
	         "dijit/layout/BorderContainer", 
	         "dijit/layout/TabContainer", 
	         "dijit/layout/AccordionContainer", 
	         "dijit/layout/ContentPane", 
	         "dijit/layout/AccordionPane"]);
</script>

</head>
<body class="${dojoTheme}">
	<div data-dojo-type="dijit/layout/BorderContainer"
		style="width: 100%; height: 100%;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:true, region:'top', liveSplitters:false, design:'sidebar'">
		yigos.com
		</div>
		<div data-dojo-type="dijit/layout/AccordionContainer"
			data-dojo-props="splitter:true, region:'leading'" style="width: 15%;">
			<div data-dojo-type="dijit/layout/AccordionPane" title="菜单管理">
				菜单管理
			</div>
			<div data-dojo-type="dijit/layout/AccordionPane" title="系统管理" data-dojo-props="selected: true">
				<%@ include file="menu.jsp"%>
			</div>
		</div>
		<div data-dojo-type="dijit.layout.TabContainer" data-dojo-props="region:'center', tabStrip:true" id="topTabs">
			<div id="basicFormTab" data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props="title:'Basic Form Widgets', style:'padding:10px;display:none;'">
				<h2>Radio Buttons</h2>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="splitter:true, region:'trailing'">Trailing
			pane</div>
		<div data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="splitter:true, region:'bottom'">Bottom pane</div>
	</div>
</body>
</html>