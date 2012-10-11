<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					<tr>
						<td>
						<c:if
							test="${researcher.chineseName.visibility==1 && !empty researcher.chineseName.value}">
							<span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.chinesename" /></span>
							<div class="dynaFieldValue">${researcher.chineseName.value}</div>
						</c:if>
						</td>
					</tr>
					
					
			