<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cilea.osd.jdyna.model.PropertiesDefinition"%>
<%@page
	import="it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition"%>
<%@page import="it.cilea.hku.authority.model.dynamicfield.AccessLevelConstants"%>
<%@ page import="java.net.URL"%>

<%-- Calendar code --%>
<style type="text/css">@import url(<%=request.getContextPath()%>/js/jscalendar/calendar-blue.css );</style>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar.js"> </script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/lang/calendar-en.js"> </script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar-setup.js"> </script>

<c:set var="root"><%=request.getContextPath()%></c:set>

<c:set var="admin"><%=isAdmin%></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>

	
<c:set var="commandObject" value="${anagraficadto}" scope="request" />
<c:set var="simpleNameAnagraficaObject"
	value="${simpleNameAnagraficaObject}" scope="page" />

<c:set var="disabledfield" value=" disabled=\"disabled\" "></c:set>
<h1>${researcher.fullName}</h1>


<c:if test="${not empty messages}">
	<div class="message" id="successMessages"><c:forEach var="msg"
		items="${messages}">
		<div id="authority-message">${msg}</div>
	</c:forEach></div>
	<c:remove var="messages" scope="session" />
</c:if>


<div id="researcher">
<form:form commandName="anagraficadto"
	action="" method="post" enctype="multipart/form-data">
	<%-- if you need to display all errors (both global and all field errors,
		 use wildcard (*) in place of the property name --%>
	<spring:bind path="anagraficadto.*">
		<c:if test="${!empty status.errorMessages}">
			<div id="errorMessages">
		</c:if>
		<c:forEach items="${status.errorMessages}" var="error">
			<span class="errorMessage"><fmt:message
				key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
			<br />
		</c:forEach>
		<c:if test="${!empty status.errorMessages}">
			<div id="errorMessages">
		</c:if>
	</spring:bind>


	<dyna:hidden propertyPath="anagraficadto.objectId" />
	<input type="hidden" id="newTabId" name="newTabId" />
	
	
	<p style="color: red; text-decoration: underline; font-weight: bold; text-align: center;"><fmt:message key='jsp.rp.edit-tips'/></p>

	<table width="98%" align="left" cellpadding="0" cellspacing="0">
		<tbody>

			<tr>
				<c:forEach items="${tabList}" var="area">
					<td align="center" class="tb-head0">&nbsp;</td>
					
						<c:choose>
				<c:when test="${area.id == anagraficadto.tabId}">
					<td nowrap="" align="center" class="tb-head1">
							<img  border="0"  
									
									src="<%=request.getContextPath()%>/researcheredittabimage/${area.id}"
									title="${area.shortName} picture" alt="X"/>
					
						${area.title}</td>
				</c:when>
				<c:otherwise>
					<td nowrap="" align="center" class="tb-head2">
						<a onclick="changeArea(${area.id})"><img border="0"				
						
									src="<%=request.getContextPath()%>/researcheredittabimage/${area.id}"
									title="${area.shortName} picture" alt="X"> ${area.title}</a></td>
				</c:otherwise>
				</c:choose>
				
				</c:forEach>
				<td align="center" class="tb-head0">&nbsp;</td>
			</tr>
			<tr>
				<td bgcolor="#f9f9f9" valign="top" class="tb-body" colspan="9">

				
				
				<c:forEach items="${propertiesHolders}" var="holder">
				
				<c:set value="${researcher:isThereMetadataNoEditable(holder.shortName, holder.class)}" var="isThereMetadataNoEditable"></c:set>
					
					
							<%!public URL fileURL;%>

							<c:set var="urljspcustom"
								value="/authority/jdyna/custom/edit${holder.shortName}.jsp" scope="request" />
								
							<%
								String filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

										fileURL = pageContext.getServletContext().getResource(
												filePath);
							%>

							<%
								if (fileURL != null) {
							%>
				
											
				
				
						<c:set var="holder" value="${holder}" scope="request"/>
						<c:set var="isThereMetadataNoEditable" value="${isThereMetadataNoEditable}" scope="request"/>												
						<c:import url="${urljspcustom}" />
					
					
							<%
								} else {
							%>
					
						<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend>
						<label>
						<!--c:if test="${!admin && isThereMetadataNoEditable eq true}"-->

							<span class="green"><fmt:message
								key='jsp.layout.hku.researcher.message.sendemail'>
								<fmt:param>
									<fmt:message
										key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
								</fmt:param>
							</fmt:message></span>

						<!--/c:if--> </label>
							
	
						<table width="98%" cellpadding="0" cellspacing="4">
							<tr>
							<td>
								
						<c:forEach
							items="${propertiesDefinitionsInHolder[holder.shortName]}"
							var="tipologiaDaVisualizzare">
							<c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
							<%
								List<String> parameters = new ArrayList<String>();
												parameters.add(pageContext.getAttribute(
														"simpleNameAnagraficaObject").toString());
												parameters
														.add(((DecoratorRPPropertiesDefinition) pageContext
																.getAttribute("tipologiaDaVisualizzare"))
																.getShortName());
												pageContext.setAttribute("parameters", parameters);
							%>
							<c:set var="show" value="true" />
							<c:choose>							
							<c:when
								test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
								<c:set var="disabled" value="" />
								<c:set var="visibility" value="true" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="false" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="true" />
							</c:when>
							<c:otherwise>
								<c:set var="show" value="false" />
							</c:otherwise>
							</c:choose>
							
							<c:if
								test="${show && (tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition')}">
								
								
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabled}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties" hideLabel="${hideLabel}"
									validationParams="${parameters}" visibility="${visibility}"/>
									
								</c:if>
								<c:if
								test="${show && (tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField')}">

				
										<c:set var="urljspcustom" value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
										

							<c:set var="urljspcustom"
								value="/authority/jdyna/custom/${holder.shortName}.jsp" scope="request" />
								
							<%
								filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

										fileURL = pageContext.getServletContext().getResource(
												filePath);
							%>

							<%
								if (fileURL != null) {
							%>
							<c:import url="${urljspcustom}" />
							<% } %>
																
										
										
									
									
								</c:if>
						</c:forEach>
						</td>
						</tr>
						</table>
						</fieldset>
						
						</div>
						
						<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>

						<p></p>
				
<% } %>

				</c:forEach>
				
				
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit"
					value="<fmt:message key="jsp.layout.hku.researcher.button.save"/>" /></td>
			</tr>

		</tbody>
	</table>
</form:form>
</div>