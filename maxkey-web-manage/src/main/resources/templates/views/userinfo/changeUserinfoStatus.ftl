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

	<script type="text/javascript">
	<!--
	$(function(){	
					
	});
	//-->
	</script>
</head>
<body>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/userinfo/changeUserinfoStatus"  class="needs-validation" novalidate> 

	  <table   class="table table-bordered" >
			<tbody>
			<tr>
				<th><@locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="hidden" id="id" name="id" class="required" title="" value="${model.id}"/>
					<input readonly type="text" id="displayName" name="displayName"  class="form-control"  title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username"  class="form-control"  title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.status" />:</th>
				<td>
				<select name="status"  id="status" class="form-control" >
						<option value="1"   <#if 1==model.status>selected</#if>><@locale code="userinfo.status.active" /></option>
						<option value="2"   <#if 2==model.status>selected</#if>><@locale code="userinfo.status.withdrawn" /></option>
						<option value="3"   <#if 3==model.status>selected</#if>><@locale code="userinfo.status.inactive" /></option>
						<option value="4"   <#if 4==model.status>selected</#if>><@locale code="userinfo.status.retiree" /></option>
				</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-primary mr-3"  style="width:100px"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
		    		
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</body>
</html>