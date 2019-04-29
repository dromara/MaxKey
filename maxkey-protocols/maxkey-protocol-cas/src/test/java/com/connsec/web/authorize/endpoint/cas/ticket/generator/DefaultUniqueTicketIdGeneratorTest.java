package com.connsec.web.authorize.endpoint.cas.ticket.generator;
import org.maxkey.authz.cas.endpoint.ticket.generator.DefaultUniqueTicketIdGenerator;

public class DefaultUniqueTicketIdGeneratorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultUniqueTicketIdGenerator t=new DefaultUniqueTicketIdGenerator();
		System.out.println(t.getNewTicketId("ST"));
	}

}
