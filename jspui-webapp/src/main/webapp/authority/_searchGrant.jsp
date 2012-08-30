<%--
  - _searchGrant.jsp
  - 
  --%><%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@page import="it.cilea.hku.authority.model.ResearcherGrant"%><%@page import="it.cilea.hku.authority.webui.dto.ResearcherGrantDTO"%><%@ page import="org.apache.solr.client.solrj.response.FacetField" %><%@ page import="org.apache.solr.client.solrj.response.FacetField.Count" %><%@page import="java.util.Calendar"%><%	String sponsor			= request.getParameter("sponsor") == null ? "" : request.getParameter("sponsor");		String query1 			= request.getParameter("queryString") == null ? "" : request.getParameter("queryString");	String query2 			= request.getParameter("queryStringFirst") == null ? "" : request.getParameter("queryStringFirst");	String query3 			= request.getParameter("queryStringSecond") == null ? "" : request.getParameter("queryStringSecond");	String field1 			= request.getParameter("queryField") == null ? "ANY" : request.getParameter("queryField");	String field2 			= request.getParameter("queryFieldFirst") == null ? "ANY" : request.getParameter("queryFieldFirst");	String field3 			= request.getParameter("queryFieldSecond") == null ? "ANY" : request.getParameter("queryFieldSecond");	String conjunction1 	= request.getParameter("firstOperator") == null ? "AND" : request.getParameter("firstOperator");	String conjunction2 	= request.getParameter("secondOperator") == null ? "AND" : request.getParameter("secondOperator");   %>
<div id="content">

