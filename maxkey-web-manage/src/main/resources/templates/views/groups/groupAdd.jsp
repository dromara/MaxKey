
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/groups/add">
	<table border="0" cellpadding="0" cellspacing="0" class="datatable" >
		<tbody>
			<tr>
				<th><s:Locale code="group.name" />：</th>
				<td nowrap>
					<span class="intspan"><input type="text" id="name" name="name" class="int required" title="" value="${group.name}"/></span>
					<b class="orange">*</b><label for="name"></label>
				</td>
			</tr>
			<tr>
				<td nowrap colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input  id="status" type="hidden" name="status"  value="1"/>
		    		<input class="button"  id="submitBtn" type="button" value="<s:Locale code="button.text.save" />">
	  				<input class="button"  id="closeBtn"   type="button" value="<s:Locale code="button.text.cancel" />"> 
				</td>
			</tr>
		</tbody>
	</table>
</form>