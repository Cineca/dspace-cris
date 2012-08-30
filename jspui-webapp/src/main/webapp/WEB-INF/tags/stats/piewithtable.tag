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
<c:set var="targetDiv" scope="page">div_${data.jspKey}_${statType}_${objectName}_${pieType}_${objectName}</c:set>

<!-- <div id="${targetDiv}"></div -->
<a name="${statType}.${objectName}.${pieType}">&nbsp;</a>
<c:choose>
	<c:when test="${fn:length(data.resultBean.dataBeans[statType][objectName][pieType].limitedDataTable)>0}">
<table>
<tr>
<td class="graphCell" id="${targetDiv}"></td>
<td class="dataCell"><stats:table data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" useLocalMap="${useLocalMap}" useFmt="${useFmt}" /></td>
</tr>
</table>
<%-- this is the javascript to build the pie --%>
<stats:pie data="${data}" statType="${statType}" objectName="${objectName}" pieType="${pieType}" targetDiv="${targetDiv}" />
 
<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.description" />
</c:when>	
	<c:otherwise>
		<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.empty" />
	</c:otherwise>
</c:choose>
<c:set var="useFmt">false</c:set>
<c:set var="useLocalMap">false</c:set>