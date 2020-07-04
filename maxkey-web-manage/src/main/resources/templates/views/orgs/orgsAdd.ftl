<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>

<script type="text/javascript">
	
$(function () {
	$("#pId").val($.cookie("select_org_id"));
	$("#pName").val($.cookie("select_org_name"));
});
</script>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>

</head>
<body> 
     <form  id="actionForm" action='<@base/>/orgs/add' method="post" class="needs-validation" novalidate>
     	<table  class="datatable" style="width:600px" >
     		<tr>
     			<td>
					<ul class="switch_tab"  style="width:100%" >
						<li id="switch_common" value="table_switch_common" style="width:49%" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);"> <@locale code="org.tab.basic" /></a></li>
						<li id="switch_extra"  value="table_switch_extra" style="width:49%" class="switch_tab_class"><a href="javascript:void(0);"> <@locale code="org.tab.extra" /></a></li>
					</ul>
				</td>
     		</tr>
     		<tr><td>
     	<table id="table_switch_common"   class="table table-bordered">
           <tr style="display:none">
              <th ><input type="text" id="status" type="hidden" name="status"  value="1"/>
              <input type="text" id="_method" type="hidden" name="_method"  value="put"/></th>
              <td></td>
           </tr>
           <tr >
              <td > <@locale code="org.pid" />：</td>
              <td><input type="text"  id="pId" name="parentId"  class="form-control"/></td>
           </tr>
           <tr>
              <th  width="200px"> <@locale code="org.pname" />：</td>
              <td><input type="text"  id="pName" name="parentName"    class="form-control"/></td>
           </tr>
           <tr >
              <td > <@locale code="org.id" />：</td>
              <td><input type="text" id="id" name="id"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.name" />：</td>
              <td><input type="text"  id="name" name="name" required=""    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.fullname" />：</td>
              <td><input type="text"  id="fullName" name="fullName"  required=""   class="form-control"/></td>
           </tr>
            <tr >
              <td > <@locale code="org.xpath" /> ：
              </th>
              <td><input type="text"  id="xPath" name="codePath"    class="form-control"/></td>
           </tr>
           
            <tr >
              <td > <@locale code="org.xnamepath" /> ：
              </th>
              <td><input type="text"  id="xNamePath" name="namePath"    class="form-control"/></td>
           </tr>
           
           <tr>
              <td > <@locale code="org.type" />：</td>
              <td><input type="text"  id="type" name="type"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.division" />：</td>
              <td><input type="text"  id="division" name="division"    class="form-control"/></td>
           </tr>
           <tr>
              <th >
                 <@locale code="org.sortorder" /> ：
              </th>
              <td><input type="text"  id="sortIndex" name="sortIndex"    class="form-control" value='1'/></td>
           </tr>
          
           <tr>
              <th ><@locale code="org.description" />：</td>
              <td><input type="text"  id="description" name="description"    class="form-control"/></td>
           </tr>
        </table>
        <table id="table_switch_extra"   class="table table-bordered"  style="display:none">
        	<tr>
              <td > <@locale code="org.contact" />：</td>
              <td><input type="text"  id="contact" name="contact"    class="form-control"/></td>
           </tr>
           <tr>
              <th  width="200px"> <@locale code="org.phone" />：</td>
              <td><input type="text"  id="phone" name="phone"    class="form-control"/></td>
           </tr>
            <tr>
              <td > <@locale code="org.email" />：</td>
              <td><input type="text"  id="email" name="email"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.fax" />：</td>
              <td><input type="text"  id="fax" name="fax"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.country" />：</td>
              <td><input type="text"  id="country" name="country"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.region" />：</td>
              <td><input type="text"  id="region" name="region"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.locality" />：</td>
              <td><input type="text"  id="locality" name="locality"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.street" />：</td>
              <td><input type="text"  id="street" name="street"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.address" />：</td>
              <td><input type="text"  id="address" name="address"    class="form-control"/></td>
           </tr>
           <tr>
              <td > <@locale code="org.postalcode" />：</td>
              <td><input type="text"  id="postalCode" name="postalCode"    class="form-control"/></td>
           </tr>
        </table>
     		</td></tr>
     		<tr><td nowrap class="center">
                        
                          <input id="submitBtn" class="button btn btn-primary mr-3"      type="submit"   style="width:100px"  value="<@locale code="button.text.save" />"/>
                       
     		</td></tr>
     	</table>

     </form>
</body>
</html>
