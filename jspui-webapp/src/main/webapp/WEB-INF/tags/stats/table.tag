<%@ attribute name="data" required="true" type="java.lang.Object"%>
<%@ attribute name="statType" required="true"%>
<%@ attribute name="objectName" required="true"%>
<%@ attribute name="pieType" required="true"%>
<%@ attribute name="useLocalMap" required="false" %>
<%@ attribute name="useFmt" required="false" %>
<%@ taglib uri="statstags" prefix="stats" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<table class="statsTable" border="1">
			<tbody>
				<tr class="evenRowOddCol">
					<th><fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.title" /></th>
					<th><fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.value" /></th>
				</tr> 
				<c:forEach items="${data.resultBean.dataBeans[statType][objectName][pieType].limitedDataTable}" var="row" varStatus="status">
				<c:set var="id" scope="page">${statType}_${objectName}_${pieType}_${status.count}</c:set>
					<tr class="evenRowOddCol">
						<td>
						<%-- <a href="javascript:drillDown('<c:out value="${row.label}"/>',${itemId},'drillDown-${pieType}', 'drillDownDiv','${data.jspKey}');">--%>	
								<c:choose>
								<c:when test="${useLocalMap}">
									<c:choose>
										<c:when test="${!empty data.label[objectName][row.label].handle}">
											<a href="<%= request.getContextPath() %>/handle/${data.label[objectName][row.label].handle}">ID: ${row.label} - ${data.label[objectName][row.label].name}</a>
										</c:when>
										<c:otherwise>
											ID: ${row.label} - ${data.label[objectName][row.label].name}
										</c:otherwise>
									</c:choose>
									
								</c:when>
								<c:when test="${useFmt}">
									<c:choose>
										<c:when test="${row.label eq 'Unknown'}">
											<fmt:message key="statistics.table.value.${pieType}.${row.label}" />
										</c:when>
										<c:otherwise>
											${row.label} - <fmt:message key="statistics.table.value.${pieType}.${row.label}" />
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${row.label eq 'Unknown'}">
											<fmt:message key="statistics.table.value.${pieType}.${row.label}" />
										</c:when>
										<c:otherwise>
											<c:out value="${row.label}"/>
										</c:otherwise>
									</c:choose>
									
								</c:otherwise>
								</c:choose>
						<%-- 	</a>--%>
						</td>
						<td>
							<c:out value="${row.value}"/>
						</td>
					</tr>
				</c:forEach>									
			</tbody>
		</table>
	