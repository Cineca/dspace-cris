<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna" %>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cilea.osd.jdyna.model.PropertiesDefinition"%>
<%@page import="it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition"%>
<%@page import="it.cilea.hku.authority.model.dynamicfield.AccessLevelConstants"%>
<%@page import="java.net.URL"%>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<c:set var="root"><%= request.getContextPath() %></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>
<%
Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
%>
<c:set var="admin"><%=isAdmin%></c:set>
<c:set var="commandObject" value="${anagraficadto}" scope="request" />
<c:set var="simpleNameAnagraficaObject" value="${simpleNameAnagraficaObject}" scope="page" />
<c:set var="disabledfield" value=" disabled=\"disabled\" "></c:set>
                                                <div id="hidden_first${holder.shortName}">
                                                <fieldset><legend> <a
                                                        onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
                                                        href="#"> <span id="toggle_appear${holder.shortName}"> <img
                                                        src="<%=request.getContextPath()%>/images/collapse.gif"
                                                        border="0" /> </span></a> ${holder.title} </legend>
                                                <label>
                                                <%--c:if test="${!admin && isThereMetadataNoEditable eq true}"--%>

                                                        <span class="green"><fmt:message
                                                                key='jsp.layout.hku.researcher.message.sendemail'>
                                                                <fmt:param>
                                                                        <fmt:message
                                                                                key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
                                                                </fmt:param>
                                                        </fmt:message></span>

                                                <%--/c:if--%> </label>


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

<c:choose>
	<c:when test="${tipologiaDaVisualizzare.shortName eq 'coprojectssuppressvisibility'}">
<spring:bind path="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}][0]">
	<c:set var="inputValue"><c:out value="${status.value == null?'':status.value}" escapeXml="true"></c:out></c:set>
	<c:set var="inputName"><c:out value="${status.expression}" escapeXml="false"></c:out></c:set>
</spring:bind>
<div class="dynaField"><span class="dynaLabel">
	<input type="hidden" id="${inputName}" name="${inputName}" value="${inputValue}" />
	<input id="_${inputName}.visibility" name="_${inputName}.visibility" type="hidden"  />
	<input id="${inputName}.visibility" name="${inputName}.visibility" type="hidden" value="false" />
	<input type="checkbox" id="projectssuppressvisibility_checkbox" ${inputValue == 'suppressed' ? 'checked' : ''}  onclick="document.getElementById('${inputName}').value = this.checked ? 'suppressed' : '';" />
	<label for="projectssuppressvisibility_checkbox">${tipologiaDaVisualizzare.label}</label>
	</span>
</div>
<div class="dynaClear"></div>
	</c:when>
	<c:when test="${tipologiaDaVisualizzare.shortName eq 'coprojects'}">
<c:set var="propertyPath" value="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]" />
<spring:bind path="${propertyPath}">
        <c:set var="values" value="${status.value}" />
        <c:set var="inputName" value="${status.expression}" />
</spring:bind>
<div class="dynaField">
	<input type="button" value="Uncheck All Visibilities" onclick="uncheck_all_${tipologiaDaVisualizzare.shortName}_visibilities();" />
</div>
<div class="dynaClear"></div>
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabled}"
                                                                        propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
                                                                        ajaxValidation="validateAnagraficaProperties" hideLabel="true"
                                                                        validationParams="${parameters}" visibility="${visibility}"/>
	<script type="text/javascript">
		var ${tipologiaDaVisualizzare.shortName}_tmpShortNames = tmpShortNames;
		function uncheck_all_${tipologiaDaVisualizzare.shortName}_visibilities() {
			for (var i = 0;i < ${fn:length(values)};i++) {
				for (var j = 0;j < ${tipologiaDaVisualizzare.shortName}_tmpShortNames.length;j++) {
					var nn = '${inputName}[' + i + '].object.anagraficaProperties[' + ${tipologiaDaVisualizzare.shortName}_tmpShortNames[j] + '][0].visibility';
					if (document.getElementById('check' + nn) != null) document.getElementById('check' + nn).checked = false;
					if (document.getElementById(nn) != null) document.getElementById(nn).value = 'false';
				}
			}
		}
	</script>
	</c:when>
	<c:otherwise>
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabled}"
                                                                        propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
                                                                        ajaxValidation="validateAnagraficaProperties" hideLabel="{$hideLabel}"
                                                                        validationParams="${parameters}" visibility="${visibility}"/>
	</c:otherwise>
</c:choose>
                                                                </c:if>
                                                                <c:if
                                                                test="${show && (tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField')}">
<c:set var="urljspcustom" value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />


                                                        <c:set var="urljspcustom"
                                                                value="/authority/jdyna/custom/${holder.shortName}.jsp" scope="request" />

                                                        <%
                                                                String filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

                                                                 URL fileURL = pageContext.getServletContext().getResource(
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
