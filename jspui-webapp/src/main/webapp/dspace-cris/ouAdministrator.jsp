<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>

<c:set var="dspace.layout.head.last" scope="request">
    
    <script type="text/javascript"><!--

		var j = jQuery.noConflict();
    j(document).ready(function()
			{
    		j('#addentity').click(function() {
    		  j('#dto').submit();
    		});
			}
    );
		-->
	</script>
    
</c:set>
<dspace:layout locbar="link" navbar="admin"	titlekey="jsp.dspace-admin.project">
	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.ou" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=request.getContextPath()%><%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.ou")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>


	<c:if test="${!empty error}">
		<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> <fmt:message key="${error}"/></span>
	</c:if>
	<div>&nbsp;</div>
	<div>&nbsp;</div>

	<ul>

		<li>

		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/cris/administrator/ou/listTabs.htm"><fmt:message
			key="jsp.dspace-admin.hku.jdyna-configuration.listtabs" /></a></div>		
		<div>&nbsp;</div>
		</li>
		<li>

		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/cris/administrator/ou/listEditTabs.htm"><fmt:message
			key="jsp.dspace-admin.hku.jdyna-configuration.listedittabs" /></a></div>	
		
		<div>&nbsp;</div>
		</li>
		<li>

		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/cris/administrator/ou/listBoxs.htm"><fmt:message
			key="jsp.dspace-admin.hku.jdyna-configuration.listboxs" /></a></div>	
		
		<div>&nbsp;</div>
		</li>
	
		<li>
		<div style="padding: 0; margin: 0 10px;"><a id="addentity"
			href="#"><fmt:message
			key="jsp.dspace-admin.hku.add-organizationunit" /></a></div>	
		
		
		<div style="display: none; float: right;"><c:set
			var="contextPath"><%=request.getContextPath()%></c:set> <form:form 
			action="${contextPath}/cris/administrator/ou/addOrganizationUnit.htm"
			method="post" commandName="dto">

		</form:form>
		</div>
		

		<div>&nbsp;</div>
		</li>

		<li>
		<div style="padding: 0; margin: 0 10px;"><a
			href="<%=request.getContextPath()%>/cris/administrator/ou/list.htm"><fmt:message
			key="jsp.dspace-admin.hku.see-ou" /></a></div>
		<div>&nbsp;</div>
		</li>
	
	</ul>
</dspace:layout>