<%--
The contents of this file are subject to the license and copyright
 detailed in the LICENSE and NOTICE files at the root of the source
 tree and available online at
 
 https://github.com/CILEA/dspace-cris/wiki/License
--%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ page import="org.dspace.app.webui.util.UIUtil" %>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<c:set var="root" scope="request"><%=request.getContextPath()%></c:set>
<c:set var="entity" value="${ou}" scope="request" />
<c:choose>
<c:when test="${param.onlytab}">
<c:forEach items="${tabList}" var="areaIter" varStatus="rowCounter">
	<c:if test="${areaIter.id == tabId}">
	<c:set var="area" scope="request" value="${areaIter}"></c:set>
	<c:set var="currTabIdx" scope="request" value="${rowCounter.count}" />
	<jsp:include page="singleTabDetailsPage.jsp"></jsp:include>
	</c:if>
</c:forEach>
</c:when>
<c:otherwise>
<c:forEach items="${tabList}" var="areaIter" varStatus="rowCounter">
	<c:if test="${areaIter.id == tabId}">
	<c:set var="currTabIdx" scope="request" value="${rowCounter.count}" />
	</c:if>
</c:forEach>
<%
	// Is the logged in user an admin
	Boolean admin = (Boolean)request.getAttribute("is.admin");
	boolean isAdmin = (admin == null ? false : admin.booleanValue());
    // Get the current page, minus query string
    String currentPage = UIUtil.getOriginalURL(request);
    int c = currentPage.indexOf( '?' );
    if( c > -1 )
    {
        currentPage = currentPage.substring( 0, c );
    }

%>
<c:set var="admin" scope="request"><%= isAdmin %></c:set>

<c:set var="dspace.layout.head.last" scope="request">
    <script type="text/javascript"><!--

		var j = jQuery.noConflict();    	
    	var ajaxurlnavigation = "<%=request.getContextPath()%>/cris/${specificPartPath}/navigation.json";
    	
 
    	var activeTab = function(){
    		j(".box:not(.expanded)").accordion({
    			autoHeight: false,
    			navigation: true,
    			collapsible: true,
    			active: false
    		});
    		j(".box.expanded").accordion({
    			autoHeight: false,
    			navigation: true,
    			collapsible: true,
    			active: 0
    		});
    		
    		var ajaxurlrelations = "<%=request.getContextPath()%>/cris/${specificPartPath}/viewNested.htm";
			j('.nestedinfo').each(function(){
				var id = j(this).html();
				j.ajax( {
					url : ajaxurlrelations,
					data : {																			
						"parentID" : ${entity.id},
						"typeNestedID" : id,
						"pageCurrent": j('#nested_'+id+"_pageCurrent").html(),
						"limit": j('#nested_'+id+"_limit").html(),
						"editmode": j('#nested_'+id+"_editmode").html(),
						"totalHit": j('#nested_'+id+"_totalHit").html(),
						"admin": ${admin}
					},
					success : function(data) {																										
						j('#viewnested_'+id).html(data);
						var ajaxFunction = function(page){
							j.ajax( {
								url : ajaxurlrelations,
								data : {																			
									"parentID" : ${entity.id},
									"typeNestedID" : id,													
									"pageCurrent": page,
									"limit": j('#nested_'+id+"_limit").html(),
									"editmode": j('#nested_'+id+"_editmode").html(),
									"totalHit": j('#nested_'+id+"_totalHit").html(),
									"admin": ${admin}
								},
								success : function(data) {									
									j('#viewnested_'+id).html(data);
									postfunction();
								},
								error : function(data) {
								}
							});		
						};
						var postfunction = function(){
							j('#nested_'+id+'_next').click(
									function() {
								    	ajaxFunction(parseInt(j('#nested_'+id+"_pageCurrent").html())+1);
										
							});
							j('#nested_'+id+'_prev').click(
									function() {
										ajaxFunction(parseInt(j('#nested_'+id+"_pageCurrent").html())-1);
							});
							j('.nested_'+id+'_nextprev').click(
									function(){
										ajaxFunction(j(this).attr('id').substr(('nested_'+id+'_nextprev_').length));
							});
						};
						postfunction();
					},
					error : function(data) {
					}
				});
			});
    	};
    	
		j(document).ready(function()
				{
		j("#log3").dialog({closeOnEscape: true, modal: true, autoOpen: false, resizable: false, open: function(event, ui) { j(".ui-dialog-titlebar").hide();}});
			
			j("#tabs").tabs({
				cache: true,
				selected: ${currTabIdx-1},
				load: function(event, ui){
					activeTab();
					}							
			});
			
			j('.navigation-tabs:not(.expanded)').accordion({
				collapsible: true,
				active: false,
				event: "click mouseover"
			});
			j('.navigation-tabs.expanded').accordion({
				collapsible: true,
				active: 0,
				event: "click mouseover"
			});
			j.ajax( {
				url : ajaxurlnavigation,
				data : {																			
					"objectId": ${entity.id}
				},
				success : function(data) {
					for (var i = 0; i < data.navigation.size(); i++)
					{
						if (data.navigation[i].boxes == null || data.navigation[i].boxes.size() == 0)
						{
							j('#bar-tab-'+data.navigation[i].id).remove();
							j('#cris-tabs-navigation-'+data.navigation[i].id).remove();
						}
						else
						{
							j('#bar-tab-'+data.navigation[i].id+' a img').attr('src','<%=request.getContextPath()%>/cris/researchertabimage/'+data.navigation[i].id);
							var img = j('#bar-tab-'+data.navigation[i].id+' a img');
							j('#bar-tab-'+data.navigation[i].id+' a').html(data.navigation[i].title);
							j('#bar-tab-'+data.navigation[i].id+' a').prepend(img);
							img.after('&nbsp;');
							j('#cris-tabs-navigation-'+data.navigation[i].id+' h3 a img').attr('src','<%=request.getContextPath()%>/cris/researchertabimage/'+data.navigation[i].id);
							j('#cris-tabs-navigation-'+data.navigation[i].id+'-ul').html('');
							for (var k = 0; k < data.navigation[i].boxes.size(); k++)
							{	
								j('#cris-tabs-navigation-'+data.navigation[i].id+"-ul")
									.append('<li class="ui-accordion ui-widget-content ui-state-default"><a href="${root}/cris/${specificPartPath}/${authority}/'
											+data.navigation[i].shortName+'.html?open='+data.navigation[i].boxes[k].shortName+'">'+data.navigation[i].boxes[k].title+'</a></li>');
							}
							j('.navigation-tabs').accordion("resize");							
						}
					}
				},
				error : function(data) {
					//nothing				
				}
			});
			
			activeTab();
		});
		-->
	</script>
    
