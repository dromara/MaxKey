<!DOCTYPE html>
<html >
<head>
	<#include  "authorize_common.ftl">
	<script type="text/javascript">
	    window.top.location.href = "${model.authorizeApproveUri}?oauth_approval=${model.oauth_approval}&clientId=${model.client.clientId!}";
	    //TODO:后续配置成参数
	    //self.location.href = "${model.authorizeApproveUri}?oauth_approval=${model.oauth_approval}&clientId=${model.client.clientId!}";
	</script>
</head>

<body  style="display:none;">
	<div id="top">

	</div>
	<div class="container">	
		<#if 'oauth 2.0'==model.oauth_version>
			 <!-- oauth 2.0 -->
			 <table  class="table table-bordered">
                        <tr>
                            <td colspan="2">
                                <div style="text-align: center;">                            
                                    <!--<p>You hereby authorize "${model.client.clientId!}" to access your protected resources.</p>-->
                                    <form id="confirmationForm" name="confirmationForm" action="<@base/>/authz/oauth/v20/authorize" method="post">
                                        <input id="user_oauth_approval" name="user_oauth_approval" value="true" type="hidden"/>
                                        <input class="button btn btn-primary mr-3" name="authorize"
                                            value='' type="submit"/>
                                    </form>
                                </div>
                            </td>
                        </tr>
              </table>   
	    </#if>
    </div>
</body>
</html>
