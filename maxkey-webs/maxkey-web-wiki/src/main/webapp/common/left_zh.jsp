<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>

 <!-- ZH treeView -->	
<div class="treeView">
    <ul id="menu">
      <li id="home"> <a href="<%=path %>/index.jsp?language=zh_CN" class="nav1 <c:if test="${param.pageType == 'home' }">current</c:if>" ><em class="icon_home"></em> <span>平台介绍</span> </a></li>
      <li> <a href="" class="nav1"> <em class="icon_home"></em> <span class="tet">应用认证集成</span> <em  id="2n" class="nav_arr"></em> </a>
        <ul id="authz">
	      <li><a id="authz-intros"  class="nav2 <c:if test="${param.pageType == 'authz-intros' }">current</c:if>" href="<%=path %>/page/authz/am.jsp" class="nav2"> <em class="icon_home"></em> <span class="tet">认证集成介绍</span>  </a></li>
          
	      <li> <a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">OAuth1.0a集成</span>  </a>
	        <ul id="authz-oauth10a">
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth2.jsp" ><em class="icon_home"></em>OAuth1.0a介绍</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth3' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth3.jsp" ><em class="icon_home"></em>通过SSO的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth4' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth4.jsp" ><em class="icon_home"></em>通过应用本身的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth5' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth5.jsp" ><em class="icon_home"></em>Web应用验证授权</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth6' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth7.jsp" ><em class="icon_home"></em>API接口标准</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth7' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth6.jsp" ><em class="icon_home"></em>通过OAuth2.0 token调用API</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth8' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth8.jsp" ><em class="icon_home"></em>OAuth2.0 错误码</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth10a-oauth9' }">current</c:if>" href="<%=path %>/page/authz/oauthv10a/oauth9.jsp" ><em class="icon_home"></em>SDK应用集成指引示例</a></li>
	        </ul>
	      </li>
	      <li> <a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">OAuth2.0集成</span>  </a>
	        <ul id="authz-oauth20">
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth2.jsp" ><em class="icon_home"></em>OAuth2.0介绍</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth3' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth3.jsp" ><em class="icon_home"></em>通过SSO的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth4' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth4.jsp" ><em class="icon_home"></em>通过应用本身的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth5' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth5.jsp" ><em class="icon_home"></em>Web应用验证授权</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth6' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth7.jsp" ><em class="icon_home"></em>API接口标准</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth7' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth6.jsp" ><em class="icon_home"></em>通过OAuth2.0 token调用API</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth8' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth8.jsp" ><em class="icon_home"></em>OAuth2.0 错误码</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oauth20-oauth9' }">current</c:if>" href="<%=path %>/page/authz/oauthv20/oauth9.jsp" ><em class="icon_home"></em>SDK应用集成指引示例</a></li>
	        </ul>
	      </li>
	      <li> <a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">OpenID Connect 1.0集成</span>  </a>
	        <ul id="authz-oidc10">
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth2.jsp" ><em class="icon_home"></em>OpenID Connect介绍</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc3' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth3.jsp" ><em class="icon_home"></em>通过SSO的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc4' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth4.jsp" ><em class="icon_home"></em>通过应用本身的认证流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc5' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth5.jsp" ><em class="icon_home"></em>Web应用验证授权</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc6' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth7.jsp" ><em class="icon_home"></em>API接口标准</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc7' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth6.jsp" ><em class="icon_home"></em>通过OAuth2.0 token调用API</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc8' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth8.jsp" ><em class="icon_home"></em>OAuth2.0 错误码</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-oidc10-oidc9' }">current</c:if>" href="<%=path %>/page/authz/oidcv10/oauth9.jsp" ><em class="icon_home"></em>SDK应用集成指引示例</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">SAML 1.1集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-saml11">
	          <li><a class="nav3" href="<%=path %>/page/authz/samlv11/saml1.jsp" <c:if test="${param.pageType == 'authz-saml11' }">class="current"</c:if>><em class="icon_home"></em>SAML介绍</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/samlv11/saml2.jsp" <c:if test="${param.pageType == 'authz-saml11-saml2' }">class="current"</c:if>><em class="icon_home"></em>SP-Init SSO流程</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/samlv11/saml3.jsp" <c:if test="${param.pageType == 'authz-saml11-saml3' }">class="current"</c:if>><em class="icon_home"></em>IDP-Init SSO流程</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/samlv11/saml4.jsp" <c:if test="${param.pageType == 'authz-saml11-saml4' }">class="current"</c:if>><em class="icon_home"></em>SAML安全认证配置</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">SAML 2.0集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-saml20">
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-saml20' }">current</c:if>" href="<%=path %>/page/authz/samlv20/saml1.jsp" ><em class="icon_home"></em>SAML介绍</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-saml20-saml2' }">current</c:if>" href="<%=path %>/page/authz/samlv20/saml2.jsp" ><em class="icon_home"></em>SP-Init SSO流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-saml20-saml3' }">current</c:if>" href="<%=path %>/page/authz/samlv20/saml3.jsp" ><em class="icon_home"></em>IDP-Init SSO流程</a></li>
	          <li><a class="nav3 <c:if test="${param.pageType == 'authz-saml20-saml4' }">current</c:if>" href="<%=path %>/page/authz/samlv20/saml4.jsp" ><em class="icon_home"></em>SAML安全认证配置</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">CAS集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-cas">
	          <li><a class="nav3" href="<%=path %>/page/authz/cas/p1.jsp" <c:if test="${param.pageType == 'authz-cas' }">class="current"</c:if>><em class="icon_home"></em>CAS介绍</a></li>
	         </ul>
	      </li>  
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">TokenBased集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-TokenBased">
	          <li><a class="nav3" href="<%=path %>/page/authz/tokenbased/p1.jsp" <c:if test="${param.pageType == 'authz-TokenBased' }">class="current"</c:if>><em class="icon_home"></em>TokenBased介绍</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">LTPA集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-ltpa">
	          <li><a class="nav3" href="<%=path %>/page/authz/ltpa/p1.jsp" <c:if test="${param.pageType == 'authz-ltpa' }">class="current"</c:if>><em class="icon_home"></em>LTPA介绍</a></li>
	       </ul>
	      </li>  
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">FormBased集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-formbased">
	          <li><a class="nav3" href="<%=path %>/page/authz/formbased/p1.jsp" <c:if test="${param.pageType == 'authz-formbased' }">class="current"</c:if>><em class="icon_home"></em>FormBased介绍</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">定制认证集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-custom">
	          <li><a class="nav3" href="<%=path %>/page/authz/custom/p1.jsp" <c:if test="${param.pageType == 'authz-custom' }">class="current"</c:if>><em class="icon_home"></em>定制认证介绍</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">Adapter认证集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="authz-adapter">
	          <li><a class="nav3" href="<%=path %>/page/authz/adapter/adapter.jsp" <c:if test="${param.pageType == 'authz-adapter' }">class="current"</c:if>><em class="icon_home"></em>Adapter介绍</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/adapter/p1.jsp" <c:if test="${param.pageType == 'authz-adapter-adapter1' }">class="current"</c:if>><em class="icon_home"></em>IBM WebSphere</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/adapter/p2.jsp" <c:if test="${param.pageType == 'authz-adapter-adapter2' }">class="current"</c:if>><em class="icon_home"></em>Oracle WebLogic</a></li>
	          <li><a class="nav3" href="<%=path %>/page/authz/adapter/p3.jsp" <c:if test="${param.pageType == 'authz-adapter-adapter3' }">class="current"</c:if>><em class="icon_home"></em>MS IIS</a></li>
	       	  <li><a class="nav3" href="<%=path %>/page/authz/adapter/p4.jsp" <c:if test="${param.pageType == 'authz-adapter-adapter4' }">class="current"</c:if>><em class="icon_home"></em>Liferay Portal</a></li>
	        </ul>
	      </li>
        </ul>
      </li>
      <li> <a href="" class="nav1"> <em class="icon_home"></em> <span class="tet">组织&账号集成</span> <em  id="2n" class="nav_arr"></em> </a>
        <ul id="idm">
          <li><a id="idm-intros"  class="nav2 <c:if test="${param.pageType == 'idm-intros' }">current</c:if>" href="<%=path %>/page/idm/idm.jsp" class="nav2"> <em class="icon_home"></em> <span class="tet">组织&账号集成介绍</span>  </a></li>
          
	      <li> <a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">MS AD集成</span>  </a>
	        <ul id="idm-msad">
	          <li><a class="nav3 <c:if test="${param.pageType == 'idm-msad' }">current</c:if>" href="<%=path %>/page/idm/msad/p1.jsp" ><em class="icon_home"></em>MS AD集成</a></li>
	        </ul>
	      </li>
	      <li> <a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">LDAP v3集成</span>  </a>
	        <ul id="idm-ldapv3">
	          <li><a class="nav3 <c:if test="${param.pageType == 'idm-ldapv3' }">current</c:if>"  href="<%=path %>/page/idm/ldapv3/p1.jsp" ><em class="icon_home"></em>LDAP v3集成</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">REST集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="idm-rest">
	          <li><a  class="nav3 <c:if test="${param.pageType == 'idm-rest' }">current</c:if>"  href="<%=path %>/page/idm/rest/p1.jsp" ><em class="icon_home"></em>REST集成</a></li>
	         </ul>
	      </li>
	      <li><a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">SPML集成</span>  </a>
	        <ul id="idm-spml">
	          <li><a  class="nav3 <c:if test="${param.pageType == 'idm-spml' }">current</c:if>"  href="<%=path %>/page/idm/spml/p1.jsp" ><em class="icon_home"></em>SPML集成</a></li>
	        </ul>
	      </li>
	      <li><a href="#" class="nav2"> <em class="icon_home"></em> <span class="tet">SCIM集成</span>  </a>
	        <ul id="idm-scim">
	          <li><a  class="nav3 <c:if test="${param.pageType == 'idm-scim' }">current</c:if>"  href="<%=path %>/page/idm/scimv20/p1.jsp"><em class="icon_home"></em>SCIM集成</a></li>
	        </ul>
	      </li>
	      <li> <a href="" class="nav2"> <em class="icon_home"></em> <span class="tet">组织&账号定制集成</span> <em  id="2n" class="nav_arr"></em> </a>
	        <ul id="idm-custom">
	          <li><a  class="nav3 <c:if test="${param.pageType == 'idm-custom' }">current</c:if>"  href="<%=path %>/page/idm/custom/p1.jsp" ><em class="icon_home"></em>定制集成</a></li>
	        </ul>
	      </li>
	      <li> <a  id="idm-connector"  class="nav2 <c:if test="${param.pageType == 'idm-connector' }">current</c:if>"  href="<%=path %>/page/idm/connector/p1.jsp"> <em class="icon_home"></em> <span class="tet">Connector集成</span> <em  id="2n" class="nav_arr"></em> </a>
	      </li> 
	      <li> <a  id="idm-pvsrule"  class="nav2 <c:if test="${param.pageType == 'idm-pvsrule' }">current</c:if>"  href="<%=path %>/page/idm/pvsrule.jsp"> <em class="icon_home"></em> <span class="tet">账号供应策略</span> <em  id="2n" class="nav_arr"></em> </a>
	      </li>  
        </ul>
      </li>
	  <li id="slo"> <a  class="nav1 <c:if test="${param.pageType == 'slo' }">current</c:if>"  href="<%=path %>/page/slo/slo.jsp?language=zh_CN" class="nav1" <c:if test="${param.pageType == 'slo' }">class="current"</c:if>> <em class="icon_home"></em> <span>单点注销(SLO)</span> </a></li>
      <li><a href="#" class="nav1"> <em class="icon_home"></em> <span class="tet">应用集成申请</span> <em id="4n" class="nav_arr"></em> </a>
        <ul id="inte">
          <li><a class="nav2 <c:if test="${param.pageType == 'inte-inte' }">current</c:if>"  href="<%=path %>/page/inte/inte1.jsp"  ><em class="icon_home"></em>OAuth应用集成注册</a></li>
          <li><a class="nav2 <c:if test="${param.pageType == 'inte-inte2' }">current</c:if>"  href="<%=path %>/page/inte/inte2.jsp" ><em class="icon_home"></em>SAML应用集成注册</a></li>
          <li><a class="nav2 <c:if test="${param.pageType == 'inte-inte3' }">current</c:if>"  href="<%=path %>/page/inte/inte3.jsp" ><em class="icon_home"></em>注册流程</a></li>
        </ul>
      </li>
      <li id="dencrypt"> <a href="<%=path %>/page/dencrypt.jsp?language=zh_CN" class="nav1 <c:if test="${param.pageType == 'dencrypt' }">current</c:if>" > <em class="icon_home"></em> <span>加解密技术</span> </a></li>
      <li id="glossary"> <a href="<%=path %>/page/glossary.jsp?language=zh_CN" class="nav1 <c:if test="${param.pageType == 'glossary' }">current</c:if>" > <em class="icon_home"></em> <span>专业术语词汇表</span> </a></li>
    </ul>
  </div>
 <!-- ZH treeView End -->
