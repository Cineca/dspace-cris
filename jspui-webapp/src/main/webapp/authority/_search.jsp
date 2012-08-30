<%--
  - _search.jsp
  -
  - Version: $Revision: 1693 $
  --%>

<%--
  - ResearcherPage Search JSP
  --%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils" %>
<%@ page import="java.util.List" %><%@page import="it.cilea.hku.authority.webui.dto.ResearcherPageDTO"%>
<%@page import="it.cilea.hku.authority.model.ResearcherPage"%><%@ page import="org.apache.solr.client.solrj.response.FacetField" %><%@ page import="org.apache.solr.client.solrj.response.FacetField.Count" %><%	String faculty 			= request.getParameter("faculty") == null ? "" : request.getParameter("faculty");	String query1 			= request.getParameter("queryString") == null ? "" : request.getParameter("queryString");	String query2 			= request.getParameter("queryStringFirst") == null ? "" : request.getParameter("queryStringFirst");	String query3 			= request.getParameter("queryStringSecond") == null ? "" : request.getParameter("queryStringSecond");	String field1 			= request.getParameter("queryField") == null ? "ANY" : request.getParameter("queryField");	String field2 			= request.getParameter("queryFieldFirst") == null ? "ANY" : request.getParameter("queryFieldFirst");	String field3 			= request.getParameter("queryFieldSecond") == null ? "ANY" : request.getParameter("queryFieldSecond");	String conjunction1 	= request.getParameter("firstOperator") == null ? "AND" : request.getParameter("firstOperator");	String conjunction2 	= request.getParameter("secondOperator") == null ? "AND" : request.getParameter("secondOperator");   %>
<div id="content">

<h1><fmt:message key="jsp.layout.hku.search.title"/></h1>

<p class="text10">
HKU ResearcherPages sustain and enhance the research reputations of HKU authors and their institution.
They enable <a href="http://www3.hku.hk/strategic-development/eng/strategic-themes-for-09-14/promotion-knowledge-exchange-and-demonstrating-leadership.php" target="_new">Knowledge Exchange</a>
between HKU and the community in Hong Kong, China, and the World.
Read more about <a href="<%=request.getContextPath()%>/help.jsp#ResearcherPages">ResearcherPages</a>.
</p><c:set var="contextPath"><%= request.getContextPath() %></c:set>
<form:form commandName="dto" method="post" action="${contextPath}/rp/search.htm">
<table width="98%" align="left" cellpadding="0" cellspacing="0">
	<tr>
		<td><!-- Search box -->
							<input type="hidden" name="advanced" value="true" />
		<div id="search">
		<fieldset><legend><fmt:message key="jsp.layout.hku.search.box.label"/></legend>

			<table>				<tr>				<td nowrap="nowrap" align="left" colspan="2"><fmt:message key="jsp.layout.hku.search.label.box.faculty"/></td>				<td><select name="faculty">					<option value=""><fmt:message key="jsp.layout.hku.search.label.box.faculty.optionvalue.all"/></option>				<c:forEach items="${faculties}" var="communitySearch">									<option value="${communitySearch.name}" <%= faculty.equals(((FacetField.Count)(pageContext.getAttribute("communitySearch"))).getName()) ? "selected=\"selected\"" : "" %>>${communitySearch.name} (${communitySearch.count})</option>								</c:forEach>								</select></td>								</tr>								<tr>				<td>					<fmt:message key="jsp.layout.hku.search.label.box.search"/>				</td>				<td><select name="queryField">				<c:forEach items="${fields}" var="field">                    					<option value="${field}" <%= field1.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryString" size="30" /></td>				</tr>								<tr>				<td>		<select name="firstOperator">								<option value="AND" <%= conjunction1.equals("AND") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.and"/></option>										<option value="OR" <%= conjunction1.equals("OR") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.or"/></option>			</select></td>				<td><select name="queryFieldFirst">				<c:forEach items="${fields}" var="field">									<option value="${field}"<%= field2.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryStringFirst" size="30" /></td>				</tr>								<tr>				<td>								<select name="secondOperator">          					<option value="AND" <%= conjunction2.equals("AND") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.and"/></option>										<option value="OR" <%= conjunction2.equals("OR") ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.or"/></option>				</select>				</td>				<td><select name="queryFieldSecond">				<c:forEach items="${fields}" var="field">									<option value="${field}" <%= field3.equals(pageContext.getAttribute("field")) ? "selected=\"selected\"" : "" %>><fmt:message key="jsp.layout.hku.search.option.${field}"/></option>								</c:forEach>							</select></td>				<td><form:input path="queryStringSecond" size="30"/></td>				</tr>								<tr>				<td align="left" valign="top" colspan="3">					<input type="submit" value="<fmt:message key="jsp.search.advanced.search2"/>" name="searchMode" id="searchMode" />									<input type="reset" value="<fmt:message key="jsp.search.advanced.clear"/>" name="reset" id="reset"/>				</td>				</tr>			</table>															
			
				
		</fieldset>
		</div>
		</td>
	</tr>


	<tr>
		<td>&nbsp;</td>
	</tr>

<%
String rpid = (String)session.getAttribute("rpid");
%>

	<tr>
		<td valign="middle">
		<div id="search">
		<div id="searchmiddle">
		<fieldset><fmt:message key="jsp.layout.hku.search.byRP"/>&nbsp;		<form:input path="rpQuery" /><input type="submit" name="rpSearchMode" id="rpSearchMode"
			value="Go" />

<% if (rpid != null) { %>
	<p>Go to <a href="<%=request.getContextPath()%>/rp/<%=rpid %>">My ResearcherPage</a></p>
<%	} %>

		</fieldset>
		
		</div>
		</div>		
		</td>
	</tr>

	<!-- Enter staffNo -->

	
		<tr>			<td>&nbsp;</td>		</tr>		<tr>
			<c:if test="${see_search_staffno}">	
			<td valign="middle">	
			<div id="search">	
			<div id="searchmiddle">	
	
			<fieldset><fmt:message key="jsp.layout.hku.search.byStaffNo"/>&nbsp;<form:input path="staffQuery" /> 			<input type="submit" name="staffNoSearchMode" id="staffNoSearchMode"	
				value="Go" />
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
							<display:column titleKey="jsp.layout.table.hku.researchers.rpid">								<a href="../rp/<%= ((ResearcherPageDTO)pageContext.getAttribute("objectList")).getPersistentIdentifier()%>"><%= ((ResearcherPageDTO)pageContext.getAttribute("objectList")).getPersistentIdentifier()%></a>							</display:column>								
							<display:column titleKey="jsp.layout.table.hku.researchers.academicName" property="academicName" />						
							<display:column titleKey="jsp.layout.table.hku.researchers.chineseName" property="chineseName" />
							<display:column titleKey="jsp.layout.table.hku.researchers.department" property="dept" />
							<%
							if(isAdmin) {							    
							%>
							<display:column titleKey="jsp.layout.table.hku.researchers.status" property="status"/>							
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
</table></form:form>

