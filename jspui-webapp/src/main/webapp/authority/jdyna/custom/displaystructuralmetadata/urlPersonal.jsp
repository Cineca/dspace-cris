<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tr>
<td>
							<c:if
							test="${researcher.urlPersonal.visibility==1 && !empty researcher.urlPersonal.value}">
							<a target="_blank" href="${researcher.urlPersonal.value}" style="text-decoration: underline;">
								<fmt:message key="jsp.layout.hku.detail.researcher.personalwebpage" />
							</a>
							</c:if></td>
					</tr>