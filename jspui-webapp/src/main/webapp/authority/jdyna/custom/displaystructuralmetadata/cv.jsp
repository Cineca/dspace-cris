<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

					<c:if test="${(!empty researcher.cv.value || !empty researcher.cv.remoteUrl) 
						&& researcher.cv.visibility==1}">
					<tr>
						<td colspan="2" align="right">
						<img alt="mark icon" src="<%=request.getContextPath()%>/image/wd2h.gif" />
						<a class="cv" href="<%=request.getContextPath()%>/researchercv/${authority}">
						<fmt:message
								key="jsp.layout.hku.detail.researcher.cv" /></a>
						<img alt="mark icon" src="<%=request.getContextPath()%>/image/wd2g.gif" /></td>
					</tr>
					</c:if>