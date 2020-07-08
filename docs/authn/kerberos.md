<h2>基于Kerberos/SPNEGO/AD自动登录集成</h2>

MaxKey未提供基于Kerberos的认证，但是提供JWT的接入方式，可以通过Kerberos认证完成，然后使用JWT的实现MaxKey的自动登录。

建议参考

1)spring-security-kerberos的实现

https://github.com/spring-projects/spring-security-kerberos

2)SPNEGO 实现

Integrated Windows Authentication and Authorization in Java

http://spnego.sourceforge.net/
