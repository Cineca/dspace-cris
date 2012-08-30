<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
	String subscribe = request.getParameter("subscribe");
	boolean showSubMsg = false;
	boolean showUnSubMsg = false;
	if (subscribe != null && subscribe.equalsIgnoreCase("true"))
	{
	    showSubMsg = true;
	}
	if (subscribe != null && subscribe.equalsIgnoreCase("false"))
	{
	    showUnSubMsg = true;
	}
%>
<c:set var="dspace.layout.head" scope="request">
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
</c:set>
<tr><td>
<dspace:layout style="hku-researcher" titlekey="jsp.researcher-page.details">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>

<td width="200" valign="top" align="left">
	<%@ include file="/layout/navbar-hku-researcher.jsp" %>
</td>
<td width="700" valign="top" align="left">
	<%@ include file="/authority/_researcherDetailsPage.jsp" %>
</td>
</tr>
</table>
<script type="text/javascript"><!--

var j = jQuery.noConflict();
j(document).ready(function()
		{
		  j(".control").click(function()
		  {
			  j(this).toggleClass("expanded");
			  j(this).children("img").toggleClass("hide");
		      j(this).next(".collapsable").slideToggle(300);
		  });
<% 
	if (showSubMsg) {%>alert('Email Alert Now On');<%}
	if (showUnSubMsg) {%>alert('Email Alert Now Off');<%}
%>
		});
-->
</script>
</dspace:layout>
