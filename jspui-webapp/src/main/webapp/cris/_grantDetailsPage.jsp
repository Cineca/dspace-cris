<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils"%>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="grant" value="${grant}" scope="request" />

<div id="content">
<h1><fmt:message key="jsp.layout.hku.detail.grant" /></h1>
<div>&nbsp;</div>
<table width="100%" align="left" cellpadding="0" cellspacing="0"
	style="padding: 0 0 10px 10px">
	<c:if test="${!grant.status}">
		<tr class="warning">
			<td colspan="3"><fmt:message
				key="jsp.layout.hku.detail.grant-disabled" /><a
				target="_blank"
				href="<%=request.getContextPath()%>/rp/tools/editGrantDynamicData.htm?id=${grant.id}"><fmt:message
				key="jsp.layout.hku.detail.grant-disabled.fixit" /></a></td>
		</tr>
	</c:if>


	<tr>

		<td>

		<div id="record">

		<table width="100%" style="margin-top: 0pt;">
			<tbody>

				<tr class="itemMetadataRow">
					<th scope="row">
					<div style="float: left;">
					</div>
						<fmt:message key="jsp.layout.hku.detail.grant.projectcode" />
					</th>
					<td>${grant.rgCode}</td>	
				</tr>
				
				<tr class="itemMetadataRow">					
					<th scope="row">
					<div style="float: left;">
					</div>
						<fmt:message key="jsp.layout.hku.detail.grant.principalinvestigator" />
					</th>
					<td>
					<c:choose>
					<c:when test="${!empty grant.investigator.extInvestigator}">
					<ul class="topmenu" id="css3menu1">
						<li class="topmenu"><span>${grant.investigator.extInvestigator}</span>
						<ul>
							<li><a title="click to search" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${grant.investigator.extInvestigator}">Grant</a></li>						
						</ul>
						</li>
					</ul>
					<a class="rightpad" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${grant.investigator.extInvestigator}"><img border="0" src="../../image/authority/grant.jpg"></a>
					<br/>					
				
						
					</c:when>
					<c:otherwise>
					<c:set var="rpkey" value="${researcher:rpkey(grant.investigator.intInvestigator.id)}"/>
					
					<ul class="topmenu" id="css3menu1">					
						<li class="topmenu"><a title="click and go to rp page" href="../${rpkey}"><span>${grant.investigator.intInvestigator.fullName}</span></a>
						<ul>
							<li><a title="click to search" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${rpkey}">Grant</a></li>
							<li><a title="click and go to rp page" href="../${rpkey}">ResearcherPage</a></li>						
						</ul>
						</li>
					</ul>
					<a class="rightpad" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${rpkey}"><img border="0" src="../../image/authority/grant.jpg"></a>
					<a class="rightpad" href="../${rpkey}"><img border="0" src="../../image/authority/newauthority.jpg"></a>
					
					</c:otherwise>
					</c:choose>
					</td>	
				</tr>
						
				<c:if test="${!empty grant.coInvestigators}">
				<tr class="itemMetadataRow">
					<th scope="row">
					<div style="float: left;">
					</div>
						<fmt:message key="jsp.layout.hku.detail.grant.coinvestigators" />
					</th>
					<td>
					
					<c:forEach items="${grant.coInvestigators}" var="coinv">
					
					
					<c:choose>
					<c:when test="${!empty coinv.extInvestigator}">
					<ul class="topmenu" id="css3menu1">
						<li class="topmenu"><span>${coinv.extInvestigator}</span>
						<ul>
							<li><a title="click to search" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${coinv.extInvestigator}">Grant</a></li>						
						</ul>
						</li>
					</ul>
					<a class="rightpad" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${coinv.extInvestigator}"><img border="0" src="../../image/authority/grant.jpg"></a>
					<br/>				
				
						
					</c:when>
					<c:otherwise>
					<c:set var="rpkey" value="${researcher:rpkey(coinv.intInvestigator.id)}"/>
					<ul class="topmenu" id="css3menu1">					
						<li class="topmenu"><a title="click and go to rp page" href="../${rpkey}"><span>${coinv.intInvestigator.fullName}</span></a>
						<ul>
							<li><a title="click to search" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${rpkey}">Grant</a></li>
							<li><a title="click and go to rp page" href="../${rpkey}">ResearcherPage</a></li>						
						</ul>
						</li>
					</ul>
					<a class="rightpad" href="../searchGrants.htm?advancedSyntax=true&amp;searchMode=Search&amp;queryString=investigators:${rpkey}"><img border="0" src="../../image/authority/grant.jpg"></a>
					<a class="rightpad" href="../${rpkey}"><img border="0" src="../../image/authority/newauthority.jpg"></a>
					<br/>	
					</c:otherwise>
					</c:choose>
					
					
					</c:forEach>
					
					</td>	
				</tr>
				</c:if>
				<c:forEach items="${propertiesHolders}" var="holder">
					
						<c:forEach
								items="${propertiesDefinitionsInHolder[holder.shortName]}"
										var="tipologiaDaVisualizzare" varStatus="status">
											<c:set var="showit" value="false" target="java.lang.Boolean"/>
											<c:set var="forvisibility" value="${grant.anagrafica4view[tipologiaDaVisualizzare.shortName]}" target="java.lang.Boolean"/>
											<c:if test="${!empty forvisibility}">
												<c:forEach items="${forvisibility}" var="vis">
													<c:if test="${vis.visibility==1}">
														<c:set var="showit" value="true" target="java.lang.Boolean"/>
													</c:if>
											</c:forEach>
											</c:if>
											<c:if test="${showit}">
											<tr class="itemMetadataRow">						
											<th scope="row">
											<div style="float: left;">
											</div>
												${tipologiaDaVisualizzare.label}
											</th>
											<td>
											<c:choose>
											<c:when test="${dyna:isContainedList(tipologiaDaVisualizzare.shortName,changedToLink)}">
											
												<c:forEach var="value" items="${grant.anagrafica4view[tipologiaDaVisualizzare.shortName]}" varStatus="iterationStatus">
												<a href="../searchGrants.htm?advancedSyntax=true&searchMode=Search&queryString=${tipologiaDaVisualizzare.shortName}:${value}">
													${value}
												</a>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<dyna:display tipologia="${tipologiaDaVisualizzare.real}" hideLabel="true"							
												values="${grant.anagrafica4view[tipologiaDaVisualizzare.shortName]}" />
											</c:otherwise>
											</c:choose>
											</td>	
											
											</tr>
											</c:if>
						</c:forEach>
					
				</c:forEach>
			</tbody>
		</table>
		</div>

		</td>
	</tr>
</table>
</div>
