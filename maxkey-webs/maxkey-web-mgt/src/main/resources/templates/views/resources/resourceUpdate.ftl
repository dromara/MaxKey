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
</head>
<body>
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/resources/update"  class="needs-validation" novalidate>
	 <table  border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
		<tbody>
		<tr style="display:none1">
			<th><@locale code="resource.id" />：</th>
			<td nowrap>
				<input id="id" type="text" readonly name="id"  class="form-control"   value="${model.id}"/>
			</td>
		</tr>
		<tr>
			<th><@locale code="resource.name" />：</th>
			<td nowrap>
				<input type="text" id="name" name="name" class="form-control" title="" value="${model.name}"  required="" />
			</td>
		</tr>
		<tr>
                <th><@locale code="resource.parentId" />：</th>
                <td nowrap>
                    <input type="text" id="parentId" name="parentId" class="form-control" title="" value="${model.parentId!}"  required="" />
                </td>
            </tr>
            <tr style="display:none;">
                <th><@locale code="apps.id" />：</th>
                <td nowrap>
                    <input type="text" id="appId" name="appId" class="form-control" title="" value="${model.appId!}"  required="" />
                </td>
            </tr>
            <tr>
                <th><@locale code="resource.parentName" />：</th>
                <td nowrap>
                    <input type="text" id="parentName" name="parentName" class="form-control" title="" value="${model.parentName!}"  required="" />
                </td>
            </tr>
            <tr>
                <th><@locale code="resource.resourceType" />：</th>
                <td nowrap>
                    <select id="resourceType"  name="resourceType"   class="form-control" >
						<option value="MENU"    <#if 'MENU'==model.resourceType>selected</#if> 		><@locale code="resource.resourceType.Menu" /></option>
						<option value="ELEMENT" <#if 'ELEMENT'==model.resourceType>selected</#if>	><@locale code="resource.resourceType.Element" /></option>
						<option value="BUTTON"  <#if 'BUTTON'==model.resourceType>selected</#if>	><@locale code="resource.resourceType.Button" /></option>
						<option value="MODULE"  <#if 'MODULE'==model.resourceType>selected</#if>	><@locale code="resource.resourceType.Module" /></option>
						<option value="FILE"  	<#if 'FILE'==model.resourceType>selected</#if>		><@locale code="resource.resourceType.File" /></option>
						<option value="DATA"  	<#if 'DATA'==model.resourceType>selected</#if>		><@locale code="resource.resourceType.Data" /></option>
						<option value="OTHER"  	<#if 'OTHER'==model.resourceType >selected</#if>	><@locale code="resource.resourceType.Other" /></option>
					</select>
				</td>
            </tr>
            <tr>
                <th><@locale code="resource.resourceUrl" />：</th>
                <td nowrap>
                    <input type="text" id="resourceUrl" name="resourceUrl" class="form-control" title="" value="${model.resourceUrl!}"   />
                </td>
            </tr>
            <tr>
                <th><@locale code="resource.resourceAction" />：</th>
                <td nowrap>
                    <input type="text" id="resourceAction" name="resourceAction" class="form-control" title="" value="${model.resourceAction!}"   />
                </td>
            </tr>
            <tr>
                <th><@locale code="resource.resourceIcon" />：</th>
                <td nowrap>
                    <input type="text" id="resourceIcon" name="resourceIcon" class="form-control" title="" value="${model.resourceIcon!}"   />
                </td>
            </tr>
            <tr>
                <th><@locale code="resource.resourceStyle" />：</th>
                <td nowrap>
                    <input type="text" id="resourceStyle" name="resourceStyle" class="form-control" title="" value="${model.resourceStyle!}"  />
                </td>
            </tr>
            <tr>
            	<th><@locale code="common.text.sortindex"/></th>
         		<td>
         		<input  type="text" id="sortIndex" class="form-control"  name="sortIndex"  title="" value="${model.sortIndex!}"  required=""    />
         		</td>
         	</tr>
            <tr>
                <th><@locale code="common.text.description" />：</th>
                <td nowrap>
                    <input type="text" id="description" name="description" class="form-control" title="" value="${model.description!}"  />
                </td>
            </tr>
		<tr>
			<td nowrap colspan="2"  class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
				<input id="status" type="hidden" name="status"  value="1"/>
	    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />">
  				<input class="button btn btn-secondary mr-3"  id="closeBtn"   type="button" value="<@locale code="button.text.cancel" />">	 
			</td>
		</tr>
		</tbody>
	  </table>
</form>
</body>
</html>