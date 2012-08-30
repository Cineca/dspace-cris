<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>
	<div id="${holder.shortName}" class="showMoreLessBox-dark box">
		<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
		<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
		<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
		${holder.title}</h2>
   		<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>
		<table border="0" cellpadding="0" cellspacing="0" class="hkudata">

			<tr>
				<td valign="top" class="columnBody">

				<div class="box_info_and_picture">
				
				<table width="170px" border="0">
				<c:choose>
				<c:when
								test="${!empty researcher.pict.value && researcher.pict.visibility==1}">
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
					<c:set value="false" var="variantsvisibility"></c:set>
					<c:forEach items="${researcher.variants}"
								var="variantvisibilityitem">
								<c:if test="${variantvisibilityitem.visibility==1}">
									<c:set value="true" var="variantsvisibility"></c:set>
								</c:if>
					</c:forEach>
					 <c:if
								test="${!empty researcher.variants && variantsvisibility==true}">
			<tr>
			
				<td class="ncHead" colspan="2">
			
			<div id="variants" class="variants box">
			<h4 class="variantsLessControlElement control">
				<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif" />
				<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" class="hide" />
				<fmt:message key="jsp.layout.hku.detail.researcher.variants" /></h4>
   			<div class="collapsable expanded-content" style="display: none;">
				
				<table>
					<tbody>
						<tr>
							<td>
							


									<ul class="ncBody">
									<c:forEach items="${researcher.variants}" var="variant">
										<c:if test="${variant.visibility==1}">
											<li class="ncBody">${variant.value}</li>
										</c:if>
									</c:forEach>
									</ul>

							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
				</td>
			</tr>
			</c:if>
			<tr>
						<c:if
							test="${researcher.officeTel.visibility==1 && !empty researcher.officeTel.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.officetel" /></td>
							<td class="ncBody" nowrap="nowrap">${researcher.officeTel.value}</td>
						</c:if>
					</tr>
					<tr>
						<c:if
							test="${researcher.address.visibility==1 && !empty researcher.address.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.officeaddress" /></td>
							<td class="ncBody">${researcher.address.value}
							
							<c:if
							test="${researcher.email.visibility==1 && !empty researcher.email.value}">
							<a
								href="mailto:${researcher.email.value}"><img border="0" src="<%=request.getContextPath()%>/image/authority/email_go.png"></a>
							
							</c:if>
							
							</td>
						</c:if>
					</tr>
					<tr>
						<c:if
							test="${researcher.dept.visibility==1 && !empty researcher.dept.value}">
							<td class="ncHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.department" /></td>
							<td class="ncBody">
<c:url var="deptSearch" value="search.htm">
	<c:param name="advancedSyntax" value="true" />
	<c:param name="searchMode" value="Search" />
	<c:param name="queryString" value="dept:\"${researcher.dept.value}\"" />
</c:url>
<a href="${deptSearch}">${researcher.dept.value}</a></td>
						</c:if>
					</tr>

					
					<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['faculty'].real}" var="tipologia"></c:set>
						 
						<c:forEach var="value" items="${anagraficaObject.anagrafica4view[tipologia.shortName]}" varStatus="valueStatus">

						<c:if test="${value.visibility == 1}">
						<tr>
							<td class="ncHead">${tipologia.label}:</td>
							<td class="ncBody">
<c:url var="facultySearch" value="search.htm">
	<c:param name="advancedSyntax" value="true" />
	<c:param name="searchMode" value="Search" />
	<c:param name="queryString" value="rp_${tipologia.shortName}:\"${dyna:display(tipologia,value.value.real)}\"" />
</c:url>
<a href="${facultySearch}">${dyna:display(tipologia,value.value.real)}</a></td>
						</tr>	
						</c:if>
						
						</c:forEach>
					


					<c:if test="${(!empty researcher.cv.value || !empty researcher.cv.remoteUrl) 
						&& researcher.cv.visibility==1}">
					<tr>
						<td colspan="2" class="ncBody" nowrap="nowrap" align="center">
						<img alt="mark icon" src="<%=request.getContextPath()%>/image/wd2h.gif" />
						<a class="cv" href="<%=request.getContextPath()%>/researchercv/${authority}">
						<fmt:message
								key="jsp.layout.hku.detail.researcher.cv" /></a>
						<img alt="mark icon" src="<%=request.getContextPath()%>/image/wd2g.gif" /></td>
					</tr>
					</c:if>
						
						
				</table>

				
				</div>
				</td>



				<td>&nbsp;</td>






				<td class="columnBody" width="70%">

				<table class="hkudatadetail">
					<tr>
						<td colspan="2">
						<span class="header4"> <c:if
							test="${researcher.honorific.visibility==1}">${researcher.honorific.value}</c:if>
						${researcher.fullName}</span>

<!-- R-Badge -->
                  <c:if
							test="${!empty researcher.ridISI.value && researcher.ridISI.visibility==1}">
							<span id='badgeCont100000' style='width: 26px'><script
								src='http://labs.researcherid.com/mashlets?el=badgeCont100000&mashlet=badge&showTitle=false&className=a&rid=${researcher.ridISI.value}&size=small'></script></span>
						</c:if> 

<!-- no use -->
<!--
    					<img src="rid.jpg" alt="RID Badge" width="24" height="24" />
