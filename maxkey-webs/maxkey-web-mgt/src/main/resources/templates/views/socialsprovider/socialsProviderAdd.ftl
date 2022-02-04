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

</script>
</head>
<body>
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/socialsprovider/add"  class="needs-validation" novalidate>
	<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
		<tbody>
			<tr style="display:none">
				<th><@locale code="common.text.id" /></th>
				<td nowrap>
					<input type="text" id="id" name="id" class="form-control" title="" value=""  />
				</td>
			</tr>
			<tr>
                <th><@locale code="socials.provider.icon" /></th>
                <td nowrap>
                    <input type="text" id="title" name="icon" class="form-control" title="" value=""  required="" />
                </td>
            </tr>
            <tr>
                <th><@locale code="socials.provider.provider" /></th>
                <td nowrap>
                    <input type="text" id="provider" name="provider" class="form-control" title="" value=""  required="" />
                </td>
            </tr>
            <tr>
                    <th><@locale code="socials.provider.providerName" /></th>
                    <td nowrap>
                        <input type="text" id="providerName" name="providerName" class="form-control" title="" value=""  required="" />
                    </td>
            </tr>
            <tr>
                    <th><@locale code="socials.provider.clientId" /></th>
                    <td nowrap>
                        <input type="text" id="clientId" name="clientId" class="form-control" title="" value=""  required="" />
                    </td>
            </tr>
            <tr>
                    <th><@locale code="socials.provider.clientSecret" /></th>
                    <td nowrap>
                        <input type="text" id="clientSecret" name="clientSecret" class="form-control" title="" value=""  required="" />
                    </td>
            </tr>
            <tr>
                    <th><@locale code="socials.provider.agentId" /></th>
                    <td nowrap>
                        <input type="text" id="agentId" name="agentId" class="form-control" title="" value=""  required="" />
                    </td>
            </tr>
            <tr>
                    <th><@locale code="socials.provider.scanCode" /></th>
                    <td nowrap>
                        <select  id="hidden" name="scanCode" class="form-control  form-select" >
                            <option value="none" selected><@locale code="socials.provider.scanCode.none"/></option>
                            <option value="https" ><@locale code="socials.provider.scanCode.https"/></option>
                            <option value="http" ><@locale code="socials.provider.scanCode.http"/></option>
                        </select>
                    </td>
            </tr>
            <tr>
                    <th><@locale code="common.text.sortindex" /></th>
                    <td nowrap>
                        <input type="text" id="sortIndex" name="sortIndex" class="form-control" title="" value=""  required="" />
                    </td>
            </tr>
            
            <tr>
                    <th><@locale code="socials.provider.hidden" /></th>
                    <td nowrap>
                        <select  id="hidden" name="hidden" class="form-control  form-select" >
                            <option value="true" ><@locale code="common.text.yes"/></option>
                            <option value="false" selected><@locale code="common.text.no"/></option>
                        </select>
                    </td>
            </tr>
            <tr>
                    <th><@locale code="common.text.status" /></th>
                    <td nowrap>
                        <select  id="status" name="status" class="form-control  form-select" >
                            <option value="1" selected><@locale code="common.text.status.activate"/></option>
                            <option value="2" ><@locale code="common.text.status.inactive"/></option>
                        </select>
                    </td>
            </tr>
			<tr>
				<td nowrap colspan="2" class="center">
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