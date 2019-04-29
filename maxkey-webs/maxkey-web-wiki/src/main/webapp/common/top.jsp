<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String language="zh_CN";
if(request.getParameter("language")!=null){
	language = request.getParameter("language");
}
%>
<!--================ TOP ================-->
<div class="connsec-header">
    <div class="dockbar"></div>
    <div class="menubar">
    	<div class="container" style="width:990px;">
        	<div class="row">
                <a class="header-logo" href="<%=path %>/index.jsp?language=<%=language%>" ><img src="<%=path %>/images/logo.jpg"/></a>
            </div>
            <div class="headtitle">
            <% if(language.equals("zh_CN")){%>	
                                                 应用集成开发指南
            <%} else { %> 
            	 App Integration Develop Wiki
            <%} %>
            </div>
             <p class="multi-lang">
            	 <a style="color: #fff;  font-size: 14px;" href="?language=zh_CN">中文</a> | 
            	 <a style="color: #fff;  font-size: 14px;" href="?language=en_US">ENGLISH</a>
             </p>
        </div>
       
    </div>
</div>
