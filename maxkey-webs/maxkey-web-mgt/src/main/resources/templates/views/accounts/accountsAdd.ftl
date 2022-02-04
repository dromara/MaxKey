<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
    <script type="text/javascript">
    $(function(){
        $("#relatedUsername").focus(function(){
          if($("#relatedUsername").val()==""){
            $("#relatedUsername").val($("#username").val());
          }
        });
        
        $("#generateSecret").on("click",function(){
            $.post("<@base/>/userinfo/randomPassword/", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
                $("#relatedPassword").val(data+"");
            }); 
        });
        
        $("#generateAccountBtn").on("click",function(){
            $.post("<@base/>/accounts/generate/", {
                        _method:"post",
                        userId : $("#userId").val(),
                        appId : $("#appId").val(),
                        strategyId : $("#strategyId").val(),
                        currTime:(new Date()).getTime()
                        }, function(data) {
                $("#relatedUsername").val(data+"");
            }); 
        });
        
        $("#view").on("click",function(){
            if($("#relatedPassword").attr("type")=="text"){
                $("#relatedPassword").attr("type","password");
            }else{
                $("#relatedPassword").attr("type","text");
            }
        });
    });
    </script>
</head>
<body>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/accounts/add"  class="needs-validation" novalidate>
	<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
		<tbody>
			<tr style="display:none">
				<th><@locale code="userinfo.id" /></th>
				<td nowrap>
					<input  required="" type="text" id="id" name="id" readonly  class="form-control" title="" value="${model.id!}"/>
	
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /></th>
				<td nowrap  style="width:60%">
				    <div class="input-group mb-3" style="vertical-align: middle;">
					   <input  required="" readonly type="text" id="username" name="username"  class="form-control username" title="" value="${model.username!}"   required="" />
					   <input class="button btn btn-primary mr-3 window"  type="button"    id="selectUserinfoBtn" value="<@locale code="button.text.select" />"
                            wurl="<@base/>/userinfo/select"
                                    wwidth="800"
                                    wheight="620"
                                    target="window"/>
                    </div>
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.displayName" /></th>
				<td nowrap>
				    
					<input  required="" readonly  type="text" id="displayName" name="displayName"  class="form-control displayName"  title="" value="${model.displayName!}"   required="" />
			
				</td>
			</tr>
			<tr>
				<th><@locale code="apps.name" /></th>
				<td nowrap>
				    <div class="input-group mb-3" style="vertical-align: middle;">
					   <input  required="" readonly  type="text" id="appName" name="appName"  class="form-control appName" title="" value="${model.appName!}"   required="" />
					   <input class="button btn btn-primary mr-3 window"  type="button"    id="selectAppsubmitBtn" value="<@locale code="button.text.select" />"
                            wurl="<@base/>/accountsstrategy/select"
                                    wwidth="800"
                                    wheight="620"
                                    target="window"/>
					</div>
				</td>
			</tr>
			<tr>
				<th><@locale code="account.relatedUsername" /></th>
				<td nowrap>
				    <div class="input-group mb-3" style="vertical-align: middle;">
					   <input type="text" id="relatedUsername" name="relatedUsername"  class="form-control" title="" value="${model.relatedUsername!}"    required="" />
                       <input class="button btn btn-primary mr-3"  type="button"    id="generateAccountBtn" value="<@locale code="button.text.generate" />"/>
                    </div>
				</td>
			</tr>
			<tr>
				<th><@locale code="account.relatedPassword" /></th>
				<td nowrap>
				    <div class="input-group mb-3" style="vertical-align: middle;">
					   <input type="password" id="relatedPassword" name="relatedPassword"  class="form-control" title="" value="${model.relatedPassword!}"  required="" />
		               <input id="generateSecret" type="button" class="button btn btn-primary mr-3" style="width:75px"  value="<@locale code="button.text.generate"/>"/>
                       <input id="view" type="button" class="button btn btn-primary mr-3" style="width:75px"  value="<@locale code="button.text.view"/>"/>
                    </div>
		        </td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="status" type="hidden" name="status"  value="1"/>
					<input type="hidden" id="userId" name="userId" class="userId" title="" value="${model.userId!}"/>
					<input type="hidden" id="strategyId" name="strategyId" class="strategyId" title="" value="${model.strategyId!}"/>
					<input type="hidden" id="appId" name="appId" class="appId" title="" value="${model.appId!}"/>
					<input type="hidden" id="createType" name="createType"  title="" value="manual"/>
			   		<input class="button btn btn-primary mr-3"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					<input class="button btn btn-secondary mr-3"  type="button"    id="closeBtn" value="<@locale code="button.text.cancel" /> "/>	
					
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
</html>