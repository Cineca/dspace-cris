<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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


	<table width="98%" align="left" cellpadding="0"
		cellspacing="4">

	
		<tr>
		<td>
		<table id="tabledataimagefields" align="left" cellpadding="0" cellspacing="4">
		<tr>
			
			<td><span class="dynaLabel"><fmt:message key="jsp.layout.hku.primarydata.label.picture" /></span></td>
			<td>
				<div id="image" style="width: 113px;height: 132px;overflow: hidden">
				<c:choose>
				<c:when test="${!empty researcher.pict.value}">
					<img id="picture" name="picture" alt="A ${researcher.fullName} picture" src="<%=request.getContextPath() %>/researcherimage/${authority_key}"
						title="A ${researcher.fullName} picture" />
				</c:when>
				<c:otherwise>
					<img id="picture" name="picture" alt="No image" src="<%=request.getContextPath() %>/image/authority/noimage.jpg"
						title="No picture for ${researcher.fullName}" />
				</c:otherwise>
				</c:choose></div>
			
				<c:if test="${!empty researcher.urlPict}"><a target="_blank" href="${researcher.urlPict}"><span style="font-size: xx-small"><fmt:message key="jsp.layout.hku.primarydata.label.pictureurl" /></span></a></c:if>		
				
			 	<input type="file" size="50%" name="pict.file"/>
            	<div style="font-size: small">
            		<fmt:message key="jsp.layout.hku.primarydata.label.picturehint">
            			<fmt:param value="${maxsizepict}"></fmt:param>
            		</fmt:message>
            	</div>
			 	<c:if test="${!empty researcher.pict.value}"><div style="font-size: xx-small"><fmt:message key="jsp.layout.hku.primarydata.label.deletepicture" /><input type="checkbox" name="deleteImage" value="true"/></div></c:if>		
			 </td>
			 
			<td><form:checkbox path="pict.visibility" value="1" /></td>
		</tr>
		
			&nbsp;
			</table>
			</td>
		</tr>
		

	
	</table>
</fieldset></div>
		<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>
		<p></p>