<table  width="100%" border="1">
	<tr>
		<th><!-- spacer cell --></th>
		<th><fmt:message key="view.${data.jspKey}.data.${key1}.${key2}.${key3}.row-0" /></th>
	</tr>
	<tr class="evenRowOddCol">
		<td width="99%">
			<fmt:message key="view.${data.jspKey}.data.${key1}.${key2}.${key3}.col-0" />
		</td>
		<td>
			${data.resultBean.dataBeans[key1][key2][key3].dataTable[0][0]}
		</td>						
	</tr>
</table>