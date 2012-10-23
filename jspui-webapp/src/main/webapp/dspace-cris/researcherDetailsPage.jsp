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
<%@ page import="org.dspace.app.webui.util.UIUtil" %>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="entity" value="${researcher}" scope="request" />

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
      <img alt="" src="<%= request.getContextPath() %>/image/<%= ( currentPage.endsWith( "/editDynamicData" ) ? "arrow-highlight" : "arrow" ) %>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%= request.getContextPath() %>/cris/tools/rp/editDynamicData.htm?id=${researcher.id}&anagraficaId=${researcher.dynamicField.id}<c:if test='${!empty tabIdForRedirect}'>&tabId=${tabIdForRedirect}</c:if>"><fmt:message key="jsp.layout.navbar-hku.staff-mode.edit.primary-data"/></a>
    </td>
  </tr>  
  </c:if>
   <tr class="navigationBarItem">
    <td>
      <img alt="" src="<%= request.getContextPath() %>/image/<%= ( currentPage.endsWith( "/rebindItemsToRP" ) ? "arrow-highlight" : "arrow" ) %>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%= request.getContextPath() %>/cris/tools/rp/rebindItemsToRP.htm?id=${researcher.id}"><fmt:message key="jsp.layout.navbar-hku.staff-mode.bind.items"/></a>
    </td>
  </tr>
   <tr class="navigationBarItem">
    <td>
      <img alt="" src="<%= request.getContextPath() %>/image/<%= ( currentPage.endsWith( "/help#ResearcherPages" ) ? "arrow-highlight" : "arrow" ) %>.gif" width="16" height="16"/>
    </td>
    <td nowrap="nowrap" class="navigationBarItem">
      <a href="<%= request.getContextPath() %>/help.jsp#ResearcherPages">Help</a>
    </td>
  </tr>
  </c:if>
  
  <% if (isAdmin) { %>
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
<% } %>


<c:if test="${!empty entity && (!empty addModeType && addModeType=='display')}">    
 <% if (!isAdmin) { %>
 <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
<% } %>	
 <tr> 
  <td colspan="2">
		
	    <c:forEach items="${tabList}" var="tabfornavigation">				
			
				<div id="cris-tabs-navigation-${tabfornavigation.shortName}" class="navigation-tabs" style="display: none">		

					<div id="menu-${tabfornavigation.shortName}" class="showMoreLessBox1-dark box">
						<h3 class="showMoreLessControlElement control ${tabfornavigation.id != tabId?"":"expanded"}">
						<img src="<%=request.getContextPath() %>/image/cris/btn_lite_expand.gif"  ${tabfornavigation.id != tabId?"":"class=\"hide\""}/>
						<img src="<%=request.getContextPath() %>/image/cris/btn_lite_collapse.gif" ${tabfornavigation.id != tabId?"class=\"hide\"":""} />
							${tabfornavigation.title}
						</h3>		
						<div class="collapsable expanded-content" ${tabfornavigation.id != tabId?"style=\"display: none;\"":""}>
						<div id="nav-sublocal">
						<ul>
						<div id="snavmenu-${tabfornavigation.shortName}">
							<div class="log">
							<img
								src="<%=request.getContextPath()%>/image/jdyna/indicator.gif"
			    				class="loader" />
			    			</div>
						</div>
						</ul>
						</div>
						</div>
					</div>
		

				</div>
			
		 </c:forEach>
	   
	</td>
  </tr> 
</c:if>
 

