<c:set value="${researcher}" var="researcher" scope="request" />

<script type="text/javascript">

	function addVariant() {
		 var ni = document.getElementById('myDiv');
		 var numi = document.getElementById('theValue');
		 var num = (document.getElementById("theValue").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tablevariantfields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');

		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "Variant #"+(num-1);
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"variants[" + (num - 1) + "].value\" value=\"\">";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"variants["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_variants["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
		 		 
	}

	function addTitle() {
		 var ni = document.getElementById('myDivTitle');
		 var numi = document.getElementById('theValueTitle');
		 var num = (document.getElementById("theValueTitle").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tabletitlefields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');

		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "Title #"+(num-1);
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"title[" + (num - 1) + "].value\" value=\"\">";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"title["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_title["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
	}

	function addInterest() {
		 var ni = document.getElementById('myDivInterests');
		 var numi = document.getElementById('theValueInterests');
		 var num = (document.getElementById("theValueInterests").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tableinterestfields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');
	 
		 newTD0.innerHTML = "Interest #"+(num-1);
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"interests[" + (num - 1) + "].value\" value=\"\">";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"interests["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_interests["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
	}
</script>

<h1>${researcher.fullName}</h1>

<div>&nbsp;</div>
<div>&nbsp;</div>

<form:form commandName="researcher" method="post" enctype="multipart/form-data">

	<%-- if you need to display all errors (both global and all field errors,
		 use wildcard (*) in place of the property name --%>
	<spring:bind path="researcher.*">
		<c:if test="${!empty status.errorMessages}">
		<div id="errorMessages">
		</c:if>
		<c:forEach items="${status.errorMessages}" var="error">
			<span class="errorMessage"><fmt:message key="jsp.layout.hku.prefix-error-code"/> ${error}</span>									
			<br />
		</c:forEach>
		<c:if test="${!empty status.errorMessages}">
		<div id="errorMessages">
		</c:if>
	</spring:bind>


	<table width="98%" align="left" cellpadding="0"
		cellspacing="4">

		<tr>
		<td>
			<table id="tabledatafields" align="left" cellpadding="0" cellspacing="4">
			&nbsp;
		
		<% if (isAdmin) { %>
		
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.staffno" /></td>
			<td><form:input path="staffNo" size="80%" /></td>
			<td></td>
		</tr>
		<tr>

			<td><fmt:message key="jsp.layout.hku.primarydata.label.fullname" /></td>
			<td><form:input path="fullName" size="80%" /></td>
			<td></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<% } %>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.academicname" /></td>
			<td><form:input path="academicName.value" size="80%" /></td>
			<td><form:checkbox path="academicName.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.chinesename" /></td>
			<td><form:input path="chineseName.value" size="80%" /></td>
			<td><form:checkbox path="chineseName.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.honorific" /></td>
			<td><form:input path="honorific.value" size="80%" /></td>
			<td><form:checkbox path="honorific.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.bio" /></td>
			<td><form:input path="bio.value"  size="80%" /></td>
			<td><form:checkbox path="bio.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>	
		&nbsp;
		</table>
		
		</td>
		</tr>
		<tr>		
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.title" /></legend>
		<table id="tabletitlefields" align="left" cellpadding="0" cellspacing="4">
		<c:forEach items="${researcher.title}" var="title"
			varStatus="i">
			<tr>
				<td>Title #${i.index}:</td>
				<td><form:input path="title[${i.index}].value"
					size="80%" /></td>
				<td><form:checkbox
					path="title[${i.index}].visibility" value="1" /></td>
			</tr>
		</c:forEach>
		<tr>											
			<td colspan="4"><div id="myDivTitle"><input type="hidden" id="theValueTitle" value="${fn:length(researcher.title)}"/></div></td>
		</tr>
		&nbsp;
		</table>
		</fieldset>
		</td>
		</tr>
		<tr>
			<td><input id="addTitles" name="addTitles" type="button" onclick="addTitle()" value="Add Title"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
		<td>
		<table id="tabledatacvfields" align="left" cellpadding="0" cellspacing="4">
		<tr>
			
			<td nowrap="nowrap"><fmt:message key="jsp.layout.hku.primarydata.label.cv" />
			</td>
			<td>
				<span style="font-size: small"><fmt:message key="jsp.layout.hku.primarydata.label.cv-url-or-file" /></span><br/>
			 	
			 	<table>
			 	<tr>
			 		<td nowrap="nowrap">
			 			<form:label path="cv.remoteUrl"><fmt:message key="jsp.layout.hku.primarydata.label.cv-url" /></form:label>
			 		</td>
			 		<td>
				 		<span style="font-size: small">
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
			</td>
		</tr>
		<tr>
		<td>
		<table id="tabledataimagefields" align="left" cellpadding="0" cellspacing="4">
		<tr>
			
			<td><fmt:message key="jsp.layout.hku.primarydata.label.picture" /></td>
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
		

		<tr>		
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.variants" /></legend>
			<table id="tablevariantfields" align="left" cellpadding="0" cellspacing="4">

				<c:forEach items="${researcher.variants}" var="variant"
					varStatus="i">
				<tr>
				<td>Variant #${i.index}:</td>
				<td><form:input path="variants[${i.index}].value"
					size="80%" /></td>
				<td><form:checkbox
					path="variants[${i.index}].visibility" value="1" /></td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="4"><div id="myDiv"><input type="hidden" id="theValue" value="${fn:length(researcher.variants)}"/></div></td>
				</tr>

				&nbsp;

			</table>
			</fieldset>
			</td>
		
		</tr>
		<tr>
			<td><input id="addVariants" name="addVariants" type="button" onclick="addVariant()" value="Add Variant"/></td>
		</tr>
		<tr>
		<td>
		<tr><td>
		<table id="tabledataotherfields" align="left" cellpadding="0" cellspacing="4">
		<tr>

			<td><fmt:message key="jsp.layout.hku.primarydata.label.dept" /></td>
			<td><form:input path="dept.value" size="80%" /></td>
			<td><form:checkbox path="dept.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.email" /></td>
			<td><form:input path="email.value" size="80%" /></td>
			<td><form:checkbox path="email.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.office" /></td>
			<td><form:input path="address.value" size="80%" /></td>
			<td><form:checkbox path="address.visibility" value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.officetel" /></td>
			<td><form:input path="officeTel.value" size="80%" /></td>
			<td><form:checkbox path="officeTel.visibility"
				value="1" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="jsp.layout.hku.primarydata.label.personalurl" /></td>
			<td><form:input path="urlPersonal.value" size="80%" /></td>
			<td><form:checkbox path="urlPersonal.visibility"
				value="1" /></td>
		</tr>
		&nbsp;</table></td>
		</tr>

		<tr>		
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.interest" /></legend>
			<table id="tableinterestfields" align="left" cellpadding="0" cellspacing="4">
		<c:forEach items="${researcher.interests}" var="interest"
			varStatus="i">
			<tr>
				<td>Interest #${i.index}:</td>
				<td><form:input path="interests[${i.index}].value"
					size="80%" /></td>
				<td><form:checkbox
					path="interests[${i.index}].visibility" value="1" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4">
			<div id="myDivInterests"><input type="hidden" id="theValueInterests"
				value="${fn:length(researcher.interests)}" /></div>
			</td>
		</tr>
		
		</table>
			</fieldset>
			</td>
		</tr>
		
		<tr>
			<td><input id="addInterests" name="addInterests"
				type="button" onclick="addInterest()" value="Add Interest" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit"
				value="<fmt:message key="jsp.layout.hku.researcher.button.save"/>" /></td>
		</tr>
	</table>

</form:form>
