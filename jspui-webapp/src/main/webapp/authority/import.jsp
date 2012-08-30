<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>

<style type="text/css">
@import
url(<%=request.getContextPath()%>/js/jscalendar/calendar-blue.css
);
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"> </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jscalendar/lang/calendar-en.js"> </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"> </script>

<dspace:layout locbar="link" navbar="admin"
	titlekey="jsp.dspace-admin.researchers-import">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.researchers-import" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=LocaleSupport.getLocalizedMessage(pageContext,
						"help.site-admin.rp-import")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>

		<c:if test="${not empty messages}">
			<div class="message" id="successMessages"><c:forEach var="msg"
				items="${messages}">
				<div id="authority-message">${msg}</div>
				</c:forEach>
				</div>
			<c:remove var="messages" scope="session" />
		</c:if>
		
	<table width="98%" align="left" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<form:form commandName="dto"
				method="post" enctype="multipart/form-data">


				<%-- if you need to display all errors (both global and all field errors,
                                 use wildcard (*) in place of the property name --%>
				<spring:bind path="dto.*">
					<c:if test="${not empty status.errorMessages}">
						<div id="errorMessage"><c:forEach
							items="${status.errorMessages}" var="error">
							<span class="errorMessage"><fmt:message
								key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
							<br />
						</c:forEach></div>
					</c:if>
				</spring:bind>

				<div id="export">
				<fieldset><legend><fmt:message
					key="jsp.layout.hku.import.box.label" /></legend>
					
					<input type="file" size="50%" name="file"/>
					
				</fieldset>
				</div>
			<input type="submit"
			value="<fmt:message key="jsp.import.advanced.downloadxsd"/>"
			name="modeXSD" />				
			<input type="submit"
			value="<fmt:message key="jsp.import.advanced.import"/>"
			name="submit" />
			</form:form>		
			
			</td>
		</tr>
	</table>
</dspace:layout>