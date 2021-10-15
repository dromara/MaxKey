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
  	.ck-content {
   		min-height: 300px;
	}
</style>
<script type="text/javascript">

</script>
</head>
<body>
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/socialsprovider/update"  class="needs-validation" novalidate>
	 <table  border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
		<tbody>
		<tr style="display:none">
			<th><@locale code="common.text.id" />：</th>
			<td nowrap>
				<input id="id" type="text" readonly name="id"  class="form-control"   value="${model.id}"/>
			</td>
		</tr>
		<tr>
            <th><@locale code="socials.provider.icon" />：</th>
            <td nowrap>
                <input type="text" id="title" name="icon" class="form-control" title="" value="${model.icon!}"  required="" style="width:80%;float:left;"/><img height='30' border='0px' src='<@base/>/static/${model.icon!}'/>
            </td>
        </tr>
		<tr>
			<th><@locale code="socials.provider.provider" />：</th>
			<td nowrap>
				<input type="text" id="provider" name="provider" class="form-control" title="" value="${model.provider!}"  required="" />
			</td>
		</tr>
		<tr>
				<th><@locale code="socials.provider.providerName" />：</th>
				<td nowrap>
					<input type="text" id="providerName" name="providerName" class="form-control" title="" value="${model.providerName!}"  required="" />
				</td>
		</tr>
		<tr>
                <th><@locale code="socials.provider.clientId" />：</th>
                <td nowrap>
                    <input type="text" id="clientId" name="clientId" class="form-control" title="" value="${model.clientId!}"  required="" />
                </td>
        </tr>
		<tr>
                <th><@locale code="socials.provider.clientSecret" />：</th>
                <td nowrap>
                    <input type="text" id="clientSecret" name="clientSecret" class="form-control" title="" value="${model.clientSecret!}"  required="" />
                </td>
        </tr>
        <tr>
                <th><@locale code="socials.provider.agentId" />：</th>
                <td nowrap>
                    <input type="text" id="agentId" name="agentId" class="form-control" title="" value="${model.agentId!}"  required="" />
                </td>
        </tr>
        <tr>
                <th><@locale code="common.text.sortindex" />：</th>
                <td nowrap>
                    <input type="text" id="sortIndex" name="sortIndex" class="form-control" title="" value="${model.sortIndex!}"  required="" />
                </td>
        </tr>
        
        <tr>
                <th><@locale code="socials.provider.hidden" />：</th>
                <td nowrap>
                    <input type="text" id="hidden" name="hidden" class="form-control" title="" value="${model.hidden!}"  required="" />
                </td>
        </tr>
        <tr>
                <th><@locale code="common.text.status" />：</th>
                <td nowrap>
                    <input type="text" id="status" name="status" class="form-control" title="" value="${model.status!}"  required="" />
                </td>
        </tr>
		<tr>
			<td nowrap colspan="2"  class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
	    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />">
  				<input class="button btn btn-secondary mr-3"  id="closeBtn"   type="button" value="<@locale code="button.text.cancel" />">	 
			</td>
		</tr>
		</tbody>
	  </table>
</form>
</body>
</html>