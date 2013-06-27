<%--
The contents of this file are subject to the license and copyright
 detailed in the LICENSE and NOTICE files at the root of the source
 tree and available online at
 
 https://github.com/CILEA/dspace-cris/wiki/License
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>


<dspace:layout locbar="link" navbar="admin"	titlekey="jsp.dspace-admin.do">
	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.do" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.do")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>


	<c:if test="${!empty error}">
		<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> <fmt:message key="${error}"/></span>
	</c:if>
	<div>&nbsp;</div>
	<div>&nbsp;</div>

	<ul>

		<li>
		<div style="padding: 0; margin: 0 10px;"><a id="addentity"
			href="${contextPath}/cris/administrator/do/add.htm"><fmt:message
			key="jsp.dspace-admin.hku.add-typodynamicobject" /></a></div>	
		
		<div>&nbsp;</div>
		</li>
		<li>
		<div style="padding: 0; margin: 0 10px;">
			
			<display:table name="${researchobjects}" cellspacing="0" cellpadding="0" 
			requestURI="" id="objectList" htmlId="objectList"  class="displaytaglikemisctable" export="false">
			<display:column headerClass="id" titleKey="jsp.layout.table.cris.admin-list.id" property="id" url="/cris/do/details.htm" paramId="id" paramProperty="id" sortable="true" />							
			<display:column headerClass="shortname" titleKey="jsp.layout.table.cris.admin-list.shortname" property="shortName" url="/cris/do/details.htm" paramId="id" paramProperty="id" sortable="true" />										
			<display:column headerClass="label" class="label" titleKey="jsp.layout.table.cris.admin-list.label" property="label" sortable="true"/>		
						
			</display:table>
		</div>
		<div>&nbsp;</div>
		</li>
	
	</ul>
</dspace:layout>
