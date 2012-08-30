<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
					<tr>
						<c:if
							test="${researcher.email.visibility==1 && !empty researcher.email.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.email" /></td>
							<td class="ncBody"><a
								href="mailto:${researcher.email.value}">${researcher.email.value}</a>
							</td>
						</c:if>
					</tr>