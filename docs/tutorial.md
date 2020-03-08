<h2>介绍</h2>
为了你更好的使用MaxKey，本教程介绍在Windows中如何快速配置和使用MaxKey，在开始本文档前，请先下载MaxKey并解压到C:盘。

<h2>配置</h2>
hosts配置文件目录
<pre class="prettyprint">
C:\Windows\System32\drivers\etc
</pre>
新增如下内容
<pre><code class="ini hljs">
127.0.0.1  sso.maxkey.org
127.0.0.1  tokenbased.demo.maxkey.org
127.0.0.1  cas.demo.maxkey.org
127.0.0.1  oauth.demo.maxkey.org
</code></pre>

<h2>应用服务启动</h2>
    1)启动数据库
		<pre><code class="bash hljs">
			start_maxkey_db.bat
		</code></pre>
	2)启动认证服务
		<pre><code class="bash hljs">
			start_maxkey.bat
		</code></pre>
	3)启动管理服务
		<pre><code class="bash hljs">
			start_maxkey_mgt.bat
		</code></pre>
	4)启动样例及WIKI
		<pre><code class="bash hljs">
			start_maxkey_wiki.bat
		</code></pre>
	
<h2>访问</h2>
打开浏览器，访问以下地址
<table border="0" class="table table-striped table-bordered ">
		<thead>
			<tr>
				<th>序号</th><th>名称</th><th>访问地址</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td><td>认证平台</td><td><a href="http://sso.maxkey.org/maxkey/login" target="blank">http://sso.maxkey.org/maxkey/login</a></td>
			</tr>
			<tr>
				<td>2</td><td>管理平台</td><td><a href="http://sso.maxkey.org:9521/maxkey-mgt/login" target="blank">http://sso.maxkey.org:9521/maxkey-mgt/login</a></td>
			</tr>
			<tr>
				<td>3</td><td>集成指南</td><td><a href="http://sso.maxkey.org:8080/wiki" target="blank">http://sso.maxkey.org:8080/wiki</a></td>
			</tr>
			<tr>
				<td>4</td><td>账户密码</td><td>admin/admin</td>
			</tr>
		</tbody>
</table>		


<h2>目录结构</h2>
<table border="0" class="table table-striped table-bordered ">
	<thead>
		<tr>
			<th>序号</th><th>目录/文件</th><th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td><td>jdk</td><td>运行时JDK</td>
		</tr>
		<tr>
			<td>2</td><td>license</td><td>许可证</td>
		</tr>
		<tr>
			<td>3</td><td>maxkey</td><td>认证服务</td>
		</tr>
		<tr>
			<td>4</td><td>maxkey_mgt</td><td>管理服务</td>
		</tr>
		<tr>
			<td>5</td><td>maxkey_mysql</td><td>数据库</td>
		</tr>
		<tr>
			<td>6</td><td>maxkey_wiki</td><td>WIKI和样例</td>
		</tr>
		<tr>
			<td>7</td><td>maxkey_lib</td><td>公共包</td>
		</tr>
		<tr>
			<td>8</td><td>start_maxkey.bat</td><td>启动认证服务器</td>
		</tr>
		<tr>
			<td>9</td><td>start_maxkey_mgt.bat</td><td>启动管理服务器</td>
		</tr>
		<tr>
			<td>10</td><td>start_maxkey_db.bat</td><td>启动数据库</td>
		</tr>
		<tr>
			<td>11</td><td>start_maxkey_wiki.bat</td><td>启动WIKI和样例</td>
		</tr>
		<tr>
			<td>12</td><td>set_maxkey_env.bat</td><td>环境设置脚本</td>
		</tr>
	</tbody>
</table>