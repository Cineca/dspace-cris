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
	String fullname = (String) request.getAttribute("fullname");
	
%>


<c:set value="${researchertarget.dynamicField}" var="anagraficaObject"></c:set>
<c:set var="depthOld" value="${depth}"/>



<div id="innerprofiletarget">



	<c:choose>		
			<c:when test="${depthOld==1 && typo=='dept'}">		
					
					<div class="target-separator"><fmt:message key="network.profilefragment.memberofdept"/></div>
					
			</c:when>
			<c:otherwise>
					<div class="target-separator"><fmt:message key="network.profilefragment.targetseparator.and"/></div>				
			</c:otherwise>
	</c:choose>

	<div id="target-rp">

			<div class="rp-label"><fmt:message
								key="jsp.network.label.title.targetprofile" /></div>
					<div class="rp-header">
						<div class="rp-image">
						<c:choose>
							<c:when
								test="${!empty researchertarget.pict && !empty researchertarget.pict.value && researchertarget.pict.visibility==1}">

										<a target="_blank" href="../rp/${authoritytarget}">
											<img id="picture" name="picture"
												alt="${researchertarget.fullName} picture"
												src="<%=request.getContextPath()%>/researcherimage/${authoritytarget}"
												title="${researchertarget.fullName} picture" />
										</a>
															
							</c:when>
							<c:otherwise>

								<tr>

									<td class="image_td" colspan="2" align="center">

										<div class="image"></div></td>

								</tr>
							</c:otherwise>
						</c:choose>

					</div>
					<div class="rp-content">
							<div class="rp-name"><a target="_blank" href="../rp/${authoritytarget}"><c:if
							test="${researchertarget.honorific.visibility==1}">${researchertarget.honorific.value}</c:if>
							${researchertarget.fullName}</a></div>
							<c:forEach
							items="${researchertarget.title}" var="title" varStatus="counter">
							<c:if test="${title.visibility==1 && counter.count==1}">
								<div class="rp-title"><strong> ${title.value}</strong></div>
							</c:if>
							</c:forEach>
							<div class="rp-dept rp-dept-main">
													
								<c:if
						test="${researchertarget.dept.visibility==1 && !empty researchertarget.dept.value}">
						<span><fmt:message
								key="jsp.layout.hku.detail.researcher.department" />
						</span>
						<ul><li><c:url var="deptSearch"
								value="../rp/search.htm">
								<c:param name="advancedSyntax" value="true" />
								<c:param name="searchMode" value="Search" />
								<c:param name="queryString" value="dept:\"${researchertarget.dept.value}\"" />
							</c:url> <a target="_blank" href="${deptSearch}">${researchertarget.dept.value}</a>

							<a target="_blank" href="<%=request.getContextPath()%>/dnetwork/graph?dept=<%=URLEncoder.encode(researchertarget.getDept().getValue(),"UTF-8")%>"><span class="icon-network"><img src='../image/wheel-icon2.jpg' alt="<fmt:message key='jsp.network.label.link.network.dept'/>" title="<fmt:message key='jsp.network.label.link.network.dept'/>"/></span></a>
							<a target="_blank" href="<%=request.getContextPath()%>/network/${authoritytarget}"><span class="icon-network"><img src='../image/wheel-icon1.jpg' alt="<fmt:message key='jsp.layout.hku.network.researcher.link'><fmt:param value='<%= researchertarget.getFullName()%>'/></fmt:message>" title="<fmt:message key='jsp.layout.hku.network.researcher.link'><fmt:param value='<%= researchertarget.getFullName()%>'/></fmt:message>"/></span></a>
						</li></ul>
						
						
					</c:if>
					
							</div>
						
						</div>
					</div>
				
		</div>

		<c:choose>		
			<c:when test="${typo=='dept'}">		

			</c:when>
			<c:otherwise>
			<div class="target-separator"><fmt:message key="network.profilefragment.targetseparator.share"/></div>

			<div id="target-common">

				
					<c:choose>
						<c:when test="${depthOld==1 && typo=='dept'}">
						
						</c:when>
						<c:otherwise>
							<div style="font-size: 1.2em;" class="rp-label"><fmt:message key="network.profilefragment.title.targetcommon"/></div>

									<ul>
										<c:forEach var="relation" items="${relations}">

											<li><a class="relationspan"
												id="relation_${relation.key}"><fmt:message
														key="network.profilefragment.number.${relation.key}" />
													${relation.value}</a>
											</li>

										</c:forEach>
									</ul>
						</c:otherwise>
					</c:choose>
				
			</div>

		</c:otherwise>
		</c:choose>


<script type="text/javascript">
	
	
	j(".relationspan")
			.click(
					function() {						
								
						
						j("#log").dialog("open");
						Log.write("Loading...");
											
						var parameterId = this.id;
						var servletpathcaller = "<%= request.getServletPath() %>";
						var ajaxurlrelations = "<%= request.getContextPath() %>/networkdatarelations/${authority}";
						j.ajax( {
							url : ajaxurlrelations,
							data : {
								"servletpathcaller" : servletpathcaller,
								"relation" : parameterId,
								"with" : "${authoritytarget}"
							},
							success : function(data) {								
								j('#relationfragment').dialog("open");	
								j(".ui-dialog-titlebar").html("${fullname} / ${researchertarget.fullName} &nbsp; <a class='ui-dialog-titlebar-close ui-corner-all' href='#' role='button'><span class='ui-icon ui-icon-closethick'>close</span></a>");j(".ui-dialog-titlebar").show();
								j('#relationfragmentcontent').html(data);
								j('#relationfragment').dialog('option', 'position', 'center');
								j("#log").dialog("close");
							},
							error : function(data) {
								j('#relationfragment').dialog("close");
								j("#log").dialog("open");
								Log.write(data.statusText);
								j("#log").dialog("close");
							}
						});

					});
	j(".ui-dialog-titlebar").click(function() {	j('#relationfragment').dialog("close");});
</script>