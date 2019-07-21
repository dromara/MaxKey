<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<script type="text/javascript">
   <!--
      $(function(){	
      	$("#iconFileImg").on("click",function(){
      		if(!$("#iconFileImg").hasClass("appended")){
      			$("#iconFileImg").after('<input  type="file" id="iconFile" name="iconFile"  title="" value=""/>');
      			$("#iconFileImg").addClass("appended");
      		}
      	});
      });
      //-->
</script>
<!--table-->
<table width="960"  class="datatable" >
   <tbody>
      <tr>
         <td colspan=4><s:Locale code="apps.basic.info"/></td>
      </tr>
      <tr>
         <th style="width:15%;"><s:Locale code="apps.id"/>：</th>
         <td style="width:35%;">
         	<div style="width:100%;font-weight: bold;">${model.id}</div>
            <input type="hidden" id="id" name="id"  title="" value="${model.id}"/>
            <input type="hidden" id="status" name="status"  title="" value="${model.status}"/>
         </td>
         <th style="width:15%;"><s:Locale code="apps.secret"/>：</th>
         <td style="width:35%;">
         	<input id="generateSecret" type="button" class="button" value="<s:Locale code="button.text.generate"/>"/><br>
         	<div id="secret_text" style="width:100%;font-weight: bold;">${model.secret}</div>
            <input type="hidden" id="secret" name="secret"  title="" value="${model.secret}"/>
         </td>
      </tr>
      <tr>
         <th><s:Locale code="apps.name"/>：</th>
         <td  colspan="3">
            <input type="text" id="name" name="name"  title="" value="${model.name}"/>
            <b class="orange">*</b><label for="name"></label>
         </td>
      </tr>
      <tr>
         <th><s:Locale code="apps.loginUrl"/>：</th>
         <td colspan="3">
            <input type="text" id="loginUrl" name="loginUrl"  title="" value="${model.loginUrl}"/>
            <b class="orange">*</b><label for="loginUrl"></label>
         </td>
      </tr>
      <tr>
         <th><s:Locale code="apps.protocol"/>：</th>
         <td>${model.protocol}
            <input type="hidden" id="protocol" name="protocol"  title="" value="${model.protocol}"/>
         </td>
         <th><s:Locale code="apps.category"/>：</th>
         <td>
            <select name="category"  class="select_t">
				<c:forEach items="${appCategorysList}" var="appCategory">
					<option value="${appCategory.id}"  <c:if test="${model.category==appCategory.id}">selected</c:if> >${appCategory.name}</option>
	      		</c:forEach>
			</select>
         </td>
      </tr>
      <tr>
         <th><s:Locale code="apps.icon"/>：</th>
         <td><img id="iconFileImg" width='30' height='30' src='<s:Base/>/image/${model.id}'/>
            <b class="orange">*</b><label for="iconFile"></label>
         </td>
         <th><s:Locale code="common.text.sortorder"/></th>
         <td>
         	<input  type="text" id="sortOrder" name="sortOrder"  title="" value="${model.sortOrder}"/>
            <b class="orange">*</b><label for="sortOrder"></label>
         </td>
      </tr>
      <tr>
         <th><s:Locale code="apps.vendor"/>：</th>
         <td>
            <input  type="text" id="vendor" name="vendor"  title="" value="${model.vendor}"/>
            <b class="orange">*</b><label for="vendor"></label>
         </td>
         <th><s:Locale code="apps.vendor.url"/>：</th>
         <td>
            <input type="text" id="vendorUrl" name="vendorUrl"  title="" value="${model.vendorUrl}"/>
            <b class="orange">*</b><label for="vendorUrl"></label>
         </td>
      </tr>
      <tr>
      	<th><s:Locale code="apps.visible"/></th>
         <td>
         	<select  id="visible" name="visible" >
				<option value="0"  <c:if test="${0==model.visible}">selected</c:if> ><s:Locale code="apps.visible.hidden"/></option>
				<option value="1"  <c:if test="${1==model.visible}">selected</c:if> ><s:Locale code="apps.visible.all"/></option>
				<option value="2"  <c:if test="${2==model.visible}">selected</c:if> ><s:Locale code="apps.visible.internet"/></option>
				<option value="3"  <c:if test="${3==model.visible}">selected</c:if> ><s:Locale code="apps.visible.intranet"/></option>
			</select>
         </td>
         <th><s:Locale code="common.text.description"/>：</th>
         <td>
            <input  type="text" id="description" name="description"  title="" value="${model.description}"/>
            <b class="orange">*</b><label for="description"></label>
         </td>
      </tr>
   </tbody>
</table>
