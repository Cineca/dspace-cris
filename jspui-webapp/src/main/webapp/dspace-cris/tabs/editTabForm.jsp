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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>
<%@ taglib uri="jdynatags" prefix="dyna" %>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@page import="java.net.URL"%>
<%@page import="org.dspace.app.cris.model.jdyna.VisibilityTabConstant"%>

<dspace:layout locbar="link" navbar="admin"
	titlekey="jsp.dspace-admin.edit-tab">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.edit-tab" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>




	<form:form commandName="tab" method="post" id="jdynatab" enctype="multipart/form-data">
		<c:set value="${message}" var="message" scope="request" />
		<c:if test="${!empty message}">
			<div id="authority-message"><fmt:message key="${message}" /></div>
		</c:if>

		<c:if test="${not empty messages}">
			<div class="message" id="successMessages"><c:forEach var="msg"
				items="${messages}">
				<div id="authority-message">${msg}</div>
			</c:forEach></div>
			<c:remove var="messages" scope="session" />
		</c:if>

		<%--  first bind on the object itself to display global errors - if available  --%>
		<spring:bind path="tab">
			<c:forEach items="${status.errorMessages}" var="error">
				<span id="errorMessage"><fmt:message
					key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>
		
				
		<spring:bind path="tab.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="error"><c:forEach var="error"
				items="${status.errorMessages}">
	               ${error}<br />
			</c:forEach></div>
		</c:if>
		</spring:bind>

		<c:if test="${not empty status.errorMessages}">
		<div class="error"><c:forEach var="error"
			items="${status.errorMessages}">
                 ${error}<br />
		</c:forEach></div>
		</c:if>
		
		
		<dyna:text propertyPath="tab.shortName"  helpKey="help.jdyna.message.tab.shortname"
			labelKey="jsp.layout.hku.label.shortname" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text propertyPath="tab.title"  helpKey="help.jdyna.message.tab.title"
			labelKey="jsp.layout.hku.label.title" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text propertyPath="tab.priority"  helpKey="help.jdyna.message.tab.priority"
				labelKey="jsp.layout.hku.label.priority" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
	

		<spring:bind path="visibility">
			<c:set var="inputValue">
				<c:out value="${status.value}" escapeXml="true"></c:out>
			</c:set>
			<c:set var="inputName">
				<c:out value="${status.expression}" escapeXml="false"></c:out>
			</c:set>

			<div class="dynaField"><span class="dynaLabel"><label for="${inputName}"><fmt:message
				key="jsp.layout.hku.label.visibility" /></label></span>

			<div class="dynaFieldValue">
			<c:forEach items="<%= VisibilityTabConstant.getValues() %>" var="item">
				<input ${disabled} id="${inputName}" name="${inputName}"
					type="radio" value="${item}"
					<c:if test="${inputValue==item}">checked="checked"</c:if> />
				<fmt:message
					key="jsp.layout.hku.label.visibility.${item}" />

			</c:forEach>
			<input ${disabled} name="_${inputName}" id="_${inputName}"
				value="true" type="hidden" />
			</div>
			</div>
		</spring:bind>
		<div class="dynaClear">
			&nbsp;
		</div>
		

		<fieldset>
		<legend><fmt:message key="jsp.layout.hku.label.tab.icon" /></legend>
		<input type="file" size="50%" name="iconFile"/>
		<c:if test="${!empty tab.ext || !empty tab.mime}"><div style="font-size: xx-small"><fmt:message key="jsp.layout.hku.label.deleteicon" /><input type="checkbox" name="deleteIcon" value="true"/></div></c:if>
		</fieldset>
		<br/><br/>		
		<fieldset><legend><fmt:message
			key="jsp.layout.hku.label.boxlist" /></legend> <c:forEach items="${researcher:sortBoxByComparator(boxsList,'org.dspace.app.webui.cris.comparator.CustomBoxComparator')}"
			var="box">

						<c:set var="hasCustomEditJSP" value="false" scope="request" />
							<c:set var="hasCustomDisplayJSP" value="false" scope="request" />
							<c:set var="urljspcustomone"
								value="/authority/jdyna/custom/edit${box.shortName}.jsp" scope="request" />
							<c:set var="urljspcustomtwo"
								value="/authority/jdyna/custom/${box.shortName}.jsp" scope="request" />
								
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

			<div class="mask"><c:if test="${!empty tab.id}">
					
					<a class="jdynaeditbutton"
					title="<fmt:message
				key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
					href="<%=request.getContextPath()%>/cris/administrator/${specificPartPath}/editBox.htm?id=${box.id}&tabId=${tab.id}">
				<img
					src="<%=request.getContextPath()%>/image/authority/jdynaeditbutton.jpg"
					border="0"
					alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
					title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.editdynamicfield" />"
					name="edit" id="edit_${box.id}" /> </a>


				
			</c:if>
			
			
			
			
			 <spring:bind path="mask">
				<input id="_${status.expression}" name="_${status.expression}"
					value="true" type="hidden" />
				<c:set var="checked" value="false" />
				<c:forEach var="ownered" items="${owneredBoxs}" varStatus="i">
					<c:if test="${ownered.id eq box.id}">
						<c:set var="checked" value="true" />
						<spring:transform value="${ownered.id}" var="optionToCompare" />
					</c:if>
				</c:forEach>
				<form:label path="mask" for="mask">${box.title}</form:label>



				
				<input class="jdynacontainable" type="checkbox"
				<c:choose>
					<c:when test="${!empty tab.displayTab}"> 
						disabled="disabled" checked="checked" value="${box.id}"
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${checked == true}">
								value="${optionToCompare}" checked="checked"
							</c:when>
							<c:otherwise>
								value="${box.id}"
							</c:otherwise>
						</c:choose>
						
					</c:otherwise>
				</c:choose>
								
				 id="mask_${box.id}" name="mask"			
			 	/>

			 	
			 </spring:bind>
			 <c:if test="${hasCustomDisplayJSP eq true}">
			
			<img
				src="<%=request.getContextPath()%>/image//authority/jdynahavecustomdisplayjsp.jpg"
				border="0"
				alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomdisplayjsp" />"
				title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomdisplayjsp" />"
				id="havecustom_${box.id}" />
			</c:if>
			<c:if test="${hasCustomEditJSP eq true}">
			
			<img
				src="<%=request.getContextPath()%>/image//authority/jdynahavecustomeditjsp.jpg"
				border="0"
				alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomeditjsp" />"
				title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.havecustomeditjsp" />"
				id="havecustom_${box.id}" />
			</c:if>
			 </div>
		</c:forEach></fieldset>

		<c:if test="${empty tab.displayTab}">
			<div id="hidden_first" style="padding: 0; margin: 0 10px;"><a
				onclick="Effect.toggle('hidden_appear', 'appear'); return false;"
				href="#"> <span id="toggle_appear"> <fmt:message
				key="jsp.dspace-admin.hku.jdyna-configuration.edittab-hookup" /></span> </a></div>
			<div id="hidden_appear" style="display: none; float: right;"><c:set
				var="contextPath"><%=request.getContextPath()%></c:set> <em
				class="bodyText"><fmt:message
				key="jsp.dspace-admin.hku.add-displaytab.message" /></em> <input
				type="text" name="hookedtab" id="hookedtab" value="" /> <input
				type="submit" name="hookupit"
				value="<fmt:message key="jsp.layout.hku.researcher.button.hookup" />" />
			</div>
		</c:if>
		<c:if test="${!empty tab.displayTab}">
			<input type="submit" name="dehookupit" id="dehookupit"
				value="<fmt:message key="jsp.layout.hku.researcher.button.de-hookup" />" />
		</c:if>

		
		<input type="submit" name="submit"
			value="<fmt:message key="jsp.layout.hku.researcher.button.save" />" />

	</form:form>
	<c:if test="${!empty tab.id && empty tab.displayTab}">
		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/cris/administrator/${specificPartPath}/createBox.htm?tabId=${tab.id}">
		<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.newbox" />
		</a></div>
	</c:if>
		<script language="javascript" type="text/javascript">
	//          	
	// horizontal slider control
	var slider1 = new Control.Slider('handle1', 'track1', {
		onSlide : function(v) {
			$('debug1').innerHTML = (v * 100).toFixed();
			$('priority').value = (v * 100).toFixed();
		},
		onChange : function(v) {
			$('debug1').innerHTML = (v * 100).toFixed();
			$('priority').value = (v * 100).toFixed();
		}
	});

	//
</script>

	<script language="javascript" type="text/javascript">
	setSliderValue(slider1, document.getElementById('priority').value);

	function setSliderValue(slider, value) {

		if (value == '') {
			return;
		}
		if (isNaN(value)) {
			slider.setValue(0);
		} else {
			slider.setValue(value / 100);
		}
	}
</script>
</dspace:layout>
