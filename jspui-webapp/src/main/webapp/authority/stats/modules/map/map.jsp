<%--  http://www.beginningspatial.com/creating_proportional_symbol_maps_google_maps  --%>
<c:set var="pieType" >location</c:set>
<c:set var="targetDiv" scope="page" >div_${data.jspKey}_${statType}_${objectName}_${pieType}</c:set>
<a name="${statType}.${objectName}.${pieType}">&nbsp;</a>
<div id="${targetDiv}" style="width:600px; height: 300px"></div>
<fmt:message key="view.${data.jspKey}.data.${statType}.${objectName}.${pieType}.description" /> 

<script type="text/javascript">
<c:set var="jsDataObjectName" scope="page"> data_${statType}_${objectName}_${pieType}_${pieType}</c:set>
	var ${jsDataObjectName} = new Array (${fn:length(data.resultBean.dataBeans[statType][objectName][pieType].dataTable)});
	<c:forEach items="${data.resultBean.dataBeans[statType][objectName][pieType].dataTable}" var="row" varStatus="status">	
		${jsDataObjectName}[${status.count-1}]= ['<c:out value="${row.latitude}"/>','<c:out value="${row.longitude}"/>','<c:out value="${row.value}"/>',<c:out value="${row.percentage}"/>];
	</c:forEach>	
</script>	
	
<script type="text/javascript">

function initialize_${jsDataObjectName}() {
    var latlng_${jsDataObjectName} = new google.maps.LatLng(${jsDataObjectName}[0][0],${jsDataObjectName}[0][1]);
    var myOptions_${jsDataObjectName} = {
      zoom: 4,
      center: latlng_${jsDataObjectName},
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
	  var map = new google.maps.Map(document.getElementById("${targetDiv}"),myOptions_${jsDataObjectName});
	  	  for(var i = 0; i < ${jsDataObjectName}.length; i++) {
		  var myLatlng = new google.maps.LatLng(${jsDataObjectName}[i][0],${jsDataObjectName}[i][1]);
		  var marker = new google.maps.Marker({
		      position: myLatlng, 
		      map: map, 
		      title:${jsDataObjectName}[i][2]
		  });   
	  }

  }
	</script>

    <script type="text/javascript">
	if (${jsDataObjectName}.length>0){
	initialize_${jsDataObjectName}();
}else{
	setMessage('<fmt:message key="view.${data.jspKey}.${statType}.${objectName}.location.data.empty" />','${targetDiv}');
	}
	
	</script>
	

				
				
				
				