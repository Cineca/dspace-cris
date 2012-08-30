<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>

<dspace:layout locbar="link" navbar="admin"	titlekey="jsp.dspace-admin.grants">
	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.rg" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.grant")%>'><fmt:message
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

		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/rp/grants/administrator/listPropertiesDefinitions.htm?id=${onlyOneBox}"><fmt:message
			key="jsp.dspace-admin.hku.jdyna-configuration.listpropertiesdefinitions" /></a></div>	
		
		<div>&nbsp;</div>
		</li>
		<li>

		<div id="hidden_first" style="padding: 0; margin: 0 10px;"><a
			onclick="Effect.toggle('hidden_appear', 'appear'); return false;"
			href="#"> <span id="toggle_appear"> <fmt:message
			key="jsp.dspace-admin.hku.add-grant" /></span> </a>

		<div id="hidden_appear" style="display: none; float: right;"><c:set
			var="contextPath"><%=request.getContextPath()%></c:set> <form:form
			action="${contextPath}/rp/grants/administrator/addGrants.htm"
			method="post" commandName="dto">


			<em class="bodyText"><fmt:message
				key="jsp.dspace-admin.hku.add-grants.message" /></em>
			<form:input path="code" />

			<input type="submit"
				value="<fmt:message key="jsp.dspace-admin.hku.add-grants.go"/>">
		</form:form></div>
					

		<div>&nbsp;</div>

		</li>

	</ul>
</dspace:layout>