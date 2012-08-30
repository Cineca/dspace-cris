
<%
boolean disabled = false;
if(!isAdmin) { %>
	<c:set var="disabled" value="true"/>	
<%}%>

<c:set value="${researcher}" var="researcher" scope="request" />

	<h1>${researcher.fullName}</h1>

	<div>&nbsp;</div>
	<div>&nbsp;</div>
	
	<form:form commandName="researcher" method="post">
		<%--  first bind on the object itself to display global errors - if available  --%>
		<spring:bind path="researcher">
			<c:forEach items="${status.errorMessages}" var="error">
				<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>


		<fieldset><legend>		
		<fmt:message key="jsp.layout.hku.detail.fieldset-legend.isi" />
		</legend>
		<table width="98%" align="left" cellpadding="0" cellspacing="4">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.isirid" /></td>
				<td><form:input path="ridISI.value" size="80%"  disabled="${disabled}"/></td>
				<td><form:checkbox path="ridISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.linkisirid" /></td>
				<td><form:input path="ridLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="ridLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.papercountisi" /></td>
				<td><form:input path="paperCountISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperCountISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.paperlinkisi" /></td>
				<td><form:input path="paperLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.citationcountisi" /></td>
				<td><form:input path="citationCountISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationCountISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.citationlinkisi" /></td>
				<td><form:input path="citationLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorsisi" /></td>
				<td><form:input path="coAuthorsISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.hindexisi" /></td>
				<td><form:input path="hindexISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="hindexISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>									
			
			</table>
		</fieldset>
		<br/>
		<br/>
		<fieldset><legend>		
			<fmt:message key="jsp.layout.hku.detail.fieldset-legend.scopus" />
		</legend>
		<table width="98%" align="left" cellpadding="0" cellspacing="4">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.idauthorscopus" /></td>
				<td><form:input path="authorIdScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="authorIdScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.idauthorlinkscopus" /></td>
				<td><form:input path="authorIdLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="authorIdLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.papercountscopus" /></td>
				<td><form:input path="paperCountScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperCountScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.paperlinkscopus" /></td>
				<td><form:input path="paperLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.citationcountscopus" /></td>
				<td><form:input path="citationCountScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationCountScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.citationlinkscopus" /></td>
				<td><form:input path="citationLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>	
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorscopus" /></td>
				<td><form:input path="coAuthorsScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorlinkscopus" /></td>
				<td><form:input path="coAuthorsLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="jsp.layout.hku.scopuswos.label.hindexscopus" /></td>
				<td><form:input path="hindexScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="hindexScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>									
			
		</table>
		</fieldset>
		<br/>
		<input type="submit"
					value="<fmt:message key="jsp.layout.hku.researcher.button.save" />" />
	</form:form>