<c:set value="${researcher}" var="researcher" scope="request" />

	<h1>${researcher.fullName}</h1>

	<div>&nbsp;</div>
	<div>&nbsp;</div>

	<form:form commandName="dto" method="post">
		<%--  first bind on the object itself to display global errors - if available  --%>
		<spring:bind path="dto">
			<c:forEach items="${status.errorMessages}" var="error">
						    <span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>

		<table width="98%" align="left" cellpadding="0"
			cellspacing="4">
			<colgroup>
				<col width="20%" />
				<col width="70%" />
				<col width="10" />
			</colgroup>
			<tr>
			
				<th><fmt:message key="jsp.layout.hku.additional.captionlabel.label" /></th>
				<th><fmt:message key="jsp.layout.hku.additional.captionlabel.value" /></th>
				<th><fmt:message key="jsp.layout.hku.additional.captionlabel.visibility" /></th>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>												
			<c:forEach items="${dto.labels}" var="key"
				varStatus="i">
				<tr>													
					<td>														
						${key}
					</td>
					<td>								
						
						<form:input path="additionalFields['${key}'].field.value" size="70%"/>
																																									
					</td>
					<td>														
						
						<form:checkbox path="additionalFields['${key}'].field.visibility" value="1"/>
																											
					</td>
				</tr>
			</c:forEach>																					
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit"
					value="<fmt:message	key="jsp.layout.hku.researcher.button.save" />" /></td>
			</tr>
		</table>

	</form:form>