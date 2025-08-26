---
title: 项目依赖
---
## 基础软件

<table border="0" class="table table-striped table-bordered ">
    <thead>
        <tr>
            <th>SoftWare</th>
			<th>Version</th>
            <th>Introduction</th>
            <th>Home</th>
            <th>License</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>MySQL Community Server</td>
			<td>8.0.*</td>
            <td>
			MySQL is the most trusted and widely used open source database platform in use today. 10 out of the top 10 most popular and highly-trafficked websites in the world rely on MySQL. 
			</td>
            <td>https://www.mysql.com/</td>
            <td>GNU General Public License v2.0</td>
		</tr>
		<tr>
            <td>PostgreSQL </td>
			<td>13.6.*</td>
            <td>
			PostgreSQL is a powerful, open source object-relational database system with over 30 years of active development that has earned it a strong reputation for reliability, feature robustness, and performance.
			</td>
            <td>https://www.postgresql.org/</td>
            <td>PostgreSQL License</td>
		</tr>
		<tr>
            <td>OpenJDK</td>
			<td>17</td>
            <td>
			The place to collaborate on an open-source implementation of the Java Platform, Standard Edition, and related projects,open-source JDK for most popular Linux distributions. Oracle's free, GPL-licensed, production-ready OpenJDK JDK 14 binaries are at jdk.java.net/14; Oracle's commercially-licensed JDK 14 binaries for Linux, macOS, and Windows, based on the same code
			</td>
            <td>http://openjdk.java.net/</td>
            <td>GNU General Public License v2.0</td>
		</tr>
		<tr>
            <td>Node.js</td>
			<td>Node.js v16.13.1</td>
            <td>
			Node.js® is a JavaScript runtime built on Chrome's V8 JavaScript engine.
			</td>
            <td>https://nodejs.org/</td>
            <td>MIT License</td>
		</tr>
		<tr>
            <td>Tomcat/Tomcat-embed</td>
			<td>9.0.*</td>
            <td>
			The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies. The Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket specifications are developed under the Java Community Process.
			</td>
            <td>https://tomcat.apache.org/</td>
            <td>Apache License 2.0</td>
		</tr>
		<tr>
            <td>nginx</td>
			<td>1.21.*</td>
            <td>
			nginx [engine x] is an HTTP and reverse proxy server, a mail proxy server, and a generic TCP/UDP proxy server, originally written by Igor Sysoev.
			</td>
            <td>http://nginx.org/</td>
            <td>2-clause BSD-like license</td>
		</tr>
		<tr>
            <td>Redis</td>
			<td>6.0.*</td>
            <td>
			Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker. It supports data structures such as strings, hashes, lists, sets, sorted sets with range queries, bitmaps, hyperloglogs, geospatial indexes with radius queries and streams. Redis has built-in replication, Lua scripting, LRU eviction, transactions and different levels of on-disk persistence, and provides high availability via Redis Sentinel and automatic partitioning with Redis Cluster
			</td>
            <td>https://redis.io/</td>
            <td>BSD licensed</td>
		</tr>
		<tr>
            <td>OpenLDAP</td>
			<td>4.4.*</td>
            <td>
			OpenLDAP Software is an open source implementation of the Lightweight Directory Access Protocol.
			</td>
            <td>https://www.openldap.org/</td>
            <td>OpenLDAP Public License</td>
		</tr>
		<tr>
            <td>Apache Kafka</td>
			<td>2.5.0</td>
            <td>
			Kafka® is used for building real-time data pipelines and streaming apps. It is horizontally scalable, fault-tolerant, wicked fast, and runs in production in thousands of companies.
			</td>
            <td>https://kafka.apache.org/</td>
            <td>Apache License 2.0</td>
		</tr>
		<tr>
            <td>Apache RocketMQ</td>
			<td>4.9.*</td>
            <td>
			Apache RocketMQ™ is a unified messaging engine, lightweight data processing platform.
			</td>
            <td>https://rocketmq.apache.org/</td>
            <td>Apache License 2.0</td>
		</tr>
		<tr>
            <td>Gradle</td>
			<td>7.*</td>
            <td>
			Gradle is an open-source build automation tool focused on flexibility and performance. Gradle build scripts are written using a Groovy or Kotlin DSL. Read about Gradle features to learn what is possible with Gradle.
			</td>
            <td>https://gradle.org/</td>
            <td>Apache License 2.0</td>
		</tr>
		<tr>
            <td>Eclipse</td>
			<td>eclipse-jee-2020-06</td>
            <td>
			The Eclipse IDE is famous for our Java Integrated Development Environment (IDE), but we have a number of pretty cool IDEs, including our C/C++ IDE, JavaScript/TypeScript IDE, PHP IDE, and more.
			</td>
            <td>https://www.eclipse.org/</td>
            <td>Eclipse Public License - v 2.0</td>
		</tr>
		<tr>
            <td>Visual Studio Code</td>
			<td>VSCode Version 1.66</td>
            <td>
			Visual Studio Code is a lightweight but powerful source code editor which runs on your desktop and is available for Windows, macOS and Linux
			</td>
            <td>https://code.visualstudio.com/</td>
            <td>MIT license </td>
		</tr>
    </tbody>
