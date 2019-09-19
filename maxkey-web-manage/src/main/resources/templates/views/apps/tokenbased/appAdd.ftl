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
	
	$("#selectAdapter").change(function(){
		$("#adapter").val($("#selectAdapter").val());
	}); 

});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/tokenbased/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="datatable" >
						<tbody>
						<tr>
							<td colspan=4><s:Locale code="apps.tokenbased.info" /></td>
							</tr>
							<tr>
								<th style="width:15%;"><s:Locale code="apps.tokenbased.redirectUri" />：</th>
								<td style="width:35%;" colspan=3>
									<input type="text" id="redirectUri" name="redirectUri"  title="" value=""/>
									<b class="orange">*</b><label for="redirectUri"></label>
								</td>
							</tr>
							<tr>
								<th style="width:15%;"><s:Locale code="apps.tokenbased.algorithm" />：</th>
								<td style="width:35%;">
									<select id="algorithm" name="algorithm"  >
										<option value="DES">DES</option>
										<option value="DESede">DESede</option>
										<option value="Blowfish">Blowfish</option>
										<option value="AES"  selected>AES</option>
										<option value="HS256"  >HMAC SHA-256</option>
										<option value="RS256"  >RSA SHA-256</option>
									</select>
									<b class="orange">*</b><label for="algorithm"></label>
								</td>
								<th width="140px"><s:Locale code="apps.tokenbased.algorithmKey" />：</th>
								<td width="340px">
									<span id="algorithmKey_text">${model.algorithmKey}</span>
									<input type="hidden" id="algorithmKey" name="algorithmKey"  title="" value="${model.algorithmKey}"/>
								
								</td>
							</tr>
							<tr>
								<th><s:Locale code="apps.tokenbased.token.content" />：</th>
								<td colspan=3>
									<table class="hidetable" style="width:100%;">
										<tr>
											<td><s:Locale code="userinfo.id" /><input type="checkbox" id="uid" name="uid" value="1"/></td>
											<td><s:Locale code="userinfo.username" /><input type="checkbox" id="username" name="username" value="1"/></td>
											<td><s:Locale code="userinfo.email" /><input type="checkbox" id="email" name="email" value="1"/></td>
											<td><s:Locale code="userinfo.windowsAccount" /><input type="checkbox" id="windowsAccount" name="windowsAccount" value="1"/></td>
											<td><s:Locale code="userinfo.employeeNumber" /><input type="checkbox" id="employeeNumber" name="employeeNumber" value="1"/></td>
											<td><s:Locale code="userinfo.departmentId" /><input type="checkbox" id="departmentId" name="departmentId" value="1"/></td>
											<td><s:Locale code="userinfo.department" /><input type="checkbox" id="department" name="department" value="1"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><s:Locale code="apps.tokenbased.expires" />：</th>
								<td>
									<input type="text" id="expires" name="expires"  title="" value="1"/>
								</td>
								<th><s:Locale code="apps.isAdapter" />：</th>
								<td >
									<select  id="isAdapter" name="isAdapter" >
										<option value="0"  selected><s:Locale code="apps.isAdapter.no" /></option>
										<option value="1"><s:Locale code="apps.isAdapter.yes" /></option>
									</select>
								</td>
							</tr>
							<tr>
								<th><s:Locale code="apps.adapter" />：</th>
								<td colspan =3>
									<select id="selectAdapter" name="selectAdapter"  >
										<option value="">No Adapter</option>
										<option value="com.connsec.web.authorize.endpoint.adapter.TokenBasedDefaultAdapter" <c:if test="${'com.connsec.web.authorize.endpoint.adapter.TokenBasedDefaultAdapter'==model.adapter}">selected</c:if>>DefaultAdapter</option>
										<option value="com.connsec.web.authorize.endpoint.adapter.TokenBasedSimpleAdapter"  <c:if test="${'com.connsec.web.authorize.endpoint.adapter.TokenBasedSimpleAdapter'==model.adapter}">selected</c:if> >SimpleAdapter</option>
										<option value="com.connsec.web.authorize.endpoint.adapter.TokenBasedJWTAdapter" <c:if test="${'com.connsec.web.authorize.endpoint.adapter.TokenBasedJWTAdapter'==model.adapter}">selected</c:if>>JWTAdapter</option>
										<option value="com.connsec.web.authorize.endpoint.adapter.TokenBasedJWTHS256Adapter" <c:if test="${'com.connsec.web.authorize.endpoint.adapter.TokenBasedJWTHS256Adapter'==model.adapter}">selected</c:if>>JWTHS256Adapter</option>
									</select>
								</td>
							</tr>
							<tr>
								<th><s:Locale code="apps.adapter" />：</th>
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
    		<input class="button"  id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button"  id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>