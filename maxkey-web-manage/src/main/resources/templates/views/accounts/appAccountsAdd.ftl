<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
</head>
<body>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/app/accounts/add">
	<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
		<tbody>
			<tr style="display:none">
				<th><@locale code="userinfo.id" />：</th>
				<td nowrap>
					<input type="text" id="id" name="id" readonly  class="form-control" title="" value="${model.id!}"/>
	
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" />：</th>
				<td nowrap>
					<input readonly type="text" id="username" name="username"  class="form-control username" title="" value="${model.username!}"/>
					<input class="button btn btn-primary mr-3 window"  type="button"    id="selectUserinfoBtn" value="<@locale code="button.text.select" />"
					 wurl="<@base/>/userinfo/select"
						 		    wwidth="800"
						 		    wheight="500"
					 		    	target="window"/>

				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.displayName" />：</th>
				<td nowrap>
					<input readonly  type="text" id="displayName" name="displayName"  class="form-control displayName"  title="" value="${model.displayName!}"/>
			
				</td>
			</tr>
			<tr>
				<th><@locale code="apps.name" />：</th>
				<td nowrap>
					<input readonly  type="text" id="appName" name="appName"  class="form-control appName" title="" value="${model.appName!}"/>
					<input class="button btn btn-primary mr-3 window"  type="button"    id="selectAppsubmitBtn" value="<@locale code="button.text.select" />"
					  wurl="<@base/>/apps/select"
						 		    wwidth="800"
						 		    wheight="500"
					 		    	target="window"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="account.relatedUsername" />：</th>
				<td nowrap>
					<input type="text" id="relatedUsername" name="relatedUsername"  class="form-control" title="" value="${model.relatedUsername!}"/>

				</td>
			</tr>
			<tr>
				<th><@locale code="account.relatedPassword" />：</th>
				<td nowrap>
					<input type="password" id="relatedPassword" name="relatedPassword"  class="form-control" title="" value="${model.relatedPassword!}"/>
		
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="status" type="hidden" name="status"  value="1"/>
					<input type="hidden" id="uid" name="uid" class="uid" title="" value="${model.uid!}"/>
					<input type="hidden" id="appId" name="appId" class="appId" title="" value="${model.appId!}"/>
			   		<input class="button btn btn-primary mr-3"  type="button"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					<input class="button"  type="button"    id="closeBtn" value="<@locale code="button.text.cancel" /> "/>	
					
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
</html>