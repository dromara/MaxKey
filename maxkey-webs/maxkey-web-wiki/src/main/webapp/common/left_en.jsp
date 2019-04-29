<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!-- EN treeView -->
<div class="treeView">
    <ul id="menu">
      <li id="index"> <a href="<%=path %>/index.jsp?language=en_US"  class="nav1" <c:if test="${param.pageType == 'home' }">class="current"</c:if>> <em class="icon_home"></em> <span>Platform Introduction</span> </a>
      <li> <a href="#" class="nav1"> <em class="icon_home"></em> <span class="tet">OAuth2.0 App Integration</span> <em id="1n" class="nav_arr"></em> </a>
        <ul id="oauth">
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth2_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_2' }">class="current"</c:if>><em class="icon_home"></em>OAuth2.0 Introduction</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth3_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_3' }">class="current"</c:if>><em class="icon_home"></em>SSO Certified Authentication Procedure</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth4_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_4' }">class="current"</c:if>><em class="icon_home"></em>APP Certified Authentication Procedure</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth5_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_5' }">class="current"</c:if>><em class="icon_home"></em>WEB Applied Verification and authorization</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth7_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_7' }">class="current"</c:if>><em class="icon_home"></em>API Interface Standard</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth6_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_6' }">class="current"</c:if>><em class="icon_home"></em>Invoke API through OAuth2.0 token</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth8_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_8' }">class="current"</c:if>><em class="icon_home"></em>OAuth2.0 Error Code</a></li>
          <li><a class="nav2"  href="<%=path %>/page/oauthv20/oauth9_en.jsp?language=en_US" <c:if test="${param.pageType == 'oauth_9' }">class="current"</c:if>><em class="icon_home"></em>SDK Application Integration Guide Example</a></li>
        </ul>
      </li>
      <li> <a href="" class="nav1"> <em class="icon_home"></em> <span class="tet">SAML2.0 App Integration</span> <em  id="2n" class="nav_arr"></em> </a>
        <ul id="saml">
          <li><a class="nav2"  href="<%=path %>/page/samlv20/saml1_en.jsp?language=en_US" <c:if test="${param.pageType == 'saml_1' }">class="current"</c:if>><em class="icon_home"></em>SAML Introduction</a></li>
          <li><a class="nav2"  href="<%=path %>/page/samlv20/saml2_en.jsp?language=en_US" <c:if test="${param.pageType == 'saml_2' }">class="current"</c:if>><em class="icon_home"></em>SP-Init SSO Procedure</a></li>
          <li><a class="nav2"  href="<%=path %>/page/samlv20/saml3_en.jsp?language=en_US" <c:if test="${param.pageType == 'saml_3' }">class="current"</c:if>><em class="icon_home"></em>IDP-Init SSO Procedure</a></li>
          <li><a class="nav2"  href="<%=path %>/page/samlv20/saml4_en.jsp?language=en_US" <c:if test="${param.pageType == 'saml_4' }">class="current"</c:if>><em class="icon_home"></em>SAML Safety Authentication Configuration</a></li>
        </ul>
      </li>

      <li> <a href="#" class="nav1"> <em class="icon_home"></em> <span class="tet">Application Integration Applicate</span> <em id="4n" class="nav_arr"></em> </a>
        <ul id="inte">
          <li><a class="nav2"  href="<%=path %>/page/inte/inte1_en.jsp?language=en_US" <c:if test="${param.pageType == 'inte_1' }">class="current"</c:if>><em class="icon_home"></em>OAuth Application Integration Registration</a></li>
          <li><a class="nav2"  href="<%=path %>/page/inte/inte2_en.jsp?language=en_US" <c:if test="${param.pageType == 'inte_2' }">class="current"</c:if>><em class="icon_home"></em>SAML Application Integration Registration</a></li>
          <li><a class="nav2"  href="<%=path %>/page/inte/inte3_en.jsp?language=en_US" <c:if test="${param.pageType == 'inte_3' }">class="current"</c:if>><em class="icon_home"></em>Registration Procedure</a></li>
        </ul>
      </li>
      <li id="index"> <a class="nav1"  href="<%=path %>/page/glossary.jsp?language=en_US" <c:if test="${param.pageType == 'glossary' }">class="current"</c:if>> <em class="icon_home"></em> <span>Glossary</span> </a></li>
    </ul>
  </div>
<!-- EN treeView End-->
