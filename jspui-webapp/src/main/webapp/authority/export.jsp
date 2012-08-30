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
		<form
			action="<%=request.getContextPath()%>/rp/administrator/export.htm"
			method="post">

		<div id="export">
		<fieldset><legend><fmt:message
			key="jsp.layout.hku.export.box.label" /></legend>
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.default-boolean-operator" /></div>
		<div class="rightpadboolean">
			<span class="exportbooleanoption"><fmt:message
			key="jsp.layout.hku.export.option.boolean-and" /><input type="radio"
			name="fieldoperator" id="tfieldoperator" value="and" checked="checked" /> </span>
			<span class="exportbooleanoption"><fmt:message
			key="jsp.layout.hku.export.option.boolean-or" /><input type="radio"
			name="fieldoperator" id="tfieldoperator" value="or" /></span>
		</div>
		</div>

		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.names" /></div>
		<div class="rightpad"><input type="text" name="names" id="names"/>
		</div>
		</div>
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.dept" /></div>
		<div class="rightpad"><input type="text" name="dept" id="dept" />
		</div>
		</div>
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.interests" /></div>
		<div class="rightpad"><input type="text" name="interests"
			id="interests" /></div>
		</div>
		<div>
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.media" /></div>
		<div class="rightpad"><input type="text" name="media" id="media" />
		</div>
		</div>
		<div  class="rangeinput"">
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.id" /></div>
		<div class="rightpad"><input type="text" name="id0" id="id0" />
		<span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span> <input type="text"
			name="id1" id="id1" /></div>
		</div>
		<div  class="rangeinput"">
		<div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.staffNo" /></div>
		<div class="rightpad"><input type="text" name="staffNo0"
			id="staffNo0" /> <span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span> <input
			type="text" name="staffNo1" id="staffNo1" />

		</div>
		</div>
		<div class="rangeinput"><div class="leftpad"><fmt:message
			key="jsp.layout.hku.export.option.data" /></div>
			<div class="rightpad">
			 <input type="text"
			name="date0" id="date0" readonly="readonly"/> <img id="date0_button"
			src="<c:url value="../../js/jscalendar/img.gif"/>" alt="calendar"
			style="cursor: pointer;" /> <script type="text/javascript">
			
						var tomorrow = new Date();
						tomorrow.setTime(tomorrow.getTime() + (24 * 60 * 60 * 1000));
						
						Calendar.setup(
							{
							inputField : "date0", // ID of the input field								
							ifFormat : "%d-%m-%Y %H:%M", // the date format						
							button : "date0_button", // ID of the button
							cache: false,
							date : tomorrow,							
							showsTime : true													
							}
						);
					</script> <span id="fromto"><fmt:message key="jsp.export.label.fromto" /></span> <input type="text"
			name="date1" id="date1" readonly="readonly"/> <img id="date1_button"
			src="<c:url value="../../js/jscalendar/img.gif"/>" alt="calendar"
			style="cursor: pointer;" /> <script type="text/javascript">

						var tomorrow = new Date();
						tomorrow.setTime(tomorrow.getTime() + (24 * 60 * 60 * 1000));
						
						Calendar.setup(
							{
							inputField : "date1", // ID of the input field								
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
			key="jsp.layout.hku.export.option.enabled" /></span><input type="radio"
			name="field1" id="tfield1" value="true" /></div> 
			<div id="radiofieldexport"><span><fmt:message
			key="jsp.layout.hku.export.option.disabled" /></span><input type="radio"
			name="field1" id="tfield1" value="false" /></div>
			<div id="radiofieldexport"><span><fmt:message
			key="jsp.layout.hku.export.option.all" /></span><input type="radio"
			name="field1" id="tfield1" value="null" checked="true" /></div>
			
		</fieldset>
		</div>
		<br/>
		<input type="submit"
			value="<fmt:message key="jsp.export.advanced.export1"/>"
			name="exportmode1" /> <input type="submit"
			value="<fmt:message key="jsp.export.advanced.export2"/>"
			name="exportmode2" /></form>
		</td>
	</tr>


</table>

</dspace:layout>