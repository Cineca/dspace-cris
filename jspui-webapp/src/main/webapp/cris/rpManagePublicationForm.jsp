<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.dspace.eperson.EPerson"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
	<link href="<%= request.getContextPath() %>/css/jquery-1.8.8-ui.css" type="text/css" rel="stylesheet" />
    <link href="<%= request.getContextPath() %>/css/rpManagePublications.css" rel="stylesheet" type="text/css" >
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-ui-1.8.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.tablednd_0_5.js"></script>    
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/rpManagePublications.js"></script>
</style>

<script><!--
	
	var j = jQuery.noConflict();
	
	j(document).ready(function() {
		initializeRIAForm();
		}
	);
	--></script>
</head>
<body>
<h2><b>Manage Publications</b></h2>
<div class="intro">Publications on your page can have one of three categories: 
<ul>
<li>Active, will display normally,</li> 
<li>Hidden, will not display on your ResearcherPage, but will still be searchable in The Hub,</li> 
<li>and Selected, will appear in the "Selected Publications" box.</li>
</ul>
This page opens showing the Active category, <span class="activestate">highlighted in gold</span>.<br />
You could click on the other categories to show publications in those categories.<br/>  
For any particular publication, you can change the category by,
<ul> 
<li>clicking on the category icon, A, H, or S, or,</li> 
<li>dragging the entire publication entry to your chosen box on the right and dropping it.</li>
</ul>
If so, the chosen box will briefly turn yellow.  
You may use the Filter, to search through your publications.
<br />
</div>
<form:form id="rpMPForm" commandName="dto" method="post">

<%
	Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
	boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
	if(!isAdmin) { %>
		<c:set var="hidden" value="style= 'display:none;'" />	
<% } %>
<c:set var="activepublications" value="${dto.active}" />
<c:set var="selectedpublications" value="${dto.selected}" />
<c:set var="hidepublications" value="${dto.hided}" />
<c:set var="unlinkedpublications" value="${dto.unlinked}" />

<div class="droppable">
<div id="droppableunlinked" class="ui-widget-header" ${hidden}>
<h2 class="controlunlinked"><fmt:message key="jsp.managepublication.container.unlinked"/></h2>
<h3 class="controlh3 controlunlinkedh3"></h3>
</div>
<div id="droppableselected" class="ui-widget-header">
<h2 class="controlselected"><fmt:message key="jsp.managepublication.container.selected"/></h2>
<h3 class="controlh3 controlselectedh3"></h3>
</div>
<div id="droppablehided" class="ui-widget-header">
<h2 class="controlhided"><fmt:message key="jsp.managepublication.container.hided"/></h2>
<h3 class="controlh3 controlhidedh3"></h3>
</div>
<div id="droppableactive" class="collapsed-container ui-widget-header">
<h2 class="controlactive"><fmt:message key="jsp.managepublication.container.active"/></h2>
<h3 class="controlh3 controlactiveh3"></h3>
</div>
</div>
<div id="centralPanel">
<div id="processing">
Data are loading... please wait 
</div>
<table id="publicationTable" class="invisible">
<thead>
	<tr class="nodrag nodrop">
		<th class="filtered">&nbsp;</th>
		<th class="filtered">&nbsp;</th>
		<th><fmt:message key="jsp.managepublication.table.header.title"/></th>
		<th><fmt:message key="jsp.managepublication.table.header.author"/></th>
		<th><fmt:message key="jsp.managepublication.table.header.date"/></th>
		<th><fmt:message key="jsp.managepublication.table.header.type"/></th>
		<th><fmt:message key="jsp.managepublication.table.header.category"/></th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${activepublications}" var="publication">
		<tr class="draggable active nodrop" id="publication_${publication}">
			<td class="filtered"></td>
			<td class="filtered">&nbsp;</td>
			<td>${grid[publication][0]}</td>
			<td>${grid[publication][1]}</td>
			<td class="date">${grid[publication][2]}</td>
			<td>${grid[publication][3]}</td>
			<td class="button">				
				<a  class="dropactive filtered" id="drop_active_${publication}" title="Drop to active">A</a>
				<a  class="drophided" id="drop_hided_${publication}" title="Drop to hidden">H</a>
				<a  class="dropselected" id="drop_selected_${publication}" title="Drop to selected">S</a>
				<a ${hidden} class="dropunlinked" id="drop_unlinked_${publication}" title="Drop to unlinked">U</a>
			</td>
		</tr>
	</c:forEach>
	<c:forEach items="${selectedpublications}" var="publication" varStatus="status">
		<tr class="draggable selected filtered" id="publication_${publication}">
			<td class="filtered">${status.count}</td>
			<td>&nbsp;</td>
			<td>${grid[publication][0]}</td>
			<td>${grid[publication][1]}</td>
			<td class="date">${grid[publication][2]}</td>
			<td>${grid[publication][3]}</td>
			<td class="button">
				<a  class="dropactive" id="drop_active_${publication}" title="Drop to active">A</a>
				<a  class="drophided" id="drop_hided_${publication}" title="Drop to hidden">H</a>
				<a  class="dropselected" id="drop_selected_${publication}" title="Drop to selected">S</a>
				<a ${hidden} class="dropunlinked" id="drop_unlinked_${publication}" title="Drop to unlinked">U</a>				
			</td>

		</tr>
	</c:forEach>
	<c:forEach items="${hidepublications}" var="publication">
		<tr class="draggable hided filtered nodrop" id="publication_${publication}">
			<td class="filtered"></td>
			<td class="filtered">&nbsp;</td>
			<td>${grid[publication][0]}</td>
			<td>${grid[publication][1]}</td>
			<td class="date">${grid[publication][2]}</td>
			<td>${grid[publication][3]}</td>
			<td class="button">
				<a  class="dropactive" id="drop_active_${publication}" title="Drop to active">A</a>
				<a  class="drophided" id="drop_hided_${publication}" title="Drop to hidden">H</a>
				<a  class="dropselected" id="drop_selected_${publication}" title="Drop to selected">S</a>
				<a ${hidden} class="dropunlinked" id="drop_unlinked_${publication}" title="Drop to unlinked">U</a>			</td>
			
		</tr>
	</c:forEach>
	
	<c:forEach items="${unlinkedpublications}" var="publication">
		<tr class="draggable unlinked filtered nodrop" id="publication_${publication}" ${hidden}>
			<td class="filtered"></td>
			<td class="filtered">&nbsp;</td>
			<td>${grid[publication][0]}</td>
			<td>${grid[publication][1]}</td>
			<td class="date">${grid[publication][2]}</td>
			<td>${grid[publication][3]}</td>
			<td class="button">				
				<a  class="dropactive" id="drop_active_${publication}" title="Drop to active">A</a>
				<a  class="drophided" id="drop_hided_${publication}" title="Drop to hidden">H</a>
				<a  class="dropselected" id="drop_selected_${publication}" title="Drop to selected">S</a>
				<a ${hidden} class="dropunlinked" id="drop_unlinked_${publication}" title="Drop to unlinked">U</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>

<div id="message" class="invisible">Click submit to save changes</div>
 	
	<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" id="bsubmit"
	value="Submit" /> <input class="ui-button ui-widget ui-state-default ui-corner-all" type="submit" id="cancel" value="Cancel" />
</form:form>
</body>
</html>