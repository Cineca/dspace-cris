<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${researcher.media}" var="media">
			<c:if test="${media.visibility==1}">
				<c:set value="true" var="boxmediaspokenship4"></c:set>
			</c:if>
		</c:forEach>
		
<c:if test="${!empty researcher.media && boxmediaspokenship4==true}">
					<tr>
						<td colspan="4" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.areaofexpertise" /></td>
					</tr>
					<tr>
						<td colspan="4" class="columnBody">
						<ul class="columnBody">
							<c:forEach items="${researcher.media}" var="media">
								<c:set var="msg" value="${media.value}" />
								<c:set var="msg1"
									value="${fn:replace(msg, newLineCharLinux, replaceNewLine)}" />
								<c:if test="${media.visibility==1}">
									<li><c:out
										value="${fn:replace(msg1, newLineChar, replaceNewLine)}"
										escapeXml="false" /></li>
								</c:if>
							</c:forEach>
						</ul>
						</td>
					</tr>
				</c:if>