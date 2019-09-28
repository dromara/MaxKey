
<!--table-->
<table width="960"   class="datatable" >
   <tbody>
      <tr>
         <td colspan=4><@locale code="apps.basic.info"/></td>
      </tr>
      <tr>
         <th><@locale code="apps.id"/>：</th>
         <td>
         	<span id="id_text"  style="width:100%;font-weight: bold;">${model.id}</span>
            <input type="hidden" id="id" name="id"  title="" value="${model.id}"/>
         </td>
         <th><@locale code="apps.secret"/>：</th>
         <td>
         	<span id="secret_text"  style="width:100%;font-weight: bold;">${model.secret}</span>
            <input type="hidden" id="secret" name="secret"  title="" value="${model.secret}"/>
         </td>
      </tr>
       <tr>
         <th><@locale code="apps.name"/>：</th>
         <td colspan="3">
            <input type="text" id="name" name="name"  size="100"  title="" value=""/>
            <b class="orange">*</b><label for="name"></label>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.loginUrl"/>：</th>
         <td colspan="3">
            <input type="text" id="loginUrl" name="loginUrl" size="100"  title="" value=""/>
            <b class="orange">*</b><label for="loginUrl"></label>
         </td>
      </tr>
      <tr>
         <th style="width:15%;"><@locale code="apps.protocol"/>：</th>
         <td style="width:35%;">
         	<span  id="protocol_text" >${model.protocol}</span>
            <input type="hidden" id="protocol" name="protocol"  title="" value="${model.protocol}"/>
         </td>
         <th style="width:15%;"><@locale code="apps.category"/>：</th>
         <td style="width:35%;">
         	<select name="category"  class="select_t">
				<c:forEach items="${appCategorysList}" var="appCategory">
					<option value="${appCategory.id}">${appCategory.name}</option>
	      		</c:forEach>
			</select>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.icon"/>：</th>
         <td>
            <input  type="file" id="iconFile" name="iconFile"  title="" value=""/>
            <b class="orange">*</b><label for="iconFile"></label>
         </td>
         <th><@locale code="common.text.sortorder"/></th>
         <td>
         	<input  type="text" id="sortOrder" name="sortOrder"  title="" value="0"/>
            <b class="orange">*</b><label for="sortOrder"></label>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.vendor"/>：</th>
         <td>
            <input  type="text" id="vendor" name="vendor"  title="" value=""/>
            <b class="orange">*</b><label for="vendor"></label>
         </td>
         <th><@locale code="apps.vendor.url"/>：</th>
         <td>
            <input type="text" id="vendorUrl" name="vendorUrl"  title="" value=""/>
            <b class="orange">*</b><label for="vendorUrl"></label>
         </td>
      </tr>
      <tr>
      	<th><@locale code="apps.visible"/></th>
         <td>
         	<select  id="visible" name="visible" >
				<option value="0" ><@locale code="apps.visible.hidden"/></option>
				<option value="1"  selected><@locale code="apps.visible.all"/></option>
				<option value="2" ><@locale code="apps.visible.internet"/></option>
				<option value="3" ><@locale code="apps.visible.intranet"/></option>
			</select>
         </td>
         <th><@locale code="common.text.description"/>：</th>
         <td>
            <input  type="text" id="description" name="description"  title="" value=""/>
            <b class="orange">*</b><label for="description"></label>
         </td>
      </tr>
   </tbody>
</table>
