<!--table-->
<table width="960"   class="table table-bordered" >
   <tbody>
      <tr>
         <td colspan=4><h5><@locale code="apps.basic.info"/></h5></td>
      </tr>
      <tr>
         <th style="width:15%;"><@locale code="apps.id"/></th>
         <td style="width:35%;">
            <div class="input-group">
                <input class="form-control" type="text"  readonly  id="id" name="id"  title="" value="${model.id!}"/>
                <input class="button btn btn-primary mr-3 window" style="float: left;" id="addExtendAttrBtn" type="button" 
                        value="<@locale code="apps.extendAttr"/>" 
                        wurl="<@base/>/apps/forwardAppsExtendAttr/${model.id!}"
                        wwidth="900"
                        wheight="650"
                        target="window">
            </div>
            <input type="hidden" id="status" name="status"  title="" value="${model.status!}"/>
         </td>
         <th style="width:15%;"><@locale code="apps.secret"/></th>
         <td style="width:35%;">
            <div class="input-group">
         	      <input type="text" id="secret" readonly style="font-weight: bold;" class="form-control" name="secret"  title="" value="${model.secret!}"/>
                  <input id="generateSecret" type="button"  class="button btn btn-danger mr-2" value="<@locale code="button.text.generate"/>"/>
            </div>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.name"/></th>
         <td>
            <input type="text" class="form-control" id="name" name="name"  title="" value="${model.name!}"  required="" /> 
         </td>
        <th><@locale code="apps.icon"/></th>
        <td>
            <img id="iconFileImg" height='30' src='${model.iconBase64!}'/>
        </td>
      </tr>
      <tr>
         <th><@locale code="apps.loginUrl"/></th>
         <td colspan="3">
            <div class="input-group" >
                <input type="text" class="form-control" id="loginUrl" name="loginUrl"  title="" value="${model.loginUrl!}"  required="" />
                <input  class="button btn btn-primary btn-collapse"  type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" collapseId="#basic_info" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
            </div>
         </td>
      </tr>
      </tbody>
      <tbody id="basic_info" style="display:none" >
      <tr>
         <th><@locale code="apps.logoutUrl"/></th>
         <td>
         <input type="text" class="form-control" id="logoutUrl" name="logoutUrl"  title="" value="${model.logoutUrl!}" />
         </td>
         <th><@locale code="apps.logoutType"/></th>
         <td>
         	<select  id="logoutType" name="logoutType" class="form-control  form-select" >
				<option value="0" <#if 0==model.logoutType!>selected</#if> ><@locale code="apps.logoutType.none"/></option>
				<option value="1" <#if 1==model.logoutType!>selected</#if> ><@locale code="apps.logoutType.back_channel"/></option>
				<option value="2" <#if 2==model.logoutType!>selected</#if> ><@locale code="apps.logoutType.front_channel"/></option>
			</select>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.protocol"/></th>
         <td>
            <input type="text" class="form-control" id="protocol" name="protocol"  title="" value="${model.protocol!}"/>
         </td>
         <th><@locale code="apps.category"/></th>
         <td>
            <select  id="category" name="category" class="form-control  form-select" >
                <option value="none" <#if 'none'==model.category!>selected</#if> ><@locale code="apps.category.none"/></option>
                <option value="1011" <#if '1011'==model.category!>selected</#if> ><@locale code="apps.category.1011"/></option>
                <option value="1012" <#if '1012'==model.category!>selected</#if> ><@locale code="apps.category.1012"/></option>
                <option value="1013" <#if '1013'==model.category!>selected</#if> ><@locale code="apps.category.1013"/></option>
                <option value="1014" <#if '1014'==model.category!>selected</#if> ><@locale code="apps.category.1014"/></option>
                <option value="1015" <#if '1015'==model.category!>selected</#if> ><@locale code="apps.category.1015"/></option>
                <option value="1016" <#if '1016'==model.category!>selected</#if> ><@locale code="apps.category.1016"/></option>
                <option value="1017" <#if '1017'==model.category!>selected</#if> ><@locale code="apps.category.1017"/></option>
                <option value="1012" <#if '1012'==model.category!>selected</#if> ><@locale code="apps.category.1012"/></option>
                <option value="1111" <#if '1111'==model.category!>selected</#if> ><@locale code="apps.category.1111"/></option>
                <option value="1112" <#if '1112'==model.category!>selected</#if> ><@locale code="apps.category.1112"/></option>
                <option value="1113" <#if '1113'==model.category!>selected</#if> ><@locale code="apps.category.1113"/></option>
                <option value="1114" <#if '1114'==model.category!>selected</#if> ><@locale code="apps.category.1114"/></option>
                <option value="1211" <#if '1211'==model.category!>selected</#if> ><@locale code="apps.category.1211"/></option>
                <option value="1212" <#if '1212'==model.category!>selected</#if> ><@locale code="apps.category.1212"/></option>
                <option value="1213" <#if '1213'==model.category!>selected</#if> ><@locale code="apps.category.1213"/></option>
                <option value="1214" <#if '1214'==model.category!>selected</#if> ><@locale code="apps.category.1214"/></option>
                <option value="1215" <#if '1215'==model.category!>selected</#if> ><@locale code="apps.category.1215"/></option>
                <option value="1311" <#if '1311'==model.category!>selected</#if> ><@locale code="apps.category.1311"/></option>
                <option value="1411" <#if '1411'==model.category!>selected</#if> ><@locale code="apps.category.1411"/></option>
                <option value="1511" <#if '1511'==model.category!>selected</#if> ><@locale code="apps.category.1511"/></option>
                <option value="1512" <#if '1512'==model.category!>selected</#if> ><@locale code="apps.category.1512"/></option>
                <option value="1611" <#if '1611'==model.category!>selected</#if> ><@locale code="apps.category.1611"/></option>
                <option value="1711" <#if '1711'==model.category!>selected</#if> ><@locale code="apps.category.1711"/></option>
                <option value="1712" <#if '1712'==model.category!>selected</#if> ><@locale code="apps.category.1712"/></option>
                <option value="1811" <#if '1811'==model.category!>selected</#if> ><@locale code="apps.category.1811"/></option>
                <option value="1812" <#if '1812'==model.category!>selected</#if> ><@locale code="apps.category.1812"/></option>
                <option value="1911" <#if '1911'==model.category!>selected</#if> ><@locale code="apps.category.1911"/></option>
                <option value="1912" <#if '1912'==model.category!>selected</#if> ><@locale code="apps.category.1912"/></option>
            </select>
         </td>
      </tr>
      <tr>
        <th><@locale code="apps.visible"/></th>
        <td>
            <select  id="visible" name="visible" class="form-control  form-select">
                <option value="0"  <#if 0==model.visible!>selected</#if> ><@locale code="apps.visible.hidden"/></option>
                <option value="1"  <#if 1==model.visible!>selected</#if> ><@locale code="apps.visible.all"/></option>
                <option value="2"  <#if 2==model.visible!>selected</#if> ><@locale code="apps.visible.internet"/></option>
                <option value="3"  <#if 3==model.visible!>selected</#if> ><@locale code="apps.visible.intranet"/></option>
            </select>
         </td>
         <th><@locale code="common.text.sortindex"/></th>
         <td>
         	<input  type="text" class="form-control" id="sortIndex" name="sortIndex"  title="" value="${model.sortIndex!}"  required="" />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.vendor"/></th>
         <td>
            <input  type="text" class="form-control" id="vendor" name="vendor"  title="" value="${model.vendor!}"/>
         </td>
         <th><@locale code="apps.vendor.url"/></th>
         <td>
            <input type="text" class="form-control" id="vendorUrl" name="vendorUrl"  title="" value="${model.vendorUrl!}"/>
         </td>
      </tr>
	 <tr>
		<th><@locale code="apps.isAdapter" /></th>
		<td>
			<select  id="isAdapter" name="isAdapter"  class="form-control  form-select">
				<option value="0"  <#if 0==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.no"/></option>
				<option value="1"  <#if 1==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.yes"/></option>
			</select>
		</td>
		<th><@locale code="apps.adapter" /></th>
		<td>
		      <div class="input-group">
			     <input type="hidden" class="form-control"   id="adapterId" name="adapterId"  title="" value="${model.adapterId!}"/>
			     <input type="text" class="form-control"     id="adapterName" name="adapterName"  title="" value="${model.adapterName!}"  />
			     <input type="hidden" class="form-control"   id="adapter" name="adapter"  title="" value="${model.adapter!}"/>
			
    			 <input class="button btn btn-primary mr-3 window"  id="selectAdaptersBtn" type="button" 
             			value="<@locale code="button.text.select"/>" 
    		 		    wurl="<@base/>/apps/adapters/selectAdaptersList?protocol=${model.protocol!}"
    		 		    wwidth="750"
    		 		    wheight="600"
    	 		    	target="window">
    	 	 </div>
		</td>
	</tr>
	 <tr>
         <th><@locale code="common.text.description"/></th>
         <td colspan =3>
            <input  type="text"  class="form-control" id="description" name="description"  title="" value="${model.description!}"/>
         </td>
      </tr>
   </tbody>
</table>
