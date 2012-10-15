<%@page import="it.cilea.hku.authority.model.dynamicfield.AccessLevelConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<c:set var="root"><%= request.getContextPath() %></c:set>
<%
Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
%>
<c:set var="admin"><%=isAdmin%></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>
<c:set var="disabledfield" value=" disabled=\"disabled\" "></c:set>

		<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend><label>
<c:if test="${isThereMetadataNoEditable eq true}">

	<span class="green"><fmt:message
		key='jsp.layout.hku.researcher.message.sendemail'>
		<fmt:param>
			<fmt:message
				key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
		</fmt:param>
	</fmt:message></span>

</c:if> </label>


<table width="98%" cellpadding="0"
		cellspacing="4">

	

	<tr>
		<td>
			<table id="tabledatafields" align="left" cellpadding="0" cellspacing="4">
			&nbsp;
		
		
		<tr>
			<td> <span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.staffno" /></span></td>
			<td><form:input path="staffNo" size="80%" disabled="${!admin}" /></td>
			<td></td>
		</tr>
		<tr>

			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.fullname" /></span></td>
			<td><form:input path="fullName" size="80%" disabled="${!admin}" /></td>
			<td></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.academicname" /></span></td>
			<td><form:input path="academicName.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="academicName.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.chinesename" /></span></td>
			<td><form:input path="chineseName.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="chineseName.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.honorific" /></span></td>
			<td><form:input path="honorific.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="honorific.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	
		&nbsp;
		</table>
		
		</td>
		</tr>
				
			<tr><td>
		<table id="tabledataotherfields" align="left" cellpadding="0" cellspacing="4">
		<tr>

			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.dept" /></span></td>
			<td><form:input path="dept.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="dept.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.email" /></span></td>
			<td><form:input path="email.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="email.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.office" /></span></td>
			<td><form:input path="address.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="address.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.officetel" /></span></td>
			<td><form:input path="officeTel.value" size="80%" disabled="${!admin}" /></td>
			<td><form:checkbox path="officeTel.visibility"
				value="1" /></td>
		</tr>
			<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
	    
	    
	    <td>
		
		</td>
	</tr>		
		
	</table>
	</td>
	</tr>
			
		
</table>



			<c:forEach items="${propertiesDefinitionsInHolder[holder.shortName]}"
				var="tipologiaDaVisualizzare">


				

					<c:choose>
						<c:when
							test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties"
									validationParams="${parameters}" visibility="true" />

							</c:if>
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


								<c:set var="urljspcustom"
									value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
								<c:catch var="noCustomJSP">
									<c:import url="${urljspcustom}" />
								</c:catch>


							</c:if>

						</c:when>
						<c:otherwise>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">

									<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
										disabled="${disabledfield}"
										propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
										ajaxValidation="validateAnagraficaProperties"
										validationParams="${parameters}" visibility="false" />

								</c:if>
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


									<c:set var="urljspcustom"
										value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
									<c:catch var="noCustomJSP">
										<c:import url="${urljspcustom}" />
									</c:catch>


								</c:if>
							</c:if>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">

								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">


									<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
										disabled="${disabledfield}"
										propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
										ajaxValidation="validateAnagraficaProperties"
										validationParams="${parameters}" visibility="true" />

								</c:if>
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


									<c:set var="urljspcustom"
										value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
									<c:catch var="noCustomJSP">
										<c:import url="${urljspcustom}" />
									</c:catch>


								</c:if>
							</c:if>
						</c:otherwise>
					</c:choose>

				

			</c:forEach>

</fieldset></div>
		<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>
		<p></p>