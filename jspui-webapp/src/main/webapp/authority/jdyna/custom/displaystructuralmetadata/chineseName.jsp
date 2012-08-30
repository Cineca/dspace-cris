<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
					<tr>
						<td>
						<c:if
							test="${researcher.chineseName.visibility==1}">
							<span class="header4">${researcher.chineseName.value}</span>
						</c:if>
						</td>
					</tr>