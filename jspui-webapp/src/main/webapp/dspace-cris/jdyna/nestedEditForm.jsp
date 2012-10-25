<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>	
<%@page import="it.cilea.osd.jdyna.model.AccessLevelConstants"%>
<%@page
	import="it.cilea.osd.jdyna.model.ANestedPropertiesDefinition"%>

<%@ taglib uri="jdynatags" prefix="dyna"%>

<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>

	
<c:set var="commandObject" value="${nesteddto}" scope="request" />

<c:set var="simpleNameAnagraficaObject"
	value="${simpleNameAnagraficaObject}" scope="page" />

<c:set var="disabledfield" value=" disabled=\"disabled\" "></c:set>


<div id="researcher_nested${elementID}">
<form:form commandName="nesteddto" id="nesteddto_${elementID}"
	action="" method="post" enctype="multipart/form-data">
	<%-- if you need to display all errors (both global and all field errors,
		 use wildcard (*) in place of the property name --%>
	<spring:bind path="nesteddto.*">
		<c:if test="${!empty status.errorMessages}">
			<div id="errorMessages">
		</c:if>
		<c:forEach items="${status.errorMessages}" var="error">
			<span class="errorMessage"><fmt:message
				key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
			<br />
		</c:forEach>
		<c:if test="${!empty status.errorMessages}">
			<div id="errorMessages">
		</c:if>
	</spring:bind>


	<dyna:hidden propertyPath="nesteddto.objectId" />
	<dyna:hidden propertyPath="nesteddto.tipologiaId" />
	<dyna:hidden propertyPath="nesteddto.parentId" />
	
	
				
					<c:forEach
							items="${maschera}"
							var="tipologiaDaVisualizzare">
							
						
							<c:set var="show" value="true" />
							<c:choose>							
							<c:when
								test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
								<c:set var="disabled" value="" />
								<c:set var="visibility" value="true" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="false" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="true" />
							</c:when>							
							<c:otherwise>
								<c:set var="show" value="false" />
							</c:otherwise>
							</c:choose>	

		
								<%
								List<String> parameters = new ArrayList<String>();
												parameters.add(pageContext.getAttribute(
														"simpleNameAnagraficaObject").toString());
												parameters
														.add(((ANestedPropertiesDefinition) pageContext
																.getAttribute("tipologiaDaVisualizzare"))
																.getShortName());
												pageContext.setAttribute("parameters", parameters);
								%>
									
								<dyna:edit tipologia="${tipologiaDaVisualizzare}" disabled="${disabled}"
									propertyPath="nesteddto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties" hideLabel="false"
									validationParams="${parameters}" visibility="${visibility}"/>
		
			
				</c:forEach>
		<c:if test="${show}">		
			<input type="submit" id="ajaxsubmit"
					value="<fmt:message key="jsp.layout.hku.researcher.button.save"/>" />
		</c:if>
							
	<script type="text/javascript">									
	j(document).ready(function() { 
	    var options = { 
	        target:        '#viewnested_${elementID}',   // target element(s) to be updated with server response 
	        success:       showResponse // post-submit callback
	        
	        // other available options: 
	        //url:       url         // override for form's 'action' attribute 
	        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
	        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
	        //clearForm: true        // clear all form fields after successful submit 
	        //resetForm: true        // reset the form after successful submit 
	 
	        // $.ajax options can be used here too, for example: 
	        //timeout:   3000 
	    }; 
	 
	    // bind form using 'ajaxForm' 
	    j('#nesteddto_${elementID}').ajaxForm(options); 
	}); 
	 
	

	</script>										

	
</form:form>
</div>
