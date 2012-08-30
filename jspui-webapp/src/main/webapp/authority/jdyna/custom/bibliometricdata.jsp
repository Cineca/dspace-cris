<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el" %>
<c:set value="true" var="invisibleScopus"></c:set>
					<c:choose>
						<c:when
							test="${researcher.authorIdScopus.visibility==1 && researcher.authorIdLinkScopus.visibility==1 && !empty researcher.authorIdLinkScopus.value}">
							<c:set value="false" var="invisibleScopus"></c:set>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.authorIdScopus.visibility==1 && researcher.authorIdLinkScopus.visibility==0 && !empty researcher.authorIdLinkScopus.value}">
								<c:set value="false" var="invisibleScopus"></c:set>
							</c:if>
						</c:otherwise>
					</c:choose>

					<c:if test="${invisibleScopus==true}">
						<c:choose>
							<c:when
								test="${researcher.paperCountScopus.visibility==1 && researcher.paperLinkScopus.visibility==1 && !empty researcher.paperLinkScopus.value}">
								<c:set value="false" var="invisibleScopus"></c:set>
							</c:when>
							<c:otherwise>
								<c:if
									test="${researcher.paperCountScopus.visibility==1 && researcher.paperLinkScopus.visibility==0 && !empty researcher.paperLinkScopus.value}">
									<c:set value="false" var="invisibleScopus"></c:set>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if test="${invisibleScopus==true}">
							<c:choose>
								<c:when
									test="${researcher.citationCountScopus.visibility==1 && researcher.citationLinkScopus.visibility==1 && !empty researcher.citationCountScopus.value}">
									<c:set value="false" var="invisibleScopus"></c:set>
								</c:when>
								<c:otherwise>
									<c:if
										test="${researcher.citationCountScopus.visibility==1 && researcher.citationLinkScopus.visibility==0 && !empty researcher.citationCountScopus.value}">
										<c:set value="false" var="invisibleScopus"></c:set>
									</c:if>
								</c:otherwise>
							</c:choose>

							<c:if
								test="${researcher.hindexScopus.visibility==1 && !empty researcher.hindexScopus.value}">
								<c:set value="false" var="invisibleScopus"></c:set>
							</c:if>
							<c:if test="${invisibleScopus==true}">
								<c:choose>
									<c:when
										test="${researcher.coAuthorsScopus.visibility==1 && researcher.coAuthorsLinkScopus.visibility==1 && !empty researcher.coAuthorsLinkScopus.value}">
										<c:set value="false" var="invisibleScopus"></c:set>
									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose>
							</c:if>
						</c:if>
					</c:if>


					<c:set value="true" var="invisibleISI"></c:set>
					<c:choose>
						<c:when
							test="${researcher.ridISI.visibility==1 && researcher.ridLinkISI.visibility==1 && !empty researcher.ridISI.value}">
							<c:set value="false" var="invisibleISI"></c:set>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.ridISI.visibility==1 && researcher.ridLinkISI.visibility==0 && !empty researcher.ridISI.value}">
								<c:set value="false" var="invisibleISI"></c:set>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:if test="${invisibleISI==true}">
						<c:choose>
							<c:when
								test="${researcher.paperCountISI.visibility==1 && researcher.paperLinkISI.visibility==1 && !empty researcher.paperCountISI.value}">
								<c:set value="false" var="invisibleISI"></c:set>

							</c:when>
							<c:otherwise>
								<c:if
									test="${researcher.paperCountISI.visibility==1 && researcher.paperLinkISI.visibility==0 && !empty researcher.paperCountISI.value}">
									<c:set value="false" var="invisibleISI"></c:set>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if test="${invisibleISI==true}">
							<c:choose>
								<c:when
									test="${researcher.citationCountISI.visibility==1 && researcher.citationLinkISI.visibility==1 && !empty researcher.citationCountISI.value}">
									<c:set value="false" var="invisibleISI"></c:set>
								</c:when>
								<c:otherwise>
									<c:if
										test="${researcher.citationCountISI.visibility==1 && researcher.citationLinkISI.visibility==0 && !empty researcher.citationCountISI.value}">
										<c:set value="false" var="invisibleISI"></c:set>
									</c:if>
								</c:otherwise>
							</c:choose>
							<c:if test="${invisibleISI==true}">
								<c:if
									test="${researcher.hindexISI.visibility==1 && !empty researcher.hindexISI.value}">
									<c:set value="false" var="invisibleISI"></c:set>
								</c:if>
								<c:if
									test="${researcher.coAuthorsISI.visibility==1 && !empty researcher.coAuthorsISI.value}">
									<c:set value="false" var="invisibleISI"></c:set>
								</c:if>
							</c:if>
						</c:if>
					</c:if>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'mathscinet')}" var="invisibleMathSciNet"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'socialscience')}" var="invisibleSocialScience"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'biomed')}" var="invisibleBiomedExpert"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'repec')}" var="invisibleRepec"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'acm')}" var="invisibleACM"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'pp')}" var="invisiblePP"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'scholaruniverse')}" var="invisibleScholarUniverse"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'mathematics')}" var="invisibleMathematics"></c:set>
					<c:set value="${researcher:isGroupFieldsHidden(anagraficaObject,'pubmed')}" var="invisiblePubMed"></c:set>
					

	<c:if
			test="${!empty researcher.authorIdScopus.value && researcher.authorIdScopus.visibility==1 || !empty researcher.ridISI.value && researcher.ridISI.visibility==1 || !empty researcher.paperCountScopus.value && researcher.paperCountScopus.visibility==1 || !empty researcher.paperCountISI.value && researcher.paperCountISI.visibility==1 || !empty researcher.citationCountScopus.value && researcher.citationCountScopus.visibility==1 || !empty researcher.hindexScopus.value && researcher.hindexScopus.visibility==1 || !empty researcher.hindexISI.value && researcher.hindexISI.visibility==1 || !empty researcher.coAuthorsScopus.value && researcher.coAuthorsScopus.visibility==1 || !empty researcher.coAuthorsISI.value && researcher.coAuthorsISI.visibility==1 || !invisibleMathSciNet || !invisibleSocialScience || !invisibleBiomedExpert || !invisibleRepec || !invisibleACM || !invisiblePP || !invisibleScholarUniverse || !invisibleMathematics}">


		<div id="${holder.shortName}" class="showMoreLessBox box">
			<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
					<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
		<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
			${holder.title}</h2>
    		<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>
			
			<table border="0" cellpadding="0" cellspacing="0" class="hkudata">

			<tr>
				<td valign="top">
			
					
					
			<c:set value="bibliometricsleft" var="cssClassNext"></c:set>
			<div class="${cssClassNext}"
						<c:if test="${invisibleScopus==true}">style="display: none"</c:if>>
					<c:if test="${invisibleScopus==false}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:if>	
					<span id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.scopus" /></span>
						<div class="bibliometricspiece">
						 <c:choose>
						<c:when
							test="${researcher.authorIdScopus.visibility==1 && researcher.authorIdLinkScopus.visibility==1 && !empty researcher.authorIdLinkScopus.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.scopusauthorid" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.authorIdLinkScopus.value}">${researcher.authorIdScopus.value}</a>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.authorIdScopus.visibility==1 && researcher.authorIdLinkScopus.visibility==0 && !empty researcher.authorIdLinkScopus.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.scopusauthorid" /></div>
								<div class="field_value">
								${researcher.authorIdScopus.value}</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:choose>
						<c:when
							test="${researcher.paperCountScopus.visibility==1 && researcher.paperLinkScopus.visibility==1 && !empty researcher.paperLinkScopus.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.scopusdocumentcount" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.paperLinkScopus.value}">
							${researcher.paperCountScopus.value}</a></div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.paperCountScopus.visibility==1 && researcher.paperLinkScopus.visibility==0 && !empty researcher.paperLinkScopus.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.scopusdocumentcount" />
								</div>
								<div class="field_value">
								${researcher.paperCountScopus.value}</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:choose>
						<c:when
							test="${researcher.citationCountScopus.visibility==1 && researcher.citationLinkScopus.visibility==1 && !empty researcher.citationCountScopus.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.scopuscited" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.citationLinkScopus.value}">${researcher.citationCountScopus.value}</a>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.citationCountScopus.visibility==1 && researcher.citationLinkScopus.visibility==0 && !empty researcher.citationCountScopus.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.scopuscited" /></div>
								<div class="field_value">${researcher.citationCountScopus.value}
								</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:if
						test="${researcher.hindexScopus.visibility==1 && !empty researcher.hindexScopus.value}">
						<div class="field">
						<div class="field_label"><fmt:message
							key="jsp.layout.hku.detail.researcher.scopushindex" /></div>
						<div class="field_value">${researcher.hindexScopus.value}</div>
						</div>
					</c:if> <c:choose>
						<c:when
							test="${researcher.coAuthorsScopus.visibility==1 && researcher.coAuthorsLinkScopus.visibility==1 && !empty researcher.coAuthorsLinkScopus.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.scopuscoauthors" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.coAuthorsLinkScopus.value}">${researcher.coAuthorsScopus.value}</a>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.coAuthorsScopus.visibility==1 && researcher.coAuthorsLinkScopus.visibility==0}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.scopuscoauthors" /></div>
								<div class="field_value">${researcher.coAuthorsScopus.value}</div>
								</div>
							</c:if>
						</c:otherwise>				
						
					</c:choose>
					</div>
					</div>
					
					
					<div class="${cssClassNext}"
						<c:if test="${invisibleISI==true}">style="display: none"</c:if>>
						
						<c:if test="${invisibleISI==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.isi" /></span> 
						<div class="bibliometricspiece">
						<c:choose>
						<c:when
							test="${researcher.ridISI.visibility==1 && researcher.ridLinkISI.visibility==1 && !empty researcher.ridISI.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.isiresearcherid" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.ridLinkISI.value}">${researcher.ridISI.value}</a>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.ridISI.visibility==1 && researcher.ridLinkISI.visibility==0 && !empty researcher.ridISI.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.isiresearcherid" /></div>
								<div class="field_value">${researcher.ridISI.value}</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:choose>
						<c:when
							test="${researcher.paperCountISI.visibility==1 && researcher.paperLinkISI.visibility==1 && !empty researcher.paperCountISI.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.isidocumentcount" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.paperLinkISI.value}">${researcher.paperCountISI.value}</a>
							</div>
							</div>

						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.paperCountISI.visibility==1 && researcher.paperLinkISI.visibility==0 && !empty researcher.paperCountISI.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.isidocumentcount" /></div>
								<div class="field_value">
								${researcher.paperCountISI.value}</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:choose>
						<c:when
							test="${researcher.citationCountISI.visibility==1 && researcher.citationLinkISI.visibility==1 && !empty researcher.citationCountISI.value}">
							<div class="field">
							<div class="field_label"><fmt:message
								key="jsp.layout.hku.detail.researcher.isicited" /></div>
							<div class="field_value"><a target="_blank"
								href="${researcher.citationLinkISI.value}">${researcher.citationCountISI.value}</a>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if
								test="${researcher.citationCountISI.visibility==1 && researcher.citationLinkISI.visibility==0 && !empty researcher.citationCountISI.value}">
								<div class="field">
								<div class="field_label"><fmt:message
									key="jsp.layout.hku.detail.researcher.isicited" /></div>
								<div class="field_value">${researcher.citationCountISI.value}
								</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose> <c:if
						test="${researcher.hindexISI.visibility==1 && !empty researcher.hindexISI.value}">
						<div class="field">
						<div class="field_label"><fmt:message
							key="jsp.layout.hku.detail.researcher.isihindex" /></div>
						<div class="field_value">${researcher.hindexISI.value}</div>
						</div>
					</c:if> <c:if
						test="${researcher.coAuthorsISI.visibility==1 && !empty researcher.coAuthorsISI.value}">
						<div class="field">
						<div class="field_label"><fmt:message
							key="jsp.layout.hku.detail.researcher.isicoauthors" /></div>
						<div class="field_value"><a target="_blank"
							href="${researcher.coAuthorsISI.value}"><fmt:message
							key="jsp.layout.hku.detail.researcher.isilinkcoauthors" /></a></div>
						</div>
					</c:if>
					
					</div>
					</c:if>
					
					</div>
										
					<div class="${cssClassNext}"
						<c:if test="${invisibleMathSciNet==true}">style="display: none"</c:if>>							
						<c:if test="${invisibleMathSciNet==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>						
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.mathscinet" /></span>						
						
						<div class="bibliometricspiece">
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathscinetauthorid'].real}" var="tipologiaDaVisualizzare"></c:set>																		
						<dyna:display tipologia="${tipologiaDaVisualizzare}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathscinetearliestindex']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathscinetpublications']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathscinetcitations']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
					<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathscinetcoauthors']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
