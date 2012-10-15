<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					<tr>
						<c:if
							test="${researcher.email.visibility==1 && !empty researcher.email.value}">
							<td class="dynaLabel"><fmt:message
								key="jsp.layout.hku.detail.researcher.email" /></td>
							<td class="dynaFieldValue"><a
								href="mailto:${researcher.email.value}">${researcher.email.value}</a>
							</td>
						</c:if>
					</tr>