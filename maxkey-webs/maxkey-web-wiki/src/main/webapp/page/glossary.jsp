<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/glossary.jsp");	
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="glossary" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>专业术语词汇表</h1>
    <div class="text-section">
     <p class="section">解释本文档所用到的专业术语。</p>
    </div><!-- 一段描述结束 -->
     <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>简称(ShortName)</th><th>en(英文)</th><th>zh(中文名称)</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>SSO</td>
		        <td>Single sign-on</td>
		        <td>单点登录</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>IAM</td>
		        <td>Identity and Access Management</td>
		        <td>身份识别与访问管理</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>IdM</td>
		        <td>Identity management</td>
		        <td>身份管理</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>AM</td>
		        <td>Access Management</td>
		        <td>访问管理</td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>AC</td>
		        <td>Access control</td>
		        <td>访问控制</td>
		    </tr>
		     <tr>
		        <td>6</td>
		        <td>FId</td>
		        <td>Identity Federation</td>
		        <td>联邦身份</td>
		    </tr>
		    <tr>
		        <td>7</td>
		        <td>FIdM</td>
		        <td>Federated Identity Management</td>
		        <td>联邦身份管理</td>
		    </tr>
		    <tr>
		        <td>8</td>
		        <td>Authz</td>
		        <td>Authorization </td>
		        <td>授权</td>
		    </tr>
		    <tr>
		        <td>9</td>
		        <td>Authn</td>
		        <td>Authentication</td>
		        <td>认证</td>
		    </tr>
		    <tr>
		        <td>10</td>
		        <td>IDaas</td>
		        <td>Identity Management as a Service</td>
		        <td>身份管理即服务</td>
		    </tr>
		    <tr>
		        <td>11</td>
		        <td>SAML</td>
		        <td>Security Assertion Markup Language</td>
		        <td>安全断言标记语言</td>
		    </tr>
		    <tr>
		        <td>12</td>
		        <td>OAuth</td>
		        <td>OAuth</td>
		        <td>为用户资源的授权提供了一个安全的、开放而又简易的标准</td>
		    </tr>
		    <tr>
		        <td>13</td>
		        <td>OAuth 2.0</td>
		        <td>OAuth 2.0</td>
		        <td>OAuth1a的升级</td>
		    </tr>
		    <tr>
		        <td>14</td>
		        <td>OpenID Connect(OIDC)</td>
		        <td>OpenID Connect</td>
		        <td>OpenID 的升级，基于OAuth 2.0，提供身份的认证</td>
		    </tr>
		    <tr>
		        <td>15</td>
		        <td>OpenID</td>
		        <td>OpenID </td>
		        <td>以用户为中心的数字身份识别框架，它具有开放、分散、自由等特性</td>
		    </tr>
		    <tr>
		        <td>16</td>
		        <td>CAS</td>
		        <td>Central Authentication Service </td>
		        <td>中央认证服务,Yale 大学发起的一个开源项目</td>
		    </tr>
		    <tr>
		        <td>17</td>
		        <td>SCIM</td>
		        <td>System for Cross-Domain Identity Management</td>
		        <td>跨域系统身份管理</td>
		    </tr>
		    <tr>
		        <td>18</td>
		        <td>SPML</td>
		        <td>Service Provisioning Markup Language</td>
		        <td>服务供应标记语言</td>
		    </tr>
		    <tr>
		        <td>19</td>
		        <td>STS</td>
		        <td>Security Token Service</td>
		        <td>安全令牌服务</td>
		    </tr>
		    
		    <tr>
		        <td>20</td>
		        <td>WS-Security</td>
		        <td>Web Services Security</td>
		        <td>Web服务安全</td>
		    </tr>
		    <tr>
		        <td>21</td>
		        <td>WS-Federation</td>
		        <td>Web Services Federation</td>
		        <td>基于Web服务的联邦身份验证规范</td>
		    </tr>
		     <tr>
		        <td>22</td>
		        <td>Token</td>
		        <td>Token</td>
		        <td>令牌</td>
		    </tr>
		    <tr>
		        <td>23</td>
		        <td>Kerberos</td>
		        <td>Kerberos</td>
		        <td>一种网络认证协议</td>
		    </tr>
		    
		    <tr>
		        <td>24</td>
		        <td>CAPTCHA</td>
		        <td>CAPTCHA</td>
		        <td>验证字/验证码</td>
		    </tr>
		    <tr>
		        <td>25</td>
		        <td>X.509</td>
		        <td>X.509</td>
		        <td>数字证书的格式</td>
		    </tr>
		    <tr>
		        <td>26</td>
		        <td>PKI</td>
		        <td>Public Key Infrastructure</td>
		        <td>公钥基础设施</td>
		    </tr>
		    <tr>
		        <td>27</td>
		        <td>PMI</td>
		        <td>Privilege Management Infrastructure</td>
		        <td>授权管理基础设施</td>
		    </tr>
		    <tr>
		        <td>28</td>
		        <td>RBAC</td>
		        <td>Role-Based Access Control</td>
		        <td>基于角色的访问控制</td>
		    </tr>
		    <tr>
		        <td>29</td>
		        <td>ABAC</td>
		        <td>Attribute-Based Access Control</td>
		        <td>基于属性的访问控制</td>
		    </tr>
		     <tr>
		        <td>30</td>
		        <td>PBAC</td>
		        <td>Policy-Based Access Control</td>
		        <td>基于策略的访问控制</td>
		    </tr>
		    
		    <tr>
		        <td>31</td>
		        <td>XACML</td>
		        <td>Xtensible Access Control Markup Language</td>
		        <td>可扩展的访问控制标记语言</td>
		    </tr>
		    <tr>
		        <td>32</td>
		        <td>IdP</td>
		        <td>Identity Provider</td>
		        <td>身份提供者</td>
		    </tr>
		    <tr>
		        <td>33</td>
		        <td>SP</td>
		        <td>Service Provider</td>
		        <td>服务提供者</td>
		    </tr>
		     <tr>
		        <td>34</td>
		        <td>CP</td>
		        <td>Claims Provider</td>
		        <td>声明提供者/IdP</td>
		    </tr>
		    <tr>
		        <td>35</td>
		        <td>RP</td>
		        <td>Relying Party</td>
		        <td>依赖提供者/SP</td>
		    </tr>
		   
		     <tr>
		        <td>36</td>
		        <td>Account Provisioning</td>
		        <td>Account Provisioning</td>
		        <td>账号供应</td>
		    </tr>
		    <tr>
		        <td>37</td>
		        <td>OTP</td>
		        <td>One Time Password</td>
		        <td>一次性密码</td>
		    </tr>
		    <tr>
		        <td>38</td>
		        <td>TFA</td>
		        <td>Two-Factor Authentication</td>
		        <td>双因素认证</td>
		    </tr>
		    <tr>
		        <td>39</td>
		        <td>LDAP</td>
		        <td>Lightweight Directory Access Protocol</td>
		        <td>轻量级目录访问协议</td>
		    </tr>
		    <tr>
		        <td>40</td>
		        <td>Directory Service</td>
		        <td>Directory Service</td>
		        <td>目录服务</td>
		    </tr>
		    <tr>
		        <td>41</td>
		        <td>AD</td>
		        <td>Active Directory</td>
		        <td>微软活动目录</td>
		    </tr>
		     <tr>
		        <td>42</td>
		        <td>ADFS</td>
		        <td>Active Directory Federation Services</td>
		        <td>基于微软活动目录的联邦服务</td>
		    </tr>
		    
		    <tr>
		        <td>43</td>
		        <td>XMPP</td>
		        <td>Extensible Messaging and Presence Protocol</td>
		        <td>可扩展消息处理现场协议</td>
		    </tr>
		     <tr>
		        <td>44</td>
		        <td>XKMS</td>
		        <td>XML Key Management Service</td>
		        <td>XML密钥管理服务</td>
		    </tr>
		    <tr>
		        <td>45</td>
		        <td>XDAS</td>
		        <td>Distributed Audit Service</td>
		        <td>分布式审计服务</td>
		    </tr>
		    <tr>
		        <td>46</td>
		        <td>JDBC</td>
		        <td>Java Database Connectivity</td>
		        <td>Java数据库连接</td>
		    </tr>
		    <tr>
		        <td>47</td>
		        <td>JNDI</td>
		        <td>Java Naming and Directory Interface</td>
		        <td>Java命名和目录接口</td>
		    </tr>
		    <tr>
		        <td>48</td>
		        <td>API</td>
		        <td>Application Programming Interface</td>
		        <td>应用程序编程接口</td>
		    </tr>
		     <tr>
		        <td>49</td>
		        <td>Web Services</td>
		        <td>Web Services</td>
		        <td>Web服务</td>
		    </tr>
		    <tr>
		        <td>50</td>
		        <td>SOAP</td>
		        <td>Simple Object Access Protocol</td>
		        <td>简单对象访问协议</td>
		    </tr>
		    <tr>
		        <td>51</td>
		        <td>WSDL</td>
		        <td>Web Services Description Language</td>
		        <td>Web服务描述语言</td>
		    </tr>
		    <tr>
		        <td>52</td>
		        <td>REST</td>
		        <td>Representational state transfer</td>
		        <td>表征状态转移</td>
		    </tr>
		     <tr>
		        <td>53</td>
		        <td>RESTful</td>
		        <td>RESTful Web API</td>
		        <td>一个使用HTTP并遵循REST原则的Web服务</td>
		    </tr>
		    <tr>
		        <td>54</td>
		        <td>HTTP</td>
		        <td>Hypertext Transfer Protocol</td>
		        <td>超文本传输协议</td>
		    </tr>
		     <tr>
		        <td>55</td>
		        <td>HTTPS</td>
		        <td>Hypertext Transfer Protocol Secure</td>
		        <td>安全HTTP</td>
		    </tr>
		    <tr>
		        <td>56</td>
		        <td>SMTP</td>
		        <td>Simple Mail Transfer Protocol</td>
		        <td>简单邮件传输协议</td>
		    </tr>
		    <tr>
		        <td>56</td>
		        <td>SDK</td>
		        <td>Software Development Kit</td>
		        <td>软件开发包</td>
		    </tr>
		    <tr>
		        <td>57</td>
		        <td>IDE</td>
		        <td>Integrated Development Environment</td>
		        <td>集成开发环境</td>
		    </tr>
		    <tr>
		        <td>58</td>
		        <td>Adapter</td>
		        <td>Adapter</td>
		        <td>适配器,用于增强服务的功能,提供额外的服务</td>
		    </tr>
		    <tr>
		        <td>59</td>
		        <td>Connector</td>
		        <td>Connector</td>
		        <td>连接器,用于本地连接/同步数据到其他服务</td>
		    </tr>
		     <tr>
		        <td>60</td>
		        <td>HTTPHeader</td>
		        <td>HTTPHeader</td>
		        <td>HTTP请求头</td>
		    </tr>
		    <tr>
		        <td>61</td>
		        <td>JIT</td>
		        <td>Just-in-Time</td>
		        <td>实时/即时</td>
		    </tr>
		    <tr>
		        <td>62</td>
		        <td>JWT</td>
		        <td>JSON Web Token</td>
		        <td>基于Web传送的签名令牌</td>
		    </tr>
		    
		    
		    
		    
	    </tbody>
    </table>
    
 </div>
 <!-- content end -->
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
