<script type="text/javascript">
<!--
$(function(){	
	$("#algorithm").change(function(){
		$.post("<@base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
			
		});
	}); 

});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/ltpa/add"  
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960" class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="datatable" >
						<tbody>
							<tr>
								<td colspan=4><@locale code="apps.ltpa.info" /></td>
							</tr>
							<tr>
								<th><@locale code="apps.ltpa.redirectUri" />：</th>
								<td  colspan=3>
									<input type="text" id="redirectUri" name="redirectUri"  title="" value=""/>
									<b class="orange">*</b><label for="redirectUri"></label>
								</td>
							</tr>
							<tr>
									<th ><@locale code="apps.ltpa.cookieName" />：</th>
									<td  colspan=3>
										<input type="text" id="redirectUri" name="cookieName"  title="" value=""/>
										<b class="orange">*</b><label for="cookieName"></label>
									</td>
								</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.ltpa.algorithm" />：</th>
								<td style="width:35%;" >
									<select id="algorithm" name="algorithm" >
										<option value="DES" selected>DES</option>
										<option value="DESede">DESede</option>
										<option value="Blowfish">Blowfish</option>
										<option value="AES">AES</option>
									</select>
									<b class="orange">*</b><label for="algorithm"></label>
								</td>
								<th style="width:15%;"><@locale code="apps.ltpa.algorithmKey" />：</th>
								<td style="width:35%;" >
									<span id="algorithmKey_text">${model.algorithmKey}</span>
									<input type="hidden" id="algorithmKey" name="algorithmKey"  title="" value="${model.algorithmKey}"/>
								
								</td>
							</tr>
							<tr>
								<th><@locale code="apps.ltpa.token.content" />：</th>
								<td colspan=3>
									<table class="hidetable" style="width:100%;">
										<tr>
											<td><@locale code="userinfo.id" /><input type="checkbox" id="uid" name="uid" value="1"/></td>
											<td><@locale code="userinfo.username" /><input type="checkbox" id="username" name="username" value="1"/></td>
											<td><@locale code="userinfo.email" /><input type="checkbox" id="email" name="email" value="1"/></td>
											<td><@locale code="userinfo.windowsAccount" /><input type="checkbox" id="windowsAccount" name="windowsAccount" value="1"/></td>
											<td><@locale code="userinfo.employeeNumber" /><input type="checkbox" id="employeeNumber" name="employeeNumber" value="1"/></td>
											<td><@locale code="userinfo.departmentId" /><input type="checkbox" id="departmentId" name="departmentId" value="1"/></td>
											<td><@locale code="userinfo.department" /><input type="checkbox" id="department" name="department" value="1"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><@locale code="apps.ltpa.expires" />：</th>
								<td>
									<input type="text" id="expires" name="expires"  title="" value="1"/>
								</td>
								<th><@locale code="apps.isAdapter" />：</th>
								<td>
									<select  id="isAdapter" name="isAdapter" >
										<option value="0"  selected>
											<@locale code="apps.isAdapter.no" /></option>
										<option value="1">
											<@locale code="apps.isAdapter.yes" /></option>
									</select>
								</td>
							</tr>
							<tr>
								<th><@locale code="apps.adapter" />：</th>
								<td colspan =3>
									<input type="text" id="adapter" name="adapter"  title="" value=""/>
								</td>
							</tr>
							</tbody>
						  </table>
					</td>
				</tr>
				</tbody>
				</table>
			   

			<input class="button"  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>