<%@include file="../enclosures/jstlInclude.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<html dir="${dir}">
<head>
  <title><decorator:title default=""/></title>
	<meta http-equiv="Content-Language" content="${locale.language}">
	<meta http-equiv="Content-Type" content="text/html;UTF-8">
	
	<style type="text/css">
    .style1 {background-color:#ffffff;font-weight:bold;border:2px #006699 solid;}
    </style>
    
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
	<!-- highcharts JS -->
	<script type="text/javascript" src="${contextPath}/js/highcharts/highcharts.js"></script>
	<script type="text/javascript" src="${contextPath}/js/highcharts/modules/exporting.js"></script>
	<script type="text/javascript" src="${contextPath}/js/highchartsUtilsPie.js"></script>
	<script type="text/javascript" src="${contextPath}/js/highchartsUtilsSpline.js"></script>
		
		
	 
	<script type="text/javascript" src="${contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${contextPath}/dwr/interface/ItemDrillDown.js"></script>
	<script type="text/javascript" src="${contextPath}/dwr/interface/CollectionDrillDown.js"></script>
	<script type="text/javascript" src="${contextPath}/dwr/interface/CommunityDrillDown.js"></script>
	<script type="text/javascript" src="${contextPath}/dwr/interface/RpStatisticDrillDown.js"></script>
	
	
	
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	  html { height: 100% }
	  body { height: 100%; margin: 0px; padding: 0px }
	  #map_canvas { height: 100% }
	</style>
	<script type="text/javascript"
	    src="http://maps.google.com/maps/api/js?sensor=true">
	</script>	
	
	<script type="text/javascript">
		function setMessage(message,div){
			document.getElementById(div).innerHTML=message;
		}
		function setGenericEmpityDataMessage(div){
			document.getElementById(div).innerHTML='<fmt:message key="view.generic.data.empty" />';
		}
	</script>
	
</head>
<body>
	<center>
<table cellspacing="0" cellpadding="0" class="mainTable" width="100%">
	<tr>
		<td class="statisticRow">
		<center>
			<decorator:body/> 
		</center>
		</td>	
	</tr>	
</table>
			
<!-- 
		<table cellspacing="0" cellpadding="0" width="758px">
			<tr>
				<td colspan="2">
				</td>
			</tr>
 -->
		</table>		
	</center>
</body>
</html>