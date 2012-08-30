<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tr>
<td>
<c:if
							test="${researcher.bio.visibility==1 && !empty researcher.bio.value}">
							<a target="_blank" href="${researcher.bio.value}"><fmt:message
								key="jsp.layout.hku.detail.researcher.biography" /> </a>
						</c:if></td>
					</tr>
