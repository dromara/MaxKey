<!DOCTYPE html>
<html >
<head>
	<#include  "authorize_common.ftl">
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
                        <tr>
                            <td colspan="2">
                                <div style="text-align: center;">                            
                                    <!--<p>You hereby authorize "${model.client.clientId!}" to access your protected resources.</p>-->
                                    <form id="confirmationForm" name="confirmationForm" action="<@base/>/authz/oauth/v20/authorize" method="post">
                                        <input id="user_oauth_approval" name="user_oauth_approval" value="true" type="hidden"/>
                                        <input class="button btn btn-primary mr-3" name="authorize"
                                            value='<@locale code="apps.oauth.approval.authorize"/>' type="submit"/>
                                    </form>
                                </div>
                            </td>
                        </tr>
              </table>   
	    </#if>
    </div>
    <div id="footer">
		<#include  "authorize_footer.ftl">
	</div>
</body>
</html>
