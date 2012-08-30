<%@page import="it.cilea.hku.authority.model.dynamicfield.AccessLevelConstants"%>
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

<dspace:layout locbar="link" navbar="admin"
	titlekey="jsp.dspace-admin.researchers-list">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message
				key="jsp.dspace-admin.edit-propertiesdefinition" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>



	<form:form commandName="propertiesdefinition" method="post">
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
		<spring:bind path="propertiesdefinition">
			<c:forEach items="${status.errorMessages}" var="error">
				<span id="errorMessage"><fmt:message
					key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>

		<spring:bind path="propertiesdefinition.*">
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

		<fieldset>
		<legend><fmt:message
			key="jsp.layout.hku.label.propertiesdefinition.rendering.${propertiesdefinition.rendering.triview}" /></legend> 


		<dyna:text visibility="false" propertyPath="real.shortName"
				labelKey="jsp.layout.hku.label.propertiesdefinition.shortName" helpKey="help.jdyna.message.shortName"/>
		
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text visibility="false" propertyPath="real.label" size="50"
				labelKey="jsp.layout.hku.label.propertiesdefinition.label" helpKey="help.jdyna.message.label"/>						
		
		<div class="dynaClear">
			&nbsp;
		</div>

		<c:if test="${empty renderingparent}">
								
		<spring:bind path="real.accessLevel">
			<c:set var="inputValue">
				<c:out value="${status.value}" escapeXml="true"></c:out>
			</c:set>
			<c:set var="inputName">
				<c:out value="${status.expression}" escapeXml="false"></c:out>
			</c:set>


			<dyna:label propertyPath="${inputName}" labelKey="jsp.layout.hku.label.propertiesdefinition.accessLevel" helpKey="help.jdyna.message.accessLevel"/>
			<div class="dynaClear">
			&nbsp;
			</div>
			<c:forEach items="<%= AccessLevelConstants.getValues() %>" var="item">
				<input ${disabled} id="${inputName}" name="${inputName}"
					type="radio" value="${item}"
					<c:if test="${inputValue==item}">checked="checked"</c:if> />
				<fmt:message
					key="jsp.layout.hku.label.propertiesdefinition.accessLevel.${item}" />

			</c:forEach>
			<input ${disabled} name="_${inputName}" id="_${inputName}"
				value="true" type="hidden" />
		</spring:bind>


		<div class="dynaClear">
			&nbsp;
		</div>
		

		<%--
		<dyna:boolean propertyPath="propertiesdefinition.real.mandatory"
				labelKey="jsp.layout.hku.label.propertiesdefinition.mandatory" helpKey="help.jdyna.message.mandatory"/>
		
		<div class="dynaClear">
			&nbsp
		</div>
		--%>						
		
		</c:if>
		
		
		
		<dyna:boolean propertyPath="propertiesdefinition.real.repeatable"
				labelKey="jsp.layout.hku.label.propertiesdefinition.repeatable" helpKey="help.jdyna.message.repeatable"/>
		<div class="dynaClear">
			&nbsp;
		</div>

		<dyna:text propertyPath="propertiesdefinition.real.priority"  helpKey="help.jdyna.message.priority"
				labelKey="jsp.layout.hku.label.propertiesdefinition.priority" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		
		<dyna:boolean propertyPath="propertiesdefinition.real.newline"
				labelKey="jsp.layout.hku.label.propertiesdefinition.newline" helpKey="help.jdyna.message.newline"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text propertyPath="propertiesdefinition.real.labelMinSize"  helpKey="help.jdyna.message.labelMinSize"
				labelKey="jsp.layout.hku.label.propertiesdefinition.labelMinSize" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>		
						
		<dyna:text propertyPath="propertiesdefinition.real.fieldMinSize.col"  helpKey="help.jdyna.message.fieldminsize.col"
				labelKey="jsp.layout.hku.label.propertiesdefinition.fieldminsize.col" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text propertyPath="propertiesdefinition.real.fieldMinSize.row"  helpKey="help.jdyna.message.fieldminsize.row"
				labelKey="jsp.layout.hku.label.propertiesdefinition.fieldminsize.row" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>				
		</fieldset>
		
		<c:choose>

			<c:when test="${propertiesdefinition.rendering.triview == 'testo'}">
				<fieldset>
					<dyna:text visibility="false" propertyPath="real.rendering.dimensione.col"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.text.size" helpKey="help.jdyna.message.rendering.text.size"/>
					<div class="dynaClear">
					&nbsp;
					</div>			
					<dyna:text visibility="false" propertyPath="real.rendering.regex"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.text.regex" helpKey="help.jdyna.message.rendering.text.regex"/>
											
				</fieldset>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when
						test="${propertiesdefinition.rendering.triview eq 'calendar'}">



					</c:when>
					<c:otherwise>
					<c:choose>
					<c:when
						test="${propertiesdefinition.rendering.triview eq 'link'}">
						<fieldset>
						<dyna:text visibility="false" propertyPath="real.rendering.labelHeaderLabel"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.link.labelHeaderLabel" helpKey="help.jdyna.message.rendering.link.labelHeaderLabel"/>

						<dyna:text visibility="false" propertyPath="real.rendering.labelHeaderURL"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.link.labelHeaderURL" helpKey="help.jdyna.message.rendering.link.labelHeaderURL"/>
						</fieldset>
					</c:when>
					<c:otherwise>
					<c:choose>
					<c:when test="${propertiesdefinition.rendering.triview eq 'file'}">
						<fieldset>
						<dyna:boolean propertyPath="real.rendering.showPreview"
							labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.showpreview" helpKey="help.jdyna.message.rendering.file.showpreview"/>
						<div class="dynaClear">
							&nbsp;
						</div>
						<dyna:text visibility="false" propertyPath="real.rendering.fileDescription"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.fileDescription" helpKey="help.jdyna.message.rendering.file.description"/>
						<div class="dynaClear">
							&nbsp;
						</div>
						<dyna:text visibility="false" propertyPath="real.rendering.labelAnchor"
								labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.labelAnchor" helpKey="help.jdyna.message.rendering.file.labelAnchor"/>
						</fieldset>											
					</c:when>
					<c:otherwise>
					<c:if test="${propertiesdefinition.rendering.triview eq 'combo'}">
						<fieldset><legend><fmt:message
							key="jsp.layout.hku.label.propertiesdefinition.widget" /></legend> <input
							type="hidden" id="renderingparent" name="renderingparent"
							value="${renderingparent}" /> 
							
						<input type="submit" name="text"
							value="<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.addtextnesteddynamicfield" />" />
						<input type="submit" name="date"
							value="<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.adddatenesteddynamicfield" />" />
						<input type="submit" name="link"
							value="<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.addlinknesteddynamicfield" />" />
						<input type="submit" name="file"
							value="<fmt:message key="jsp.dspace-admin.hku.jdyna-configuration.addfilenesteddynamicfield" />" />	
							

						<c:forEach
							items="${propertiesdefinition.rendering.sottoTipologie}"
							var="subtypo" varStatus="i">

							<fieldset><legend><fmt:message
									key="jsp.layout.hku.label.propertiesdefinition.rendering.${subtypo.rendering.triview}" /></legend> <form:label
								path="rendering.sottoTipologie[${i.count - 1}].shortName"
								for="rendering.sottoTipologie[${i.count - 1}].shortName">
								<fmt:message
									key="jsp.layout.hku.label.propertiesdefinition.shortName" />
							</form:label><form:input
								path="rendering.sottoTipologie[${i.count - 1}].shortName" /> <form:label
								path="rendering.sottoTipologie[${i.count - 1}].label"
								for="rendering.sottoTipologie[${i.count - 1}].label">
								<fmt:message
									key="jsp.layout.hku.label.propertiesdefinition.label" />
							</form:label><form:input path="rendering.sottoTipologie[${i.count - 1}].label" />
								<div class="dynaClear">
								&nbsp;
								</div>
							<dyna:boolean propertyPath="rendering.sottoTipologie[${i.count - 1}].repeatable"
									labelKey="jsp.layout.hku.label.propertiesdefinition.repeatable" helpKey="help.jdyna.message.repeatable"/>
							<div class="dynaClear">
								&nbsp;
							</div>
										<c:if
											test="${subtypo.rendering.triview eq 'link'}">
											<fieldset style="width: 90%;"><dyna:text visibility="false"
												propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.labelHeaderLabel"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.link.labelHeaderLabel"
												helpKey="help.jdyna.message.rendering.link.labelHeaderLabel" />

											<dyna:text visibility="false"
												propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.labelHeaderURL"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.link.labelHeaderURL"
												helpKey="help.jdyna.message.rendering.link.labelHeaderURL" />
											</fieldset>
											<div class="dynaClear">
												&nbsp;
											</div>
										</c:if>
					
										<c:if
											test="${subtypo.rendering.triview eq 'testo'}">
											<fieldset style="width: 90%;"><dyna:text visibility="false"
												propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.dimensione.col"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.text.size" helpKey="help.jdyna.message.rendering.text.size" />
											<div class="dynaClear">
												&nbsp;
											</div>
											<dyna:text visibility="false"
												propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.regex"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.text.regex"
												helpKey="help.jdyna.message.rendering.text.regex" />
											</fieldset>

										</c:if>
					
										<c:if
											test="${subtypo.rendering.triview eq 'file'}">
											<fieldset style="width: 90%;">
											
											<dyna:boolean propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.showPreview"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.showpreview" helpKey="help.jdyna.message.rendering.file.showpreview"/>
											<div class="dynaClear">
												&nbsp;
											</div>
											<dyna:text visibility="false" propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.fileDescription"
												labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.fileDescription" helpKey="help.jdyna.message.rendering.file.description"/>
											<div class="dynaClear">
												&nbsp;
											</div>
											<dyna:text visibility="false" propertyPath="rendering.sottoTipologie[${i.count - 1}].rendering.labelAnchor"
											labelKey="jsp.layout.hku.label.propertiesdefinition.rendering.file.labelAnchor" helpKey="help.jdyna.message.rendering.file.labelAnchor"/>
																	
											
											</fieldset>

										</c:if>
								<dyna:text propertyPath="rendering.sottoTipologie[${i.count - 1}].priority"  helpKey="help.jdyna.message.priority"
				labelKey="jsp.layout.hku.label.propertiesdefinition.priority" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		
		<dyna:boolean propertyPath="rendering.sottoTipologie[${i.count - 1}].newline"
				labelKey="jsp.layout.hku.label.propertiesdefinition.newline" helpKey="help.jdyna.message.newline"/>
		<div class="dynaClear">
			&nbsp;
		</div>
				
		<dyna:text propertyPath="rendering.sottoTipologie[${i.count - 1}].labelMinSize"  helpKey="help.jdyna.message.labelMinSize"
				labelKey="jsp.layout.hku.label.propertiesdefinition.labelMinSize" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>		
						
		<dyna:text propertyPath="rendering.sottoTipologie[${i.count - 1}].fieldMinSize.col"  helpKey="help.jdyna.message.fieldminsize.col"
				labelKey="jsp.layout.hku.label.propertiesdefinition.fieldminsize.col" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>
		
		<dyna:text propertyPath="rendering.sottoTipologie[${i.count - 1}].fieldMinSize.row"  helpKey="help.jdyna.message.fieldminsize.row"
				labelKey="jsp.layout.hku.label.propertiesdefinition.fieldminsize.row" size="5" visibility="false"/>
		<div class="dynaClear">
			&nbsp;
		</div>				
		
							<a class="jdynaremovebutton"
								title="<fmt:message
				key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
								href="<%=request.getContextPath()%>/${specificPartPath}/administrator/deleteDynamicField.htm?pDId=${subtypo.id}&boxId=${boxId}&tabId=${tabId}">
							<img
								src="<%=request.getContextPath()%>/image/authority/jdynadeletebutton.jpg"
								border="0"
								alt="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
								title="<fmt:message
					key="jsp.dspace-admin.hku.jdyna-configuration.deletedynamicfield" />"
								name="remove" id="remove_${boxed.id}" /> </a>
							
							
							</fieldset>

						</c:forEach></fieldset>
						</c:if>
						</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
					</c:otherwise>
				</c:choose>
				
			</c:otherwise>
		</c:choose>

		<input type="hidden" id="tabId" name="tabId" value="${tabId}" />
		<input type="hidden" id="boxId" name="boxId" value="${boxId}" />
		<input type="submit" name="save"
			value="<fmt:message key="jsp.layout.hku.researcher.button.save" />" />

	</form:form>


</dspace:layout>