</table>

## 项目依赖包

<p>以下是此项目的项目依赖中的编译依赖项列表。这些依赖项可以包含在子模块中，以编译和运行子模块：</p>

<table border="0" class="table table-striped table-bordered ">
    <thead>
        <tr>
            <th>Dependency</th>
            <th>GroupId</th>
            <th>ArtifactId</th>
            <th>Version</th>
            <th>Type</th>
            <th>License</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>test</td>
            <td>junit</td>
            <td>junit</td>
            <td>4.11</td>
            <td>jar</td>
            <td>EPL 1.0</td>
        </tr>
        <tr>
            <td>test</td>
            <td>javax.servlet</td>
            <td>javax.servlet-api</td>
            <td>3.0.1</td>
            <td>jar</td>
            <td>GPL 2.0</td>
        </tr>
        <tr>
            <td>test</td>
            <td>org.mockito</td>
            <td>mockito-all</td>
            <td>1.10.19</td>
            <td>jar</td>
            <td>MIT</td>
        </tr>
        <tr>
            <td>test</td>
            <td>xmlunit</td>
            <td>xmlunit</td>
            <td>1.6</td>
            <td>jar</td>
            <td>BSD</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-beanutils</td>
            <td>commons-beanutils</td>
            <td>1.9.3</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-codec</td>
            <td>commons-codec</td>
            <td>1.15</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-collections</td>
            <td>commons-collections</td>
            <td>3.2.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-collections4</td>
            <td>4.4</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-compress</td>
            <td>1.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>	
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-csv</td>
            <td>1.7</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td>org.apache.commons </td>
            <td>commons-text</td>
            <td>1.9</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-dbcp2</td>
            <td>2.6.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-dbutils</td>
            <td>commons-dbutils</td>
            <td>1.7</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-digester3</td>
            <td>3.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-digester</td>
            <td>commons-digester</td>
            <td>2.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-io</td>
            <td>commons-io</td>
            <td>2.8.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-lang</td>
            <td>commons-lang</td>
            <td>2.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-lang3</td>
            <td>3.11</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-logging</td>
            <td>commons-logging</td>
            <td>1.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-pool2</td>
            <td>2.6.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-httpclient</td>
            <td>commons-httpclient</td>
            <td>3.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>commons-fileupload</td>
            <td>commons-fileupload</td>
            <td>1.4</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>commons-email</td>
            <td>1.5</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.httpcomponents</td>
            <td>httpclient</td>
            <td>4.5.13</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.httpcomponents</td>
            <td>fluent-hc</td>
            <td>4.5.13</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.httpcomponents</td>
            <td>httpclient-cache</td>
            <td>4.5.13</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.httpcomponents</td>
            <td>httpmime</td>
            <td>4.5.13</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.httpcomponents</td>
            <td>httpcore</td>
            <td>4.4.14</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.velocity</td>
            <td>velocity</td>
            <td>1.7</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>velocity</td>
            <td>velocity-dep</td>
            <td>1.4</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.freemarker</td>
            <td>freemarker</td>
            <td>2.3.31</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.commons</td>
            <td>not-yet-commons-ssl</td>
            <td>0.3.9</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td> org.apache.poi</td>
            <td>
				poi              <br />
				poi-scratchpad   <br />
				poi-ooxml        <br />
				poi-ooxml-schemas
				</td>
            <td>4.1.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>	
		<tr>
            <td>compile</td>
            <td> org.apache.xmlbeans</td>
            <td>xmlbeans</td>
            <td>3.0.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>	
        <tr>
            <td>compile</td>
            <td>org.apache.logging.log4j</td>
            <td>
				log4j-1.2-api    <br />
				log4j-api        <br />
				log4j-core       <br />
				log4j-jcl        <br />
				log4j-jul        <br />
				log4j-slf4j-impl <br /> 
				log4j-web
			</td>
            <td>2.17.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.slf4j</td>
            <td>slf4j-api</td>
            <td>1.7.30</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.jboss.logging</td>
            <td>jboss-logging</td>
            <td>3.4.1.Final</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.springframework</td>
            <td>
				spring-aop                  <br />
				spring-aspects              <br />
				spring-beans                <br />
				spring-core                 <br />
				spring-context              <br />
				spring-context-indexer      <br />
				spring-context-support      <br />
				spring-expression           <br />
				spring-instrument           <br />
				spring-jcl                  <br />
				spring-jdbc                 <br />
				spring-jms                  <br />
				spring-messaging            <br />
				spring-orm                  <br />
				spring-oxm                  <br />
				spring-tx                   <br />
				spring-web                  <br />
				spring-webflux              <br />
				spring-webmvc               <br />
				spring-websocket            <br />
				spring-test
			</td>
            <td>5.3.14</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.kafka</td>
            <td>kafka-clients</td>
            <td>2.6.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.springframework.kafka</td>
            <td>spring-kafka</td>
            <td>2.8.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.springframework.retry</td>
            <td>spring-retry</td>
            <td>1.3.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.springframework.security</td>
            <td>
				spring-security-core<br />
				spring-security-web<br />
				spring-security-crypto
			</td>
            <td>5.6.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.springframework.boot</td>
            <td>
				spring-boot<br />
				spring-boot-starter<br />
				spring-boot-actuator<br />
				spring-boot-autoconfigure<br />
				spring-boot-starter-freemarker<br />
				spring-boot-starter-log4j2<br />
				spring-boot-starter-web<br />
				spring-boot-starter-tomcat<br />
				spring-boot-starter-test
			</td>
            <td>2.6.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
		</tr>
		<tr>
            <td>compile</td>
            <td>org.springframework.security</td>
            <td>spring-security-oauth</td>
            <td>2.5.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
		</tr>	
		<tr>
            <td>compile</td>
            <td>org.springframework.data</td>
            <td>
				spring-data-commons<br />
				spring-data-keyvalue<br />
				spring-data-redis
				</td>
            <td>5.6.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>	
		<tr>
            <td>compile</td>
            <td>org.springframework.plugin</td>
            <td>spring-plugin-core</td>
            <td>2.0.0.RELEASE</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>		
		<tr>
            <td>compile</td>
            <td>org.springframework.plugin</td>
            <td>spring-plugin-metadata</td>
            <td>2.0.0.RELEASE</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td>org.springframework.session</td>
            <td>spring-session-core</td>
            <td>5.6.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
	    <tr>
            <td>compile</td>
            <td>org.springframework.session</td>
            <td>spring-session-data-redis</td>
            <td>5.6.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.opensaml</td>
            <td>opensaml</td>
            <td>2.6.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.opensaml</td>
            <td>openws</td>
            <td>1.5.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.opensaml</td>
            <td>xmltooling</td>
            <td>1.4.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
		<tr>
            <td>compile</td>
            <td>net.shibboleth.utilities</td>
            <td>java-support</td>
            <td>7.5.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>	
        <tr>
            <td>compile</td>
            <td>com.nimbusds</td>
            <td>nimbus-jose-jwt</td>
            <td>9.4.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>net.jcip</td>
            <td>jcip-annotations</td>
            <td>1.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>net.minidev</td>
            <td>json-smart</td>
            <td>2.3</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>net.minidev</td>
            <td>asm</td>
            <td>1.0.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.xkcoding.http</td>
            <td>simple-http</td>
            <td>1.0.3</td>
            <td>jar</td>
            <td>LGPL 3.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>me.zhyd.oauth</td>
            <td>JustAuth</td>
            <td>1.15.9</td>
            <td>jar</td>
            <td>MIT</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.javassist</td>
            <td>javassist</td>
            <td>3.23.0-GA</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.owasp.esapi</td>
            <td>esapi</td>
            <td>2.2.0.0</td>
            <td>jar</td>
            <td>BSD 2-clause</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.sun.mail</td>
            <td>javax.mail</td>
            <td>1.6.2</td>
            <td>jar</td>
            <td>GPL 2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.eclipse.persistence</td>
            <td>javax.persistence</td>
            <td>2.2.1</td>
            <td>jar</td>
            <td>Eclipse Public License v. 2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.activation</td>
            <td>activation</td>
            <td>1.1.1</td>
            <td>jar</td>
            <td>CDDL 1.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.annotation</td>
            <td>javax.annotation-api</td>
            <td>1.3.2</td>
            <td>jar</td>
            <td>GPLv2</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.transaction</td>
            <td>jta</td>
            <td>1.1</td>
            <td>jar</td>
            <td>GPLv2</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.transaction</td>
            <td>javax.transaction-api</td>
            <td>1.3</td>
            <td>jar</td>
            <td>GPLv2</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.validation</td>
            <td>validation-api</td>
            <td>2.0.1.Final</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.xml</td>
            <td>jsr173</td>
            <td>1.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>javax.xml.bind</td>
            <td>jaxb-api</td>
            <td>2.3.1</td>
            <td>jar</td>
            <td>CDDL 1.1</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.sun.xml.bind</td>
            <td>jaxb-core</td>
            <td>2.3.0.1</td>
            <td>jar</td>
            <td>Eclipse Distribution License - v 1.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.sun.xml.bind</td>
            <td>jaxb-impl</td>
            <td>2.3.2</td>
            <td>jar</td>
            <td>Eclipse Distribution License - v 1.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.sun.xml.bind</td>
            <td>jaxb-xjc</td>
            <td>2.3.2</td>
            <td>jar</td>
            <td>Eclipse Distribution License - v 1.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.bouncycastle</td>
            <td>bcpkix-jdk15on</td>
            <td>1.64</td>
            <td>jar</td>
            <td>Bouncy Castle Licence</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.bouncycastle</td>
            <td>bcprov-jdk15on</td>
            <td>1.64</td>
            <td>jar</td>
            <td>Bouncy Castle Licence</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.bouncycastle</td>
            <td>bcprov-ext-jdk15on</td>
            <td>1.64</td>
            <td>jar</td>
            <td>Bouncy Castle Licence</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.google.crypto.tink</td>
            <td>tink</td>
            <td>1.4.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.jhlabs</td>
            <td>filters</td>
            <td>2.0.235-1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.github.penggle</td>
            <td>kaptcha</td>
            <td>2.3.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.google.code.gson</td>
            <td>gson</td>
            <td>2.8.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.fasterxml.jackson.core</td>
            <td>
				jackson-annotations <br />
				jackson-core        <br />
				jackson-databind
			</td>
            <td>2.12.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.fasterxml</td>
            <td>classmate</td>
            <td>1.5.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>com.alibaba</td>
            <td>fastjson</td>
            <td>1.2.74</td>
            <td>jar</td>
            <td>Apache-2.0</td>
        </tr>
        <tr>
            <td>compile</td>
            <td>org.reactivestreams</td>
            <td>reactive-streams</td>
            <td>1.0.2</td>
            <td>jar</td>
            <td>CC0 1.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>io.projectreactor</td>
            <td>reactor-core</td>
            <td>3.2.10.RELEASE</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>eu.tekul</td>
            <td>szxcvbn_2.9.2</td>
            <td>0.2</td>
            <td>jar</td>
            <td>MIT</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.quartz-scheduler</td>
            <td>quartz</td>
            <td>2.3.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>mysql</td>
            <td>mysql-connector-java</td>
            <td>8.0.26</td>
            <td>jar</td>
            <td>GPL 2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>org.postgresql</td>
            <td>postgresql</td>
            <td>42.2.20</td>
            <td>jar</td>
            <td>BSD-2-Clause</td>
            </tr>	
        <tr>
            <td>compile</td>
            <td>com.alibaba</td>
            <td>druid</td>
            <td>1.2.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>com.alibaba</td>
            <td>druid-spring-boot-starter</td>
            <td>1.2.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>redis.clients</td>
            <td>jedis</td>
            <td>3.7.1</td>
            <td>jar</td>
            <td>MIT</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.ehcache</td>
            <td>ehcache</td>
            <td>3.9.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.mybatis</td>
            <td>mybatis</td>
            <td>3.5.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.mybatis</td>
            <td>mybatis-spring</td>
            <td>2.0.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.hibernate.validator</td>
            <td>hibernate-validator</td>
            <td>6.2.0.Final</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.hibernate</td>
            <td>hibernate-validator-cdi</td>
            <td>6.2.0.Final</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.hibernate.validator</td>
            <td>hibernate-validator-annotation-processor</td>
            <td>6.2.0.Final</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>joda-time</td>
            <td>joda-time</td>
            <td>2.10.9</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.yaml</td>
            <td>snakeyaml</td>
            <td>1.26</td>
            <td>1.26</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>net.sourceforge.nekohtml</td>
            <td>nekohtml</td>
            <td>1.9.22</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.jdom</td>
            <td>jdom</td>
            <td>2.0.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>com.google.zxing</td>
            <td>core</td>
            <td>3.4.1</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>com.google.guava</td>
            <td>guava</td>
            <td>31.0.1-jre</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>ognl</td>
            <td>ognl</td>
            <td>3.2.14</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>cglib</td>
            <td>cglib</td>
            <td>3.3.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
		</tr>
		<tr>
            <td>compile</td>
            <td>org.lionsoul</td>
            <td>ip2region</td>
            <td>1.7.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
		</tr>
        <tr>
            <td>compile</td>
            <td>org.ow2.asm</td>
            <td>asm</td>
            <td>7.3.1</td>
            <td>jar</td>
            <td>BSD 3-clause</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>aopalliance</td>
            <td>aopalliance</td>
            <td>1.0</td>
            <td>jar</td>
            <td>Public</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.aspectj</td>
            <td>aspectjtools</td>
            <td>1.9.4</td>
            <td>jar</td>
            <td>EPL 1.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>dom4j</td>
            <td>dom4j</td>
            <td>1.6.1</td>
            <td>jar</td>
            <td>BSD</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>xalan</td>
            <td>serializer</td>
            <td>2.7.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>xml-resolver</td>
            <td>xml-resolver</td>
            <td>1.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.santuario</td>
            <td>xmlsec</td>
            <td>1.5.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.ogce</td>
            <td>xpp3</td>
            <td>1.1.6</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>com.thoughtworks.xstream</td>
            <td>xstream</td>
            <td>1.4.10</td>
            <td>jar</td>
            <td>BSD 3-clause</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>org.passay</td>
            <td>passay</td>
            <td>1.6.0</td>
            <td>jar</td>
            <td>Apache 2.0 &amp; LGPL 3.0</td>
            </tr>	
		<tr>
            <td>compile</td>
            <td>io.micrometer</td>
            <td> micrometer-core</td>
            <td>1.6.4</td>
            <td>jar</td>
            <td>Apache 2.0 </td>
            </tr>	
		<tr>
            <td>compile</td>
            <td>org.latencyutils</td>
            <td> LatencyUtils</td>
            <td>2.0.3</td>
            <td>jar</td>
            <td>CC0 1.0 </td>
            </tr>	
		<tr>
            <td>compile</td>
            <td>org.codehaus.woodstox</td>
            <td> stax2-api</td>
            <td>4.2.1</td>
            <td>jar</td>
            <td>	BSD 2-clause </td>
            </tr>	
		<tr>
            <td>compile</td>
            <td>org.mapstruct</td>
            <td> mapstruct</td>
            <td>1.4.1.Final</td>
            <td>jar</td>
            <td>Apache 2.0 </td>
            </tr>	
        <tr>
            <td>compile</td>
            <td>com.aliyun</td>
            <td>aliyun-java-sdk-core</td>
            <td>4.5.1</td>
            <td>jar</td>
            <td>Aliyun </td>
            </tr>
        <tr>
            <td>compile</td>
            <td>com.tencentcloudapi</td>
            <td>tencentcloud-sdk-java</td>
            <td>3.1.33</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.tomcat.embed</td>
            <td>tomcat-embed-core</td>
            <td>9.0.46</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
        <tr>
            <td>compile</td>
            <td>org.apache.tomcat.embed</td>
            <td>tomcat-embed-logging-juli</td>
            <td>8.5.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		 <tr>
            <td>compile</td>
            <td>mybatis-jpa-extra</td>
            <td>mybatis-jpa-extra</td>
            <td>2.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>mybatis-jpa-extra</td>
            <td>mybatis-jpa-extra-spring-boot-starter</td>
            <td>2.8</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>com.highgo</td>
            <td>HgdbJdbc</td>
            <td>2.3</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>gradle.plugin.com.google.cloud.tools</td>
            <td>jib-gradle-plugin</td>
            <td>3.1.4</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>io.swagger</td>
            <td>swagger-annotations<br />
				swagger-models</td>
            <td>1.6.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>
		<tr>
            <td>compile</td>
            <td>io.swagger.core.v3</td>
            <td>
				swagger-annotations<br />
				swagger-core<br />
				swagger-integration<br />
				swagger-models<br />
			</td>
            <td>2.1.11</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>	
		<tr>
            <td>compile</td>
            <td>io.springfox</td>
            <td>
				springfox-bean-validators<br />
				springfox-core<br />
				springfox-data-rest<br />
				springfox-spi<br />
				springfox-oas<br />
				springfox-schema<br />
				springfox-swagger2<br />
				springfox-swagger-ui<br />
				springfox-swagger-common<br />
				springfox-spring-webmvc<br />
				springfox-spring-web<br />
				springfox-spring-webflux<br />
				springfox-boot-starter
			</td>
            <td>3.0.0</td>
            <td>jar</td>
            <td>Apache-2.0</td>
            </tr>		
		<tr>
            <td>compile</td>
            <td>com.github.xiaoymin</td>
            <td>
				knife4j-annotations<br />
				knife4j-core<br />
				knife4j-spring-mvc<br />
				knife4j-spring<br />
				knife4j-spring-ui<br />
				knife4j-spring-boot-starter<br />
				knife4j-spring-boot-autoconfigure
			</td>
            <td>3.0.2</td>
            <td>jar</td>
            <td>Apache-2.0</td>
		</tr>	
    </tbody>
</table>

## 前端组件依赖

<table border="0" class="table table-striped table-bordered ">
    <thead>
        <tr>
            <th>Dependency</th>
            <th>Component</th>
            <th>Version</th>
            <th>Type</th>
            <th>License</th>
            </tr>
    </thead>
    <tbody>
		<tr>
            <td>compile</td>
            <td>Angular</td>
            <td>13.3.3</td>
            <td>typescript</td>
            <td>MIT-style License</td>
		</tr>
		<tr>
            <td>compile</td>
            <td>Ant Design of Angular(NG-ZORRO)</td>
            <td>v13.1.1</td>
            <td>typescript</td>
            <td>MIT</td>
		</tr>	
		<tr>
            <td>compile</td>
            <td>NG ALAIN</td>
            <td>v13.4.2</td>
            <td>typescript</td>
            <td>MIT</td>
		</tr>	
		<tr>
            <td>compile</td>
            <td>ng-ant-admin</td>
            <td>v12.2+</td>
            <td>typescript</td>
            <td>MIT License</td>
		</tr>	
		<tr>
            <td>compile</td>
            <td>CryptoJS</td>
            <td>4.1.1</td>
            <td>javascript</td>
            <td>MIT License</td>
		</tr>
    </tbody>
</table>
