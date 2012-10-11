<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<tr>
	<td><c:if
			test="${researcher.academicName.visibility==1 && !empty researcher.academicName.value}">
			<span class="dynaLabel"><fmt:message
					key="jsp.layout.hku.primarydata.label.academicname" />
			</span>
			<div class="dynaFieldValue">${researcher.academicName.value}</div>
		</c:if></td>
</tr>