<%@ attribute name="data" required="true" type="java.lang.Object"%>
<%@ attribute name="statType" required="true"%>
<%@ attribute name="objectName" required="true"%>
<%@ attribute name="pieType" required="true"%>
<%@ attribute name="targetDiv" required="true"%>
<%@ taglib uri="statstags" prefix="stats" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="jsDataObjectName" scope="page"> data_${statType}_${objectName}_${pieType}_${pieType}</c:set>
<script>
	var ${jsDataObjectName} = new Array (${fn:length(data.resultBean.dataBeans[statType][objectName][pieType].limitedDataTable)});
	<c:forEach items="${data.resultBean.dataBeans[statType][objectName][pieType].limitedDataTable}" var="row" varStatus="status">
	${jsDataObjectName}[${status.count-1}]= ['<c:out value="${row.label}"/>',<c:out value="${row.percentage}"/>];
	</c:forEach>	
		if (${jsDataObjectName}.length>0){
	  			drawPie(${jsDataObjectName} ,${data.id},'<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.graph" />','${targetDiv}','${drillDownInfo}', 'drillDownDiv','${data.jspKey}${rpSwitchDrillDownMethod}');
		}else{
			setMessage('<fmt:message key="view.${data.jspKey}.${statType}.${objectName}.time.data.empty" />','${targetDiv}');
			}
</script>