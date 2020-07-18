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
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/roles/add"  class="needs-validation" novalidate>
	<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
		<tbody>
			<tr>
				<th><@locale code="role.id" />：</th>
				<td nowrap>
					<input type="text" id="id" name="id" class="form-control" title="" value=""  />
				</td>
			</tr>
			<tr>
				<th><@locale code="role.name" />：</th>
				<td nowrap>
					<input type="text" id="name" name="name" class="form-control" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
                <th><@locale code="common.text.description" />：</th>
                <td nowrap>
                    <input type="text" id="description" name="description" class="form-control" title="" value=""  />
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
</body>
</html>