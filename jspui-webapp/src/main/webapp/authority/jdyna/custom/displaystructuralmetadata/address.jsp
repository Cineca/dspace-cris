<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

					<tr>
						<c:if
							test="${researcher.address.visibility==1 && !empty researcher.address.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.officeaddress" /></td>
							<td class="ncBody">${researcher.address.value}</td>
						</c:if>
					</tr>