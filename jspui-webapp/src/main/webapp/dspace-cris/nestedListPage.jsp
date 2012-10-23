<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="it.cilea.hku.authority.dspace.HKUAuthority"%>
<%@ page import="java.net.URL"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils"%>
<%@ page import="java.io.File"%>
<%@ page import="org.dspace.core.ConfigurationManager"%>
<%@ page import="org.dspace.browse.BrowseInfo"%>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<% 
	Integer offset = (Integer)request.getAttribute("offset");
	Integer limit = (Integer)request.getAttribute("limit");
	Integer totalHit = (Integer)request.getAttribute("totalHit");
	Integer hitPageSize = (Integer)request.getAttribute("hitPageSize");
	Integer pageCurrent = (Integer)request.getAttribute("pageCurrent");
%>
<div id="nestedDetailDiv_${decoratorPropertyDefinition.shortName}">
	<c:if test="${totalHit > 0 || editmode}">
	<c:set var="totalpage" scope="request">
	<c:choose>
			<c:when test="${totalHit<limit}">1</c:when>
			<c:otherwise><c:choose><c:when test="${totalHit%limit==0}"><fmt:formatNumber pattern="#">${totalHit/limit}</fmt:formatNumber></c:when><c:otherwise><fmt:formatNumber pattern="#"><%= Math.ceil(totalHit/limit) + 1%></fmt:formatNumber></c:otherwise></c:choose></c:otherwise>
	</c:choose>
	</c:set>
		
	
	<table>
	<tr>
	<td>	
	<fmt:message key="jsp.layout.form.search.navigation">
	<fmt:param>${pageCurrent+1}</fmt:param>	
	<fmt:param>${totalHit}</fmt:param>
	<fmt:param>
			${totalpage}
	</fmt:param>
	<fmt:param>${decoratorPropertyDefinition.label}</fmt:param>	
	</fmt:message>
	
	</td>
	</tr>
	</table>
	
	<c:if test="${totalpage>1}">
	<table>
	<tr>
	<c:if test="${pageCurrent>0}">
		<td>	
			<input type="button" class="nextprev_${decoratorPropertyDefinition.real.shortName}" value="Prev" id="prev_${pageCurrent-1}"/>	
		</td>
		
	<% for(int indexPrevious = (pageCurrent+1>5?pageCurrent-4:1); indexPrevious<pageCurrent+1; indexPrevious++) {%>
		<td>			
			<a href="#viewnested_${decoratorPropertyDefinition.shortName}" class="nextprev_${decoratorPropertyDefinition.real.shortName}" id="prev_<%= indexPrevious - 1 %>"><%= indexPrevious %></a>			
		</td>
	<% } %>			
	</c:if>			
	


	<% int i = 0;
	                                     
	   for(int indexNext = pageCurrent; (totalHit  - (limit * (indexNext))) > 0 && i<5; indexNext++,i++) {%>
			   
		<td>		
		<% if(pageCurrent==indexNext) { %>
		<b>
		<% } %>			
			<a href="#viewnested_${decoratorPropertyDefinition.shortName}" class="nextprev_${decoratorPropertyDefinition.real.shortName}" id="next_<%=indexNext %>"><%= indexNext + 1%></a>
		<% if(pageCurrent==indexNext) { %>
		</b>
		<% } %>		
		</td>
	<% } %>
	
	
	<c:if test="${(totalHit  - (limit * (pageCurrent+1))) > 0}">
		<td>
			<input type="button" class="nextprev_${decoratorPropertyDefinition.real.shortName}" value="Next" id="next_${pageCurrent+1}"/>
		</td>			
	</c:if>
	</tr>
	</table>	
	</c:if>
		
	<dyna:display-nested values="${results}" typeDefinition="${decoratorPropertyDefinition}" editmode="${editmode}" parentID="${parentID}" specificPartPath="${specificPartPath}"/>
	</c:if>	
	<c:if test="${(editmode && decoratorPropertyDefinition.repeatable) || (editmode && empty results)}">
		<img id="add${decoratorPropertyDefinition.shortName}" src="<%= request.getContextPath() %>/image/jdyna/main_plus.gif" class="addNestedButton"/>
	</c:if>	
	<c:if test="${decoratorPropertyDefinition.real.newline}">
		<div class="dynaClear">&nbsp;</div>
	</c:if>
	
								
	<script type="text/javascript">									
	
			
		j(".nextprev_${decoratorPropertyDefinition.real.shortName}")
				.click(
						function() {	
					    
						    j("#log3").dialog("open");																			
							var parameterId = this.id.substring(5, this.id.length);	
							var pagefrom = parseInt(parameterId) + 1;
							Loader.write("Loading ${decoratorPropertyDefinition.label}... Page "+ pagefrom  +" of ${totalpage}");
							var ajaxurlrelations = "<%= request.getContextPath() %>/cris/${specificPartPath}/viewNested.htm";
							j.ajax( {
								url : ajaxurlrelations,
								data : {																			
									"elementID" : "${decoratorPropertyDefinition.real.shortName}",
									"parentID" : ${parentID},
									"typeNestedID" : ${decoratorPropertyDefinition.real.id},													
									"pageCurrent": parameterId,
									"offset": ${offset},
									"limit": ${limit},
									"editmode": ${editmode}
								},
								success : function(data) {									
									j('#viewnested_${decoratorPropertyDefinition.shortName}').html(data);								
									j("#log3").dialog("close");
								},
								error : function(data) {
									
									Loader.write(data.statusText);
									
								}
						});
	
		});		
		j("#add${decoratorPropertyDefinition.shortName}")
		.click(
				function() {						
					j("#log3").dialog("open");									
					Loader.write("Loading form...");																	
																						
					var ajaxurlrelations = "<%= request.getContextPath() %>/cris/${specificPartPath}/addNested.htm";
					j.ajax( {
						url : ajaxurlrelations,
						data : {			
							
							"parentID" : ${parentID},
							"typeNestedID" : ${decoratorPropertyDefinition.real.id}
						},
						success : function(data) {																
							j('#nestedfragment_${decoratorPropertyDefinition.shortName}').dialog("open");		
							j(".ui-dialog-titlebar").html("${decoratorPropertyDefinition.label} &nbsp; <a class='ui-dialog-titlebar-close ui-corner-all' href='#' role='button'><span class='ui-icon ui-icon-closethick'>close</span></a>");j(".ui-dialog-titlebar").show();
							j('#nestedfragmentcontent_${decoratorPropertyDefinition.shortName}').html(data);
							j('#nestedfragment_${decoratorPropertyDefinition.shortName}').dialog('option', 'position', 'center');
							j("#log3").dialog("close");
						},
						error : function(data) {
																										
							Loader.write(data.statusText);
							
						}
					});

				});								

	</script>										

	
							
</div>