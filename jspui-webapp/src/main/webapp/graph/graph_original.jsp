<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="it.cilea.hku.authority.model.ResearcherPage"%>
<%@ page import="org.dspace.core.ConfigurationManager"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<%
    String networkStarted = (String) request
            .getAttribute("networkStarted");
    String authority = (String) request.getAttribute("authority");
    ResearcherPage researcher = (ResearcherPage) request
            .getAttribute("researcher");
    String[] relations = (String[]) request.getAttribute("relations");
    Map<String, Integer> customMaxDepths = (Map<String, Integer>) request
            .getAttribute("customMaxDepths");
    Map<String, String> colorsNodes = (Map<String, String>) request
            .getAttribute("colorsNodes");
    Map<String, String> colorsEdges = (Map<String, String>) request
            .getAttribute("colorsEdges");
    String configMaxDepth = (String) request
            .getAttribute("configMaxDepth");
    Integer maxDepth = (Integer) request.getAttribute("maxDepth");
    Boolean showexternal = (Boolean) request
            .getAttribute("showexternal");
    Boolean showsamedept = (Boolean) request
            .getAttribute("showsamedept");
    boolean radiographlayout = ConfigurationManager.getBooleanProperty(
            "network.customgraphlayout", true);
%>
<c:set value="${researcher.dynamicField}" var="anagraficaObject"></c:set>
<c:set value="${authority}" var="authority"></c:set>
<c:set var="messagenodatafound"><fmt:message key="jsp.network.label.nodatafounded"/></c:set>

<html>
<head>
		
	<meta name="author" content="The University of Hong Kong Libraries" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<meta name="language" content="english"/>
	<meta name="language" content="chinese"/>
	<meta name="description" content="HKU ResearcherPage on <c:if test="${!empty researcher.academicName}">${researcher.academicName.value}</c:if>, showing contact, bibliometric, and publication list details. Expertise, specialist, collaboration, expert, contract research, media comment."/>

	<meta name="keywords" content="<c:if test="${!empty researcher.academicName}">${researcher.academicName.value},</c:if> ${researcher.fullName},<c:if test="${!empty researcher.chineseName}"> ${researcher.chineseName.value}, </c:if>
	<c:forEach items="${researcher.variants}" var="variant">${variant.value}, </c:forEach> bibliometric, bibliometrics, scientometric,
	scientometrics, contact details, publication list, paper list, statistics,
	hku, uhk, university of hong kong, the university of hong kong, ${researcher.dept.value}, <c:forEach items="${researcher.title}" var="title">${title.value}, </c:forEach>
	<c:forEach items="${researcher.interests}" var="interest">${interest.value}, </c:forEach>
	<c:forEach items="${researcher.media}" var="mediaspoken">${mediaspoken.value} </c:forEach>"/>

	<meta name="robots" content="index follow"/>
	
	<title>HKU ResearcherPage - ${researcher.academicName.value}</title>	

	<link href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon" rel="shortcut icon" />
	<link href="<%=request.getContextPath()%>/css/hub.css" type="text/css"  rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/styles.css.jsp" type="text/css"  rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/researcher.css" type="text/css"  rel="stylesheet" />	
	<link href="<%=request.getContextPath()%>/css/jdyna.css" type="text/css"  rel="stylesheet" />
	
	<link href="<%=request.getContextPath()%>/css/jquery-1.8.8-ui.css" type="text/css" rel="stylesheet" />			
    <link href="<%=request.getContextPath()%>/css/collaborationNetwork.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/layout-default-latest.css" rel="stylesheet" type="text/css" />     
        
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jit.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.layout-1.3rc.js"></script>   
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/collaborationNetwork.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.js"></script>
        
    <!--[if IE]>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/excanvas.js"></script>
    <![endif]-->
        
<style type="text/css" media="print">
@page
{
 size: landscape;
 margin: 1cm;
}

#toolbar_print {
	display: none;
}

</style>     

<script><!--




	var j = jQuery.noConflict();


	var eventTypeDepth = new Array();
	<%for (String relation : relations)
            {%>
		<%=relation%>json = new Object();		
		eventTypeDepth.push('<%=relation%>');		
	<%}%>
	
	
	var colorsNodes = new Array();
	var colorsEdges = new Array();
	<%for (String relation : relations)
            {%>				
		colorsNodes['<%=relation%>'] = '<%=colorsNodes.get(relation)%>';
		colorsEdges['<%=relation%>'] = '<%=colorsEdges.get(relation)%>';
	<%}%>
	
	
	--></script>

	
	

