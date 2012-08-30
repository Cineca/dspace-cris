<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
		
	String dept = (String) request.getAttribute("dept");	
	List metrics = (List) request.getAttribute("metrics");	

%>
<html>
<head>
		
	<meta name="author" content="The University of Hong Kong Libraries" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<meta name="language" content="english"/>
	<meta name="language" content="chinese"/>
	<meta name="description" content="HKU ResearcherPage on showing contact, bibliometric, and publication list details. Expertise, specialist, collaboration, expert, contract research, media comment."/>

	<meta name="keywords" content="<%= dept %>"/>

	<meta name="robots" content="index follow"/>
	
	<title>HKU ResearcherPage - <%= dept %></title>	

	<link href="<%= request.getContextPath() %>/images/favicon.ico" type="image/x-icon" rel="shortcut icon" />
	<link href="<%= request.getContextPath() %>/css/hub.css" type="text/css" rel="stylesheet" />
	<link href="<%= request.getContextPath() %>/styles.css.jsp" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/demo_table.css" type="text/css" media="all" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/TableTools.css" type="text/css" media="all" />
	                
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>    
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" 
		src="<%= request.getContextPath() %>/js/TableTools.min.js"></script>
			<script type="text/javascript" charset="utf-8" 
		src="<%= request.getContextPath() %>/js/ZeroClipboard.min.js"></script>
	
</head>
<body>
	
		
				
	<table id="objectList" style="width: 99%">
	<thead>
		<tr>
			<th>Type</th>
			<th>Authority</th>
			<th>Fullname</th>
			<th>Max Strength</th>
			<th>Average Strength</th>
			<th>Numbers Connections</th>
			<th>Quadratic Variance</th>			
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${metrics}" var="oo"> 
		<tr>
			<td>${oo.type}</td>
			<td>${oo.authority}</td>
			<td>${oo.fullName}</td>
			<td>${oo.maxStrength}</td>
			<td>${oo.averageStrength}</td>
			<td>${oo.numbersConnections}</td>
			<td>${oo.quadraticVariance}</td>			
		</tr>
	</c:forEach>
	</tbody>
</table>


	
	<script><!--
	var j = jQuery.noConflict();

	var myExportOption = [
				 			{
				 				"sExtends": "copy",
				 				"mColumns": 'visible'
				 			},
				 			{
				 				"sExtends": "xls",
				 				"mColumns": 'visible'
				 			},
				 			{
				 				"sExtends": "csv",
				 				"mColumns": 'visible'
				 			},
				 			{
				 				"sExtends": "print",
				 				"mColumns": 'visible'
				 			},
				 			{
				 				"sExtends": "pdf",
				 				"sPdfOrientation": 'landscape', 
				 				"mColumns": 'visible'
				 			}
				 		];

	j(document).ready(function() {
		
		j('#objectList').dataTable({
			"sDom": 'R<"H"<T><"clear">lfr>t<"F"<"clear">ip>',
			"oTableTools": {
				"sSwfPath": "<%= request.getContextPath() %>/swf/copy_cvs_xls_pdf.swf",
				"aButtons": myExportOption
			},	
			"sPaginationType": "full_numbers",
			"aLengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
			"oLanguage": {"sSearch" : "Filter"},
			"iDisplayLength": -1
		});
		
	});
	--></script>

</body>

</html>