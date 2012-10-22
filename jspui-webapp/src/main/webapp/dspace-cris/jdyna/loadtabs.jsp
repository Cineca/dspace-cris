<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
<c:when test="${showtab}">
<script type="text/javascript">	
	j('#cris-tabs-navigation-${areashortName}').show();	
</script>
<a href='<%=request.getContextPath()%>/cris/${specificPath}/${authority}/${areashortName}.html'><img
		border="0"
		src="<%=request.getContextPath()%>/researchertabimage/${areaid}"
		alt="X"> ${areatitle}</a>
</c:when>
<c:otherwise>
<script type="text/javascript">
	jQuery("#tb-head2-${areashortName}").hide();
	jQuery('#cris-tabs-navigation-${areashortName}').hide();
</script>
</c:otherwise>
</c:choose>