</head>
<body>
	<div class="ui-layout-north">
		<div class="thumbnail" id="banner">
			<img src="<%=request.getContextPath()%>/images/banner7.jpg">
		</div>
	</div>
	<div class="ui-layout-center">
		<div id="inner-center">
			<div id="toolbar_print" class="transparent">
				<a style='display:none; position: relative;' href='javascript:screenPage()' id='screen'>
					<img id='imgscreen' alt='Return to visualization' title='Return to visualization'  src='../image/window_size.png'/>
				</a>
				<a style='position: relative;'
					href='javascript:printPageWithoutWest()' id='printWithoutWest'><img
					alt='Print without name card' title='Print without name card'
					src='../image/layout-content-icon_print.png' />
				</a> 
				<a style='position: relative;'
					href='javascript:printPageWithWest()' id='printWithWest'><img
					alt='Print with name card' title='Print with name card'
					src='../image/layout-sidebar-icon_print.png' />
				</a>

								
			</div>			
			<div id="infovis"></div>			
		</div>
	</div>
	<div class="ui-layout-south">


<div id="footer-south">
<table align="center">
<tr><td>
<table>
<tr>
<td>
	<img src="<%=request.getContextPath()%>/images/libraryname2.gif" img="hku library"/>
</td>
</tr>

<tr>
<td class="footer" bgColor="#660000">
	<div class="footerdiv">
	Powered by <a href="http://www.dspace.org" class="footer" target="new">DSpace</a>, version 1.6.2. Engineered by <a class="footer" target="_new" href="http://www.cilea.it">CILEA</a>.<br/>
	This site implements <a href="http://dublincore.org" class="footer" target="new">DCMI Metadata</a> through meta-tags and an <a href="http://www.openarchives.org" class="footer" target="new">OAI-compliant</a> repository located <a href="http://hub.hku.hk/oai/request?verb=Identify" class="footer" target="new">here</a>.<br/>
	<a href="http://lib.hku.hk" class="footer" target="new">The University of Hong Kong Libraries</a>, <a href="http://www.hku.hk" class="footer" target="new">University of Hong Kong</a> | <a href="mailto:hub@lib.hku.hk" class="footer" target="new">Contact Us</a>
	</div>
</td>
</tr>
</td></tr>
</table>

<a href="/sitemaps/sitemap_index.html"></a>