</c:set>

<dspace:layout titlekey="jsp.ou.details">

<div id="content">
	<div id="cris-tabs-navigation">
	<div class="internalmenu ui-helper-reset ui-widget ui-corner-all ui-widget-content">
	<h2><fmt:message key="jsp.cris.detail.navigation-menu-heading" /></h2>
		<c:forEach items="${tabList}" var="tabfornavigation" varStatus="rowCounter">
			<div id="cris-tabs-navigation-${tabfornavigation.id}" class="navigation-tabs <c:if test="${tabfornavigation.id == tabId}">expanded</c:if>">
			<h3><a href="${tablink}"><img style="width: 16px;vertical-align: middle;" border="0"
					src="<%=request.getContextPath()%>/image/jdyna/indicator.gif"
  						alt="icon" />${tabfornavigation.title}</a></h3>
			<ul id="cris-tabs-navigation-${tabfornavigation.id}-ul">
					<li><img
							src="<%=request.getContextPath()%>/image/jdyna/indicator.gif"
		    				class="loader" />Loading</li>
			</ul>
			</div>
		</c:forEach>
		
				<%
    if (isAdmin) {
%><hr/>
	<fmt:message key="jsp.cris.detail.info.sourceid.none" var="i18nnone" />
	<div class="cris-record-info">
		<span class="cris-record-info-sourceid"><b><fmt:message key="jsp.cris.detail.info.sourceid" /></b> ${!empty entity.sourceID?entity.sourceID:i18nnone}</span>
		<span class="cris-record-info-created"><b><fmt:message key="jsp.cris.detail.info.created" /></b> ${entity.timeStampInfo.timestampCreated.timestamp}</span>
		<span class="cris-record-info-updated"><b><fmt:message key="jsp.cris.detail.info.updated" /></b> ${entity.timeStampInfo.timestampLastModified.timestamp}</span>
	</div>
<%
    }
%>	

		</div>
	 </div>
<h1><fmt:message key="jsp.layout.ou.detail.title-first" /> ${entity.name}</h1>
<div>&nbsp;</div>
	<c:if test="${!entity.status}">
		<div class="warning">
			<fmt:message key="jsp.layout.hku.detail.ou-disabled" />
			<a target="_blank"
				href="<%= request.getContextPath() %>/cris/tools/ou/editDynamicData.htm?id=${entity.id}&anagraficaId=${entity.dynamicField.id}<c:if test='${!empty tabIdForRedirect}'>&tabId=${tabIdForRedirect}</c:if>">
				<fmt:message key="jsp.layout.hku.detail.ou-disabled.fixit" />
			</a>
		</div>
	</c:if>
						
		<div id="researcher">
			
			<c:if test="${ou_page_menu && !empty ou}"> 		
				<c:if test="${!empty addModeType && addModeType=='display'}">
      			
      				<a class="cris-edit-anchor" href="<%= request.getContextPath() %>/cris/tools/ou/editDynamicData.htm?id=${entity.id}&anagraficaId=${entity.dynamicField.id}<c:if test='${!empty tabIdForRedirect}'>&tabId=${tabIdForRedirect}</c:if>"><fmt:message key="jsp.layout.navbar-hku.staff-mode.edit.ou"/></a>
      			
  				</c:if>
 			</c:if> 			
			<jsp:include page="commonDetailsPage.jsp"></jsp:include>
		</div>

</div>
</dspace:layout>
</c:otherwise>
</c:choose>
