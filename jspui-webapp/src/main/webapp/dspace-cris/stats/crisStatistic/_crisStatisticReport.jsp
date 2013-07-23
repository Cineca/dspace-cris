<div id="content-stats">

<c:choose>
<c:when test="${empty type or type == 'null' or type eq 'selectedObject'}">
	<c:set value="(From Dec 2010)" var="condition"/>
	<%@include file="../common/basicReport.jsp"%>
</c:when>
<c:otherwise>

	<c:set var="objectName" scope="page">${data.relationType}</c:set>	
	<%@include file="../common/topObject.jsp"%>	
	
</c:otherwise>
</c:choose>
</div>