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
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/synchronizers/update"  class="needs-validation" novalidate>
	 <table  border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
		<tbody>
		<tr style="display:none">
			<th><@locale code="synchronizers.id" /></th>
			<td nowrap>
				<input id="id" type="text" readonly name="id"  class="form-control"   value="${model.id}"/>
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.name" /></th>
			<td nowrap>
				<input type="text" id="name" name="name" class="form-control" title="" value="${model.name}"  required="" />
			</td>
		</tr>
		
		<tr  style="display:none">
			<th><@locale code="synchronizers.sourceType" /></th>
			<td nowrap>
				<input type="text" id="sourceType" name="sourceType" class="form-control" title="" value="${model.sourceType}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.scheduler" /></th>
			<td nowrap>
				<input type="text" id="scheduler" name="scheduler" class="form-control" title="" value="${model.scheduler!}"   />
			</td>
		</tr>
	<#if "LDAP"!=model.sourceType && "MSAD"!=model.sourceType && "JDBC"!=model.sourceType>
		<tr>
			<th><@locale code="synchronizers.principal" /></th>
			<td nowrap>
				<input type="text" id="principal" name="principal" class="form-control" title="" value="${model.principal!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.credentials" /></th>
			<td nowrap>
				<input type="password" id="credentials" name="credentials" class="form-control" title="" value="${model.credentials!}"  required="" />
			</td>
		</tr>
	</#if>
	<#if "JDBC"==model.sourceType>
		<tr>
			<th><@locale code="synchronizers.providerUrl" /></th>
			<td nowrap>
				<input type="text" id="providerUrl" name="scheduler" class="form-control" title="" value="${model.providerUrl!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.driverClass" /></th>
			<td nowrap>
				<input type="text" id="driverClass" name="driverClass" class="form-control" title="" value="${model.driverClass!}"   />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.principal" /></th>
			<td nowrap>
				<input type="text" id="principal" name="principal" class="form-control" title="" value="${model.principal!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.credentials" /></th>
			<td nowrap>
				<input type="password" id="credentials" name="credentials" class="form-control" title="" value="${model.credentials!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.filters" /></th>
			<td nowrap>
				<textarea id="filters" name="filters" class="form-control"  rows="3" cols="20">${model.filters!}</textarea>
			</td>
		</tr>
	</#if>
	<#if "LDAP"==model.sourceType || "MSAD"==model.sourceType>
		<tr>
			<th><@locale code="synchronizers.providerUrl" /></th>
			<td nowrap>
				<input type="text" id="providerUrl" name="providerUrl" class="form-control" title="" value="${model.providerUrl!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.principal" /></th>
			<td nowrap>
				<input type="text" id="principal" name="principal" class="form-control" title="" value="${model.principal!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.credentials" /></th>
			<td nowrap>
				<input type="password" id="credentials" name="credentials" class="form-control" title="" value="${model.credentials!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.basedn" /></th>
			<td nowrap>
				<input type="text" id="basedn" name="basedn" class="form-control" title="" value="${model.basedn!}"  required="" />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.msadDomain" /></th>
			<td nowrap>
				<input type="text" id="msadDomain" name="msadDomain" class="form-control" title="" value="${model.msadDomain!}"  />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.ssl" /></th>
			<td nowrap>
				<input type="text" id="sslSwitch" name="sslSwitch" class="form-control" title="" value="${model.sslSwitch!}"  />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.trustStore" /></th>
			<td nowrap>
				<input type="text" id="trustStore" name="trustStore" class="form-control" title="" value="${model.trustStore!}"  />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.trustStorePassword" /></th>
			<td nowrap>
				<input type="password" id="trustStorePassword" name="trustStorePassword" class="form-control" title="" value="${model.trustStorePassword!}"  />
			</td>
		</tr>
		<tr>
			<th><@locale code="synchronizers.filters" /></th>
			<td nowrap>
				<textarea id="filters" name="filters" class="form-control"  rows="2" cols="20">${model.filters!}</textarea>
			</td>
		</tr>
	</#if>
	
		<tr>
				<th><@locale code="synchronizers.syncStartTime" /></th>
				<td nowrap>
					<input type="text" id="syncStartTime" name="syncStartTime" class="form-control" title="" value="${model.syncStartTime!}"   />
				</td>
		</tr>
		<tr>
				<th><@locale code="synchronizers.resumeTime" /></th>
				<td nowrap>
					<input type="text" id="resumeTime" name="resumeTime" class="form-control timepicker" title="" value="${model.resumeTime!}"   />
				</td>
			</tr>
			<tr>
				<th><@locale code="synchronizers.suspendTime" /></th>
				<td nowrap>
					<input type="text" id="suspendTime" name="suspendTime" class="form-control timepicker" title="" value="${model.suspendTime!}"  />
				</td>
		</tr>

		
		<tr>
                <th><@locale code="common.text.description" /></th>
                <td nowrap>
                	<textarea id="description" name="description" class="form-control"  rows="4" cols="20">${model.description!}</textarea>
                </td>
        </tr>
		<tr>
			<td nowrap colspan="2"  class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
				<input id="status" type="hidden" name="status"  value="1"/>
	    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />">
  				<input class="button btn btn-secondary mr-3"  id="closeBtn"   type="button" value="<@locale code="button.text.cancel" />">	 
			</td>
		</tr>
		</tbody>
	  </table>
</form>
<div id="orgContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="orgsTree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
</body>
</html>