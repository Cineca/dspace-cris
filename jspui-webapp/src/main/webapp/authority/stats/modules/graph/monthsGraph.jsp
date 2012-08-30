		<c:set var="targetDiv" scope="page">lastYearsDiv_${statType}_${objectName}_${pieType}</c:set>
		<c:set var="jsDataObjectName" scope="page">splineData_${statType}</c:set>
		<div id="${targetDiv}"></div>
		<script>
  	    		var ${jsDataObjectName} = [];
  	    		<c:set var="foundfirstNotZero" scope="page">false</c:set>
  	    		<c:set var="counter" scope="page">0</c:set>
  	    		 
				<c:forEach items="${data.resultBean.dataBeans[statType][objectName][pieType].dataTable}" var="row" varStatus="status">
					<c:if test="${row[1]!=null && row[1]!= ''}">
						<c:if test="${row[1]!=0 && foundfirstNotZero==false}">
							<c:set var="foundfirstNotZero" scope="page">true</c:set>
						</c:if>					
						<c:if test="${foundfirstNotZero==true}">
							${jsDataObjectName}[${counter}]=[parseISO8601('<c:out value="${row[0]}"/>').getTime(), <c:out value="${row[1]}"/>];
							<c:set var="counter" scope="page">${counter+1}</c:set>	
						</c:if>
					</c:if>
				</c:forEach>	
  	    		drawSpline('${targetDiv}',${jsDataObjectName},'<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.title" />','<fmt:message key="view.${data.jspKey}.data.${key1}.${key2}.${key3}.yAxisLabel" />','<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.legend" />');
  	    		</script>
<fmt:message
			key="view.${data.jspKey}.data.${statType}.${objectName}.allMonths.description" />