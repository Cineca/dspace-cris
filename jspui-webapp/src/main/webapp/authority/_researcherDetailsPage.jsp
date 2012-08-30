<%@ page import="it.cilea.hku.authority.dspace.HKUAuthority"%>
<%@ page import="java.net.URL"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils"%>
<%@ page import="java.io.File"%>
<%@ page import="org.dspace.core.ConfigurationManager"%>
<%@ page import="org.dspace.browse.BrowseInfo"%>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="researcher" value="${researcher}" scope="request" />

<div id="content">
<h1><fmt:message key="jsp.layout.hku.detail.title-first" /> <c:choose>
	<c:when test="${!empty researcher.academicName.value}">
	${researcher.academicName.value}
</c:when>
	<c:otherwise>
	${researcher.fullName}
</c:otherwise>
</c:choose></h1>
<div>&nbsp;</div>
<table width="100%" align="left" cellpadding="0" cellspacing="0"
	style="padding: 0 0 10px 10px">
	<c:if test="${!researcher.status}">
		<tr class="warning">
			<td colspan="3"><fmt:message
				key="jsp.layout.hku.detail.researcher-disabled" /><a
				target="_blank"
				href="<%=request.getContextPath()%>/rp/administrator/researcherPages.htm?id=${researcher.id}&mode=position"><fmt:message
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

		<table width="100%" align="left" cellpadding="0" cellspacing="0">
			<tbody>

				<tr>
					<c:forEach items="${tabList}" var="area" varStatus="rowCounter">
						<td align="center" class="tb-head0">&nbsp;</td>

						<c:set var="tablink"><c:choose>
							<c:when test="${rowCounter.count == 1}">${root}/rp/${authority}</c:when>
							<c:otherwise>${root}/rp/${authority}/${area.shortName}.html</c:otherwise>
						</c:choose></c:set>

						<c:choose>
							<c:when test="${area.id == tabId}">
								<td nowrap="" align="center" class="tb-head1"><img
									border="0" 
									src="<%=request.getContextPath()%>/researchertabimage/${area.id}" alt="X">
								${area.title}</td>
							</c:when>
							<c:otherwise>
								<td nowrap="" align="center" class="tb-head2"><a
									href='${tablink}'><img
									border="0"
									src="<%=request.getContextPath()%>/researchertabimage/${area.id}" alt="X">
								${area.title}</a></td>
							</c:otherwise>
						</c:choose>

					</c:forEach>
					<td align="center" class="tb-head0">&nbsp;</td>
				</tr>
				<tr>
					<td bgcolor="#f9f9f9" valign="top" class="tb-body" colspan="9">

					<c:forEach items="${propertiesHolders}" var="holder">
						<c:set
							value="${researcher:isBoxHidden(researcher,holder.shortName)}"
							var="invisibleBox"></c:set>


						<c:if test="${invisibleBox==false}">

							<%!public URL fileURL;%>

							<c:set var="urljspcustom"
								value="/authority/jdyna/custom/${holder.shortName}.jsp" scope="request" />
								
							<%
								String filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

										fileURL = pageContext.getServletContext().getResource(
												filePath);
							%>

							<%
								if (fileURL == null) {
							%>

							<div id="${holder.shortName}" class="showMoreLessBox box">
								<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
								<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
								<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
								${holder.title}</h2>
    							<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>

							<table width="100%" cellpadding="0" cellspacing="4">
								<tr>
									<td><c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
									<c:forEach
										items="${propertiesDefinitionsInHolder[holder.shortName]}"
										var="tipologiaDaVisualizzare" varStatus="status">

										<c:if
											test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
											<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
												hideLabel="${hideLabel}"
												values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}" />

										</c:if>
										<c:if
											test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


											<c:set var="urljspcustom"
												value="jdyna/custom/displaystructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" scope="request" />
										<%
								 filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

										fileURL = pageContext.getServletContext().getResource(
												filePath);
							%>

							<%
								if (fileURL == null) {
							%>
								<c:import url="${urljspcustom}" />
								
								<% } %>
										</c:if>

									</c:forEach></td>
								</tr>


							</table>


							</div>

							</div>


							<p></p>

							<%
								} else {
							%>
							<c:set var="holder" value="${holder}" scope="request" />
							<c:import url="${urljspcustom}" />

							<%
								}
							%>








						</c:if>


					</c:forEach></td>
				</tr>
			</tbody>
		</table>
		</div>

		</td>
	</tr>
</table>
</div>
