<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<c:set var="root"><%= request.getContextPath() %></c:set>

	<table width="98%" cellpadding="0"
		cellspacing="4">

		<tr>
		<td>
			<table id="tabledatafields" align="left" cellpadding="0" cellspacing="4">
			&nbsp;
		

		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.office" /></td>
			<td><form:input path="address.value" size="80%" /></td>
			<td><form:checkbox path="address.visibility" value="1" /></td>
		</tr>
		
	
		
	</table>
</td>
</tr>
</table>

		<p></p>