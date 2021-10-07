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

<form 
	enctype="multipart/form-data" 
	method="post" type="label" autoclose="true"  action="<@base/>/userinfo/import"  
	class="needs-validation" novalidate> 

	  <table   class="table table-bordered" >
			<tbody>
			<tr>
				<th nowrap="nowrap"><@locale code="import.file.select" /></th>
				<td>
					<input class="form-control"  type="file" id="excelFile" name="excelFile"  />
				</td>
			</tr>
			<tr>
				<td  colspan="2">
					<a href="<@base/>/static/template/excel/Users_Import_Template.xlsx" ><@locale code="import.template.download" /></>
				</td>
			</tr>
			<tr>
				<th><@locale code="import.update.exist" /> </th>
				<td>
					<select name="updateExist"  id="updateExist" class="form-control  form-select" >
						<option value="no" selected > <@locale code="common.text.no" /></option>
						<option value="yes"> <@locale code="common.text.yes" /> </option>
					</select>
				</td>
			</tr>
			<tr>
				<th colspan="2" style="color: red;">
					<@locale code="import.tip" /> 
				</th>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-primary mr-3"  style="width:100px"  type="submit"    id="submitBtn" value="<@locale code="button.text.import" />"/>
		    		<input class="button btn btn-primary mr-3"  style="width:100px"  type="button"    id="closeBtn" value="<@locale code="common.alert.closeText" />"/>
		    		
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</body>
</html>