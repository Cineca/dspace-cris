<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

	<div id="${holder.shortName}" class="showMoreLessBox box">
		<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
		<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
		<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
		${holder.title}</h2>
   		<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>

<table border="0" cellpadding="0" cellspacing="0" id="tablemediacomment">

	<tr>
		<td valign="top" class="columnBody">
		
		 <c:set var="values"
			value="${dyna:hideComboRow(anagraficaObject.anagrafica4view['spoken'])}" />

			<c:if test="${!empty values}">
			<tr>
			<td colspan="3" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.spokenlanguages" /></td>
						<td>
			<c:forEach var="value" items="${values}" varStatus="i">					
					<c:forEach var="val" items="${value.object.anagraficaProperties['spokenEN']}" varStatus="j">
					<c:if test="${val.visibility==true}">
								<c:out value="${val}" escapeXml="false" />
						
													<c:if test="${j.index != fn:length(value.object.anagraficaProperties['spokenEN']) - 1}">
							<span class="sep_bar">|</span>
						</c:if>

					</c:if>
					</c:forEach>
					
			<c:if
					test="${i.index == fn:length(values) - 1}">
							(
			</c:if>

					
			</c:forEach>
			

		
			<c:forEach var="value" items="${values}" varStatus="i">
					
					<c:forEach var="val" items="${value.object.anagraficaProperties['spokenZH']}" varStatus="j">
					<c:if test="${val.visibility==true}">
								<c:out value="${val}" escapeXml="false" />

						<c:if test="${j.index != fn:length(value.object.anagraficaProperties['spokenZH']) - 1}">
							<span class="sep_bar">|</span>
						</c:if>



					</c:if>
					</c:forEach>
			<c:if
					test="${i.index == fn:length(values) - 1}">
							)
			</c:if>


					
			</c:forEach>			
				
			</td>
			</tr>
			
		
			<c:set var="valuesWritten"
			value="${dyna:hideComboRow(anagraficaObject.anagrafica4view['written'])}" />
			<tr>
			<td colspan="3" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.writtenlanguages" /></td>
				<td>
							<c:forEach var="value" items="${valuesWritten}" varStatus="i">
					
					<c:forEach var="val" items="${value.object.anagraficaProperties['writtenEN']}" varStatus="j">
					<c:if test="${val.visibility==true}">
								<c:out value="${val}" escapeXml="false" />

						<c:if test="${j.index != fn:length(value.object.anagraficaProperties['writtenEN']) - 1}">
							<span class="sep_bar">|</span>
						</c:if>



					</c:if>
					</c:forEach>
					
						
											
			<c:if
					test="${i.index == fn:length(valuesWritten) - 1}">
							(
			</c:if>

					
			</c:forEach>
			
				
			<c:forEach var="value" items="${valuesWritten}" varStatus="i">
					
					
					<c:forEach var="val" items="${value.object.anagraficaProperties['writtenZH']}" varStatus="j">
					<c:if test="${val.visibility==true}">
								<c:out value="${val}" escapeXml="false" />

						<c:if test="${j.index != fn:length(value.object.anagraficaProperties['writtenZH']) - 1}">
							<span class="sep_bar">|</span>
						</c:if>



					</c:if>
					</c:forEach>				
				
			<c:if
					test="${i.index == fn:length(valuesWritten) - 1}">
					)
			</c:if>
					
			</c:forEach>	
					
				</td>						
			</tr>
		</c:if>
		
		
		<c:set var="valueExpertise"
			value="${dyna:hideComboRow(anagraficaObject.anagrafica4view['expertise'])}" />
		<c:if test="${!empty valueExpertise}">
					<tr>
						<td colspan="4" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.areaofexpertise" /></td>
					</tr>
					<tr>
						<td colspan="4" class="columnBody">
						
						<ul class="columnBody">
						<br/>
										
					<c:forEach var="value" items="${valueExpertise}" varStatus="i">
					
					<c:forEach var="val" items="${value.object.anagraficaProperties['expertiseEN']}" varStatus="j">
					<c:if test="${value.object.anagraficaProperties['expertiseEN'][j.index].visibility==true || value.object.anagraficaProperties['expertiseZH'][j.index].visibility==true}">
					<li>
					</c:if>
					<c:if test="${value.object.anagraficaProperties['expertiseEN'][j.index].visibility==true}">
						<c:out value="${value.object.anagraficaProperties['expertiseEN'][j.index]}" escapeXml="false" />
								<div class="dynaClear">
								&nbsp;
								</div>														
					</c:if>									
					<c:if test="${value.object.anagraficaProperties['expertiseZH'][j.index].visibility==true}">
						<c:out value="${value.object.anagraficaProperties['expertiseZH'][j.index]}" escapeXml="false" />
					</c:if>							
					<c:if test="${value.object.anagraficaProperties['expertiseEN'][j.index].visibility==true || value.object.anagraficaProperties['expertiseZH'][j.index].visibility==true}">
					</li>
					</c:if>
					</c:forEach>

					
					</c:forEach>
			
		
						</ul>
						</td>
					</tr>
				</c:if>
		</td>
	</tr>
</table>

</div>
</div>
<p></p>