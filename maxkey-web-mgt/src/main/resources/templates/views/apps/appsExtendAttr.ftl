<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<style>
<!--

-->
</style>

<script type="text/javascript">		
<!--
$(function(){			
	var attrIndex = 0;
	
	function addExtendAttr(attribute,attributeType,attributeValue){
		var html =	'<tr  id="extendTr_' + attrIndex + '">';   
		//Index 
		html += 		'<th nowrap ><@locale code="apps.extendAttr.parameter"/>：'; 
		html += 			'<input  class="button  btn btn-danger mr-3  delExtendTr"  type="button" name="delExtendTr"  attrTrId="extendTr_'+attrIndex+'" value="<@locale code="button.text.delete" />"/>';
		html +=			'</th>';  
		//attribute
		html +=			'<td>';   
		html += 			'<input type="text" class="form-control" id="attribute_' + attrIndex + '" name="attr" class="int" title="" value="'+attribute+'"  required="" />';   
        html += 		'</span></td>';
        //attributeType
        html += 		'<th  nowrap ><@locale code="apps.extendAttr.parameter.type"/>：</th>	<td><span class="intspan">';
        html += 			'<input type="text"  class="form-control" id="attributeType_' + attrIndex + '" name="type" class="int" title="" value="'+attributeType+'"  required="" />';
        html += 		'</span></td>';
		//attributeValue   
        html += 		'<th  nowrap ><@locale code="apps.extendAttr.parameter.value"/>：</th>	<td><span class="intspan">';
        html += 			'<input type="text"  class="form-control" id="attributeValue_' + attrIndex + '" name="value" class="int" title="" value="'+attributeValue+'"  required="" />';
        html += 		'</span></td>';
       
        html += '</tr>'; 
		$('#extendAttrBody').append(html);
		attrIndex++;
	}
	
	<#if 1==model.isExtendAttr>
	var extendAttrJson = eval("("+'${model.extendAttr}'+")");
	for(extendAttrIndex in extendAttrJson){
		addExtendAttr(extendAttrJson[extendAttrIndex].attr,extendAttrJson[extendAttrIndex].type,extendAttrJson[extendAttrIndex].value);
	};
	</#if>
	
	$("#addExtendAttr").on('click',function(){
		addExtendAttr("","","");
	});	
				
	$("#extendAttrBody").delegate(".delExtendTr",'click',function(){
		$("#"+$(this).attr("attrTrId")).remove();
	});
	
	});
</script>
</head>
<body>	
<form id="actionForm"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/updateExtendAttr"  class="needs-validation" novalidate>		 
    <!-- content -->    
  	<!--table-->
	<table   class="table table-bordered" >
		<tbody>
			
			<tr>
	         	<th style="width:15%;"><@locale code="apps.id"/>：</th>
	         	<td style="width:35%;">
	         		<div style="width:100%;font-weight: bold;">${model.id!}</div>
	            	<input type="hidden" id="id" name="id"  title="" value="${model.id!}"/>
	         	</td>
		         <th><@locale code="apps.name"/>：</th>
		         <td  colspan="3">
		         	<div style="width:100%;font-weight: bold;">${model.name!}</div>
	         	</td>
	     	</tr>
			<tr>
				<th><@locale code="apps.extendAttr"/>：</th>
				<td colspan="3">
					<input type="checkbox" id="isExtendAttr" name="isExtendAttr" value="1"  <#if 1==model.isExtendAttr>checked</#if>  />
					<@locale code="apps.isExtendAttr"/>
				</td>
				<td colspan="2">
					<input class="button btn btn-primary mr-3" type="button"  value='<@locale code="button.text.add"/>' id="addExtendAttr"/>
					<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
					<input class="button btn btn-secondary mr-3"  id="closeBtn" type="button" value="<@locale code="button.text.close" />"/>	 
				</td>
			</tr>
		</tbody>
		<tbody >
			<tr>
				<td colspan =6>
				<table   class="table table-bordered" >
					<tbody id="extendAttrBody">
					</tbody>
				</table>  
			</tr>
		</tbody>
	  </table>  
</form>
</body>
</html>