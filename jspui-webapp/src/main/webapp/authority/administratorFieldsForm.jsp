<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@page import="javax.servlet.jsp.jstl.fmt.LocaleSupport"%>

<dspace:layout locbar="link" navbar="admin" titlekey="jsp.dspace-admin.additional-fields">

<script type="text/javascript">

	function addFieldAdditionalHUB() {
		 var ni = document.getElementById('myDiv');
		 var numi = document.getElementById('theValue');
		 var num = (document.getElementById("theValue").value - 1) + 2;
		 numi.value = num;
		 var divIdName = "my" + num + "Div";
		 var newdiv = document.createElement('div');
		 newdiv.setAttribute("id", divIdName);		 
		 newdiv.innerHTML = " Label #"+(num-1)+": <input size=\"100\%\" type=\"text\" name=\"labels[" + (num - 1) + "].value\" value=\"\"> <input type=\"button\" class=\"buttonDelete\" value=\"X\" onclick=\"deleteTempAdditionalField('" + (num) + "')\" />";
		 ni.appendChild(newdiv); 
	};
</script>
<script type="text/javascript">
	function deleteAdditionalField(id){
		
		
				 
						
		result = confirm("Warning: this operation will delete all the data provided for this field in ALL the Researcher Page! Are you sure to continue? \nNote: new added addition fields not already saved will be lost");        
        if (result == true){            
            var root = "<%= request.getContextPath()%>";
			location.href = ""+ root + "/rp/administrator/deleteAdditionalFields.htm?id_additionalField="+id;
		}

	};

	function deleteTempAdditionalField(num){
	     	 
		 var divIdName = "my" + num + "Div";
		 var mydiv = document.getElementById(divIdName);
		 mydiv.innerHTML = "";
		 				

	};
</script>
	<table width="95%">
		<tr>
			<td align="left">
			<h1><fmt:message key="jsp.dspace-admin.additional-fields" /></h1>
			</td>
			<td align="right" class="standard"><a target="_blank"
				href='<%=LocaleSupport.getLocalizedMessage(pageContext,
                                "help.site-admin.rp-additionalfields")%>'><fmt:message
				key="jsp.help" /></a></td>
		</tr>
	</table>
	
	
		<form:form commandName="dto" method="post">
		<%--  first bind on the object itself to display global errors - if available  --%>
		<spring:bind path="dto">
			<c:forEach items="${status.errorMessages}" var="error">
				<span id="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>
				<br>
			</c:forEach>
		</spring:bind>

		<table width="98%" align="left" cellpadding="0"
			cellspacing="4">
		<colgroup>
			<col width="10%" />
			<col width="80%" />
			<col width="10" />
		</colgroup>
			<tr>
				<th>Label</th>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			
			<c:forEach items="${dto.labels}" var="label"
				varStatus="i">
				<tr>
					<td>Label #${i.index}:</td>
					<td><form:input path="labels[${i.index}].value"
						size="100%" /></td>				
					<td><input type="button" class="buttonDelete" value="X" onclick="deleteAdditionalField('${label.id}')" /></td>
															
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4">
				<div id="myDiv"><input type="hidden" id="theValue"
					value="${fn:length(dto.labels)}" /></div>
				</td>
			</tr>
			<tr>
				<td><input id="addAdditionalField" name="addAdditionalField"
					type="button" onclick="addFieldAdditionalHUB()" value="Add Field" /></td>
			</tr>
	
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit"
					value="<fmt:message key="jsp.layout.hku.researcher.button.save" />" /></td>
			</tr>
		</table>

	</form:form>
</dspace:layout>