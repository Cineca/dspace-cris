<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<% 
	Integer offset = (Integer)request.getAttribute("offset");
	Integer limit = (Integer)request.getAttribute("limit");
	Integer totalHit = (Integer)request.getAttribute("totalHit");
	Integer hitPageSize = (Integer)request.getAttribute("hitPageSize");
	Integer pageCurrent = (Integer)request.getAttribute("pageCurrent");
%>
<div id="nestedDetailDiv_${decoratorPropertyDefinition.real.id}" class="dynaField">
			<span class="spandatabind nestedinfo">${decoratorPropertyDefinition.real.id}</span>
			<span id="nested_${decoratorPropertyDefinition.real.id}_totalHit" class="spandatabind">${totalHit}</span>
			<span id="nested_${decoratorPropertyDefinition.real.id}_limit" class="spandatabind">${limit}</span>
			<span id="nested_${decoratorPropertyDefinition.real.id}_pageCurrent" class="spandatabind">${pageCurrent}</span>
			<span id="nested_${decoratorPropertyDefinition.real.id}_editmode" class="spandatabind">false</span>
	<c:if test="${totalHit > 0 || editmode}">
	<c:set var="totalpage" scope="request">
	<c:choose>
			<c:when test="${totalHit<limit}">1</c:when>
			<c:otherwise><c:choose><c:when test="${totalHit%limit==0}"><fmt:formatNumber pattern="#">${totalHit/limit}</fmt:formatNumber></c:when><c:otherwise><fmt:formatNumber pattern="#"><%= Math.ceil(totalHit/limit) + 1%></fmt:formatNumber></c:otherwise></c:choose></c:otherwise>
	</c:choose>
	</c:set>
		
	<span class="dynaLabel">${decoratorPropertyDefinition.label}</span>
	<div class="dynaFieldValue">
	<table>
	<c:if test="${totalpage>1}">
	<tr>
	<td>	
	<fmt:message key="jsp.layout.form.search.navigation">
	<fmt:param>${pageCurrent+1}</fmt:param>	
	<fmt:param>${totalHit}</fmt:param>
	<fmt:param>
			${totalpage}
	</fmt:param>
	<fmt:param>${decoratorPropertyDefinition.label}</fmt:param>	
	</fmt:message>
	
	</td>
	</tr>
	</c:if>
	<c:if test="${totalpage==1}">
	<tr>
	<td>	
	
	<fmt:message key="jsp.layout.form.search.navigation.minimal">	
	<fmt:param>${totalHit}</fmt:param>	
	<fmt:param>${decoratorPropertyDefinition.label}</fmt:param>	
	</fmt:message>
		
	</td>
	</tr>
	</c:if>
	</table>
	
	<c:if test="${totalpage>1}">
	<table>
	<tr>
	<c:if test="${pageCurrent>0}">
		<td>	
			<input type="button" id="nested_${decoratorPropertyDefinition.real.id}_prev" value="Prev" />	
		</td>
		
	<% for(int indexPrevious = (pageCurrent+1>5?pageCurrent-4:1); indexPrevious<pageCurrent+1; indexPrevious++) {%>
		<td>			
			<a href="#viewnested_${decoratorPropertyDefinition.shortName}" class="nested_${decoratorPropertyDefinition.real.id}_nextprev" id="nested_${decoratorPropertyDefinition.real.id}_nextprev_<%= indexPrevious - 1 %>"><%= indexPrevious %></a>			
		</td>
	<% } %>			
	</c:if>			
	


	<% int i = 0;
	                                     
	   for(int indexNext = pageCurrent; (totalHit  - (limit * (indexNext))) > 0 && i<5; indexNext++,i++) {%>
			   
		<td>		
		<% if(pageCurrent==indexNext) { %>
		<b>
		<% } %>			
			<a href="#viewnested_${decoratorPropertyDefinition.shortName}" class="nested_${decoratorPropertyDefinition.real.id}_nextprev" id="nested_${decoratorPropertyDefinition.real.id}_nextprev_<%= indexNext %>"><%= indexNext +1%></a>
		<% if(pageCurrent==indexNext) { %>
		</b>
		<% } %>		
		</td>
	<% } %>
	
	
	<c:if test="${(totalHit  - (limit * (pageCurrent+1))) > 0}">
		<td>
			<input type="button" id="nested_${decoratorPropertyDefinition.real.id}_next" value="Next" />
		</td>			
	</c:if>
	</tr>
	</table>	
	</c:if>
	
	<dyna:display-nested values="${results}" typeDefinition="${decoratorPropertyDefinition}" editmode="${editmode}" parentID="${parentID}" specificPartPath="${specificContextPath}${specificPartPath}" admin="${admin}"/>
	</c:if>	
	<c:if test="${(editmode && decoratorPropertyDefinition.repeatable) || (editmode && empty results)}">
		<img id="add${decoratorPropertyDefinition.shortName}" src="<%= request.getContextPath() %>/image/jdyna/main_plus.gif" class="addNestedButton"/>
	</c:if>	
	</div>
	<c:if test="${decoratorPropertyDefinition.real.newline}">
		<div class="dynaClear">&nbsp;</div>
	</c:if>
</div>