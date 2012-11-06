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

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>

	<div id="tab-${area.id}">
					<c:forEach items="${propertiesHolders}" var="holder">
					
						<c:set
							value="${researcher:isBoxHidden(entity,holder.shortName)}"
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
<div id="${holder.shortName}" class="box ${holder.collapsed?"":"expanded"}">
  <h3><a href="#">${holder.title}</a></h3>
  <div>
    <p>
							<%
								if (fileURL == null) {
							%>

									<c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
									<c:forEach
										items="${propertiesDefinitionsInHolder[holder.shortName]}"
										var="tipologiaDaVisualizzare" varStatus="status">

			
										<c:if
											test="${dyna:instanceOf(tipologiaDaVisualizzare,'it.cilea.osd.jdyna.model.ADecoratorTypeDefinition')}">
											
											
												<c:set var="totalHit" value="0"/>
												<c:set var="limit" value="5"/>
												<c:set var="offset" value="0"/>											
												<c:set var="pageCurrent" value="0"/>	
												<c:set var="editmode" value="false"/>
												
												<div
													id="viewnested_${tipologiaDaVisualizzare.real.id}" class="viewnested">
														<img src="<%=request.getContextPath()%>/image/cris/bar-loader.gif" class="loader" />
															<fmt:message key="jsp.jdyna.nestedloading" />
												<span class="spandatabind nestedinfo">${tipologiaDaVisualizzare.real.id}</span>
												<span id="nested_${tipologiaDaVisualizzare.real.id}_totalHit" class="spandatabind">0</span>
												<span id="nested_${tipologiaDaVisualizzare.real.id}_limit" class="spandatabind">5</span>
												<span id="nested_${tipologiaDaVisualizzare.real.id}_pageCurrent" class="spandatabind">0</span>
												<span id="nested_${tipologiaDaVisualizzare.real.id}_editmode" class="spandatabind">false</span>
												</div>
										</c:if>
										<c:if
											test="${dyna:instanceOf(tipologiaDaVisualizzare,'it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition')}">
											<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
												hideLabel="${hideLabel}"
												values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}" />

										</c:if>
									</c:forEach>
							<%
								} else {
							%>
							<c:set var="holder" value="${holder}" scope="request" />
							<c:import url="${urljspcustom}" />

							<%
								}
							%>








						</c:if>
				</p>
		</div>	
</div>
					</c:forEach>
	</div>