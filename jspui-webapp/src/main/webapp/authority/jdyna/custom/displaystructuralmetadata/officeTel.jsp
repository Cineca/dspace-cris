<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

					<tr>
						<c:if
							test="${researcher.officeTel.visibility==1 && !empty researcher.officeTel.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.officetel" /></td>
							<td class="ncBody" nowrap="nowrap">${researcher.officeTel.value}</td>
						</c:if>
					</tr>