</td></tr></table>
</div>

		<div id="log">
			<img src="<%=request.getContextPath()%>/image/bar-loader.gif" id="loader" />
			<div id="logcontent">
			</div>		
		</div>
		
			<div id="relationfragment" class="dialogfragment">
				<div id="relationfragmentcontent">
				
				</div>
			</div>
			<div id="relationfragmenttwice" class="dialogfragment">
				<div id="relationfragmenttwicecontent">
			
				</div>
			</div>		
			
			
		
	</div>
	<div class="ui-layout-east">
		
		<%
				    if (relations.length > 1)
				    {
				%>
		<div id="accordionlistshowed">
			
			<h3>
				<a href="#"><fmt:message key="jsp.network.label.accordion.listshowed.title"/></a>
			</h3>
			<div>

				
				<%
								    for (String relation : relations)
								        {
								%>
					<p>   
					<c:set var="rrr">jsp.network.label.showrelationsby.<%=relation%></c:set>
					<div class="state-preview-listshowed" id="<%=relation%>-color-accordion-listshowed"></div>
					<span class="checkspan" id="<%=relation%>checkonly"><fmt:message key="${rrr}"/><input class="check" type="checkbox" value="<%=relation%>"
						id="<%=relation%>check" />
						
					</span>
																						
					</p>
				<%
				    }
				%>
				
			</div>	
														
		</div>
		<%
		    }
		%>
		<div id="accordiongeneralmenu">
		
			<h3>
				<a href="#"><fmt:message key="jsp.network.label.accordion.mainconfiguration.title"/></a>
			</h3>
			<div>

			<fmt:message key="jsp.network.label.accordion.mainconfiguration.maxdepth"/> <input type="text" id="amount"
				style="border: 0; color: #f6931f; font-weight: bold;" />
			<div id="slider"></div>
			<div id="sliderleveldistance"></div>
			
				<br />

			<span class="titleradio"><fmt:message key="jsp.network.label.mainconfiguration.showexternalresearcher"/></span> 
			<br /> 
			<input class="radio" type="radio" value="true" <%if (showexternal)
            {%> checked="checked" id="radio1-checked" <%}
            else
            {%> id="radio1" <%}%> name="radio" /> <label <%if (showexternal)
            {%> for="radio1-checked" <%}
            else
            {%> for="radio1" <%}%>> <fmt:message key="jsp.network.label.mainconfiguration.showexternalresearcher.value.activate"/></label> 
			<input class="radio" type="radio" value="false" <%if (!showexternal)
            {%> checked="checked" id="radio2-checked" <%}
            else
            {%> id="radio2" <%}%> name="radio" /> <label <%if (!showexternal)
            {%> for="radio2-checked" <%}
            else
            {%> for="radio2" <%}%>><fmt:message key="jsp.network.label.mainconfiguration.showexternalresearcher.value.deactivate"/></label>

			<br />
			<span class="titleradio"><fmt:message key="jsp.network.label.mainconfiguration.showsamedept"/></span> 
			<br /> 
			<input class="radiodept" type="radio" value="true" <%if (showsamedept)
            {%> checked="checked" id="radiodept1-checked" <%}
            else
            {%> id="radiodept1" <%}%> name="radiodept" /><label <%if (showsamedept)
            {%>  for="radiodept1-checked" <%}
            else
            {%> for="radiodept1" <%}%>><fmt:message key="jsp.network.label.mainconfiguration.showsamedept.value.activate"/></label>
			 
			<input class="radiodept" type="radio" value="false" <%if (!showsamedept)
            {%> checked="checked" id="radiodept2-checked" <%}
            else
            {%> id="radiodept2" <%}%> name="radiodept" /> <label <%if (!showsamedept)
            {%>  for="radiodept2-checked" <%}
            else
            {%> for="radiodept2" <%}%>><fmt:message key="jsp.network.label.mainconfiguration.showsamedept.value.deactivate"/></label>			

			<br />
			<span class="titleradio"><fmt:message key="jsp.network.label.mainconfiguration.graphlayout"/></span> 
			<br /> 
			<input class="radiographlayout" type="radio" value="true" <%if (radiographlayout)
            {%> checked="checked" id="radiographlayout1-checked" <%}
            else
            {%>  id="radiographlayout1" <%}%> name="radiographlayout" /><label <%if (radiographlayout)
            {%> for="radiographlayout1-checked" <%}
            else
            {%>  for="radiographlayout1" <%}%>><fmt:message key="jsp.network.label.mainconfiguration.radiographlayout.value.custom"/></label>
			 
			<input class="radiographlayout" type="radio" value="false" <%if (!radiographlayout)
            {%> checked="checked" id="radiographlayout2-checked" <%}
            else
            {%>  id="radiographlayout2" <%}%> name="radiographlayout" /> <label <%if (!radiographlayout)
            {%> for="radiographlayout2-checked" <%}
            else
            {%>  for="radiographlayout2" <%}%>><fmt:message key="jsp.network.label.mainconfiguration.radiographlayout.value.default"/></label>
				
			
			</div>	
														
		</div>
		
		<div id="accordion">
		
		
			
			<%
										    for (String relation : relations)
										    {
										%>
			<div>
			<h3>
				<c:set var="rrr">jsp.network.label.showrelationsby.<%=relation%></c:set>
				<a href="#"><fmt:message key="${rrr}"/></a>				
			</h3>
			<div class="state-preview" id="<%=relation%>-color-accordion"></div>
			</div>
			<div>
				<fmt:message key="jsp.network.label.showrelationsby.setcustommaxdepth"/> <input type="text" id="amount<%=relation%>"
					class="amountother"
					style="border: 0; color: #f6931f; font-weight: bold;" />
				<div id="slider<%=relation%>" class="sliderclass"></div>
			</div>			
			
			<%
									    }
									%>
		
		</div>
						
	</div> 


	<div class="ui-layout-west">
	<div id="profilefocus" class="profile">
			
							
			<table border="0" cellpadding="0" cellspacing="0" id="profilefocus" class="profilenetwork">

				

			<tr>
				<td class="columnBody">

				
				<c:choose>
				<c:when
								test="${!empty researcher.pict && !empty researcher.pict.value && researcher.pict.visibility==1}">
					<tr>

						<td class="image_td" colspan="2" align="center">

						<div class="image">
							
								<img id="picture" name="picture"
									alt="${researcher.fullName} picture"
									src="<%=request.getContextPath()%>/researcherimage/${authority}"
									title="${researcher.fullName} picture" />
							
						</div>

						</td>

					</tr>
					</c:when>
				<c:otherwise>
							
					<tr>

						<td class="image_td" colspan="2" align="center">

						<div class="image">
							
								
							
						</div>

						</td>

					</tr>
					</c:otherwise>
					</c:choose>




					</td>
			</tr>
			
				
				<tr>
						<td colspan="2">
						<span class="header4"><c:if
							test="${researcher.honorific.visibility==1}">${researcher.honorific.value}</c:if>
						${researcher.fullName}</span> 

			
                </td></tr>
                <tr><td colspan="2">  
						<c:if
							test="${researcher.chineseName.visibility==1}">
							<span class="header4">${researcher.chineseName.value}</span> 
						</c:if>
					</td>
				</tr>
				
				
				<tr>
				
				<tr>
					<td colspan="2">
						<a target="_blank" href="../rp/${authority}"><span id="profileTitle"><img src='../image/profile.png' alt="<fmt:message key='jsp.network.label.profile.title'/>" title="<fmt:message key='jsp.network.label.profile.title'/>"/></span></a>
					</td>
				</tr>
				
				<td colspan="2">
				<div style="text-align: left">
					<c:forEach
							items="${researcher.title}" var="title" varStatus="counter">
							<c:if test="${title.visibility==1 && counter.count==1}">
								<div><strong> ${title.value}</strong></div>
							</c:if>
						</c:forEach>
			
					
				</div>
				</td>
				</tr>


				<tr>
					<c:if
						test="${researcher.dept.visibility==1 && !empty researcher.dept.value}">
						<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.department" />
						</td>
						<td class="ncBody"><c:url var="deptSearch"
								value="../rp/search.htm">
								<c:param name="advancedSyntax" value="true" />
								<c:param name="searchMode" value="Search" />
								<c:param name="queryString" value="dept:\"${researcher.dept.value}\"" />
							</c:url> <a target="_blank" href="${deptSearch}">${researcher.dept.value}</a>
						</td>
						<td><span> <input type="checkbox"
								class="deptvisualization" id="deptvisualization"
								value="<%=URLEncoder.encode(researcher.getDept().getValue(),"UTF-8")%>" /><label
								for="deptvisualization">See DGraph</label> </span>
						</td>
					</c:if>
				</tr>
			
			</table>
				
				
				
		
		</div>
		
		<div id="profileminimized" class="profile">		
				
						<c:choose>
				<c:when
								test="${!empty researcher.pict && !empty researcher.pict.value && researcher.pict.visibility==1}">
						<div class="imagemin">
							
								<img id="picturemin" name="picturemin"
									alt="${researcher.fullName} picture"
									src="<%=request.getContextPath()%>/researcherimage/${authority}"
									title="${researcher.fullName} picture" />
							
						</div>
					</c:when>
				<c:otherwise>
							


						<div class="imagemin">
							
								
							
						</div>

					</c:otherwise>
					</c:choose>
					<div class="otherminimized">
					<span class="header4" id="headermin"><c:if
							test="${researcher.honorific.visibility==1}">${researcher.honorific.value}</c:if>
						${researcher.fullName}</span>
  					
					<c:if
							test="${researcher.chineseName.visibility==1}">
							<br/>
							<span class="header4" id="headermin">${researcher.chineseName.value}</span> 
					</c:if>
					
					<a href="../rp/${authority}"><span id="profileTitle"><img src='../image/profile.png' alt="<fmt:message key='jsp.network.label.profile.title'/>" title="<fmt:message key='jsp.network.label.profile.title'/>"/></span></a>
					
					<div id="titlemin">
					
						<c:forEach
							items="${researcher.title}" var="title" varStatus="counter">
							<c:if test="${title.visibility==1 && counter.count==1}">
								
								<div id="ulmin"><strong> ${title.value}</strong></div>
							</c:if>
						</c:forEach>
					
					</div>
					
				<div id="deptmin">
						<c:if
							test="${researcher.dept.visibility==1 && !empty researcher.dept.value}">
							<fmt:message
								key="jsp.layout.hku.detail.researcher.department" />
							
