<div id="content-stats">
<c:choose>
<c:when test="${type ne 'bitstream'}">
	<c:set value="1" var="markerasnumber"/>	
	<%@include file="../common/basicReport.jsp" %>
</c:when>
<c:otherwise>
	<c:set value="2" var="markerasnumber"/>		
	<%@include file="../common/topBitStream.jsp" %>
</c:otherwise>
</c:choose>
</div>
