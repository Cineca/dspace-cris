<%@page import="it.cilea.hku.authority.model.ResearcherPage" %>
<%@page import="it.cilea.hku.authority.model.StatSubscription" %>
<div id="content">

<h1>
<fmt:message key="jsp.statistics.title-subscritption-list" />
</h1>

<c:set var="RPTYPE"><%= ResearcherPage.RP_TYPE_ID %></c:set>
<c:set var="FREQUENCY_DAILY"><%= StatSubscription.FREQUENCY_DAILY %></c:set>
<c:set var="FREQUENCY_WEEKLY"><%= StatSubscription.FREQUENCY_WEEKLY %></c:set>
<c:set var="FREQUENCY_MONTHLY"><%= StatSubscription.FREQUENCY_MONTHLY %></c:set>

<%-- insert div block if it is not IE --%> <%
     if (useragent.indexOf("msie") == -1)
     {
 %>
<div>&nbsp;</div>
<%
    }
%> 

<display:table name="${subscriptions}" cellspacing="0" cellpadding="0" 
			requestURI="" id="objectList" htmlId="objectList"  class="displaytaglikemisctable" export="false">
			<display:column titleKey="jsp.statistics.table.type" sortable="false">
				<fmt:message key="jsp.statistics.table.type.${objectList.type}" />
			</display:column>							
			<display:column titleKey="jsp.statistics.table.object" property="objectName" sortable="false"/>			
			<display:column titleKey="jsp.statistics.table.identifier" sortable="false">
			<a href="<%= request.getContextPath() %>/<c:choose>
						<c:when test="${objectList.type == RPTYPE}">rp/</c:when><c:otherwise>handle/</c:otherwise></c:choose>${objectList.id}">${objectList.id}</a>
			</display:column>						
			<display:column titleKey="jsp.statistics.table.frequences" sortable="false">
				<form action="<%= request.getContextPath() %>/rp/tools/stats/subscription/subscribe" method="get">
					<c:choose>
						<c:when test="${objectList.type == RPTYPE}">
							<input type="hidden" name="rp" value="${objectList.id}" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="handle" value="${objectList.id}" />
						</c:otherwise>
					 </c:choose>
					 <input type="hidden" name="list" value="true" />
					<input type="checkbox" name="freq" value="${FREQUENCY_DAILY}"
				    <c:forEach var="freq" items="${objectList.freqs}">    
				     <c:if test="${freq == FREQUENCY_DAILY}">
				      checked="checked"				       
				     </c:if>
				    </c:forEach>
					 />
					 <input type="checkbox" name="freq" value="${FREQUENCY_WEEKLY}" 
 				    <c:forEach var="freq" items="${objectList.freqs}">    
				     <c:if test="${freq == FREQUENCY_WEEKLY}">
				      checked="checked"				       
				     </c:if>
				    </c:forEach>
					 />
					 <input type="checkbox" name="freq" value="${FREQUENCY_MONTHLY}" 
				    <c:forEach var="freq" items="${objectList.freqs}">    
				     <c:if test="${freq == FREQUENCY_MONTHLY}">
				      checked="checked"				       
				     </c:if>
				    </c:forEach>
					/>
					 <input type="submit" value="<fmt:message key="jsp.statistics.table.button.update" />" />
				</form>
			</display:column>
			<display:column titleKey="jsp.statistics.table.remove" sortable="false">
				<form action="<%= request.getContextPath() %>/rp/tools/stats/subscription/unsubscribe" method="get">
					<c:choose>
						<c:when test="${objectList.type == RPTYPE}">
							<input type="hidden" name="rp" value="${objectList.id}" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="handle" value="${objectList.id}" />
						</c:otherwise>
					 </c:choose>
					 <input type="hidden" name="list" value="true" />
					 <input type="submit" value="<fmt:message key="jsp.statistics.table.button.remove" />" />
				</form>	
			</display:column>
		</display:table>
</div>
