<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<c:set var="root"><%= request.getContextPath() %></c:set>
<%
Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
%>
<c:set var="admin"><%=isAdmin%></c:set>
	<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend><label>
<!--c:if test="${isThereMetadataNoEditable eq true}"-->

	<span class="green"><fmt:message
		key='jsp.layout.hku.researcher.message.sendemail'>
		<fmt:param>
			<fmt:message
				key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
		</fmt:param>
	</fmt:message></span>

<!--/c:if--> </label>
	<table width="98%" cellpadding="0"
		cellspacing="4">

	<tr>
		<td>
		
		<table id="tabledatacvfields" align="left" cellpadding="0" cellspacing="4">
		<tr>
			
			<td nowrap="nowrap"><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.cv" /></span>
			</td>
			<td>
				<span style="font-size: 9pt; display: block; width: 40em;"><fmt:message key="jsp.layout.hku.primarydata.label.cv-url-or-file" /></span><br/>
			 	
			 	<table>
			 	<tr>
			 		<td nowrap="nowrap">
			 			<form:label path="cv.remoteUrl"><fmt:message key="jsp.layout.hku.primarydata.label.cv-url" /></form:label>
			 		</td>
			 		<td>
				 		<span style="font-size: 9pt; display: block; width: 30em;">
				 		<fmt:message key="jsp.layout.hku.primarydata.hint.remoteurl-replacefile" /></span><br/>
				 		<form:input path="cv.remoteUrl" size="60%" />
			 		</td>
			 	</tr>
			 	<tr>
			 		<td nowrap="nowrap">
			 			<form:label path="cv.file"><fmt:message key="jsp.layout.hku.primarydata.label.cv-file" /></form:label>
			 		</td>
			 		<td>
				 		<span style="font-size: small">
						<c:choose>
						<c:when test="${!empty researcher.cv.value}">
							<fmt:message key="jsp.layout.hku.primarydata.label.cv-alreadyuploaded" />
						</c:when>
						<c:otherwise>
							<fmt:message key="jsp.layout.hku.primarydata.label.cv-notuploadedyet" />
						</c:otherwise>
						</c:choose>
						</span>	
						<br/>
						<input type="file" size="50%" name="cv.file"/>				
					</td>		 		
			 	</tr>
			 	<tr>
			 		<td colspan="2">
 		            	<span style="font-size: small">
		            		<fmt:message key="jsp.layout.hku.primarydata.label.cvhint">
		            			<fmt:param value="${maxsizecv}"></fmt:param>
		            		</fmt:message>
            			</span>
					 	<c:if test="${!empty researcher.cv.value}">
					 	<br/><span style="font-size: xx-small"><fmt:message key="jsp.layout.hku.primarydata.label.deletecv" /></span>
					 	<input type="checkbox" name="deleteCV" value="true"/>
					 	</c:if>		
			 		</td>
			 	</table>
			 </td>
			 
			<td><form:checkbox path="cv.visibility" value="1" /></td>
		</tr>
		
			&nbsp;
			</table>
			
	</td></tr></table>
</fieldset></div>
		<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>
		<p></p>