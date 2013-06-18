<%--
The contents of this file are subject to the license and copyright
 detailed in the LICENSE and NOTICE files at the root of the source
 tree and available online at
 
 https://github.com/CILEA/dspace-cris/wiki/License
--%>

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

<c:set var="disabledfield" value=" readonly=\"readonly\" "></c:set>

<script type="text/javascript"><!--

		var j = jQuery.noConflict();
    	
		j(document).ready(function()
		{
			activePointer();
		});

		
		function updateSelectedPointer( id, count, repeatable, displayvalue, identifiervalue ) {
			if(identifiervalue!=null) {
            	if (!repeatable){
            		j("#pointer_"+id+"_selected").html(' ');
            		count = 0;
            	}
				var div = j('<div id="pointer_'+id+'_selected_'+count+'" class="jdyna-pointer-value">');
            	var img = j('<img class="jdyna-icon jdyna-action-icon jdyna-delete-button" src="<%= request.getContextPath() %>/image/jdyna/delete_icon.gif">');
				var path = j('#pointer_'+id+'_path').html();
				var input = j( "<input type='hidden' id='"+path+"["+count+"]"+"' name='"+path+"["+count+"]"+"'>" ).val(identifiervalue);
            	var display = j("<span>").text(displayvalue);
            	var selectedDiv = j("#pointer_"+id+"_selected");
            	selectedDiv.append(div);
            	div.append(input);
            	div.append(display);
            	div.append("&nbsp;")
            	div.append(img);
            	div.effect('highlight');
            	j('#pointer_'+id+'_tot').html(count+1);
            	img.click(function(){
                	if (!repeatable){
                		selectedDiv.html(' ');
                		var _input = j( "<input type='hidden' id='_"+path+"[0]"+"' name='_"+path+"[0]"+"'>" ).val('true');
                		selectedDiv.append(_input);
                	}
                	else
                	{
                		j('#pointer_'+id+'_selected_'+count).remove();
                	}
            	});
            	if (!repeatable){
            		var _input = j( "<input type='hidden' id='_"+path+"[0]"+"' name='_"+path+"[0]"+"'>" ).val('true');
            		selectedDiv.append(_input);
            	}            	
			}
        }
		
		var activePointer = function() {
					 			
			 j(".pointerinfo").each(function(){
				 var id = j(this).html();
				 j('#pointer_'+id+'_selected div img').click(
						 function(){
					j(this).parent().remove();		 
				 });
				 var repeatable = j('#pointer_'+id+'_repeatable').html() == 'true';
				 j("#searchboxpointer_"+id).autocomplete({
					delay: 500,
		            source: function( request, response ) {	
		                j.ajax({
		                    url: "searchPointer.htm",
		                    dataType: "json", 
		                    data : {																			
								"elementID" : id,								
								"query":  request.term						
							},                  
		                    success: function( data ) {
		                        response( j.map( data.pointers, function( item ) {
		                            return {
		                                label: item.display,
		                                value: item.id
		                            }
		                        }));
		                    }
		                });
		            },		            
		            minLength: 2,
		            select: function( event, ui ) {
		            	if (ui == null || ui.item == null) return false;
		            	updateSelectedPointer( id, j('#pointer_'+id+'_tot').html(), repeatable, 
		                		ui.item.label, ui.item.value);
		            	j('#searchboxpointer_'+id).val('');
		            	return false;
		            },
		            open: function() {
		                j( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		            },
		            close: function() {
		                j( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		            }
		        });
		});

	}
		
       
		-->
	</script>
<form:form commandName="nesteddto" id="nested_edit_form"
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
							<c:set var="disabled" value=" readonly='readonly'"/>						
						
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
							
							<c:set var="classdiv" value=""/>
							<c:if test="${!show}">
								<c:set var="classdiv" value=" display:none;"/>
							</c:if>
							
							<div style="${classdiv}">		
								<dyna:edit tipologia="${tipologiaDaVisualizzare}" disabled="${disabled}"
									propertyPath="nesteddto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties" hideLabel="false"
									validationParams="${parameters}" visibility="${visibility}"/>
							</div>
			
				</c:forEach>
		<div class="dynaClear">&nbsp;</div>
		<div class="jdyna-form-button">		
			<input type="submit" value="<fmt:message key="jsp.layout.hku.researcher.button.save"/>" />
		</div>
</form:form>