<!--
					<c:forEach var="value"
					items="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"
					varStatus="valueStatus">
					<c:if test="${value.visibility == 1}">
					
							<div class="field">
							<div class="field_label" id="mathscinetcoauthorslabel"><a target="_blank"
								href="${value.value.real.valueLink}">${tipologiaDaVisualizzare.label}</a>
							</div>
							</div>						
					</c:if>
					</c:forEach>
-->

						</div>	
						</c:if>				
 					</div>
					
					
					
					<div class="${cssClassNext}"
						<c:if test="${invisibleSocialScience==true}">style="display: none"</c:if>>
	
						<c:if test="${invisibleSocialScience==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.socialscience" /></span> 
					<div class="bibliometricspiece">	
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['socialscienceperid']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['socialscienceauthorrank']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['socialsciencepapers']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['socialsciencedownloads']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['socialsciencecitations']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
									
									</div>
						</c:if>			
						
					</div>
					
					
					<div class="${cssClassNext}" id="biomed"
						<c:if test="${invisibleBiomedExpert==true}">style="display: none"</c:if>>
							
						<c:if test="${invisibleBiomedExpert==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.biomed" /></span> 
						<div class="bibliometricspiece">
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['biomedpersonalpage']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['biomedcoauthors']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['biomedpublications']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
					</div>
						</c:if>			
					</div>
					
					<div class="${cssClassNext}" id="repec"
						<c:if test="${invisibleRepec==true}">style="display: none"</c:if>>
						
						<c:if test="${invisibleRepec==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.repec" /></span> 
						<div class="bibliometricspiece">
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['repecnumber']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['repecpaperdownload']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['repecarticledownload']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
									</div>
						</c:if>				
					</div>
					
					
					<div class="${cssClassNext}" id="acm"
						<c:if test="${invisibleACM==true}">style="display: none"</c:if>>
						
						<c:if test="${invisibleACM==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.acm" /></span> 
						<div class="bibliometricspiece">
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmauthor']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmpublicationyear']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmpublicationcount']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmcitationcount']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmdownloadsixweeks']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['acmdownloadtwomonths']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
									</div>
						</c:if>									
					</div>
					
					<div class="${cssClassNext}" id="pp"
						<c:if test="${invisiblePP==true}">style="display: none"</c:if>>
						
						<c:if test="${invisiblePP==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.pp" /></span> 
						<div class="bibliometricspiece">
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pppapers']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['ppcitations']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pphindex']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['ppgindex']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['ppdateinput']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['ppfile']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
					</div>
					</c:if>
					</div>
								
					
								
					<div class="${cssClassNext}" 
					<c:if test="${cssClassNext eq 'bibliometricsleft'}">
						 id="pubmed"
					</c:if>					
					
						<c:if test="${invisiblePubMed==true}">style="display: none"</c:if>>
						
						<c:if test="${invisiblePubMed==false}">
				<c:choose>
					<c:when test="${cssClassNext eq 'bibliometricsleft'}">
						<c:set value="bibliometricsright" var="cssClassNext"></c:set>
					</c:when>
					<c:otherwise>
						<c:set value="bibliometricsleft" var="cssClassNext"></c:set>
					</c:otherwise>
				</c:choose>
				<span id="title"><fmt:message
					key="jsp.layout.hku.detail.researcher.pubmed" /></span>

					
					
					
					
					
					
				<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pmcdataItemsInPubmed']}" var="tipologiaDaVisualizzare"></c:set>


				<c:forEach var="value"
					items="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"
					varStatus="valueStatus">
					<c:if test="${value.visibility == 1}">
						<c:set var="fieldMinWidth" value="" />
						<c:set var="fieldMinHeight" value="" />
						<c:set var="fieldStyle" value="" />
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.col > 1}">
							<c:set var="fieldMinWidth"
								value="min-width:${tipologiaDaVisualizzare.real.fieldMinSize.col}em;" />
						</c:if>
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.row > 1}">
							<c:set var="fieldMinHeight"
								value="min-height:${tipologiaDaVisualizzare.real.fieldMinSize.row}em;" />
						</c:if>
						<div class="dynaField" ${fieldStyle}><c:set
							var="labelMinWidth" value="" /> <c:set var="labelStyle" value="" />
						<c:if test="${tipologiaDaVisualizzare.real.labelMinSize > 1}">
							<c:set var="labelMinWidth"
								value="width:${tipologiaDaVisualizzare.real.labelMinSize}em;" />
						</c:if> <c:if test="${!empty labelMinWidth}">
							<c:set var="labelStyle" value="style=\" ${labelMinWidth}\"" />
						</c:if> <c:if
							test="${!empty tipologiaDaVisualizzare.real.label && !hideLabel}">
							<span class="dynaLabel" ${labelStyle}>${tipologiaDaVisualizzare.real.label}:</span>
						</c:if>

						<div id="${tipologiaDaVisualizzare.shortName}Div"
							class="dynaFieldValue"><c:if
							test="${valueStatus.count != 1}">
							<br />
						</c:if> <%--<c:set var="minheight" value="" />--%> <c:set var="minwidth"
							value="" /> <c:set var="style" value="" /> <%--<c:if test="${tipologia.rendering.dimensione.row > 1}">
				<c:set var="minheight" value="min-height: ${tipologia.rendering.dimensione.row}em;" />
			</c:if>--%> <c:if
							test="${tipologiaDaVisualizzare.rendering.dimensione.col > 1}">
							<c:set var="minwidth"
								value="min-width: ${tipologiaDaVisualizzare.rendering.dimensione.col}em;" />
						</c:if> <%--<c:if test="${!empty minheight || !empty minwidth}">
				<c:set var="style" value="style=\"${minheight}${minwidth}\"" />
			</c:if>--%> <c:if test="${!empty minwidth && !subElement}">
							<c:set var="style" value="style=\" ${minwidth}\"" />
						</c:if> <a target="_blank"
							href="<%=request.getContextPath()%>/simple-search?query=dc.identifier.pmid:[*+TO+*]&facet.authors_fauthority=${authority}"><span ${style}>${value.value.real}</span></a></div>
							
					</div>
					</c:if>




				</c:forEach>



				<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pmcdataItemsInPMC']}" var="tipologiaDaVisualizzare"></c:set>


				<c:forEach var="value"
					items="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"
					varStatus="valueStatus">
					<c:if test="${value.visibility == 1}">
						<c:set var="fieldMinWidth" value="" />
						<c:set var="fieldMinHeight" value="" />
						<c:set var="fieldStyle" value="" />
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.col > 1}">
							<c:set var="fieldMinWidth"
								value="min-width:${tipologiaDaVisualizzare.real.fieldMinSize.col}em;" />
						</c:if>
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.row > 1}">
							<c:set var="fieldMinHeight"
								value="min-height:${tipologiaDaVisualizzare.real.fieldMinSize.row}em;" />
						</c:if>
						<c:if test="${!empty fieldMinHeight || !empty fieldMinWidth}">
							<c:set var="fieldStyle" value="style=\" ${fieldMinHeight}${fieldMinWidth}\"" />
							</c:if>
							<div class="dynaField"  ${fieldStyle}><c:set
								var="labelMinWidth" value="" /> <c:set var="labelStyle"
								value="" /> <c:if
								test="${tipologiaDaVisualizzare.real.labelMinSize > 1}">
								<c:set var="labelMinWidth"
									value="width:${tipologiaDaVisualizzare.real.labelMinSize}em;" />
							</c:if> <c:if test="${!empty labelMinWidth}">
								<c:set var="labelStyle" value="style=\" ${labelMinWidth}\"" />
							</c:if> <c:if
								test="${!empty tipologiaDaVisualizzare.real.label && !hideLabel}">
								<span class="dynaLabel" ${labelStyle}>${tipologiaDaVisualizzare.real.label}:</span>
							</c:if>

							<div id="${tipologiaDaVisualizzare.shortName}Div"
								class="dynaFieldValue"><c:if
								test="${valueStatus.count != 1}">
								<br />
							</c:if> <%--<c:set var="minheight" value="" />--%> <c:set var="minwidth"
								value="" /> <c:set var="style" value="" /> <%--<c:if test="${tipologia.rendering.dimensione.row > 1}">
				<c:set var="minheight" value="min-height: ${tipologia.rendering.dimensione.row}em;" />
			</c:if>--%> <c:if
								test="${tipologiaDaVisualizzare.rendering.dimensione.col > 1}">
								<c:set var="minwidth"
									value="min-width: ${tipologiaDaVisualizzare.rendering.dimensione.col}em;" />
							</c:if> <%--<c:if test="${!empty minheight || !empty minwidth}">
				<c:set var="style" value="style=\"${minheight}${minwidth}\"" />
			</c:if>--%> <c:if test="${!empty minwidth && !subElement}">
								<c:set var="style" value="style=\" ${minwidth}\"" />
							</c:if> <a target="_blank"
								href="<%=request.getContextPath()%>/simple-search?query=dc.identifier.pmcid:[*+TO+*]&facet.authors_fauthority=${authority}"><span ${style}>${value.value.real}</span></a></div>

							</div>
						</c:if></c:forEach>

						
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pmcdataCitations']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>	
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['pmcdataItemsCited']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>					
						</c:if>
				
					</div>			
										
					<div class="${cssClassNext}" id="scholaruniverse"
						<c:if test="${invisibleScholarUniverse==true}">style="display: none"</c:if>>
						
						<c:if test="${invisibleScholarUniverse==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.scholaruniverse" /></span> 
						
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['scholaruniverseprofile']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['scholaruniversecoauthors']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
				
						</c:if>
				
					</div>
					
					<div class="${cssClassNext}" id="mathematics"
						<c:if test="${invisibleMathematics==true}">style="display: none"</c:if>>
						
						<c:if test="${invisibleMathematics==false}"><c:choose><c:when test="${cssClassNext eq 'bibliometricsleft'}"><c:set value="bibliometricsright" var="cssClassNext"></c:set></c:when><c:otherwise><c:set value="bibliometricsleft" var="cssClassNext"></c:set></c:otherwise></c:choose>
						<span
						id="title"><fmt:message
						key="jsp.layout.hku.detail.researcher.mathematics" /></span> 
						
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathematicsid']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathematicsdegree']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>			
						<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathematicsadvisor']}" var="tipologiaDaVisualizzare"></c:set>
						<dyna:display tipologia="${tipologiaDaVisualizzare.real}"
									values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}"/>
		
		
		
		
		
					<c:set value="${mapPropertiesDefinitionsInHolder[holder.shortName]['mathematicsstudents']}" var="tipologiaDaVisualizzare"></c:set>
					<c:set value="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}" var="valuesMathematics"/>
					
					<c:set var="values" value="${dyna:hideComboRow(valuesMathematics)}" />		
		<c:if test="${!empty values}">
			
