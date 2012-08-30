<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cilea.osd.jdyna.model.PropertiesDefinition"%>
<%@page
	import="it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition"%>
<%@page import="it.cilea.hku.authority.model.dynamicfield.AccessLevelConstants"%>

<%@ taglib uri="jdynatags" prefix="dyna"%>
<%
	Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
	boolean isAdmin = (isAdminB != null ? isAdminB.booleanValue()
			: false);
%>
<%
	boolean disabled = false;
	if (!isAdmin) {
%>
	<c:set var="disabled" value="true"/>	
<%
		}
	%>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="admin"><%=isAdmin%></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>
<c:set var="disabledfield" value=" disabled=\"disabled\" "/>

	<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend><label>
<c:if test="${isThereMetadataNoEditable eq true}">

	<span class="green"><fmt:message
		key='jsp.layout.hku.researcher.message.sendemail'>
		<fmt:param>
			<fmt:message
				key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
		</fmt:param>
	</fmt:message></span>

</c:if> </label>
<fieldset><legend>		
		<fmt:message key="jsp.layout.hku.detail.fieldset-legend.isi" />
		</legend>
<c:if test="${isThereMetadataNoEditable eq true}">
                <div class="green"><fmt:message key='jsp.layout.hku.researcher.message.isi' /></div>
</c:if>		
		<table width="98%" align="left" cellpadding="0" cellspacing="4">

			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.isirid" /></span></td>
				<td><form:input path="ridISI.value" size="80%"  disabled="${disabled}"/></td>
				<td><form:checkbox path="ridISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.linkisirid" /></span></td>
				<td><form:input path="ridLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="ridLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.papercountisi" /></span></td>
				<td><form:input path="paperCountISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperCountISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.paperlinkisi" /></span></td>
				<td><form:input path="paperLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.citationcountisi" /></span></td>
				<td><form:input path="citationCountISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationCountISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.citationlinkisi" /></span></td>
				<td><form:input path="citationLinkISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationLinkISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorsisi" /></span></td>
				<td><form:input path="coAuthorsISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.hindexisi" /></span></td>
				<td><form:input path="hindexISI.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="hindexISI.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>									
			
			</table>
		</fieldset>
		<br/>
		<br/>
		<fieldset><legend>		
			<fmt:message key="jsp.layout.hku.detail.fieldset-legend.scopus" />
		</legend>
<c:if test="${isThereMetadataNoEditable eq true}">
                <div class="green"><fmt:message key='jsp.layout.hku.researcher.message.scopus' /></div>
</c:if>
		<table width="98%" align="left" cellpadding="0" cellspacing="4">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.idauthorscopus" /></span></td>
				<td><form:input path="authorIdScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="authorIdScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.idauthorlinkscopus" /></span></td>
				<td><form:input path="authorIdLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="authorIdLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.papercountscopus" /></span></td>
				<td><form:input path="paperCountScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperCountScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.paperlinkscopus" /></span></td>
				<td><form:input path="paperLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="paperLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.citationcountscopus" /></span></td>
				<td><form:input path="citationCountScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationCountScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.citationlinkscopus" /></span></td>
				<td><form:input path="citationLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="citationLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>	
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorscopus" /></span></td>
				<td><form:input path="coAuthorsScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.coauthorlinkscopus" /></span></td>
				<td><form:input path="coAuthorsLinkScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="coAuthorsLinkScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.scopuswos.label.hindexscopus" /></span></td>
				<td><form:input path="hindexScopus.value" size="80%" disabled="${disabled}"/></td>
				<td><form:checkbox path="hindexScopus.visibility" value="1"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>									
			
		</table>
		</fieldset>
		
		
		
		<%
									List<String[]> logicalGroups = new ArrayList<String[]>();

									String[] mathscinet = new String[] { "mathscinetauthorid",
											"mathscinetearliestindex", "mathscinetpublications",
											"mathscinetcitations", "mathscinetcoauthors" };
									String[] socialscience = new String[] { "socialscienceperid",
											"socialscienceauthorrank", "socialsciencepapers",
											"socialsciencedownloads", "socialsciencecitations" };
									String[] biomed = new String[] { "biomedpersonalpage",
											"biomedcoauthors", "biomedpublications" };
									String[] repec = new String[] { "repecnumber",
											"repecpaperdownload", "repecarticledownload" };
									String[] acm = new String[] { "acmauthor", "acmpublicationyear",
											"acmpublicationcount", "acmcitationcount",
											"acmdownloadsixweeks", "acmdownloadtwomonths" };
									//String[] pp = new String[]{"pppapers", "ppcitations", "pphindex", "ppgindex"};
									String[] scholaruniverse = new String[] { "scholaruniverseprofile",
											"scholaruniversecoauthors" };
									String[] mathematics = new String[] { "mathematicsid",
											"mathematicsdegree", "mathematicsadvisor",
											"mathematicsstudents" };
									
									String[] pubmed = new String[] { "pmcdataItemsInPubmed",
											"pmcdataItemsInPMC", "pmcdataCitations",
											"pmcdataItemsCited" };

									logicalGroups.add(mathscinet);
									logicalGroups.add(socialscience);
									logicalGroups.add(biomed);
									logicalGroups.add(repec);
									logicalGroups.add(acm);
									//logicalGroups.add(pp);
									logicalGroups.add(scholaruniverse);
									logicalGroups.add(mathematics);
									logicalGroups.add(pubmed);

									pageContext.setAttribute("logicalGroups", logicalGroups);
								%>
			
			
			<c:forEach
					items="${logicalGroups}"
							var="groups">
				<br/>
				<br/>
				<fieldset><legend>		
				<fmt:message key="jsp.layout.hku.detail.fieldset-legend.group${groups[0]}" />
				</legend>			
