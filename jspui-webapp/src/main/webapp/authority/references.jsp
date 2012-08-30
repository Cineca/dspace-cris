<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<dspace:layout style="hku" titlekey="jsp.citations.references">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>

<td width="200" valign="top" align="left">
	<%@ include file="/layout/navbar-hku.jsp" %>
</td>
<td>&nbsp;</td>
<td width="700" valign="top" align="left">
		<div id="content">


    
		    <h1><fmt:message key="jsp.citations.references.title"/></h1>


    		<p>	<div class="info_message"><fmt:message key="jsp.citations.references.message"/></div></p>


		</div>
</td>
</tr>
</table>

	
</dspace:layout>