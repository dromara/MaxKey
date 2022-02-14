<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<#setting number_format="#">
<script type="text/javascript">
    <!--
    $(function(){   
       $("#tokenbased_algorithm").change(function(){
        $.post("<@base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
            $("#algorithmKey").val(data+"");
        });
    }); 
    });
//-->
</script>
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/tokenbased/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="table table-bordered"  >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="table table-bordered"  >
						<tbody>
						<tr>
							<td colspan=4><h5><@locale code="apps.tokenbased.info" /></h5></td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.tokenbased.redirectUri" /></th>
								<td style="width:35%;" colspan=3>
									<input type="text" class="form-control" id="redirectUri" name="redirectUri"  title="" value=""  required="" />
								</td>
							</tr>
							<tr>
									<th ><@locale code="apps.tokenbased.tokenType" /></th>
									<td >
										<select id="tokenType" name="tokenType"  class="form-control  form-select">
											<option value="POST">安全令牌(TOKEN POST)</option>
											<option value="LTPA">轻量级认证(LTPA COOKIE)</option>
										</select>
									</td>
									<th ><@locale code="apps.tokenbased.cookieName" /></th>
									<td >
										<input type="text" class="form-control" id="cookieName" name="cookieName"  title="" value=""/>
									</td>
								</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.tokenbased.algorithm" /></th>
								<td style="width:35%;">
									<select id="tokenbased_algorithm" name="algorithm"   class="form-control  form-select">
										<option value="DES">DES</option>
										<option value="DESede">DESede</option>
										<option value="Blowfish">Blowfish</option>
										<option value="AES"  selected>AES</option>
									</select>
								</td>
								<th width="140px"><@locale code="apps.tokenbased.algorithmKey" /></th>
								<td width="340px">
									<input type="text" class="form-control" id="algorithmKey" name="algorithmKey"  title="" value="${model.algorithmKey!}"/>
								
								</td>
							</tr>
							<tr>
								<th><@locale code="apps.tokenbased.token.content" /></th>
								<td>
									<#include  "../userPropertys.ftl"/>
								</td>
								<th><@locale code="apps.tokenbased.expires" /></th>
								<td>
								    <div class="input-group">
									    <input type="text" class="form-control" id="expires" name="expires"  title="" value="180"  required="" />
								        <span class="input-group-text">Seconds</span>
								    </div>
								</td>
							</tr>
							<tr>
								<td colspan =4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
						    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
									<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.close" />"/>		
								</td>
							</tr>
							</tbody>
						  </table>
					</td>
				</tr>
				</tbody>
				</table>
			   

			  
</form>
        </div>
    </div>
</div>
</body>
</html>