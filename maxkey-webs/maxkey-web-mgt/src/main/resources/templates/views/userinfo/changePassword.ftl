<!DOCTYPE HTML>
<html xmlns="http//www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>


	<script type="text/javascript">
	<!--
	$(function(){	
		$("#generateSecret").on("click",function(){
			$.post("<@base/>/userinfo/randomPassword/", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
				$("#password").val(data+"");
				$("#confirmPassword").val(data+"");
			}); 
		});
		$("#view").on("click",function(){
			if($("#password").attr("type")=="text"){
				$("#password").attr("type","password");
			}else{
				$("#password").attr("type","text");
			}
		});
		
	});
	//-->
	</script>
</head>
<body>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/userinfo/changePassword"  class="needs-validation" novalidate> 

	  <table   class="table table-bordered" >
			<tbody>
			<tr>
				<th><@locale code="userinfo.displayName" /> </th>
				<td>
					<input readonly type="hidden" id="id" name="id" class="required" title="" value="${model.id}"/>
					<input readonly type="text" id="displayName" name="displayName"  class="form-control"  title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> </th>
				<td>
					<input readonly type="text" id="username" name="username"  class="form-control"  title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.newPassword" /></th>
				<td>
				    
				    <div class="input-group" style="vertical-align middle;">
					   <input type="password" id="password" name="password"  required=""  class="form-control"  title="" value=""/>
					   <i class="passwdeye fa fa-eye-slash fa-2" style="left: 350px; color: gainsboro;" refid="password" ></i>
					   <input id="generateSecret" type="button" class="button btn btn-primary mr-3"    value="<@locale code="button.text.generate"/>"/>
                       
                    </div>
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.confirmPassword" /></th>
				<td nowrap>
					<input type="password" id="confirmPassword" name="confirmPassword"  required=""  class="form-control"  title="" value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-primary mr-3"  style="width100px"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
		    		
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</body>
</html>