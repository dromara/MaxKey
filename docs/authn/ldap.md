<h2>LDAP登录集成</h2>
MaxKey支持LDAP包括Active Directory。


<h3>注释默认数据库认证</h3>

打开文件maxkey/spring/maxkey-security.xml,首先注释默认数据库认证方式

<pre><code class="xml hljs">
&lt;!-- Default Realm--&gt;
&lt;!-- realm use jdbc --&gt;
&lt;bean id="authenticationRealm" class="org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm"&gt;
	&lt;constructor-arg ref="jdbcTemplate"/&gt;
&lt;/bean&gt;
</code></pre>

<h3>LDAP支持</h3>

<pre><code class="xml hljs">
&lt;!-- LDAP Realm --&gt;
&lt;bean id="authenticationRealm" class="org.maxkey.web.authentication.realm.ldap.LdapAuthenticationRealm"&gt;
	&lt;constructor-arg ref="jdbcTemplate"/&gt;
	&lt;property name="ldapServers"&gt;
		&lt;list&gt;
			&lt;bean id="ldapServer1" class="org.maxkey.web.authentication.realm.ldap.LdapServer"&gt;
				&lt;property name="ldapUtils"&gt;
					&lt;bean id="ldapUtils" class="org.maxkey.ldap.LdapUtils"&gt;
						&lt;property name="providerUrl" value="ldap://localhost:389"&gt;&lt;/property&gt;
						&lt;property name="principal" value="cn=root"&gt;&lt;/property&gt;
						&lt;property name="credentials" value="rootroot"&gt;&lt;/property&gt;
						&lt;property name="baseDN" value="dc=connsec,dc=com"&gt;&lt;/property&gt;
					&lt;/bean&gt;
				&lt;/property&gt;
				&lt;property name="filterAttribute" value="uid"&gt;&lt;/property&gt;
			&lt;/bean&gt;	
		&lt;/list&gt;
	&lt;/property&gt;
&lt;/bean&gt; 
</code></pre>

<h3>Active Directory支持</h3>

<pre><code class="xml hljs">
&lt;!-- Active Directory  Realm --&gt;
&lt;bean id="authenticationRealm" class="org.maxkey.web.authentication.realm.activedirectory.ActiveDirectoryAuthenticationRealm"&gt;
	&lt;constructor-arg ref="jdbcTemplate"/&gt;
	&lt;property name="activeDirectoryServers"&gt;
		&lt;list&gt;
			&lt;bean id="activeDirectory1" class="org.maxkey.web.authentication.realm.activedirectory.ActiveDirectoryServer"&gt;
				&lt;property name="activeDirectoryUtils"&gt;
					&lt;bean id="ldapUtils" class="org.maxkey.ldap.ActiveDirectoryUtils"&gt;
						&lt;property name="providerUrl" value="ldap://localhost:389"&gt;&lt;/property&gt;
						&lt;property name="principal" value="cn=root"&gt;&lt;/property&gt;
						&lt;property name="credentials" value="rootroot"&gt;&lt;/property&gt;
						&lt;property name="domain" value="connsec"&gt;&lt;/property&gt;
					&lt;/bean&gt;
				&lt;/property&gt;
			&lt;/bean&gt;	
		&lt;/list&gt;
	&lt;/property&gt;
&lt;/bean&gt; 
</code></pre>

