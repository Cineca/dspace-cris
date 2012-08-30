<div id="content">

<h1>
<fmt:message key="jsp.layout.hku.sendmail.title">
<fmt:param>${dto.rp}</fmt:param>
<fmt:param>${dto.subject}</fmt:param>
</fmt:message>
</h1>


<%-- insert div block if it is not IE --%> <%
     if (useragent.indexOf("msie") == -1)
     {
 %>
<div>&nbsp;</div>
<%
    }
%> 


<%
String mode = request.getParameter("mode");
%>

<form:form commandName="dto" method="post">
	
	<%-- if you need to display all errors (both global and all field errors,
		 use wildcard (*) in place of the property name --%>
      
  	<spring:bind path="dto.*">
		<c:forEach items="${status.errorMessages}" var="error">		
			<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>									
			<br>
		</c:forEach>
	</spring:bind>


<% if (mode.equals("1")) { %>
	<p>
		This data is extracted from the HKU Communications & Public Affairs Office (CPAO) database of Media Content Directory,
		<a href="http://www3.hku.hk/hkumcd/" target="_new">http://www3.hku.hk/hkumcd/</a>
		and updated monthly to The Hub.  
	</p>
	<p>
		To make changes please write below your intended changes, and copy to Ms. Melanie Wan, <a href="mailto:melwkwan@hku.hk">melwkwan@hku.hk</a>
	</p>

<% } else if (mode.equals("2")) { %>
	<p>
		Scopus & ResearcherID data is harvested weekly to update the record here in The Hub.  The data cannot be edited in The Hub, but must be changed in the source silos of Scopus and ResaercherID.  Instructions for this are at,
		<br/>
      <a href="http://hub.hku.hk/help.jsp#bibliometric" target="_new">http://hub.hku.hk/help.jsp#bibliometric</a>
	</p>
	<p>
		Or, you may send this email to The Hub Administrators, who will help you through this process.
	</p>

<% } else { %>
	<p><fmt:message key="jsp.layout.hku.sendmail.paragraph.title"/></p>
<% } %>

				
	<p><form:textarea path="text" cols="60" rows="15" /></p>
	<input type="submit" value="<fmt:message key="jsp.mail.sendchange"/>" name="submit" />
		
	
</form:form>
</div>
