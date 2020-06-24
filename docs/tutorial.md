<h2>介绍</h2>
为了你更好的使用MaxKey，本教程介绍在Windows中如何快速配置和使用MaxKey，在开始本文档前，请先<a href="https://shimingxy.github.io/MaxKey/download.html" target="_blank">下载MaxKey</a>并解压到C:盘。

<h2>配置</h2>
hosts配置文件目录
<pre class="prettyprint">
C:\Windows\System32\drivers\etc
</pre>
新增如下内容
<pre><code class="ini hljs">
127.0.0.1  sso.maxkey.top
127.0.0.1  tokenbased.demo.maxkey.top
127.0.0.1  cas.demo.maxkey.top
127.0.0.1  oauth.demo.maxkey.top
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
				<td>1</td><td>认证平台</td><td><a href="http://sso.maxkey.top/maxkey/login" target="blank">http://sso.maxkey.top/maxkey/login</a></td>
			</tr>
			<tr>
				<td>2</td><td>管理平台</td><td><a href="http://sso.maxkey.top:9521/maxkey-mgt/login" target="blank">http://sso.maxkey.top:9521/maxkey-mgt/login</a></td>
			</tr>
			<tr>
				<td>3</td><td>集成指南</td><td><a href="http://sso.maxkey.top:8080/wiki" target="blank">http://sso.maxkey.top:8080/wiki</a></td>
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

<h2>LINUX版本</h2>

1 OpenJDK 14 安装

1.1 下载地址
http://jdk.java.net/archive/

OpenJDK 14
14 GA (build 14+36)

    Linux	64-bit	tar.gz (sha256) 190M
	
<pre><code class="bash hljs">	
wget --no-check-certificate --no-cookies https://download.java.net/java/GA/jdk14/076bab302c7b4508975440c56f6cc26a/36/GPL/openjdk-14_linux-x64_bin.tar.gz
</code></pre>
 
1.2 解压缩

<pre><code class="bash hljs">
tar -zxvf openjdk-14_linux-x64_bin.tar.gz
</code></pre>

2.安装MySQL5.6

<pre><code class="bash hljs">
rpm -ivh http://repo.mysql.com/yum/mysql-5.5-community/el/6/x86_64/mysql-community-release-el6-5.noarch.rpm
 </code></pre>
 
要安装MySQL5.6的可以安装：

<pre><code class="bash hljs">
rpm -ivh http://repo.mysql.com/mysql-community-release-el6.rpm
 </code></pre>
 
最新的yum源可以去http://dev.mysql.com/downloads/repo/yum下载


2.1修改安装好的yum源

编辑 /etc/yum.repos.d/mysql-community.repo文件，5.6的enabled改为1,其他改未0

<pre><code class="bash hljs">
# Enable to use MySQL 5.6
[mysql56-community]
name=MySQL 5.6 Community Server
baseurl=http://repo.mysql.com/yum/mysql-5.6-community/el/6/$basearch/
enabled=1
gpgcheck=1
gpgkey=file:/etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
</code></pre>
 
2.2.安装mysql-5.6

<pre><code class="bash hljs">
yum install mysql-community-client mysql-community-devel mysql-community-server php-mysql
 </code></pre>
 
2.3 调整配置

编辑 /etc/my.cnf 文件

<pre><code class="bash hljs">
character-set-server=utf8
lower_case_table_names=0
</code></pre>
 
2.4. 启动mysql服务

    > service mysqld start
	
    #或者下面这个
	
    >/etc/init.d/mysqld start
	
	停止
	
	service mysqld stop  --无需执行
	
	
2.5 设置密码	

<pre><code class="bash hljs">
/usr/bin/mysqladmin -u root password maxkey
</code></pre>
 
2.6 设置访问权限

<pre><code class="bash hljs">
mysql -u root -pmaxkey;

use mysql;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'maxkey' WITH GRANT OPTION;

flush privileges ;
</code></pre>
 
2.7. 设置开机启动

<pre><code class="bash hljs">
chkconfig --add mysqld
chkconfig mysqld on
</code></pre>

查看开机启动设置是否成功

<pre><code class="bash hljs">
 chkconfig --list | grep mysql*
 
 # mysqld 0:关闭 1:关闭 2:启用 3:启用 4:启用 5:启用 6:关闭停止
</code></pre>


3 MaxKey安装

3.1 把MaxKey上传到Linux服务器

3.2 数据导入

使用客户端工具导出windows版本的MaxKey的数据及表SQL,登陆LINUX MYSQL并创建schema maxkey,数据导入到maxkey schema中。

3.3 启动
<pre><code class="bash hljs">
  ./start_maxkey_db.sh &
  
  ./start_maxkey.sh &
  
  ./start_maxkey_mgt.sh &
  
  ./start_maxkey_wiki.sh &
</code></pre>