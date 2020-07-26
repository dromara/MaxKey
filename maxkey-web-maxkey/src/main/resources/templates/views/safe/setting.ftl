<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div class="container">
<div class="row">
<div class="col-md-1"></div>
<div class="col-md-10">
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/safe/setting"  class="needs-validation" novalidate> 
	  <table  class="table table-bordered"  style="width:100%;">
			<tbody>
			<tr>
				<th  colspan="2"><@locale code="userinfo.authnType" /></th>
			</tr>
			<tr>
				<th><@locale code="userinfo.displayName" /> :</th>
				<td >
					<input  class="form-control"  readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> :</th>
				<td>
					<input  class="form-control"  readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.authnType" />:</th>
				<td nowrap>
					<select  class="form-control"  name="authnType" id="authnType">
						<option value="1"  <#if 0==model.authnType >selected</#if>  ><@locale code="button.text.select" /></option>
						<option value="1"  <#if 1==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.1" /></option>
						<!-- 
						<option value="2"  <#if 2==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.2" /></option>
						 -->
						<option value="3"  <#if 3==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.3" /></option>
						<option value="4"  <#if 4==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.4" /></option>
						<option value="5"  <#if 5==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.5" /></option>
						<!-- 
						<option value="6"  <#if 6==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.6" /></option>
						<option value="7"  <#if 7==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.7" /></option>
						
						<option value="8"  <#if 8==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.8" /></option>
						<option value="9"  <#if 9==model.authnType >selected</#if>  ><@locale code="userinfo.authnType.authnType.9" /></option>
						<option value="10" <#if 10==model.authnType>selected</#if>  ><@locale code="userinfo.authnType.authnType.10" /></option>
						 -->
					</select>
				</td>
			</tr>	
			<tr>
				<th><@locale code="userinfo.mobile" /> :</th>
				<td>
					<input   class="form-control" type="text" id="mobile" name="mobile" required title="" value="${model.mobile}"  required="" />
					<label for="mobile"></label>
				</td>
			</tr>
			<tr style="display:none;">
				<th>Verify Code :</th>
				<td>
					<input  class="form-control"  type="text" id="mobileVerify" name="mobileVerify" required title="" value="1"  style="width:200px" /><input class="button" style="width:100px"  type="button"    id="getMobileVerifyBtn" value="get Verify"/>
					<label for="verify"></label>
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.email" /> :</th>
				<td>
					<input  class="form-control"  type="text" id="email" name="email" class="required" title="" value="${model.email}"  required="" />
					<label for="eamil"></label>
				</td>
			</tr>
			<tr style="display:none;">
				<th>Verify Code :</th>
				<td>
					<input   class="form-control" type="text" id="emailVerify" name="emailVerify" class="required" title="" value="Verify"  style="width:200px" /><input class="button" style="width:100px"  type="button"    id="getEmailVerifyBtn" value="get Verify"/>
					<label for="verify"></label>
				</td>
			</tr>	
			<tr>
                <th><@locale code="userinfo.theme" />:</th>
                <td nowrap>
                    <select  class="form-control"  name="theme" id="theme">
                        <option value="default"  <#if "default"==model.theme >selected</#if>  ><@locale code="userinfo.theme.default" /></option>
                        <option value="minty"    <#if "minty"  ==model.theme >selected</#if>  ><@locale code="userinfo.theme.minty" /></option>
                        <option value="pulse"    <#if "pulse"  ==model.theme >selected</#if>  ><@locale code="userinfo.theme.pulse" /></option>
                    </select>
                </td>
            </tr>   
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-primary" style="width:100px"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</div>
<div class="col-md-1"></div>
</div >
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>