</c:set>
<c:set var="dspace.layout.head" scope="request">
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.24.custom.min.js"></script>
    <link href="<%= request.getContextPath() %>/css/researcher.css" type="text/css" rel="stylesheet" />
    <link href="<%= request.getContextPath() %>/css/jdyna.css" type="text/css" rel="stylesheet" />
    <link href="<%= request.getContextPath() %>/css/redmond/jquery-ui-1.8.24.custom.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"><!--

		var j = jQuery.noConflict();
    	var ajaxurltabs = "<%=request.getContextPath()%>/cris/rp/loadTabs.htm";
    	var ajaxurlnavigation = "<%=request.getContextPath()%>/cris/rp/loadNavigation.htm";
    	
    	var LoaderSnippet = {    		
    		write : function(text, idelement) {
    			var elem = document.getElementById(idelement);
    			elem.innerHTML = text;		
    		}
    	};

    	var LoaderModal = {        		
       		write : function(text, idelement) {
       			var elem = document.getElementById(idelement);
       			elem.innerHTML = text;		
       		}
        };
    
    	var Loader = {
       		elem : false,
       		write : function(text) {
       			if (!this.elem)
       				this.elem = document.getElementById('logcontent3');
       			this.elem.innerHTML = text;		
      		}
        };
        	
		j(document).ready(function()
				{
				  j("#log3").dialog({closeOnEscape: true, modal: true, autoOpen: false, resizable: false, open: function(event, ui) { j(".ui-dialog-titlebar").hide();}});
			
				  j(".control").click(function()
				  {
					  j(this).toggleClass("expanded");
					  j(this).children("img").toggleClass("hide");
				      j(this).next(".collapsable").slideToggle(300);
				  });
		<% 
			if (showSubMsg) {%>alert('Email Alert Now On');<%}
			if (showUnSubMsg) {%>alert('Email Alert Now Off');<%}
		%>
		
			
		<c:forEach items="${tabList}" var="tabnavigation">		
		j.ajax( {
			url : ajaxurlnavigation,
			data : {																			
				"tabId" : ${tabnavigation.id},
				"currentOpenedTabId": ${tabId},
				"objectId": ${entity.id},
				"authority": '${authority}'
			},
			success : function(data) {				
				j('#snavmenu-${tabnavigation.shortName}').html(data);				
			},
			error : function(data) {
				//nothing				
			}
		});
		
		
		<c:choose>
		<c:when test="${tabnavigation.id == tabId}">
			j('#cris-tabs-navigation-${tabnavigation.shortName}').show();	
		</c:when>
		<c:otherwise>
		j.ajax( {
			url : ajaxurltabs,
			data : {																			
				"tabId" : ${tabnavigation.id},
				"currentOpenedTabId": ${tabId},
				"objectId": ${entity.id},
				"authority": '${authority}'
			},
			success : function(data) {				
				j('#tb-head2-${tabnavigation.shortName}').html(data);						
				
			},
			error : function(data) {
				//nothing				
			}
		});		
		</c:otherwise>
		</c:choose>
	
		</c:forEach>
		
		});
		-->
	</script>
    
</c:set>

<dspace:layout titlekey="jsp.researcher-page.details">

<table align="center" class="miscTable">
<tr>
<td>

<div id="content">
<h1><fmt:message key="jsp.layout.hku.detail.title-first" /> <c:choose>
	<c:when test="${!empty entity.preferredName.value}">
	${entity.preferredName.value}
</c:when>
	<c:otherwise>
	${entity.fullName}
</c:otherwise>
</c:choose></h1>
<div>&nbsp;</div>
<table align="center" class="miscTable">
	
	<c:if test="${!entity.status}">
		<tr class="warning">
			<td colspan="3"><fmt:message
				key="jsp.layout.hku.detail.researcher-disabled" /><a
				target="_blank"
				href="<%=request.getContextPath()%>/cris/administrator/rp/list.htm?id=${entity.id}&mode=position"><fmt:message
				key="jsp.layout.hku.detail.researcher-disabled.fixit" /></a></td>
		</tr>
	</c:if>

	<c:if test="${pendingItems > 0}">
		<tr class="warning pending">
			<td colspan="3"><fmt:message
				key="jsp.layout.hku.detail.researcher-pending-items">
				<fmt:param>${pendingItems}</fmt:param>
			</fmt:message> <fmt:message
				key="jsp.layout.hku.detail.researcher-goto-pending-items">
				<fmt:param><%=request.getContextPath()%>/dspace-admin/authority?authority=<%=HKUAuthority.HKU_AUTHORITY_MODE%>&key=${authority_key}</fmt:param>
			</fmt:message></td>
		</tr>
	</c:if>


	<tr>

		<td>

		<div id="researcher">

			<jsp:include page="commonDetailsPage.jsp"></jsp:include>
		</div>

		</td>
	</tr>
</table>
</div>
<div id="log3" class="log">
	<img
		src="<%=request.getContextPath()%>/image/cris/bar-loader.gif"
		id="loader3" class="loader"/>
	<div id="logcontent3"></div>
</div>









</td>
</tr>
</table>
</dspace:layout>
