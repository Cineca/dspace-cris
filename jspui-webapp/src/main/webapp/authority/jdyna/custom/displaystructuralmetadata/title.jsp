<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

						<span class="header5"> <c:forEach
							items="${researcher.title}" var="title">
							<c:if test="${title.visibility==1}">
								${title.value}<br />
							</c:if>
						</c:forEach> </span>