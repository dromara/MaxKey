
<!--table-->
<table width="960"   class="table table-bordered" >
   <tbody>
      <tr>
         <td colspan=4><h5><@locale code="apps.basic.info"/></h5></td>
      </tr>
      <tr>
         <th style="width:15%;"><@locale code="apps.id"/></th>
         <td style="width:35%;">
            <input type="text" readonly class="form-control" id="id" name="id"  title="" value="${model.id!}"/>
         </td>
         <th style="width:15%;"><@locale code="apps.secret"/></th>
         <td style="width:35%;">
            <input type="text" readonly class="form-control"  id="secret" name="secret"  title="" value="${model.secret!}"/>
         </td>
      </tr>
       <tr>
         <th><@locale code="apps.name"/></th>
         <td>
            <input type="text"class="form-control"  id="name" name="name"  size="100"  title="" value=""  required=""   />
         </td>
         <th><@locale code="apps.icon"/></th>
         <td>
            <input  type="file" id="iconFile" class="form-control"  name="iconFile"  title="" value=""/>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.loginUrl"/></th>
         <td colspan="3">
            <div class="input-group" >
                <input type="text" class="form-control"  id="loginUrl" name="loginUrl" size="100"  title="" value=""  required=""   />
                <input  class="button btn btn-primary  btn-collapse"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" collapseId="#basic_info" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
            </div> 
         </td>
      </tr>
      </tbody>
      <tbody id="basic_info" style="display:none" >
      <tr>
         <th><@locale code="apps.logoutUrl"/></th>
         <td>
         	<input type="text" class="form-control"  id="logoutUrl" name="logoutUrl" size="100"  title="" value=""   />
         </td>
         <th><@locale code="apps.logoutType"/></th>
         <td>
         	<select  id="logoutType" name="logoutType" class="form-control  form-select" >
				<option value="0" selected ><@locale code="apps.logoutType.none"/></option>
				<option value="1" ><@locale code="apps.logoutType.back_channel"/></option>
				<option value="2" ><@locale code="apps.logoutType.front_channel"/></option>
			</select>
         </td>
      </tr>
      <tr>
         <th ><@locale code="apps.protocol"/></th>
         <td  id="app_protocol_control">
         	<span  id="protocol_text" >${model.protocol!}</span>
            <input type="hidden"class="form-control"  id="protocol" name="protocol"  title="" value="${model.protocol!}"/>
         </td>
         <th ><@locale code="apps.category"/></th>
         <td>
            <input type="text"class="form-control"  id="category" name="category"  title="" value="${model.category!}"/>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.visible"/></th>
         <td>
            <select  id="visible" name="visible" class="form-control  form-select" >
                <option value="0" ><@locale code="apps.visible.hidden"/></option>
                <option value="1"  selected><@locale code="apps.visible.all"/></option>
                <option value="2" ><@locale code="apps.visible.internet"/></option>
                <option value="3" ><@locale code="apps.visible.intranet"/></option>
            </select>
         </td>
         <th><@locale code="common.text.sortindex"/></th>
         <td>
         	<input  type="text" id="sortIndex" class="form-control"  name="sortIndex"  title="" value="1"  required=""    />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.vendor"/></th>
         <td>
            <input  type="text" class="form-control"  id="vendor" name="vendor"  title="" value=""/>
         </td>
         <th><@locale code="apps.vendor.url"/></th>
         <td>
            <input type="text" class="form-control"  id="vendorUrl" name="vendorUrl"  title="" value=""/>
         </td>
      </tr>
      <tr>
		<th><@locale code="apps.isAdapter" /></th>
		<td>
			<select  id="isAdapter" name="isAdapter"  class="form-control  form-select"  >
				<option value="0"  selected><@locale code="apps.isAdapter.no" /></option>
				<option value="1"><@locale code="apps.isAdapter.yes" /></option>
			</select>
		</td>
		<th><@locale code="apps.adapter" /></th>
		<td >
		      <div class="input-group">
    			<input type="hidden" class="form-control"   id="adapterId" name="adapterId"      value=""/>
    			<input type="text"   class="form-control"   id="adapterName" name="adapterName"  value="" />
    			<input type="hidden" class="form-control"   id="adapter" name="adapter"          value=""/>
    			
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
            <input  type="text"  class="form-control" id="description" name="description"  title="" value=""/>
         </td>
      </tr>
   </tbody>
</table>