-->
                  <br />
						<c:if
							test="${researcher.chineseName.visibility==1}">
							<span class="header4">${researcher.chineseName.value}</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td valign="top"">
					<ul class="ncBody1"><c:forEach
							items="${researcher.title}" var="title">
							<c:if test="${title.visibility==1}">
								<li class="ncBody1"><strong> ${title.value}</strong></li>
							</c:if>
						</c:forEach></ul>
						</td>
					</tr>
					
					<c:set value="false" var="interestsvisibility"></c:set>
					<c:forEach items="${researcher.interests}"
						var="interestvisibilityitem">
						<c:if test="${interestvisibilityitem.visibility==1}">
							<c:set value="true" var="interestsvisibility"></c:set>
						</c:if>
					</c:forEach>
					<c:if
						test="${!empty researcher.interests && interestsvisibility==true}">
						<tr>
							<td colspan="2" class="columnHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.interests" /></td>

						</tr>
						<tr>
							<td colspan="2" valign="top">
							<ul class="ncBody1">
								<c:forEach items="${researcher.interests}" var="interest">
									<c:if test="${interest.visibility==1}">
										<li class="ncBody1">${interest.value}</li>
									</c:if>
								</c:forEach>
							</ul>
							</td>
						</tr>
					</c:if>			
					
					<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['myurls'].real}" var="tipologia"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'hkudatamyurls')}" var="invisibleMyUrls"></c:set>
					<c:if test="${invisibleMyUrls==false}">
					<tr>
						<td colspan="2" class="columnHead"><fmt:message
								key="jsp.layout.hku.detail.researcher.myurl" /></td>

						</tr>
						<tr>
							<td colspan="2" valign="top">
							<ul class="ncBody1">
								 
					<c:forEach var="value" items="${anagraficaObject.anagrafica4view[tipologia.shortName]}" varStatus="valueStatus">

						<c:if test="${value.visibility == 1}">
							
							<%--<c:set var="minheight" value="" />--%>
							<c:set var="minwidth" value="" />
							<c:set var="style" value="" />
						
							<c:if test="${tipologia.rendering.size > 1}">
								<c:set var="minwidth"
									value="min-width: ${tipologia.rendering.size}em;" />
							</c:if>
							
							<c:if test="${!empty minwidth && !subElement}">
								<c:set var="style" value="style=\" ${minwidth}\"" />
							</c:if>
							<c:set var="displayObject"
								value="${dyna:display(tipologia,value.value.real)}" />
								
							 <c:choose>
									<c:when test="${!empty dyna:getLinkValue(displayObject)}">
										<li class="ncBody1">
										<a target="_blank" href="${dyna:getLinkValue(displayObject)}">
										<c:choose>
											<c:when
												test="${!empty dyna:getLinkDescription(displayObject)}">
												<span${style}>${dyna:getLinkDescription(displayObject)}</span>
											</c:when>
											<c:otherwise>
												<span${style}>${dyna:getLinkValue(displayObject)}</span>
											</c:otherwise>
										</c:choose> </a>
										</li>
									</c:when>
									<c:otherwise>

										<c:if test="${!empty dyna:getLinkDescription(displayObject)}">
										<li class="ncBody1">
											<span${style}>${dyna:getLinkDescription(displayObject)}</span>
										</li>
										</c:if>


									</c:otherwise>
							</c:choose>	
							
							
						</c:if>
						
					</c:forEach>
			
							</ul>
							</td>
					</tr>
					</c:if>
					<%
					Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
					boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
					%>
					<c:set var="isAdmin" value="<%= isAdmin %>"/>
					
					<tr class="lastRow">
						<td colspan="2">
							<ul class="ncBody1">
						<c:if test="${isAdmin || !showStatsOnlyAdmin}">
<li class="ncBody1" style="list-style: none;display: inline">RP statistics<a href="<%=request.getContextPath()%>/rp/stats/rp.html?id=${authority}" title="See usage statistics for this Researcher Page">
						<img border="0" alt="Usage statistics for this Researcher Page" src="<%=request.getContextPath()%>/images/chart_curve.png" 
							 /></a>
</li>
						</c:if>
<li class="ncBody1" style="list-style: none;display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RP content alert
						<c:choose>
						<c:when test="${!subscribed}">						
						<a href="<%=request.getContextPath()%>/rp/tools/subscription/subscribe?id=${researcher.id}" title="Subscribe content email alert">
						<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/start-bell.png" 
							 /></a>
						</c:when>
						<c:otherwise>
						<a href="<%=request.getContextPath()%>/rp/tools/subscription/unsubscribe?id=${researcher.id}" title="Unubscribe the content email alert">
						<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/stop-bell.png" 
							 /></a>
						</c:otherwise>
						</c:choose>	
</li>
<li class="ncBody1" style="list-style: none;display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RSS
						<a href="<%=request.getContextPath()%>/open-search?query=authors_fauthority:${authority}&format=rss" title="Subscribe RSS content alert">
						<img border="0" alt="RSS" src="<%=request.getContextPath()%>/image/feed.png" />
						</a>
</li>

<li class="ncBody1" style="list-style: none;display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NETWORK
						<a href="<%=request.getContextPath()%>/network/${authority}"  title="See collaboration network">
						<img border="0" alt="Graph" src="<%=request.getContextPath()%>/image/custom_rgraph.png" />
						</a>
</li>
</ul>
						</td>
					</tr>
					
				</table>
				</td>
			</tr>
		</table>
		</div>
	</div>

<p></p>
		
		