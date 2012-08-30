<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>

<tr><td>
<dspace:layout style="hku" titlekey="jsp.researcher-page.scopuswos-data-form">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>

<td width="200" valign="top" align="left">
<c:set var="researcher_page_menu" value="true" />
	<%@ include file="/layout/navbar-hku.jsp" %>
</td>

<td valign="top" align="left">
	<%@ include file="/authority/_scopusAndWosDataEditForm.jsp" %>
</td>

</tr>
</table>

</dspace:layout>