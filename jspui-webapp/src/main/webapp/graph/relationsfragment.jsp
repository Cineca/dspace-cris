<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%
	

	Map<String,String> relations = (Map<String,String>) request.getAttribute("relations");
	String type = (String)request.getAttribute("type");
	
%>

<% if(type.equals("coauthors"))  { %>

<table id="edgetableinformation" class="table">
	<thead>
		<tr>
			<th><fmt:message key="jsp.network.relation.coauthorstitle"/></th>
			<th><fmt:message key="jsp.network.relation.coauthorshandle"/></th>			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${relations}" var="relation">
			<tr>
				<td><c:if test="${!empty relation.value}"><a target="_blank" href="../handle/${relation.value}"></c:if>${relation.key}<c:if test="${!empty relation.value}"></a></c:if></td>
				<td>${relation.value}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<% } else if(type.equals("cowinners"))  { %>

<table id="edgetableinformation" class="table">
	<thead>
		<tr>
			<th><fmt:message key="jsp.network.relation.awardsdate"/></th>
			<th><fmt:message key="jsp.network.relation.awards"/></th>
			<th><fmt:message key="jsp.network.relation.awardsachievements"/></th>
			<th><fmt:message key="jsp.network.relation.awardswith"/><th>
		</tr>
	</thead>
	<tbody>
	<% for(String relation : relations.keySet()) { 
		String[] relationTMP = relation.split("\\|#\\|#\\|#");
		String[] relationA = relationTMP[0].split("###");
		String[] relationB = relationTMP[1].split("###");
	%>
		<tr>
	<%	
	
		for(String rr : relationA) {
		    if(!rr.isEmpty()) {
	%>
		
		
				<td><%= rr.substring(rr.indexOf(":")+1) %></td>
										
			
	<% } } %>
				
				<td>
				<div>
				<% int ii = 0; for(String b : relationB) { 
					if(!b.isEmpty()) { %>
					<% if(ii>0){ %> - <% } %>
					<%
					    try {
				%> 
				    <%= b.substring(b.indexOf("|||")+3, b.lastIndexOf("|||")) %> 
				<% } catch(Exception e) { %> 
				    
				   <%= b.substring(b.indexOf("|||")+3) %> 
				<% } } ii++;%>
					
				<% } %>
				</div>
				</td>
		</tr>
		
			<% } %>
	</tbody>
</table>
<% } else if(type.equals("coinvestigators"))  { %>
	<table id="edgetableinformation" class="table">
	<thead>
		<tr><th></th></tr>
	</thead>
	<tbody>
	<c:forEach items="${relations}" var="relation">
	<tr>
		<td>
			<a target="_blank" href="../rp/grants/details.htm?id=-1&code=${relation.value}">${relation.key}</a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>

<% } else if(type.equals("kwdpub"))  { %>
	<table id="edgetableinformation" class="table">
	<thead>
		<tr><th></th></tr>
	</thead>
	<tbody>
	<c:forEach items="${relations}" var="relation">
	<tr>
		<td>						
			<a target="_blank" href='../simple-search?query=(dc.subject:"${relation.key}" AND (author:"${authority}" OR author:"${authoritytarget}"))'>${relation.key}</a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
<% } else if(type.equals("keywordsgrants"))  { %>
	<table id="edgetableinformation" class="table">
	<thead>
		<tr><th></th></tr>
	</thead>
	<tbody>
	<c:forEach items="${relations}" var="relation">
	<tr><td>
		<a target="_blank" href='../rp/searchGrants.htm?advancedSyntax=true&searchMode=Search&queryString=fa.keywords:"${relation.key}" AND (investigators:"${authority}" OR investigators:"${authoritytarget}")'>${relation.key}</a>
	</td></tr>
	</c:forEach>
	</tbody>
	</table>
<% } else if(type.equals("disciplines"))  { %>
	<table id="edgetableinformation" class="table">
	<thead>
		<tr><th></th></tr>
	</thead>
	<tbody>
	<c:forEach items="${relations}" var="relation">
	<tr><td>
		<a target="_blank" href='../rp/searchGrants.htm?advancedSyntax=true&searchMode=Search&queryString=fa.discipline:"${relation.key}" AND (investigators:"${authority}" OR investigators:"${authoritytarget}")'>${relation.key}</a>
	</td></tr>
	</c:forEach>
	</tbody>
	</table>
<% }  else { %>
<table id="edgetableinformation" class="table">
	<thead>
		<tr><th></th></tr>
	</thead>
	<tbody>
		<c:forEach items="${relations}" var="relation">
			<tr>
				<td>${relation.key}</td>		
			</tr>
		</c:forEach>
	</tbody>
</table>
<% } %>


<script>

	jQuery(document).ready(function() {
		jQuery("#edgetableinformation").dataTable({
			"bFilter" : false,
			"bDestroy" : true,
			"bSort" : false,
			"bInfo" : false,
			"bLengthChange" : false,
			"bPaginate" : false
		});
	});
</script>
