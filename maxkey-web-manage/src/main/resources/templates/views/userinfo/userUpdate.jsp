<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
   <!--
      $(function(){	
      	$("#picture").on("click",function(){
      		$("#pictureFile").click();
      	});
      });
      //-->
</script>

<form 
	method="post"
	type="alert"  
	action="<s:Base/>/users/update" 
	autoclose="true" 
	enctype="multipart/form-data">
	 <div class="" style="width:100%;">
		<div class="top">
			<ul class="switch_tab"  style="width:100%" >
				<li id="switch_basic" value="table_switch_basic" style="width:24%" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);"><s:Locale code="userinfo.tab.basic" /></a></li>
				<li id="switch_company"  value="table_switch_company" style="width:24%" class="switch_tab_class"><a href="javascript:void(0);"><s:Locale code="userinfo.tab.business" /></a></li>
				<li id="switch_home"  value="table_switch_home" style="width:24%" class="switch_tab_class"><a href="javascript:void(0);"><s:Locale code="userinfo.tab.personal" /></a></li>
				<li id="switch_extra"  value="table_switch_extra" style="width:24%" class="switch_tab_class"><a href="javascript:void(0);"><s:Locale code="userinfo.tab.extra" /></a></li>
			
			</ul>
		</div>

	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			   <%@ include file="userUpdateBasic.jsp"%> 
			   <%@ include file="userUpdateCompany.jsp"%> 
			   <%@ include file="userUpdateHome.jsp"%> 
  	        <div class="clear"></div>
		</div>
		</div>
			<div >
				<div >
					<input id="status" type="hidden" name="status"  value="${model.status}"/>
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="submitBtn" class="button" type="submit" value="<s:Locale code="button.text.save" />"/>
					<input id="closeBtn"  class="button"  type="button" value="<s:Locale code="button.text.cancel" />"/>		  
				</div>
			</div>
	 </div> 
</form>