
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/basic/add"  
			forward="<@base/>/apps/appsList"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			    	      	<!--table-->
			<table class="datatable" >
				<tbody>
				<tr>
					<td ><#include  "./appAddCommon.ftl"/>
				</tr>
				</tbody>
			  </table>
  	      
		    <input id="_method" type="hidden" name="_method"  value="post"/>
			<input  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button" id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>