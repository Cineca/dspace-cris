<%--
  - Home page JSP
  -
  - Attributes:
  -    communities - Community[] all communities in DSpace
  --%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- For ajax effect: search for language -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/allen.js"></script>

<tr><td>
<c:set var="dspace.layout.head" scope="request">

<style>
html body table.miscTable tbody tr td table tbody tr td table tbody tr td div#content form#dto table tbody tr td div#search fieldset table tbody tr td table tbody tr td span label {
	display: inline;
}
</style>
</c:set>
<dspace:layout style="hku" titlekey="jsp.home.title">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>

<td width="200" valign="top" align="left">
	<%@ include file="/layout/navbar-hku.jsp" %>
</td>

<td width="700" valign="top" align="left">
	<%@ include file="/authority/_searchGrant.jsp" %>
</td>

</tr>
</table>

</dspace:layout>
