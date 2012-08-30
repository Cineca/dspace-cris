<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="researchertags" prefix="researcher"%>

<c:set var="contextPath" scope="application">${pageContext.request.contextPath}</c:set>
<c:set var="dspace.layout.head" scope="request">
    <script type="text/javascript" src="${contextPath}/js/jquery-1.4.2.min.js"></script>
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
</c:set>
<tr><td>

<dspace:layout style="hku" titlekey="jsp.statistics.item-title">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>

<td width="200" valign="top" align="left">
	<%@ include file="/layout/navbar-hku.jsp" %>
</td>
<td>&nbsp;</td>
<td width="700" valign="top" align="left">
	<%@ include file="/authority/stats/collection/_collectionReport.jsp" %>
</td>

</tr>
</table>

</dspace:layout>
