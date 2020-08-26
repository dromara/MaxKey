<!--table-->
<table width="960"   class="table table-bordered" >
   <tbody>
      <tr>
         <td colspan=4><@locale code="apps.basic.info"/></td>
      </tr>
      <tr>
         <th style="width:15%;"><@locale code="apps.id"/>：</th>
         <td style="width:35%;">
         	<div style="width:100%;font-weight: bold;">${model.id!}</div>
            <input type="hidden" id="id" name="id"  title="" value="${model.id!}"/>
            <input type="hidden" id="status" name="status"  title="" value="${model.status!}"/>
         </td>
         <th style="width:15%;"><@locale code="apps.secret"/>：</th>
         <td style="width:35%;">
         	<input type="text" id="secret" readonly style="width:80%;float: left;font-weight: bold;" class="form-control" name="secret"  title="" value="${model.secret!}"/>
            <input id="generateSecret" type="button"  class="button btn btn-warning mr-2" value="<@locale code="button.text.generate"/>"/>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.name"/>：</th>
         <td  colspan="3">
            <input type="text" class="form-control" id="name" name="name"  title="" value="${model.name!}"  required="" />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.loginUrl"/>：</th>
         <td colspan="3">
            <input type="text" class="form-control" id="loginUrl" name="loginUrl"  title="" value="${model.loginUrl!}"  required="" />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.protocol"/>：</th>
         <td>
            <input type="text" class="form-control" id="protocol" name="protocol"  title="" value="${model.protocol!}"/>
         </td>
         <th><@locale code="apps.category"/>：</th>
         <td>
            <input type="text"class="form-control"  id="category" name="category"  title="" value="${model.category!}"/>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.icon"/>：</th>
         <td><img id="iconFileImg" width='30' height='30' src='<@base/>/image/${model.id!}'/>
         </td>
         <th><@locale code="common.text.sortindex"/></th>
         <td>
         	<input  type="text" class="form-control" id="sortIndex" name="sortIndex"  title="" value="${model.sortIndex!}"  required="" />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.vendor"/>：</th>
         <td>
            <input  type="text" class="form-control" id="vendor" name="vendor"  title="" value="${model.vendor!}"/>
         </td>
         <th><@locale code="apps.vendor.url"/>：</th>
         <td>
            <input type="text" class="form-control" id="vendorUrl" name="vendorUrl"  title="" value="${model.vendorUrl!}"/>
         </td>
      </tr>
      <tr>
      	<th><@locale code="apps.visible"/></th>
         <td>
         	<select  id="visible" name="visible" class="form-control">
				<option value="0"  <#if 0==model.visible!>selected</#if> ><@locale code="apps.visible.hidden"/></option>
				<option value="1"  <#if 1==model.visible!>selected</#if> ><@locale code="apps.visible.all"/></option>
				<option value="2"  <#if 2==model.visible!>selected</#if> ><@locale code="apps.visible.internet"/></option>
				<option value="3"  <#if 3==model.visible!>selected</#if> ><@locale code="apps.visible.intranet"/></option>
			</select>
         </td>
         <th><@locale code="apps.extendAttr"/></th>
         <td>
         	<input class="button btn btn-success mr-3 window" style="float: left;" id="addExtendAttrBtn" type="button" 
         			value="<@locale code="button.text.select"/>" 
		 		    wurl="<@base/>/apps/forwardAppsExtendAttr/${model.id!}"
		 		    wwidth="900"
		 		    wheight="650"
	 		    	target="window">
		 </td>
	 </tr>
	 <tr>
		<th><@locale code="apps.isAdapter" />：</th>
		<td>
			<select  id="isAdapter" name="isAdapter"  class="form-control">
				<option value="0"  <#if 0==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.no"/></option>
				<option value="1"  <#if 1==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.yes"/></option>
			</select>
		</td>
		<td >
		</td>
	</tr>
	<tr>
		<th><@locale code="apps.adapter" />：</th>
		<td colspan =3>
			<input type="text" class="form-control"   id="adapter" name="adapter"  title="" value="${model.adapter!}"/>
		</td>
	</tr>
	 <tr>
         <th><@locale code="common.text.description"/>：</th>
         <td colspan =3>
            <input  type="text"  class="form-control" id="description" name="description"  title="" value="${model.description!}"/>
         </td>
      </tr>
   </tbody>
</table>
