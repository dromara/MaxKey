package com.connsec.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class MailTest {
	
	//@Test
	public void test() throws Exception {
	String username="test@connsec.com";
	String password="3&8Ujbnm5hkjhFD";
	String smtpHost="smtp.exmail.qq.com";
	int port=465;
	boolean ssl=true;
	String senderMail="test@connsec.com";
	
	Email email = new SimpleEmail();
	email.setHostName(smtpHost);
	email.setSmtpPort(port);
	email.setAuthenticator(new DefaultAuthenticator(username, password));
	email.setSSLOnConnect(ssl);
	email.setFrom(senderMail);
	email.setSubject("One Time PassWord");
	email.setMsg("You Token is "+111+" , it validity in "+5 +" minutes");
	email.addTo("shimingxy@qq.com");
	email.send();
	}
}
