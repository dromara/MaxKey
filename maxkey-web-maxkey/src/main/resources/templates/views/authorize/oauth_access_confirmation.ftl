<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">
		$(function () {
			if("${model.approval_prompt!}"=="auto"){
				$("#confirmationForm").submit();
			}
		});
	</script>
</head>

<body <#if model.approval_prompt?? && 'auto'==model.approval_prompt> style="display:none;"</#if>>
	<div id="top">
		<#include  "../layout/nologintop.ftl"/>
	</div>
	<div class="container">	
		<#if 'oauth 2.0'==model.oauth_version>
			 <!-- oauth 2.0 -->
			 <table  class="table table-bordered">
                        <tr>
                            <th colspan='2'><@locale code="apps.oauth.approval.title"/></th>
                        </tr>
                         <tr>
                            <td><img src="<@base/>/image/${model.app.id}" title="${model.app.name}" width="65px" height="65px"  style="border:0;"/></td>
                            <td>
                                <b>${model.app.name!}</b><br/>
                                <@locale code="apps.oauth.approval.info"/>
                            </td>
                        </tr>
                         <tr>
                            <td></td>
                            <td>
                                    <span class="checkboxspan icon_checkbox_selected"></span>
                                    <@locale code="apps.oauth.approval.context"/>
                            </td>
                        </tr>
              </table>   
		      
		      <!--<p>You hereby authorize "${model.client.clientId!}" to access your protected resources.</p>-->
		      <form id="confirmationForm" name="confirmationForm" action="<@base/>/oauth/v20/authorize" method="post">
		        <input id="user_oauth_approval" name="user_oauth_approval" value="true" type="hidden"/>
		        <label><input class="button btn btn-primary mr-3" name="authorize" value='<@locale code="apps.oauth.approval.authorize"/>' type="submit"/></label>
		      </form>
	    </#if>
    </div>
    <div id="footer">
		<#include  "../layout/footer.ftl"/>
	</div>
</body>
</html>
