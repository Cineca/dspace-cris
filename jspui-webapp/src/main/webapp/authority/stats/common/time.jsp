<a name="${statType}.${objectName}.time">&nbsp;</a>
<table  width="98%" border="1">
	<tr>
		<th><!-- spacer cell --></th>		
		<th><fmt:message key="view.data.total" /></th>
		<th><fmt:message key="view.data.jan" /></th>
		<th><fmt:message key="view.data.feb" /></th>
		<th><fmt:message key="view.data.mar" /></th>
		<th><fmt:message key="view.data.apr" /></th>
		<th><fmt:message key="view.data.may" /></th>
		<th><fmt:message key="view.data.jun" /></th>
		<th><fmt:message key="view.data.jul" /></th>
		<th><fmt:message key="view.data.aug" /></th>
		<th><fmt:message key="view.data.sep" /></th>
		<th><fmt:message key="view.data.oct" /></th>
		<th><fmt:message key="view.data.nov" /></th>
		<th><fmt:message key="view.data.dec" /></th>
	</tr>
	<c:forEach var="row" items="${researcher:getAllMonthsStats(data.resultBean.dataBeans[statType][objectName]['allMonths'].dataTable)}">
	<tr class="evenRowOddCol">
		<td>${row.year}</td><td>${row.total}</td><td>${row.jan}</td><td>${row.feb}</td>	
		<td>${row.mar}</td><td>${row.apr}</td><td>${row.may}</td><td>${row.jun}</td>	
		<td>${row.jul}</td><td>${row.aug}</td><td>${row.sep}</td><td>${row.oct}</td>
		<td>${row.nov}</td><td>${row.dec}</td>																
	</tr>
	</c:forEach>
	<tr>
		<th scope="row" colspan="13" align="right"><fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.total.total" /></th>						
		<td>
			${data.resultBean.dataBeans[statType][objectName]['total'].dataTable[0][0]}
		</td>
	</tr>
	<tr>
		<th scope="row" colspan="13" align="right"><fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.total.lastyear" /></th>
		<td>
			${data.resultBean.dataBeans[statType][objectName]['lastYear'].dataTable[0][0]}
		</td>
	</tr>	
</table>

<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.total.description" />
<c:set var="pieType" scope="page">allMonths</c:set>						
<%@include file="../modules/graph/monthsGraph.jsp"%>