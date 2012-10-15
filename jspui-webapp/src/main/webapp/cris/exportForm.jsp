<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>

<style type="text/css">@import url(<%=request.getContextPath()%>/js/jscalendar/calendar-blue.css );</style>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar.js"> </script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/lang/calendar-en.js"> </script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar-setup.js"> </script>

<script type="text/javascript">
<!--
function Check(chk)
{
if(document.dto.Check_ctr.checked==true){
	for (i = 0; i < chk.length; i++)
	chk[i].checked = false ;
}
}

function UnCheck()
{
if(document.dto.Check_ctr.checked==true){
	document.dto.Check_ctr.checked=false;
}
}
-->
</script>

<dspace:layout locbar="link" navbar="admin" titlekey="jsp.dspace-admin.researchers-export">

	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.researchers-export" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp-export")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>


<table width="98%" align="left" cellpadding="0" cellspacing="0">
	<tr>
		<td><!-- Search box -->
		<form:form commandName="dto" method="post" name="dto">
			
	
			<%-- if you need to display all errors (both global and all field errors,
				 use wildcard (*) in place of the property name --%>
			<spring:bind path="dto.*">
				<c:if test="${not empty status.errorMessages}">
				<div id="errorMessage">
				<c:forEach items="${status.errorMessages}" var="error">
					<span class="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>
					<br />
				</c:forEach>
				</div>
				</c:if>
			</spring:bind>

		<div id="export">
		<fieldset><legend><fmt:message
			key="jsp.layout.hku.export.box.label" /></legend>

		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.advanced-query-sintax" /></div>
			
		<div class="rightpadboolean">
			<span class="exportbooleanoption">
			<form:checkbox path="advancedSyntax" value="true" /></span>
		</div>
		</div>



		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.default-boolean-operator" /></div>
		<fmt:message
			key="jsp.layout.hku.export.option.boolean-or" 
			var="labelOR"/>
		<fmt:message
			key="jsp.layout.hku.export.option.boolean-and" 
			var="labelAND"/>
			
		<div class="rightpadboolean">
			<span class="exportbooleanoption"><fmt:message
			key="jsp.layout.hku.export.option.boolean-and" /><form:radiobutton path="defaultOperator" value="and" /></span>
			<span class="exportbooleanoption"><fmt:message
			key="jsp.layout.hku.export.option.boolean-or" /><form:radiobutton path="defaultOperator" value="or" /></span>
		</div>
		</div>

		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.names" /></div>
		<div class="rightpad">
		<form:input path="names" />
		</div>
		</div>

		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.dept" /></div>
		<div class="rightpad"><form:input path="dept" />
		</div>
		</div>
		
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.interests" /></div>
		<div class="rightpad"><form:input path="interests" /></div>
		</div>
		
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.media" /></div>
		<div class="rightpad"><form:input path="media" />
		</div>
		</div>
		
		<div  class="rangeinput">
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.id" /></div>
		<div class="rightpad"><form:input path="rpIdStart" />
		<span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span><form:input path="rpIdEnd" />
		</div>
		</div>
		
		
		<div  class="rangeinput"">
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.staffNo" /></div>
		<div class="rightpad"><form:input path="staffNoStart" />
		<span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span><form:input path="staffNoEnd" />
		</div>
		</div>
		
		<div class="rangeinput"><div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.data" /></div>
			<div class="rightpad">
			 <form:input path="creationStart" /> <img id="date0_button"
			src="<c:url value="../../js/jscalendar/img.gif"/>" alt="calendar"
			style="cursor: pointer;" /> <script type="text/javascript">
			
						var tomorrow = new Date();
						tomorrow.setTime(tomorrow.getTime() + (24 * 60 * 60 * 1000));
						
						Calendar.setup(
							{
							inputField : "creationStart", // ID of the input field								
							ifFormat : "%d-%m-%Y %H:%M", // the date format						
							button : "date0_button", // ID of the button
							cache: false,
							date : tomorrow,							
							showsTime : true													
							}
						);
					</script> <span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span>
			
			<form:input path="creationEnd" /> <img id="date1_button"
			src="<c:url value="../../js/jscalendar/img.gif"/>" alt="calendar"
			style="cursor: pointer;" /> <script type="text/javascript">

						var tomorrow = new Date();
						tomorrow.setTime(tomorrow.getTime() + (24 * 60 * 60 * 1000));
						
						Calendar.setup(
							{
							inputField : "creationEnd", // ID of the input field								
							ifFormat : "%d-%m-%Y %H:%M", // the date format						
							button : "date1_button", // ID of the button
							cache: false,
							date : tomorrow,							
							showsTime : true													
							}
						);
					</script></div>
					</div>
			
			<div id="radiofieldexport"><span><fmt:message
			key="jsp.layout.hku.export.option.enabled" /></span>
			<form:radiobutton path="status" value="true" /></div> 
			
			<div id="radiofieldexport"><span><fmt:message
			key="jsp.layout.hku.export.option.disabled" /></span>
			<form:radiobutton path="status" value="false" /></div>
			
			<div id="radiofieldexport"><span><fmt:message
			key="jsp.layout.hku.export.option.all" /></span>
			<form:radiobutton path="status" value=""/></div>
			
		</fieldset>
		</div>
		
		<fieldset>
			
			<c:forEach items="${tabs}" var="tab">
			
				<input type="checkbox" id="tabToExport" name="tabToExport" value="${tab.id}" onClick="UnCheck()">${tab.title}</input>				
			
			</c:forEach>
			
			<input type="checkbox" name="Check_ctr" value="yes" checked="checked"
						onClick="Check(document.dto.tabToExport)"><b>All metadata</b> 		
		</fieldset>
		<br/>
		<input type="submit"
			value="<fmt:message key="jsp.export.advanced.export1"/>"
			name="mainMode" /> <input type="submit"
			value="<fmt:message key="jsp.export.advanced.export2"/>"
			name="mainModeA" />
			</form:form>
			
			
			<form action="<%= request.getContextPath() %>/rp/administrator/import.htm"
				method="post">

			<input type="submit"
			value="<fmt:message key="jsp.import.advanced.downloadxsd"/>"
			name="modeXSD" />				
			
			</form>		
		</td>
	</tr>


</table>

</dspace:layout>