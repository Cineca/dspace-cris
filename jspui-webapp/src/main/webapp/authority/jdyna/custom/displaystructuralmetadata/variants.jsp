<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="false" var="variantsvisibility"></c:set>
					<c:forEach items="${researcher.variants}"
						var="variantvisibilityitem">
						<c:if test="${variantvisibilityitem.visibility==1}">
							<c:set value="true" var="variantsvisibility"></c:set>
						</c:if>
					</c:forEach>
					<c:if
						test="${!empty researcher.variants && variantsvisibility==true}">
						<tr>
							<td colspan="2"><em><strong><fmt:message
								key="jsp.layout.hku.detail.researcher.variants" /></strong></em></td>
						</tr>
						<tr>
							<td colspan="2">
							<ul>
								<c:forEach items="${researcher.variants}" var="variant">
									<c:if test="${variant.visibility==1}">
										<li>${variant.value}</li>
									</c:if>
								</c:forEach>
							</ul>
							</td>
						</tr>
					</c:if>