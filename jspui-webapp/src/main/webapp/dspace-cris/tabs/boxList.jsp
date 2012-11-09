<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>
<%@page import="java.net.URL"%>

<dspace:layout locbar="link" navbar="admin"
	titlekey="jsp.dspace-admin.additional-fields">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.listofboxs" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>

	<c:if test="${not empty messages}">
		<div class="message" id="successMessages"><c:forEach var="msg"
			items="${messages}">
			<div id="authority-message">${msg}</div>
		</c:forEach></div>
		<c:remove var="messages" scope="session" />
	</c:if>

	<fieldset>
	<ul>
		<c:forEach items="${listBox}" var="box">
							
							<c:set var="hasCustomEditJSP" value="false" scope="request" />
							<c:set var="hasCustomDisplayJSP" value="false" scope="request" />
							<c:set var="urljspcustomone"
								value="/dspace-cris/jdyna/custom/edit${box.externalJSP}.jsp" scope="request" />
							<c:set var="urljspcustomtwo"
								value="/dspace-cris/jdyna/custom/${box.externalJSP}.jsp" scope="request" />
								
							<%
							 	URL fileDisplayURL = null;
								URL fileEditURL = null;
								String filePathOne = (String)pageContext.getRequest().getAttribute("urljspcustomone");
								String filePathTwo = (String)pageContext.getRequest().getAttribute("urljspcustomtwo");

							fileEditURL = pageContext.getServletContext().getResource(
												filePathOne);
							fileDisplayURL = pageContext.getServletContext().getResource(
												filePathTwo);
							%>

							<%
								if (fileEditURL != null) {
							%>
								<c:set var="hasCustomEditJSP"
								value="true" scope="request" />
							<%							
								}
							%>
							<%
								if (fileDisplayURL != null) {
							%>
								<c:set var="hasCustomDisplayJSP"
								value="true" scope="request" />
							<%							
								}
							%>					
			<li>
			<div style="padding: 0; margin: 0 10px;">${box.title}
			<a class="jdynaremovebutton"
					title="<fmt:message
				key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
					href="<%=request.getContextPath()%>/cris/administrator/${specificPartPath}/deleteBox.htm?id=${box.id}">
				<img
					src="<%=request.getContextPath()%>/image/authority/jdynadeletebutton.jpg"
					border="0"
					alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
					title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
					name="remove" id="remove_${box.id}" /> </a>
			<a class="jdynaeditbutton"
				title="<fmt:message
				key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
				href="<%=request.getContextPath()%>/cris/administrator/${specificPartPath}/editBox.htm?id=${box.id}">
			<img
				src="<%=request.getContextPath()%>/image/authority/jdynaeditbutton.jpg"
				border="0"
				alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
				title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
				name="edit" id="edit_${box.id}" /> </a>
			
			<c:if test="${hasCustomDisplayJSP eq true}">
			
			<img
				src="<%=request.getContextPath()%>/image/authority/jdynahavecustomdisplayjsp.jpg"
				border="0"
				alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomdisplayjsp" />"
				title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomdisplayjsp" />"
				id="havecustom_${box.id}" />
			</c:if>
			<c:if test="${hasCustomEditJSP eq true}">
			
			<img
				src="<%=request.getContextPath()%>/image/authority/jdynahavecustomeditjsp.jpg"
				border="0"
				alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomeditjsp" />"
				title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomeditjsp" />"
				id="havecustom_${box.id}" />
			</c:if>
			</div>

			</li>
			<div>&nbsp;</div>

		</c:forEach>
	</ul>
	</fieldset>

	<div style="padding: 0; margin: 0 10px;"><a
		href="<%=request.getContextPath()%>/cris/administrator/${specificPartPath}/createBox.htm">
	<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.newbox" />
	</a></div>
</dspace:layout>