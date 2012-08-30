<c:set value="${researcher}" var="researcher" scope="request" />

<script type="text/javascript"><!--


	function addMediaFields() {
		 var ni = document.getElementById('myDiv2');
		 var numi = document.getElementById('theValue2');
		 var num = (document.getElementById("theValue2").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tablemediafields')).getElementsByTagName("tbody")[0];
		 		 
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');

		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "Area #"+(num-1);
		 newTD1.innerHTML = "<textarea cols=\"57%\" rows=\"5\" name=\"media[" + (num - 1) + "].value\" value=\"\"></textarea>";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"media["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_media["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
	
	}


	function addWrittenFields() {
		 var ni = document.getElementById('myDiv4');
		 var numi = document.getElementById('theValue4');
		 var num = (document.getElementById("theValue4").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tablewrittenfields')).getElementsByTagName("tbody")[0];
		 		 
		 var row = document.createElement('tr');		 	 
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');
		 var newTD3 = document.createElement('td');
		 
		 		 	 
		 newTD0.innerHTML = "<input size=\"30\%\" type=\"text\" name=\"writtenLanguagesEN[" + (num - 1) + "].value\" value=\"\">";
		 newTD1.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"writtenLanguagesEN["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_writtenLanguagesEN["+ (num-1) +"].visibility\" value=\"on\">";
		 newTD2.innerHTML = "<input size=\"30\%\" type=\"text\" name=\"writtenLanguagesZH[" + (num - 1) + "].value\" value=\"\">";
		 newTD3.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"writtenLanguagesZH["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_writtenLanguagesZH["+ (num-1) +"].visibility\" value=\"on\">"; 
			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 row.appendChild(newTD3);
		 

		 tbody.appendChild(row);
	}

	function addSpokenFields(){
		 var ni = document.getElementById('myDiv3');
		 var numi = document.getElementById('theValue3');
		 var num = (document.getElementById("theValue3").value - 1) + 2;
		 numi.value = num;
		 var tbody = (document.getElementById('tablespokenfields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');
		 var newTD3 = document.createElement('td');
		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "<input size=\"30\%\" type=\"text\" name=\"spokenLanguagesEN[" + (num - 1) + "].value\" value=\"\">";
		 newTD1.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"spokenLanguagesEN["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_spokenLanguagesEN["+ (num-1) +"].visibility\" value=\"on\">";
		 newTD2.innerHTML = "<input size=\"30\%\" type=\"text\" name=\"spokenLanguagesZH[" + (num - 1) + "].value\" value=\"\">";
		 newTD3.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"spokenLanguagesZH["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_spokenLanguagesZH["+ (num-1) +"].visibility\" value=\"on\">"; 
			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 row.appendChild(newTD3);
		 tbody.appendChild(row);
	}
--></script>

<h1>${researcher.fullName}</h1>

<div>&nbsp;</div>
<div>&nbsp;</div>

<form:form commandName="researcher" method="post">
	<%--  first bind on the object itself to display global errors - if available  --%>
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

	<table width="98%" align="left" cellpadding="0" cellspacing="4">
		<colgroup>
			<col width="20%" />
			<col width="70%" />
			<col width="10" />
		</colgroup>

		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.media" /></legend>
			<table id="tablemediafields" align="left" cellpadding="0" cellspacing="4">

				<c:forEach items="${researcher.media}" var="media" varStatus="i">
					<tr>
						<td><fmt:message
							key="jsp.layout.hku.form.researcher.label.media">
							<fmt:param>${i.index}</fmt:param>
						</fmt:message></td>
						<td><form:textarea path="media[${i.index}].value" cols="57%"
							rows="5" /></td>
						<td><form:checkbox path="media[${i.index}].visibility"
							value="1" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
					<div id="myDiv2"><input type="hidden" id="theValue2"
						value="${fn:length(researcher.media)}" /></div>
					</td>
				</tr>



				&nbsp;

			</table>
			</fieldset>
			</td>
		</tr>
				<tr>
					<td><input id="addMedia" name="addMedia" type="button"
						onclick="addMediaFields()" value="Add Media" /></td>
				</tr>
		<tr>
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.spokes" /></legend>
			<table id="tablespokenfields" align="left" cellpadding="0" cellspacing="4">	
				<c:choose>
					<c:when
						test="${fn:length(researcher.spokenLanguagesEN)>=fn:length(researcher.spokenLanguagesZH)}">
						<c:set var="list_spoken" value="${researcher.spokenLanguagesEN}" />
					</c:when>
					<c:otherwise>
						<c:set var="list_spoken" value="${researcher.spokenLanguagesZH}" />
					</c:otherwise>
				</c:choose>
				<tr>
					<th><fmt:message
						key="jsp.layout.hku.form.researcher.label.spokesmanenglish" /></th>
					<th></th>
					<th><fmt:message
						key="jsp.layout.hku.form.researcher.label.spokesmanchinese" /></th>
				</tr>
				<c:forEach items="${list_spoken}" var="spokenEN" varStatus="i">

					<tr>
						<td><form:input path="spokenLanguagesEN[${i.index}].value"
							size="30%" /></td>
						<td><form:checkbox
							path="spokenLanguagesEN[${i.index}].visibility" value="1" /></td>
						<td><form:input path="spokenLanguagesZH[${i.index}].value"
							size="30%" /></td>
						<td><form:checkbox
							path="spokenLanguagesZH[${i.index}].visibility" value="1" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td id="myDiv3" colspan="4">
					<input type="hidden" id="theValue3"
						value="${fn:length(researcher.spokenLanguagesEN)}" />
					</td>
				</tr>
				
				&nbsp;
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
					<td><input id="addSpoken" name="addSpoken" type="button"
						onclick="addSpokenFields()" value="Add Spoken" /></td>
				</tr>
		
		<tr>
			<td>
			<fieldset><legend><fmt:message
				key="jsp.layout.hku.form.researcher.fieldset.written" /></legend>
			<table id="tablewrittenfields" align="left" cellpadding="0" cellspacing="4">
				<c:choose>
					<c:when
						test="${fn:length(researcher.writtenLanguagesEN)>=fn:length(researcher.writtenLanguagesZH)}">
						<c:set var="list_written" value="${researcher.writtenLanguagesEN}" />
					</c:when>
					<c:otherwise>
						<c:set var="list_written" value="${researcher.writtenLanguagesZH}" />
					</c:otherwise>
				</c:choose>
				<tr>
					<th><fmt:message
						key="jsp.layout.hku.form.researcher.label.writtenmanenglish" /></th>
					<th></th>
					<th><fmt:message
						key="jsp.layout.hku.form.researcher.label.writtenmanchinese" /></th>
				</tr>
				<c:forEach items="${list_written}" var="written" varStatus="i">

					<tr>
						<td><form:input path="writtenLanguagesEN[${i.index}].value"
							size="30%" /></td>
						<td><form:checkbox
							path="writtenLanguagesEN[${i.index}].visibility" value="1" /></td>
						<td><form:input path="writtenLanguagesZH[${i.index}].value"
							size="30%" /></td>
						<td><form:checkbox
							path="writtenLanguagesZH[${i.index}].visibility" value="1" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
					<div id="myDiv4"><input type="hidden" id="theValue4"
						value="${fn:length(researcher.writtenLanguagesEN)}" /></div>
					</td>
				</tr>
				

				&nbsp;
			</table>
			</fieldset>
			</td>
		</tr>

			<tr>
					<td><input id="addWritten" name="addWritten" type="button"
						onclick="addWrittenFields()" value="Add Written" /></td>
				</tr>

		<tr>
			<td colspan="3"><input type="submit" value="Save Changes" /></td>
		</tr>
	</table>

</form:form>