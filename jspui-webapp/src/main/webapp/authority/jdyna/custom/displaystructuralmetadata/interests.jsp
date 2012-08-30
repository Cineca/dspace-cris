<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

					<c:set value="false" var="interestsvisibility"></c:set>
					<c:forEach items="${researcher.interests}"
						var="interestvisibilityitem">
						<c:if test="${interestvisibilityitem.visibility==1}">
							<c:set value="true" var="interestsvisibility"></c:set>
						</c:if>
					</c:forEach>
					<c:if
						test="${!empty researcher.interests && interestsvisibility==true}">
						<tr>
							<td colspan="2" class="columnHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.interests" /></td>

						</tr>
						<tr>
							<td colspan="2" valign="top">
							<ul>
								<c:forEach items="${researcher.interests}" var="interest">
									<c:if test="${interest.visibility==1}">
										<li>${interest.value}</li>
									</c:if>
								</c:forEach>
							</ul>
							</td>
						</tr>
					</c:if>