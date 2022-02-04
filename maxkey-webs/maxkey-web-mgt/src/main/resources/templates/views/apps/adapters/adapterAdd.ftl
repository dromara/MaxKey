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

</script>
</head>
<body>
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/apps/adapters/add"  class="needs-validation" novalidate>
	<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
		<tbody>
			<tr>
				<th><@locale code="common.text.id" /></th>
				<td nowrap>
					<input type="text" id="id" name="id" class="form-control" title="" value=""  />
				</td>
			</tr>
			<tr>
				<th><@locale code="apps.adapter.name" /></th>
				<td nowrap>
					<input type="text" id="name" name="name" class="form-control" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
				<th><@locale code="apps.adapter.protocol" /></th>
				<td nowrap>
					<select name="protocol" class="form-control  form-select">
	 					<option value=""  selected>Select</option>
	 					<option value="OAuth_v2.0"><@locale code="apps.protocol.oauth2.0" /></option>
	 					<option value="SAML_v2.0"><@locale code="apps.protocol.saml2.0" /></option>
	 					<option value="CAS"><@locale code="apps.protocol.cas" /></option>
	 					<option value="JWT"><@locale code="apps.protocol.jwt" /></option>
	 					<option value="Token_Based"><@locale code="apps.protocol.tokenbased" /></option>
	 					<option value="Extend_API"><@locale code="apps.protocol.extendapi" /></option>
	 					<option value="Form_Based"><@locale code="apps.protocol.formbased" /></option>
	 				</select>
				</td>
			</tr>
			<tr>
				<th><@locale code="apps.adapter.adapter" /></th>
				<td nowrap>
					<input type="text" id="adapter" name="adapter" class="form-control" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
	         	<th><@locale code="common.text.sortindex"/></th>
	         	<td>
	         		<input  type="text" id="sortIndex" class="form-control"  name="sortIndex"  title="" value="1"  required=""    />
	         	</td>
	      	</tr>
			<tr>
                <th><@locale code="common.text.description" /></th>
                <td nowrap>
                    <textarea id="description" name="description" class="form-control"  rows="2" cols="20"></textarea>
                </td>
            </tr>
			
			<tr>
				<td nowrap colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input  id="status" type="hidden" name="status"  value="1"/>
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