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
<script type="text/javascript">

	function addCoInvestigators() {
		 var ni = document.getElementById('myDiv');
		 var numi = document.getElementById('theValue');
		 var num = (document.getElementById("theValue").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tabledatafields')).getElementsByTagName("tbody")[0];
		 		 
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 
		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "<span class=\"dynaLabel\">Co-Investigator #"+(num-1)+"</span>";
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"coInvestigators[" + (num - 1) + "]\" value=\"\">";
		  			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 
		 tbody.appendChild(row);
		 		 
	}
	
</script>
<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend>


<table width="98%" cellpadding="0"
		cellspacing="4">

	

	<tr>
		<td>
		<table id="tabledatafields" align="left" cellpadding="0" cellspacing="4">
			&nbsp;
		
		
		<tr>
			<td> <span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.code" /></span></td>
			<td><form:input path="rgCode" disabled="${!admin}" /></td>
			<td></td>
		</tr>
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.status" /></span></td>			
			<td><form:checkbox path="status" disabled="${!admin}"/></td>
			<td></td>
		</tr>
		<tr><td></td>		
		<td><span class="bodyText"><fmt:message key="jsp.layout.hku.primarydata.label.messageinvcoinv" /> </span></td>
		<td></td>
		</tr>		
		<tr>
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.investigator" /></span></td>			
			<td><form:input path="investigator" size="80%" disabled="${!admin}"/></td>
			<td></td>
		</tr>

		
	
		<c:forEach items="${grant.coInvestigators}" var="coi" varStatus="i">
				<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.coinvestigator" />#${i.index}:</span></td>
				<td><form:input path="coInvestigators[${i.index}]"
					size="80%" /></td>	
				<td></td>			
				</tr>
		</c:forEach>
		<tr>
			<td colspan="4"><div id="myDiv"><input type="hidden" id="theValue" value="${fn:length(grant.coInvestigators)}"/></div></td>
		</tr>

		&nbsp;
	
		<tr>
			<td><input id="addCoInv" name="addCoInv" type="button" onclick="addCoInvestigators()" value="Add Co-Investigator"/></td>
		</tr>
		



		
		
		&nbsp;
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
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorGrantPropertiesDefinition'}">
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
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorGrantPropertiesDefinition'}">

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
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorGrantPropertiesDefinition'}">


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