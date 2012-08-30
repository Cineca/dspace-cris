<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
					<tr>
						<c:if
							test="${researcher.dept.visibility==1 && !empty researcher.dept.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.department" /></td>
							<td class="ncBody">${researcher.dept.value}</td>
						</c:if>
					</tr>