<h1><fmt:message key="jsp.layout.hku.search.grant.title"/></h1>
<c:set var="contextPath"><%= request.getContextPath() %></c:set>
<form:form commandName="dto" method="post" action="${contextPath}/rp/searchGrants.htm">
<table width="98%" align="left" cellpadding="0" cellspacing="0">
	<tr>
		<td><!-- Search box -->
							<input type="hidden" name="advanced" value="true" />
		<div id="search">
		<fieldset><legend><fmt:message key="jsp.layout.hku.search.grant.box.label"/></legend>

			<table>				<tr>				<td nowrap="nowrap" align="left"><fmt:message key="jsp.layout.hku.search.label.box.sponsor"/></td>				<td align="left" colspan="2"><select name="sponsor">					<option value=""><fmt:message key="jsp.layout.hku.search.label.box.sponsor.optionvalue.all"/></option>				<c:forEach items="${sponsors}" var="sponsorfacet">									<option value="${sponsorfacet.name}" <%= sponsor.equals(((FacetField.Count)(pageContext.getAttribute("sponsorfacet"))).getName()) ? "selected=\"selected\"" : "" %>>${sponsorfacet.name} (${sponsorfacet.count})</option>								</c:forEach>								</select></td>												</tr>								<tr>				<td>					<fmt:message key="jsp.layout.hku.search.label.box.search"/>				</td>				<td><select name="queryField">				<c:forEach items="${fields}" var="field">                    					<option value="${field}" <%= field1.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryString" size="30" /></td>				</tr>								<tr>				<td>		<select name="firstOperator">												<option value="AND" <%= conjunction1.equals("AND") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.and"/></option>										<option value="OR" <%= conjunction1.equals("OR") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.or"/></option>			</select></td>				<td><select name="queryFieldFirst">				<c:forEach items="${fields}" var="field">									<option value="${field}"<%= field2.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryStringFirst" size="30" /></td>				</tr>								<tr>				<td>								<select name="secondOperator">          					<option value="AND" <%= conjunction2.equals("AND") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.and"/></option>										<option value="OR" <%= conjunction2.equals("OR") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.or"/></option>				</select>				</td>				<td><select name="queryFieldSecond">				<c:forEach items="${fields}" var="field">									<option value="${field}" <%= field3.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryStringSecond" size="30"/></td>				</tr>								<tr>				<td align="left" valign="top" colspan="3">					<input type="submit" value="<fmt:message key="jsp.search.advanced.search2"/>" name="searchMode" id="searchMode" />									<input type="reset" value="<fmt:message key="jsp.search.advanced.clear"/>" name="reset" id="reset"/>				</td>				</tr>			</table>												<table width="500" cellspacing="0" cellpadding="0" border="0">  <tbody><tr>     <td height="30" width="447"><fmt:message key="jsp.search.advanced.title.limitbox"/></td>  </tr>  <tr>     <td width="447">       <table cellspacing="2" cellpadding="2" border="0">        <tbody>        <tr>           <td nowrap="" width="105"><fmt:message key="jsp.search.advanced.title.limitboxfundingyear"/></td>          <td width="342" align="left"><fmt:message key="jsp.search.advanced.title.limitboxfrom"/>           <%          	  int year = Calendar.getInstance().get(Calendar.YEAR); %>         	<select name="fromYear">         	<%          	            	  int i = 1941;         	%>         	<option selected="selected" value="*">< <%= i%></option>              <%           	    while(i<=year) { %>          	              	   <option value="<%= i%>"><%= i%></option>          	    			<% i = i + 1;			          	    } %>          	          </select>                 <fmt:message key="jsp.search.advanced.title.limitboxto"/>         <select name="toYear">           <%          	            	  	i = 1941;          	    while(i<=year) { %>          	              	   <option <% if(i==year) {%> selected="selected" <%}%> value="<%= i%>"><%= i%></option>          	    			<% i = i + 1;			          	    } %>                   </select>                                                     </td>       </tr>       <c:if test="${!empty state}">       <tr>           <td nowrap="" width="105"><fmt:message key="jsp.search.advanced.title.limitboxstatus"/></td>          <td width="342" align="left" colspan="2">         						<form:checkboxes path="status" items="${state}" itemValue="name"></form:checkboxes>				         </td>                </tr>       </c:if></tbody></table></td></tr></tbody></table>
			
				
		</fieldset>		
		</div>
		</td>		
	</tr>	<tr>
	<td class="bodyText">			For phrase search, please use quotation marks,<br/>			for example, "Wann, John", or "Behavior Therapy".		</td>	</tr>

	<!-- Enter code -->

	
		<tr>			<td>&nbsp;</td>		</tr>		<tr>
			<c:if test="${see_search_grantcode}">	
			<td valign="middle">	
			<div id="search">	
			<div id="searchmiddle">	
	
			<fieldset><fmt:message key="jsp.layout.hku.search.byGrantCode"/>&nbsp;<form:input path="codeQuery" /> 			<input type="submit" name="codeSearchMode" id="codeSearchMode"	value="Go" />
			</fieldset>	
			</div>	
			</div>		
			</td>		</tr>	
			</c:if>	


	<tr>
		<td></td>
	</tr>	<spring:bind path="*">		<c:if test="${not empty status.errorMessages}">		<tr>			<td>			<p class="submitFormWarn"><c:forEach items="${status.errorMessages}" var="error">					${error}				</c:forEach></p>			</td>		</tr>
		</c:if>	</spring:bind>
<c:if test="${!empty showresults}">	<tr>	<td>
				<c:choose>
					<c:when test="${!empty result}">													&nbsp;</td>						</tr>						<tr>							<td>
						<display:table name="${result}" cellspacing="0" cellpadding="0"
							requestURI="" id="objectList" htmlId="objectList"  class="displaytaglikemisctable" export="false">
							<display:column titleKey="jsp.layout.table.hku.grants.projecttitle"><a href="../rp/grants/details.htm?id=<%= ((ResearcherGrantDTO)pageContext.getAttribute("objectList")).getId()%>"><%= ((ResearcherGrantDTO)pageContext.getAttribute("objectList")).getTitle()%></a> </display:column>						
							<display:column titleKey="jsp.layout.table.hku.grants.investigator" property="investigators"/>
							<display:column titleKey="jsp.layout.table.hku.grants.year" property="year"/>
							<%
							if(isAdmin) {							    
							%>
							<display:column titleKey="jsp.layout.table.hku.grants.status" property="status"/>							
							<%		    
							}    
							%>							
							
						</display:table>
					</c:when>
					<c:otherwise>
						<p class="submitFormWarn"><fmt:message
							key="jsp.search.general.noresults" /></p>
					</c:otherwise>
				</c:choose>				</td>				</tr>
</c:if>
</table></form:form>

