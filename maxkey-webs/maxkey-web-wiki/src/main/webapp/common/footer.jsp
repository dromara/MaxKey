<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String lang = "zh_CN";
if(request.getParameter("language")!=null){
	lang = request.getParameter("language");
}
%>
<!-- footer -->
<% if(lang.equals("zh_CN")){%>	

<div class="footer" >
   <div class="container">
    <table cellpadding="2" cellspacing="0" style="margin-top: 30px;width:100%;height:100%; border:0;">
        <TR>
          <TD align="center" valign="middle" class="footer  ">
          	版权所有 &copy; 上海技安信息技术有限公司<br>
          	<a href="http://www.connsec.com" target="_blank">www.connsec.com</a><br>
          </TD>
        </TR>
    </table> 
   </div>
</div>
<%} else { %> 
<div class="footer" >
   <div class="container">
    <table cellpadding="2" cellspacing="0" style="margin-top: 30px;width:100%;height:100%; border:0;">
        <TR>
          <TD align="center" valign="middle" class="footer  ">
          	CopyRight &copy; ShangHai ConnSec SoftWare, Inc. All rights reserved.<br>
          	<a href="http://www.connsec.com" target="_blank">www.connsec.com</a><br>
          </TD>
        </TR>
    </table> 
   </div>
</div>
 <%} %>