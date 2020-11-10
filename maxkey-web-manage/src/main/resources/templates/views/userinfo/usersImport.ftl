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
				<th><@locale code="userinfo.displayName" /> :</th>
				<td>
					<input class="form-control"  type="file" id="excelFile" name="excelFile"  />
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