<c:if test="${isThereMetadataNoEditable eq true}">
                                <div class="green"><fmt:message key="jsp.layout.hku.researcher.message.group${groups[0]}" /></div>
</c:if>
				<c:forEach
					items="${groups}"
							var="group">						
							
					<c:forEach
							items="${propertiesDefinitionsInHolder[holder.shortName]}"
							var="tipologiaDaVisualizzare">

							
							<c:if test="${tipologiaDaVisualizzare.shortName eq group}">
							
														<c:choose>							
							<c:when
								test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties"
									validationParams="${parameters}" visibility="true"/>

							</c:if>
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">
								
								
										<c:set var="urljspcustom" value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
										<c:catch var="noCustomJSP">										
										<c:import url="${urljspcustom}" />
										</c:catch>	
								
														
							</c:if>
						
							</c:when>
							<c:otherwise>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
																									
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabledfield}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties"
									validationParams="${parameters}" visibility="false"/>
									
								</c:if>
								<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">
									
									
										<c:set var="urljspcustom" value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
										<c:catch var="noCustomJSP">										
										<c:import url="${urljspcustom}" />
										</c:catch>								
									
								
								</c:if>
							</c:if>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">
								
								<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
								
								
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabledfield}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties"
									validationParams="${parameters}" visibility="true"/>
									
								</c:if>
								<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">

				
										<c:set var="urljspcustom" value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
										<c:catch var="noCustomJSP">										
										<c:import url="${urljspcustom}" />
										</c:catch>
									
									
								</c:if>
								</c:if>
							</c:otherwise>
							</c:choose>
							
							</c:if>				
							
							
						</c:forEach>
					</c:forEach>
					</fieldset>
			</c:forEach>


<p></p>
<div id="hidden_firstpp">
<fieldset><legend> <a
	onclick="Effect.toggle('hidden_appearpp', 'appear'); Effect.toggle('toggle_appearpp', 'blind'); Effect.toggle('hidden_firstpp', 'blind'); return false;"
	href="#"> <span id="toggle_appearpp"> <img
	src="<%=request.getContextPath()%>/images/collapse.gif" border="0" />
</span></a><fmt:message key="jsp.layout.hku.detail.fieldset-legend.grouppppapers" /> </legend><label></label>
<c:if test="${isThereMetadataNoEditable eq true}">
        <div class="green"><fmt:message key='jsp.layout.hku.researcher.message.grouppppapers' /></div>
</c:if>

<table width="98%" align="left" cellpadding="0" cellspacing="4">


	<tr>
		<td>
		
		<span style="text-align: justify; font-size: 9pt; display: block; width: 58em;">To obtain metrics, and input here, please download and install on your PC, “Publish or Perish” (PoP) software from <a href="http://www.harzing.com/pop.htm" target="_blank">http://www.harzing.com/pop.htm</a>. After installed on your PC, open PoP, enter your name into PoP, and choose the subject area on the right. Click on Lookup. Check the list of entries below, and remove errors or duplicates. Record in the boxes below, the PoP numbers given for, Papers, Citations, h index, and g index.</span>
		
		
		</td>
	</tr>
	<tr>
	<td>
		<%
			

			String[] pp = new String[] { "pppapers", "ppcitations", "pphindex",
					"ppgindex", "ppdateinput" , "ppfile"};
			
			pageContext.setAttribute("pp", pp);
		%>


		<c:forEach items="${pp}" var="group">

			<c:forEach items="${propertiesDefinitionsInHolder[holder.shortName]}"
				var="tipologiaDaVisualizzare">


				<c:if test="${tipologiaDaVisualizzare.shortName eq group}">

					<c:choose>
						<c:when
							test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties"
									validationParams="${parameters}" visibility="true" />

							</c:if>
							<c:if
								test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


								<c:set var="urljspcustom"
									value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
								<c:catch var="noCustomJSP">
									<c:import url="${urljspcustom}" />
								</c:catch>


							</c:if>

						</c:when>
						<c:otherwise>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">

									<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
										disabled="${disabledfield}"
										propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
										ajaxValidation="validateAnagraficaProperties"
										validationParams="${parameters}" visibility="false" />

								</c:if>
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


									<c:set var="urljspcustom"
										value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
									<c:catch var="noCustomJSP">
										<c:import url="${urljspcustom}" />
									</c:catch>


								</c:if>
							</c:if>
							<c:if
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">

								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">


									<dyna:edit tipologia="${tipologiaDaVisualizzare.object}"
										disabled="${disabledfield}"
										propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
										ajaxValidation="validateAnagraficaProperties"
										validationParams="${parameters}" visibility="true" />

								</c:if>
								<c:if
									test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRestrictedField'}">


									<c:set var="urljspcustom"
										value="custom/editstructuralmetadata/${tipologiaDaVisualizzare.shortName}.jsp" />
									<c:catch var="noCustomJSP">
										<c:import url="${urljspcustom}" />
									</c:catch>


								</c:if>
							</c:if>
						</c:otherwise>
					</c:choose>

				</c:if>


			</c:forEach>

		</c:forEach>
		</td>
	</tr>
</table>
</fieldset>
</div>
<div id="hidden_appearpp" style="display: none;">
<fieldset><legend> <a
	onclick="Effect.toggle('hidden_appearpp', 'appear'); Effect.toggle('toggle_appearpp', 'appear'); Effect.toggle('hidden_firstpp', 'slide');return false;"
	href="#"> <span id="less_morepp"> <img
	src="<%=request.getContextPath()%>/images/expand.gif" border="0" /> </span> </a>${holder.title}</legend><label></label></fieldset>
</div>
<p></p></div>
		<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>
		<p></p>