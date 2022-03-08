
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
            <select  id="category" name="category" class="form-control  form-select" >
                <option value="none" selected ><@locale code="apps.category.none"/></option>
                <option value="1011" ><@locale code="apps.category.1011"/></option>
                <option value="1012" ><@locale code="apps.category.1012"/></option>
                <option value="1013" ><@locale code="apps.category.1013"/></option>
                <option value="1014" ><@locale code="apps.category.1014"/></option>
                <option value="1015" ><@locale code="apps.category.1015"/></option>
                <option value="1016" ><@locale code="apps.category.1016"/></option>
                <option value="1017" ><@locale code="apps.category.1017"/></option>
                <option value="1012" ><@locale code="apps.category.1012"/></option>
                <option value="1111" ><@locale code="apps.category.1111"/></option>
                <option value="1112" ><@locale code="apps.category.1112"/></option>
                <option value="1113" ><@locale code="apps.category.1113"/></option>
                <option value="1114" ><@locale code="apps.category.1114"/></option>
                <option value="1211" ><@locale code="apps.category.1211"/></option>
                <option value="1212" ><@locale code="apps.category.1212"/></option>
                <option value="1213" ><@locale code="apps.category.1213"/></option>
                <option value="1214" ><@locale code="apps.category.1214"/></option>
                <option value="1215" ><@locale code="apps.category.1215"/></option>
                <option value="1311" ><@locale code="apps.category.1311"/></option>
                <option value="1411" ><@locale code="apps.category.1411"/></option>
                <option value="1511" ><@locale code="apps.category.1511"/></option>
                <option value="1512" ><@locale code="apps.category.1512"/></option>
                <option value="1611" ><@locale code="apps.category.1611"/></option>
                <option value="1711" ><@locale code="apps.category.1711"/></option>
                <option value="1712" ><@locale code="apps.category.1712"/></option>
                <option value="1811" ><@locale code="apps.category.1811"/></option>
                <option value="1812" ><@locale code="apps.category.1812"/></option>
                <option value="1911" ><@locale code="apps.category.1911"/></option>
                <option value="1912" ><@locale code="apps.category.1912"/></option>
            </select>
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
