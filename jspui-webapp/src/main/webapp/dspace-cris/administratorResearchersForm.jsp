<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>


<c:set var="dspace.layout.head" scope="request">    
	<link href="<%= request.getContextPath() %>/css/researcher.css" type="text/css" rel="stylesheet" />
</c:set>
<dspace:layout locbar="link" navbar="admin" titlekey="jsp.dspace-admin.researchers-list">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.researchers-list" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp-list")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>
	
	
	<form:form commandName="dto" method="post">
	<c:set value="${message}" var="message" scope="request"/>	
	<c:if test="${!empty message}">		
    <div id="authority-message"><fmt:message key="${message}"/></div>    
	</c:if>
	
		<%--  first bind on the object itself to display global errors - if available  --%>
		<spring:bind path="dto">
			<c:forEach items="${status.errorMessages}" var="error">
				<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>
										
		<display:table name="${dto}" cellspacing="0" cellpadding="0" 
			requestURI="" id="objectList" htmlId="objectList"  class="displaytaglikemisctable" export="false">
										
			<display:column headerClass="uuid" titleKey="jsp.layout.table.hku.researchers.uuid" property="rp.uuid" url="/cris/rp/details.htm" paramId="id" paramProperty="id" sortable="true"/>		
			<display:column headerClass="staff" class="staff" titleKey="jsp.layout.table.hku.researchers.staffNo" property="staffNo" url="/cris/rp/details.htm" paramId="persistentIdentifier" paramProperty="persistentIdentifier" sortable="true"/>			
			<display:column headerClass="names" class="names" titleKey="jsp.layout.table.hku.researchers.fullName" property="fullName" sortable="true"/>						
			<display:column headerClass="chinese" titleKey="jsp.layout.table.hku.researchers.chineseName" property="chineseName" sortable="true"/>
			<display:column headerClass="dept" class="dept" titleKey="jsp.layout.table.hku.researchers.department" sortable="true">
				<a href="${dyna:getLinkValue(objectList.dept)}">${dyna:getLinkDescription(objectList.dept)}</a> 
			</display:column>	
			<display:column headerClass="active" titleKey="jsp.layout.table.hku.researchers.status" sortable="true" sortProperty="status">				
				<form:checkbox cssClass="active" path="list[${objectList_rowNum-1}].status" value="1"/>				
			</display:column>
			<display:column headerClass="internalnote" titleKey="jsp.layout.table.hku.researchers.iternalnote" sortable="false">				
				${objectList.rp.dynamicField.anagrafica4view['internalnote'][0]}				
			</display:column>	

		</display:table>
		
		<input type="submit" value="<fmt:message key="jsp.layout.hku.researcher.button.save" />" />
		
	</form:form>				 
</dspace:layout>