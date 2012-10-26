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

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>

		<table align="center" class="miscTable">
			<tbody>

				<tr>
					<c:forEach items="${tabList}" var="area" varStatus="rowCounter">
						

						<c:set var="tablink"><c:choose>
							<c:when test="${rowCounter.count == 1}">${root}/cris/${specificPartPath}/${authority}</c:when>
							<c:otherwise>${root}/cris/${specificPartPath}/${authority}/${area.shortName}.html</c:otherwise>
						</c:choose></c:set>

						<c:choose>
							<c:when test="${area.id == tabId}">
								<td align="center" class="tb-head0">&nbsp;</td>
								<td nowrap="" align="center" class="tb-head1"><img
									border="0" 
									src="<%=request.getContextPath()%>/cris/researchertabimage/${area.id}" alt="X">
								${area.title}</td>
							</c:when>
							<c:otherwise>
								<td nowrap="" align="center" class="tb-head2" id="tb-head2-${area.shortName}">
									<img
										src="<%=request.getContextPath()%>/image/jdyna/indicator.gif"
			    						class="loader" />
								</td>							
							</c:otherwise>
						</c:choose>

					</c:forEach>
					<td align="center" class="tb-head0">&nbsp;</td>
				</tr>
				<tr>
					<td bgcolor="#f9f9f9" valign="top" class="tb-body" colspan="0">

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

							<%
								if (fileURL == null) {
							%>

							<div id="${holder.shortName}" class="showMoreLessBox box">
								<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
								<img src="<%=request.getContextPath() %>/image/cris/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
								<img src="<%=request.getContextPath() %>/image/cris/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
								${holder.title}</h2>
    							<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>

							<table width="100%" cellpadding="0" cellspacing="4">
								<tr>
									<td><c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
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
													id="viewnested_${tipologiaDaVisualizzare.shortName}"
													class="previewdialog">

														<div id="log1_${tipologiaDaVisualizzare.shortName}" class="log">
															<img
																src="<%=request.getContextPath()%>/image/cris/bar-loader.gif"
																id="loader1_${tipologiaDaVisualizzare.shortName}" class="loader" />
															<div id="logcontent1_${tipologiaDaVisualizzare.shortName}" class="logcontent"></div>
														</div>

												</div>
				
												
												<script type="text/javascript">		
				
													LoaderSnippet.write("Loading... ${tipologiaDaVisualizzare.label}", "logcontent1_${tipologiaDaVisualizzare.shortName}");												
													var parameterId = this.id;																	
													var ajaxurlrelations = "<%=request.getContextPath()%>/cris/${specificPartPath}/viewNested.htm";
													j.ajax( {
														url : ajaxurlrelations,
														data : {																			
															"elementID" : parameterId,
															"parentID" : ${entity.id},
															"typeNestedID" : ${tipologiaDaVisualizzare.real.id},
															"pageCurrent": ${pageCurrent},
															"offset": ${offset},
															"limit": ${limit},
															"editmode": 'false',
															"totalHit": ${totalHit},
															"admin": ${admin}
														},
														success : function(data) {																										
															j('#viewnested_${tipologiaDaVisualizzare.shortName}').html(data);								
															
														},
														error : function(data) {
															
															LoaderSnippet.write(data.statusText, "logcontent1_${tipologiaDaVisualizzare.shortName}");
															
														}
													});
																																				
												
												</script>
														
										</c:if>
										<c:if
											test="${dyna:instanceOf(tipologiaDaVisualizzare,'it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition')}">
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