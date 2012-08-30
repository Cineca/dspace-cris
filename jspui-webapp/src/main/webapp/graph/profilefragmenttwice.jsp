<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.cilea.hku.authority.model.ResearcherPage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<%
	
	ResearcherPage researchertarget = (ResearcherPage) request.getAttribute("researchertarget");
	String authoritytarget = (String) request.getAttribute("authoritytarget");
	String authority = (String) request.getAttribute("authority");
	Map<String,Integer> relations = (Map<String,Integer>) request.getAttribute("relations");
	String depth = (String) request.getAttribute("depth");
	String typo = (String) request.getAttribute("typo");
%>

<c:set value="${researchertarget.dynamicField}" var="anagraficaObject"></c:set>
			
	
			
			
			<div id="relationdivtwice">	
			
			<c:choose>
			<c:when test="${typo=='dept'}">		
				<c:if test="${depth==1}">
					<div class="relationlist">
						<h3 id="spanmemberof"><fmt:message key="network.profilefragment.memberofdept"/></h3>
					</div>				
				</c:if> 
			</c:when>
			<c:otherwise>			
			
			<div class="boxrelationlist">
			<div class="relationlistarrowup"><img src="../image/arrow_relation_up.png"></div>
			<div class="relationlist">
			<ul>
			<c:forEach var="relation" items="${relations}">
			
				<li><a class="relationspantwice" id="relation_${relation.key}"><fmt:message key="network.profilefragment.number.${relation.key}"/> ${relation.value}</a></li>
					
			</c:forEach>
			</ul>
			</div>
			<div class="relationlistarrowdown"><img src="../image/arrow_relation_down.png"></div>
			</div>
			</c:otherwise>
			</c:choose>
			</div>
		
			<div id="profiletargettwiceminimized" class="profile">		
				
						<c:choose>
				<c:when
								test="${!empty researchertarget.pict && !empty researchertarget.pict.value && researchertarget.pict.visibility==1}">
						<div class="imagemin">
							
								<img id="picturemin" name="picturemin"
									alt="${researchertarget.fullName} picture"
									src="<%=request.getContextPath()%>/researcherimage/${authoritytarget}"
									title="${researchertarget.fullName} picture" />
							
						</div>
					</c:when>
				<c:otherwise>
							


						<div class="imagemin">
							
								
							
						</div>

					</c:otherwise>
					</c:choose>
					<div class="otherminimized">
					<span class="header4" id="headermin"><c:if
							test="${researchertarget.honorific.visibility==1}">${researchertarget.honorific.value}</c:if>
						${researchertarget.fullName}</span>
  				<span class="network-degree">
				${depth}
				<c:if test="${depth==1}">
				<sup>st</sup>
				</c:if>
				<c:if test="${depth==2}">
				<sup>nd</sup>
				</c:if>
				<c:if test="${depth==3}">
				<sup>rd</sup>
				</c:if>
				<c:if test="${depth>3}">
				<sup>th</sup>
				</c:if>
				</span>
					<c:if
							test="${researchertarget.chineseName.visibility==1}">
							<br/>
							<span class="header4" id="headermin">${researchertarget.chineseName.value}</span> 
					</c:if>
					<c:if test="${researchertarget.internalRP}"><a target="_blank" href="../rp/${authoritytarget}"><span id="profileTitle"><img src='../image/profile.png' alt="<fmt:message key='jsp.network.label.profile.title'/>" title="<fmt:message key='jsp.network.label.profile.title'/>"/></span></a></c:if>
					<div id="titlemin">
					
						<c:forEach
							items="${researchertarget.title}" var="title" varStatus="counter">
							<c:if test="${title.visibility==1 && counter.count == 1}">
								
								<div id="ulmin"><strong> ${title.value}</strong></div>
							</c:if>
						</c:forEach>
					
					</div>
					
		
				
				</div>
		
		
					<div id="deptmin">
						<c:if
							test="${researchertarget.dept.visibility==1 && !empty researchertarget.dept.value}">
							<fmt:message
								key="jsp.layout.hku.detail.researcher.department" />
							
<c:url var="deptSearch" value="../rp/search.htm">
	<c:param name="advancedSyntax" value="true" />
	<c:param name="searchMode" value="Search" />
	<c:param name="queryString" value="dept:\"${researchertarget.dept.value}\"" />
</c:url>
<a target="_blank" href="${deptSearch}">${researchertarget.dept.value}</a>
<span> <input type="checkbox" class="deptvisualizationprofiletwice" id="deptvisualizationprofiletwice" value="<%= URLEncoder.encode(researchertarget.getDept().getValue(),"UTF-8") %>"/><label
							for="deptvisualizationprofiletwice">See DGraph</label> </span>
						</c:if>
					</div>
					
							
				<div class="viewnetworkvisualizationtd">			
						
						
						
					<c:if test="${researchertarget.internalRP}"><a href="../network/${authoritytarget}"><fmt:message	key="jsp.layout.hku.network.researcher.link"><fmt:param><c:if
							test="${researchertarget.honorific.visibility==1}">${researchertarget.honorific.value}</c:if> ${researchertarget.fullName}</fmt:param></fmt:message></a></c:if>
						
						
					
				</div>
		
				</div>
<script type="text/javascript">




j( ".deptvisualizationprofiletwice" ).button({ 
	icons: {
    	primary: "icon-network"
	},
	text: false
});


j(".deptvisualizationprofiletwice").click(function(){

	location.href = "<%=request.getContextPath()%>/dnetwork/graph?dept=" + this.value;
	return true;		
	
});

		
	j(".relationspantwice")
			.click(
					function() {						
					
						
						j("#log").dialog("open");
						Log.write("Loading...");
												
						
						var parameterId = this.id;
						var ajaxurlrelations = "<%= request.getContextPath() %>/networkdatarelations/${authority}"
						j.ajax( {
							url : ajaxurlrelations,
							data : {
								"relation" : parameterId,
								"with" : "${authoritytarget}"
							},
							success : function(data) {								
								j('#relationfragmenttwice').dialog("open");	
								j(".ui-dialog-titlebar").html("${fullname} / ${researchertarget.fullName} &nbsp; <a class='ui-dialog-titlebar-close ui-corner-all' href='#' role='button'><span class='ui-icon ui-icon-closethick'>close</span></a>");j(".ui-dialog-titlebar").show();
								j('#relationfragmenttwicecontent').html(data);
								j('#relationfragmenttwice').dialog('option', 'position', 'center');
								j("#log").dialog("close");
							},
							error : function(data) {
								j('#relationfragmenttwice').dialog("close");
								j("#log").dialog("open");
								Log.write(data.statusText);
								j("#log").dialog("close");
							}
						});

					});
	
	j(".ui-dialog-titlebar").click(function() {	j('#relationfragmenttwice').dialog("close");});
</script>