<c:set var="fieldMinWidth" value="" />
						<c:set var="fieldMinHeight" value="" />
						<c:set var="fieldStyle" value="" />
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.col > 1}">
							<c:set var="fieldMinWidth"
								value="min-width:${tipologiaDaVisualizzare.real.fieldMinSize.col}em;" />
						</c:if>
						<c:if test="${tipologiaDaVisualizzare.real.fieldMinSize.row > 1}">
							<c:set var="fieldMinHeight"
								value="min-height:${tipologiaDaVisualizzare.real.fieldMinSize.row}em;" />
						</c:if>
						<c:if test="${!empty fieldMinHeight || !empty fieldMinWidth}">
							<c:set var="fieldStyle" value="style=\" ${fieldMinHeight}${fieldMinWidth}\"" />
							</c:if>
							<div class="dynaField" ${fieldStyle}><c:set
								var="labelMinWidth" value="" /> <c:set var="labelStyle"
								value="" /> <c:if
								test="${tipologiaDaVisualizzare.real.labelMinSize > 1}">
								<c:set var="labelMinWidth"
									value="width:${tipologiaDaVisualizzare.real.labelMinSize}em;" />
							</c:if> <c:if test="${!empty labelMinWidth}">
								<c:set var="labelStyle" value="style=\" ${labelMinWidth}\"" />
							</c:if> <c:if
								test="${!empty tipologiaDaVisualizzare.real.label && !hideLabel}">
								<span class="dynaLabel" ${labelStyle}>${tipologiaDaVisualizzare.real.label}:</span>
							</c:if>

		<c:set var="subTypesSortered" value="${dyna:sortList(tipologiaDaVisualizzare.rendering.sottoTipologie)}" />
	
		<c:set var="count" value="0" />
			
		<display:table name="${values}" cellspacing="0" cellpadding="0" uid="${tipologiaDaVisualizzare.shortName}"
			class="dynaFieldComboValue" requestURI="" sort="list" export="false" pagesize="100">
		<display:setProperty name="paging.banner.no_items_found" value="" />
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.page.selected" value="" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		<c:forEach var="subtip" items="${subTypesSortered}" varStatus="valueStatus">
				
									
				<c:set var="subLabelMinWidth" value="" />
				<c:if test="${subtip.labelMinSize > 1}">
					<c:set var="subLabelMinWidth" value="width:${subtip.labelMinSize}em;" />
				</c:if>
				
					<display-el:column style="${subLabelMinWidth}" title="${subtip.label}"  
						sortProperty="value.anagrafica4view['${subtip.shortName}'][0].value.sortValue" 
						sortable="false">
					<c:set var="nameriga" value="${tipologiaDaVisualizzare.shortName}_RowNum" scope="request" />
					<c:set var="numtip"
						value="${count % fn:length(tipologiaDaVisualizzare.rendering.sottoTipologie)}" />
					<c:set var="numriga" 
						value="${(count - count % fn:length(tipologiaDaVisualizzare.rendering.sottoTipologie))/fn:length(tipologiaDaVisualizzare.rendering.sottoTipologie)}" />
					<c:set var="count" value="${count+1}" />
					
					<dyna:display tipologia="${subtip}" subElement="true" 
						values="${values[numriga].object.anagraficaProperties[subtip.shortName]}" />
			
					</display-el:column>
				
		</c:forEach>
		</display:table>
		</c:if>		
		
		
						</c:if>	
					</div>
			
					</td>
				</tr>
			</table>
			
			<p></p>
			
			</div>
			</div>
		</c:if>
		<p></p>
		

		