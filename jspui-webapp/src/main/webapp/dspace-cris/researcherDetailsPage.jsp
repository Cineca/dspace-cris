<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="it.cilea.hku.authority.dspace.it.cilea.hku.authority.dspace.RPAuthority"%>
<%@ page import="java.net.URL"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils"%>
<%@ page import="java.io.File"%>
<%@ page import="org.dspace.core.ConfigurationManager"%>
<%@ page import="org.dspace.browse.BrowseInfo"%>
<%@ page import="org.dspace.app.webui.util.UIUtil" %>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<c:set var="root" scope="request"><%=request.getContextPath()%></c:set>
<c:set var="entity" value="${researcher}" scope="request" />
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
    String subscribe = request.getParameter("subscribe");
	boolean showSubMsg = false;
	boolean showUnSubMsg = false;
	if (subscribe != null && subscribe.equalsIgnoreCase("true"))
	{
	    showSubMsg = true;
	}
	if (subscribe != null && subscribe.equalsIgnoreCase("false"))
	{
	    showUnSubMsg = true;
	}
	
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
<c:set var="admin" scope="request"><%=isAdmin%></c:set>
<c:set var="dspace.cris.navbar" scope="request">


  <c:if test="${researcher_page_menu && !empty researcher}">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  
  <tr>
    <td nowrap="nowrap" colspan="2" class="navigationBarSublabel"><fmt:message key="jsp.layout.navbar-hku.staffmode.title"/></td>
  </tr>

  <c:if test="${!empty addModeType && addModeType=='display'}">
  <tr class="navigationBarItem">
    <td>
      <img alt="" src="<%=request.getContextPath()%>/image/<%=( currentPage.endsWith( "/editDynamicData" ) ? "arrow-highlight" : "arrow" )%>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%= request.getContextPath() %>/cris/tools/rp/editDynamicData.htm?id=${researcher.id}&anagraficaId=${researcher.dynamicField.id}<c:if test='${!empty tabIdForRedirect}'>&tabId=${tabIdForRedirect}</c:if>"><fmt:message key="jsp.layout.navbar-hku.staff-mode.edit.primary-data"/></a>
    </td>
  </tr>  
  </c:if>
   <tr class="navigationBarItem">
    <td>
      <img alt="" src="<%=request.getContextPath()%>/image/<%=( currentPage.endsWith( "/rebindItemsToRP" ) ? "arrow-highlight" : "arrow" )%>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%= request.getContextPath() %>/cris/tools/rp/rebindItemsToRP.htm?id=${researcher.id}"><fmt:message key="jsp.layout.navbar-hku.staff-mode.bind.items"/></a>
    </td>
  </tr>
   <tr class="navigationBarItem">
    <td>
      <img alt="" src="<%=request.getContextPath()%>/image/<%=( currentPage.endsWith( "/help#ResearcherPages" ) ? "arrow-highlight" : "arrow" )%>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%=request.getContextPath()%>/help.jsp#ResearcherPages">Help</a>
    </td>
  </tr>
  </c:if>
  
  <%
        if (isAdmin) {
    %>
  <tr> 
  <td colspan="2">
	<c:if test="${!empty researcher}">
	
		
			<p><b>Staff no. ${researcher.sourceID} </b><br/>
			<br />
			record created at:
			${researcher.timeStampInfo.timestampCreated.timestamp} <br/>
			<br />
			last updated at:
			${researcher.timeStampInfo.timestampLastModified.timestamp}<br/>
			</p>
		
	
	</c:if>
	</td>
  </tr>
<%
    }
%>
</c:set>
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

<dspace:layout titlekey="jsp.researcher-page.details">


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
		</div>
	 </div>
<h1><fmt:message key="jsp.layout.hku.detail.title-first" /> <c:choose>
	<c:when test="${!empty entity.preferredName.value}">
	${entity.preferredName.value}
</c:when>
	<c:otherwise>
	${entity.fullName}
</c:otherwise>
</c:choose></h1>

	<c:if test="${!entity.status}">
		<p class="warning">
			<fmt:message
				key="jsp.layout.hku.detail.researcher-disabled" /><a
				target="_blank"
				href="<%=request.getContextPath()%>/cris/administrator/rp/list.htm?id=${entity.id}&mode=position"><fmt:message
				key="jsp.layout.hku.detail.researcher-disabled.fixit" /></a>
		</p>
	</c:if>

	<c:if test="${pendingItems > 0}">
		<p class="warning pending">
			<fmt:message
				key="jsp.layout.hku.detail.researcher-pending-items">
				<fmt:param>${pendingItems}</fmt:param>
			</fmt:message> <fmt:message
				key="jsp.layout.hku.detail.researcher-goto-pending-items">
				<fmt:param><%=request.getContextPath()%>/dspace-admin/authority?authority=<%=RPAuthority.HKU_AUTHORITY_MODE%>&key=${authority_key}</fmt:param>
			</fmt:message>
		</p>	
	</c:if>


		<div id="researcher">

			<jsp:include page="commonDetailsPage.jsp"></jsp:include>
		</div>
</div>

</dspace:layout>
</c:otherwise>
</c:choose>