package com.connsec.saml;

import org.opensaml.saml2.core.Response;
import org.springframework.security.core.AuthenticationException;

import com.connsec.spring.User;

public interface AssertionConsumer {

	User consume(Response samlResponse) throws AuthenticationException;

}
