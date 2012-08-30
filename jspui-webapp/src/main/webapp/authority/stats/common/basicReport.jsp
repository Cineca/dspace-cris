		<!-- Target div for drill down -->
        <!-- div id="drillDownDiv" style="width: 800px; height: 400px; margin: 1 auto;position:relative;top:0; left:0; font-size:50px; z-index:2;border-style:solid;border-width:5px;"></div-->
        <c:set var="itemId" scope="page">${data.id}</c:set>
		<c:set var="statType">selectedObject</c:set>

		<h2>${markerasnumber} <span class="titlestats"><fmt:message key="view.${data.jspKey}.${statType}.page.title" /></span></h2>

	<%@ taglib uri="statstags" prefix="stats" %>
		<c:choose>
			<c:when test="${data.resultBean.dataBeans[statType]['time']['total'].dataTable[0][0] > 0}">
				<c:set var="objectName">geo</c:set>	
				<c:set var="pieType" >continent</c:set>
				<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useFmt="true" />
				<%--@include file="piewithtable.jsp"--%> 
				
				<c:set var="pieType" >countryCode</c:set>
				<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useFmt="true" />
				<%--@include file="piewithtable.jsp"--%> 
				
				<c:set var="pieType" >city</c:set>
				<stats:piewithtable data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" />
				<%--@include file="piewithtable.jsp"--%> 
				
				<c:set var="objectName">time</c:set>	
				<%@include file="time.jsp"%> 
		
				<c:set var="objectName">geo</c:set>	
				<%@include file="../modules/map/map.jsp" %>
				</c:when>
		<c:otherwise> 
			<fmt:message key="view.${data.jspKey}.${statType}.data.empty" />
		</c:otherwise>
</c:choose>
				 