<%@ page   language="java"     import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   language="java"     import="org.maxkey.web.*" %>
<%@ page   language="java"     import="org.maxkey.domain.*"%>
<%@ page   language="java"     import="org.maxkey.domain.userinfo.*"%>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<div id="nav_primay_content" class="menuprimary">
<div class="menucontainer">
	<ul >
<%
	String menuId=request.getParameter("mnid")==null?"":request.getParameter("mnid").toString();
String selectMenuId=menuId;
if(WebContext.getNavigations()!=null){
	List<Navigations> listNavs=WebContext.getNavigations();//100000000000 
	StringBuffer navsStr=new StringBuffer("");
	int navCount=0;
	for (Navigations nav : listNavs){
		if(nav.getpId().equals("120000000000")){
	//bulid child menu
	StringBuffer childNavStr=new StringBuffer("\n\t\t<ul>");
	for (Navigations childNav : listNavs){
		if(nav.getId().equals(childNav.getpId())){
			if(menuId.equals(childNav.getId())){selectMenuId=nav.getId();}
			String childNavName=WebContext.getI18nValue("navs."+childNav.getId());
			if(childNavName == null){childNavName	=	childNav.getName();}
			childNavStr.append("\n\t\t\t<li id='nav_second_").append(childNav.getId()).append("' ").append(" class='nav_second_level' ").append(" xpath='").append(childNav.getxPath()).append("'>");;
			childNavStr.append("<a href='").append(request.getContextPath()).append("/").append(childNav.getUrl()).append("?mnid=").append(childNav.getId()).append("'>");
			childNavStr.append(childNavName);
			childNavStr.append("</a>");
			childNavStr.append("</li>");	
		}
	}
	childNavStr.append("</ul>");
	//end of child menu
	//bulid primary menu
	String navName=WebContext.getI18nValue("navs."+nav.getId());
	if(navName == null){navName	=	nav.getName();}
	navsStr.append("<li  id='nav_primay_").append(nav.getId());
	if(navCount ++ ==0){
		navsStr.append("' class='nav_primay_level primaryleft' ");
	}else{
		navsStr.append("' class='nav_primay_level' ");
	}
	navsStr.append(" xpath='").append(nav.getxPath()).append("'>");
	navsStr.append("<a ").append((selectMenuId.equals(nav.getId())?"class='selected'":"")).append(" href='").append(request.getContextPath()).append("/").append(nav.getUrl()).append("?mnid=").append(nav.getId()).append("'>");
	navsStr.append(navName);
	navsStr.append("</a>");
	//end of primary menu
	//append child menu div
	navsStr.append("\n\t<div id='nav_child_").append(nav.getId()).append("' class='nav_second_child'>");
	if(childNavStr.length()>10){
		navsStr.append(childNavStr);
	}
	navsStr.append("</div>\n");
	
	navsStr.append("</li>\n");	
		}
	}
	out.println(navsStr);	
}
%>
	</ul>
	
	</div>
</div>

<script>
	$(function(){
		function displaySecondNavs(menuId){
			$("#nav_second").html("<div class='menusecond'>"+$("#"+menuId+" .nav_second_child").html()+"</div><br style='clear: left' />");
		}
		$("#nav_primay_content ul li").mouseover(function(){
			displaySecondNavs(this.id);
		});
		<%if(menuId.length()>5){%>displaySecondNavs("nav_primay_<%=selectMenuId%>");<%}%>
	});
</script>