<c:url var="deptSearch" value="../rp/search.htm">
	<c:param name="advancedSyntax" value="true" />
	<c:param name="searchMode" value="Search" />
	<c:param name="queryString" value="dept:\"${researcher.dept.value}\"" />
</c:url>
<a href="${deptSearch}">${researcher.dept.value}</a>
<span> <input type="checkbox" class="deptvisualization" id="deptvisualizationminimized" value="<%=URLEncoder.encode(researcher.getDept().getValue(),
                        "UTF-8")%>"/><label
							for="deptvisualizationminimized">See DGraph</label> </span>
						</c:if>
					</div>
					
				</div>
		
				</div>
		
		
		
		<div id="profiletarget">
		
				
		</div>
		
		
		<div id="profiletargettwice">
		
				
		</div>	
		
		</div>
		
		
	

</body>

	<script type="text/javascript">

	ajaxurlprofile = "<%=request.getContextPath()%>/networkdataprofile/${authority}"
	var myLayout;
	

	<%if (networkStarted == null)
            {%>
	   
    j("#log").dialog({closeOnEscape: false, modal: true, autoOpen: false, resizable: false, open: function(event, ui) { j(".ui-dialog-titlebar").hide();}});
    j("#log").dialog("open");		
	Log.write("${messagenodatafound}");
 <%}
            else
            {%>
	
	j("#log").dialog({closeOnEscape: true, modal: true, autoOpen: false, resizable: false,
		   open: function(event, ui) { j(".ui-dialog-titlebar").hide();}});
	
	
	
	function initJSON(rp, network) {

		j("#log").dialog("open");			
		Log.write("Loading... network: " + network);
		var showEXT = j("input:radio[name=radio]:checked").val();
		var showSAMEDEPT = j("input:radio[name=radiodept]:checked").val();
		var jqxhr = j.getJSON("<%=request.getContextPath()%>/json/collaborationnetwork",
		  {
		    connection: network,		    
		    researcher: rp,
		    showexternal: showEXT,
		    showsamedept: showSAMEDEPT
		    
		  },
		  function(data) {					   
			  var lim = data.length;
			  for (var i = 0; i < lim; i++){			    
			       data[i].data.color = colorsNodes[network];
			       data[i].data.$color = colorsNodes[network];
			       for(var y = 0; y < data[i].adjacencies.length; y++) {
			    	   data[i].adjacencies[y].data.color = colorsEdges[network];
			    	   data[i].adjacencies[y].data.$color = colorsEdges[network];
			       }
			  }			  
			  init(data, rp, network, "rp");			  
			  <%for (String relation : relations)
                {%> 
				if(network=='<%=relation%>') {				
					addLocalJson({"<%=relation%>" : data});
				}
			<%}%>
			  j("#log").dialog("close");
			  myLayout.close("north");
			  myLayout.close("south");						
		  });
			
		
	}
	
	
	
	<%=networkStarted%>json = initJSON('<%=authority%>', '<%=networkStarted%>');
		
	<%}%>

	
	// set EVERY 'state' here so will undo ALL layout changes
	// used by the 'Reset State' button: myLayout.loadState( stateResetSettings )
	var stateResetSettings = {
			north__size:		"auto"
				,	north__initClosed:	false
				,	north__initHidden:	false
		,   south__size:		"auto"
		,	south__initClosed:	false
		,	south__initHidden:	false
		,	west__size:			300
		,	west__initClosed:	false
		,	west__initHidden:	false
		,	east__size:			500
		,	east__initClosed:	false
		,	east__initHidden:	false		
	};
	
	j(document).ready(function () {
		// this layout could be created with NO OPTIONS - but showing some here just as a sample...
		// myLayout = $('body').layout(); -- syntax with No Options

		myLayout = j('body').layout({

		//	enable showOverflow on west-pane so CSS popups will overlap north pane
		//west__showOverflowOnHover: true

		//	reference only - these options are NOT required because 'true' is the default
			closable:				true	// pane can open & close
		,	resizable:				true	// when open, pane can be resized 
		,	slidable:				true	// when closed, pane can 'slide' open over other panes - closes on mouse-out

		//	some resi"WebContent/test2.html"zing/toggling settings			
						
		,	south__spacing_closed:	20		// big resizer-bar when open (zero height)
		//	some pane-size settings
		,	south__size:			100
		,	west__size:				300
		,	west__minSize:			100
		,	west__maxSize:			300
		,	east__size:				300
		,	east__minSize:			100
		,	east__maxSize:			Math.floor(screen.availWidth / 2) // 1/2 screen width
		,	center__minWidth:		600
		
		});
		
			
	});
	
	
	function addJSON(rp, network) {

		j("#log").dialog("open");			
		Log.write("Loading... network: " + network);
		var showEXT = j("input:radio[name=radio]:checked").val();
		var showSAMEDEPT = j("input:radio[name=radiodept]:checked").val();
		
		var jqxhr = j.getJSON("<%=request.getContextPath()%>/json/collaborationnetwork",
		  {
		    connection: network,		    
		    researcher: rp,
		    showexternal: showEXT,
		    showsamedept: showSAMEDEPT
		  },
		  function(data) {			  
			  	  
			  var lim = data.length;
			  for (var i = 0; i < lim; i++){			    
			       data[i].data.color = colorsNodes[network];
			       data[i].data.$color = colorsNodes[network];
			       for(var y = 0; y < data[i].adjacencies.length; y++) {
			    	   data[i].adjacencies[y].data.color = colorsEdges[network];
			    	   data[i].adjacencies[y].data.$color = colorsEdges[network];
			       }
			  }		
				rgraph.op.sum(data, {  
				   type: 'fade:con',  
				   duration: 1500  
				});
				rgraph.refresh();
				
				<%for (String relation : relations)
            {%> 
					if(network=='<%=relation%>') {				
						addLocalJson({"<%=relation%>" : data});
					}
				<%}%>
				
			  j("#log").dialog("close");	
			  
			  return data;
		  });
			
		
	}
	
	
	function checkOnlyWithJSON(rp, network) {

		j("#log").dialog("open");			
		Log.write("Loading... network: " + network);
		var showEXT = j("input:radio[name=radio]:checked").val();
		var showSAMEDEPT = j("input:radio[name=radiodept]:checked").val();
		
		var jqxhr = j.getJSON("<%=request.getContextPath()%>/json/collaborationnetwork",
		  {
		    connection: network,		    
		    researcher: rp,
		    showexternal: showEXT,
		    showsamedept: showSAMEDEPT
		  },
		  function(data) {		  
			  var lim = data.length;
			  for (var i = 0; i < lim; i++){			    
			       data[i].data.color = colorsNodes[network];
			       data[i].data.$color = colorsNodes[network];
			       for(var y = 0; y < data[i].adjacencies.length; y++) {
			    	   data[i].adjacencies[y].data.color = colorsEdges[network];
			    	   data[i].adjacencies[y].data.$color = colorsEdges[network];
			       }
			  }			  
			  rgraph.loadJSON(data);
			  reloadLocalJson({network:data});		  
			  rgraph.refresh();
			  
			  j("#log").dialog("close");		
			  
			  return data;
		  });
			
		
	}
	
	j(document).ready(function () {
	
	customRGraph = <%=radiographlayout%>;
	
	j("#radio1").button({ 
		icons: {
        	primary: "icon-activate"        	
    	},
    	text: false
	});
	j("#radio1-checked").button({ 
		icons: {
        	primary: "icon-activate-checked"        	
    	},
    	text: false
	});
	j("#radio2").button({ 
		icons: {
        	primary: "icon-deactivate"
    	},
    	text: false
	});
	j("#radio2-checked").button({ 
		icons: {
        	primary: "icon-deactivate-checked"
    	},
    	text: false
	});
	j("#radiodept1").button({ 
		icons: {
        	primary: "icon-activate"
    	},
    	text: false
	});
	j("#radiodept1-checked").button({ 
		icons: {
        	primary: "icon-activate-checked"
    	},
    	text: false
	});
	j("#radiodept2").button({ 
		icons: {
        	primary: "icon-deactivate"
    	},
    	text: false
	});
	j("#radiodept2-checked").button({ 
		icons: {
        	primary: "icon-deactivate-checked"
    	},
    	text: false
	});	
	j("#radiographlayout1").button({ 
		icons: {
        	primary: "icon-graphlayoutcustom"
    	},
    	text: false
	});
	j("#radiographlayout1-checked").button({ 
		icons: {
        	primary: "icon-graphlayoutcustom-checked"
    	},
    	text: false
	});
	j("#radiographlayout2").button({ 
		icons: {
        	primary: "icon-graphlayoutdefault"
    	},
    	text: false
	});
	j("#radiographlayout2-checked").button({ 
		icons: {
        	primary: "icon-graphlayoutdefault-checked"
    	},
    	text: false
	});	
	
	j( ".deptvisualization" ).button({ 
		icons: {
        	primary: "icon-network"
    	},
    	text: false
	});

	j(".deptvisualization").click(function(){

		location.href = "<%=request.getContextPath()%>/dnetwork/graph?dept=" + this.value;
		return true;		
		
	});
		
	
	j(".radio").click(function(){		
		
		var showEXT = j("input:radio[name=radio]:checked").val();		
		var showSAMEDEPT = j("input:radio[name=radiodept]:checked").val();
		location.href = "<%=request.getContextPath()%>/network/${authority}?showexternal=" + showEXT + "&showsamedept=" + showSAMEDEPT;
		return true;		
		
	});	
		
	j(".radiodept").click(function(){		
		
		var showEXT = j("input:radio[name=radio]:checked").val();
		var showSAMEDEPT = j("input:radio[name=radiodept]:checked").val();
		location.href = "<%=request.getContextPath()%>/network/${authority}?showexternal=" + showEXT + "&showsamedept=" + showSAMEDEPT;
		return true;		
		
	});	
	
	
	j("#radiographlayout1").click(function(){
		
		
		if (!customRGraph)
		{
			j("#radiographlayout2").checked = false;
			j("#radiographlayout1").checked = true;
			j(".icon-graphlayoutcustom").addClass("icon-graphlayoutcustom-checked");
			j(".icon-graphlayoutdefault-checked").removeClass("icon-graphlayoutdefault-checked");			
			customRGraph = true;		
			rgraph.refresh();
		}		
	});
	
	j("#radiographlayout2").click(function(){		
		if (customRGraph)
		{
			j("#radiographlayout1").checked = false;
			j("#radiographlayout2").checked = true;
			j(".icon-graphlayoutdefault").addClass("icon-graphlayoutdefault-checked");
			j(".icon-graphlayoutcustom-checked").removeClass("icon-graphlayoutcustom-checked").addClass("icon-graphlayoutcustom");
			customRGraph = false;
			rgraph.refresh();
		}		
	});	
	
	j("#radiographlayout1-checked").click(function(){
		
		
		if (!customRGraph)
		{
			j("#radiographlayout2").checked = false;
			j("#radiographlayout1").checked = true;
			j(".icon-graphlayoutcustom").addClass("icon-graphlayoutcustom-checked");
			j(".icon-graphlayoutdefault-checked").removeClass("icon-graphlayoutdefault-checked");			
			customRGraph = true;		
			rgraph.refresh();
		}		
	});
	
	j("#radiographlayout2-checked").click(function(){		
		if (customRGraph)
		{
			j("#radiographlayout1").checked = false;
			j("#radiographlayout2").checked = true;
			j(".icon-graphlayoutdefault").addClass("icon-graphlayoutdefault-checked");
			j(".icon-graphlayoutcustom-checked").removeClass("icon-graphlayoutcustom-checked").addClass("icon-graphlayoutcustom");
			customRGraph = false;
			rgraph.refresh();
		}		
	});	
	
	<%for (String relation : relations)
            {%>
			
	j("#<%=relation%>-color-accordion").css('background-color', '<%=colorsNodes.get(relation)%>');
	j("#<%=relation%>-color-accordion-listshowed").css('background-color', '<%=colorsNodes.get(relation)%>');

	
	
	j("#<%=relation%>check").click(function(){
		if(this.checked) {
			
			if(j.isEmptyObject(<%=relation%>json)) {	
				
				<%=relation%>json = addJSON('<%=authority%>', '<%=relation%>');			
				
			}
			else {				
				j("#log").dialog("open");		
				Log.write("Loading (from cache)... network: " + '<%=relation%>');
				rgraph.op.sum(<%=relation%>json, {  
				   type: 'fade:con',  
				   duration: 1500  
				});
				addLocalJson({"<%=relation%>" : <%=relation%>json});
				
				j("#log").dialog("close");		
			}
			
			
		}
		else {
			var n = j(".check:checked").length;
			if(n==0) {
				j("#<%=relation%>check").attr('checked','checked');
			}
			else {
				othernetwork = new Array();
				removeLocalJson("<%=relation%>");	
			}						
		}			
	});
	
	j("#<%=relation%>checkonly").dblclick(function(){
		
		othernetwork = new Array();
		bezierRatioSettings = new Array();
		if(j.isEmptyObject(<%=relation%>json)) {	
			<%=relation%>json = checkOnlyWithJSON('<%=authority%>', '<%=relation%>');
		}
		else {
			j("#log").dialog("open");		
			Log.write("Loading (from cache)... network: " + '<%=relation%>');
			reloadLocalJson({"<%=relation%>":<%=relation%>json});
			rgraph.loadJSON(<%=relation%>json);
			rgraph.refresh();
			j("#log").dialog("close");
		}
		
		<%for (String r : relations)
                {

                    if (r != relation)
                    {%>
			j('#<%=r + "check"%>').attr('checked','');			
		<%}
                }%>
		j('#<%=relation + "check"%>').attr('checked','checked');
		
	});
	<%}%>
	});	
	
	j(function() {
					
		j( "#sliderleveldistance" ).slider({
			orientation: "horizontal",
			min: 20,
			max: 400,
			value: 120,
			slide: function( event, ui ) {
				rgraph.config.levelDistance = ui.value;				
				rgraph.refresh();
			}
		});
		
		j( "#slider" ).slider({
			orientation: "horizontal",
			range: "min",
			min: 0,
			max: <%=configMaxDepth%>,
			value: <%=maxDepth%>,
			slide: function( event, ui ) {
				j( "#amount" ).val( ui.value );
				
				//j( ".sliderclass" ).slider( "option", "max", ui.value);
				j( ".sliderclass" ).slider( "option", "value", ui.value);								
				j( ".amountother" ).val( ui.value );
				
				rgraph.refresh();
			}
		});
		j( "#amount" ).val( j( "#slider" ).slider( "value" ) );
		
		
				
		<%for (String relation : relations)
            {%>
		j( "#slider<%=relation%>").slider({
			orientation: "horizontal",
			range: "min",
			min: 0,
			max: <%=customMaxDepths.get(relation)%>,
			value: <%=customMaxDepths.get(relation)%>,
			slide: function( event, ui ) {
				j( "#amount<%=relation%>").val( ui.value );				
				rgraph.refresh();
			}
		});
		j( "#amount<%=relation%>").val( j( "#slider<%=relation%>").slider( "value" ) );
		<%}%>
	});	
	

	
	j(function() {
		j( "#accordion" ).accordion({active: false});	
	});
	
	<%if (relations.length > 1)
            {%>
	j(function() {
		j( "#accordionlistshowed" ).accordion();
	});
	<%}%>

	j(function() {
		j( "#accordiongeneralmenu" ).accordion();
	});
			
	
	
	
	j('#<%=networkStarted + "check"%>').attr('checked','checked');
	

	j(function() {

		  var $slideMe = j("<div/>")
		                    .css({ position : 'absolute' , top : 10, left : 0}).css('background-color', 'white').css('white-space', 'nowrap')
		                    .text("Zoom in/out")
		                    .hide()


		  j("#sliderleveldistance").slider()
		                .find(".ui-slider-handle")
		                .append($slideMe)
		                .hover(function()
		                        { $slideMe.show()}, 
		                       function()
		                        { $slideMe.hide()}
		                )

	});

	j("#relationfragment").dialog({closeOnEscape: true, modal: true, autoOpen: false, width: 'auto'});
	j("#relationfragmenttwice").dialog({closeOnEscape: true, modal: true, autoOpen: false, width: 'auto'});
		
	
	function screenPage() {		
		
		j('#screen').hide();		
		
		rgraph.config.levelDistance = 200;
		rgraph.canvas.resize(winW, winH);
		rgraph.canvas.translate(-(rgraph.canvas.getSize().width)/6, 0);
		rgraph.refresh();
		
		var width = winW + "px";
		j('#infovis').css("width",width);
		j('#infovis').css("margin","auto");
		myLayout.open("west");
		myLayout.open("east");
		
	}	
	
	
	function printPageWithWest() {		
		printIt(true);
	}
	
	function printPageWithoutWest() {	
		printIt(false);
	}
	
	function printIt(isWestOpened) {
		
		j('#screen').show();
		
		rgraph.config.levelDistance = 100;
		
		
		rgraph.canvas.resize(900, 640);				
		if(isWestOpened) {
			myLayout.open("west");	
			rgraph.canvas.translate(-(rgraph.canvas.getSize().width)/8, 0);
		}	
		else {
			myLayout.close("west");	
		}
		myLayout.close("east");
		myLayout.close("north");
		myLayout.close("south");			
		
				
		rgraph.refresh();
		
		
		j('#infovis').css("width","900px");
		j('#infovis').css("margin","0");
		
		setTimeout(function(){
				window.print();
		},2000);
		
	}

	j( "#toolbar_print" ).buttonset();


	</script>
</html>