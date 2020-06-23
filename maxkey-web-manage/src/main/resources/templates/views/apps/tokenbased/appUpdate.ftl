<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
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
	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/"+$("#algorithm").val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
});
//-->
</script>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/tokenbased/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"   class="table table-bordered" >
								<tbody>
								
								<tr>
									<td colspan=4><@locale code="apps.tokenbased.info" /></td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.tokenbased.redirectUri" />：</th>
									<td  colspan=3>
										<input type="text" id="redirectUri" class="form-control" name="redirectUri"  title="" value="${model.redirectUri}"  required="" />
									</td>
								</tr>
								<tr>
									<th ><@locale code="apps.tokenbased.tokenType" />：</th>
									<td >
										<select id="tokenType" name="tokenType"  class="form-control">
											<option value="POST" <#if 'POST'==model.tokenType>selected</#if> >安全令牌(TOKEN POST)</option>
											<option value="LTPA" <#if 'LTPA'==model.tokenType>selected</#if> >轻量级认证(LTPA COOKIE)</option>
										</select>
									</td>
									<th ><@locale code="apps.tokenbased.cookieName" />：</th>
									<td >
										<input type="text" class="form-control" id="cookieName" name="cookieName"  title="" value="${model.cookieName!}"/>
										<b class="orange">*</b><label for="cookieName"></label>
									</td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.tokenbased.algorithm" />：</th>
									<td style="width:35%;">
										<select id="algorithm" name="algorithm" class="form-control" >
											<option value="DES"  <#if 'DES'==model.algorithm>selected</#if> >DES</option>
											<option value="DESede" <#if 'DESede'==model.algorithm>selected</#if>>DESede</option>
											<option value="Blowfish" <#if 'Blowfish'==model.algorithm>selected</#if>>Blowfish</option>
											<option value="AES" <#if 'AES'==model.algorithm>selected</#if>>AES</option>
											<option value="HS256" <#if 'HS256'==model.algorithm>selected</#if>>HMAC SHA-256</option>
											<option value="RS256" <#if 'RS256'==model.algorithm>selected</#if>>RSA SHA-256</option>
											
										</select>
									</td>
									<th style="width:15%;"><@locale code="apps.tokenbased.algorithmKey" />：</th>
									<td style="width:35%;">
										<span id="algorithmKey_text">${model.algorithmKey}</span>
										<input type="hidden" id="algorithmKey" name="algorithmKey"  title="" value="${model.algorithmKey}"/>

									</td>
								</tr>
								<tr>
									<th><@locale code="apps.tokenbased.token.content" />：</th>
									<td colspan=3>
										<table  class="hidetable"  style="width:100%;">
											<tr>
												<td><@locale code="userinfo.id" /><input type="checkbox" id="uid" name="uid" value="1" <#if 1==model.uid>checked</#if> /></td>
												<td><@locale code="userinfo.username" /><input type="checkbox" id="username" name="username" value="1" <#if 1==model.username>checked</#if>/></td>
												<td><@locale code="userinfo.email" /><input type="checkbox" id="email" name="email" value="1" <#if 1==model.email>checked</#if>/></td>
												<td><@locale code="userinfo.windowsAccount" /><input type="checkbox" id="windowsAccount" name="windowsAccount" value="1" <#if 1==model.windowsAccount>checked</#if>/></td>
												<td><@locale code="userinfo.employeeNumber" /><input type="checkbox" id="employeeNumber" name="employeeNumber" value="1" <#if 1==model.employeeNumber>checked</#if>/></td>
												<td><@locale code="userinfo.departmentId" /><input type="checkbox" id="departmentId" name="departmentId" value="1" <#if 1==model.departmentId>checked</#if>/></td>
												<td><@locale code="userinfo.department" /><input type="checkbox" id="department" name="department" value="1" <#if 1==model.department>checked</#if>/></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<th><@locale code="apps.tokenbased.expires" />：</th>
									<td>
										<input type="text" class="form-control" id="expires" name="expires"  title="" value="${model.expires}"  required="" />
									</td>
									<th><@locale code="apps.isAdapter" />：</th>
									<td>
										<select  id="isAdapter" name="isAdapter" class="form-control">
											<option value="0"  <#if 0==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.no" /></option>
											<option value="1"  <#if 1==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.yes" /></option>
										</select>
									</td>
								</tr>
								<tr>
									<th><@locale code="apps.adapter" />：</th>
									<td colspan =3>
										<select id="selectAdapter" name="selectAdapter"  class="form-control">
											<option value="">No Adapter</option>
											<option value="org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter" <#if 'org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter'==model.adapter!false>selected</#if>>DefaultAdapter</option>
											<option value="org.maxkey.authz.token.endpoint.adapter.TokenBasedSimpleAdapter"  <#if 'org.maxkey.authz.token.endpoint.adapter.TokenBasedSimpleAdapter'==model.adapter!>selected</#if> >SimpleAdapter</option>
											<option value="org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTAdapter" <#if 'org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTAdapter'==model.adapter!>selected</#if>>JWTAdapter</option>
											<option value="org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTHS256Adapter" <#if 'org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTHS256Adapter'==model.adapter!>selected</#if>>JWTHS256Adapter</option>
										</select>
									</td>
								</tr>
								<tr>
									<th><@locale code="apps.adapter" />：</th>
									<td colspan =3>
										<input type="text" class="form-control" id="adapter" name="adapter"  title="" value="${model.adapter!}"/>
									</td>
								</tr>
								<tr>
									<td colspan =4>
							    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
										<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	
									</td>
								</tr>
								</tbody>
							  </table>
			  </td>
				</tr>
				</tbody>
				</table>  
</form>
</body>
</html>