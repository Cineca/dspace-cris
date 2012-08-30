<c:set var="statType" >top</c:set>
<h2>${markerasnumber} <span class="titlestats"><fmt:message key="view.${data.jspKey}.${statType}.${objectName}.page.title" /></span></h2>
<%@ taglib uri="statstags" prefix="stats" %>
<c:choose>
	<c:when test="${data.resultBean.dataBeans[statType][objectName]['total'].dataTable[0][0] > 0}">
	
		<c:set var="drillDownInfo" >drillDown-${pieType}-${objectName}</c:set>
		
		<c:set var="pieType" >continent</c:set>
		<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useFmt="true" />
		
		<c:set var="pieType" >countryCode</c:set>
		<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useFmt="true" /> 
		
		<c:set var="pieType" >city</c:set>
		<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" />
		 
		<c:set var="pieType" >id</c:set>
		<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useLocalMap="true" />   		
		
		<%@include file="time.jsp"%> 
		
		<%@include file="../modules/map/map.jsp" %> 
	</c:when>
	<c:otherwise> 
		<fmt:message key="view.${data.jspKey}.${statType}.${objectName}.data.empty" />
	</c:otherwise>
</c:choose>

