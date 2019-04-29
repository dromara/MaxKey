/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.


package com.connsec.saml;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.connsec.saml.binding.KeyStoreCredentialResolverDelegate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test-config.xml"})
public class KeyStoreCredentialResolverDelegateIntTest {

	private final static Logger logger = LoggerFactory
			.getLogger(KeyStoreCredentialResolverDelegateIntTest.class);
	
	private KeyStoreCredentialResolverDelegate keyStoreCredentialResolverDelegate;
	
	
	
	@Autowired
	public void setKeyStoreCredentialResolverDelegate(
			KeyStoreCredentialResolverDelegate keyStoreCredentialResolverDelegate) {
		this.keyStoreCredentialResolverDelegate = keyStoreCredentialResolverDelegate;
	}



	@Test
	public void testResolvePrivateKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("sp");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Iterable<Credential> iterable = keyStoreCredentialResolverDelegate.resolve(criteriaSet);
		
		assertNotNull(iterable);
		
		Iterator<Credential> iterator = iterable.iterator();
		
		if(iterator.hasNext()) {
			Credential credential = iterator.next();
			logger.debug("private credential is {}", credential);
			assertEquals("sp",credential.getEntityId());
			assertNotNull(credential.getPrivateKey());
			assertFalse(iterator.hasNext());
		}
		
		else {
			fail("Iterable was empty");
		}
		

	}
	
	@Test
	public void testResolveSinglePrivateKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("sp");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Credential credential = keyStoreCredentialResolverDelegate.resolveSingle(criteriaSet);
		
		assertNotNull(credential);

		logger.debug("private credential is {}", credential);
		assertEquals("sp",credential.getEntityId());
		assertNotNull(credential.getPrivateKey());
		
		
		logger.debug("entity id is: {} " + credential.getEntityId());
	}
	
	
	
	@Test
	public void testResolvePublicKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("idp");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Iterable<Credential> iterable = keyStoreCredentialResolverDelegate.resolve(criteriaSet);
		
		assertNotNull(iterable);
		
		Iterator<Credential> iterator = iterable.iterator();
		
		if(iterator.hasNext()) {
			Credential credential = iterator.next();
			logger.debug("public credential is {}", credential);
			assertEquals("idp",credential.getEntityId());
			assertNotNull(credential.getPublicKey());
			assertFalse(iterator.hasNext());
		}
		
		else {
			fail("Iterable was empty");
		}
		
	

	}
	
	@Test
	public void testResolveSinglePublicKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("idp");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Credential credential = keyStoreCredentialResolverDelegate.resolveSingle(criteriaSet);
		
		assertNotNull(credential);

		logger.debug("public credential is {}", credential);
		assertEquals("idp",credential.getEntityId());
		assertNotNull(credential.getPublicKey());
		
		logger.debug("entity id is: {} " + credential.getEntityId());
	}
	
	@Test
	public void testResolveUnknownKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("foo");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Iterable<Credential> iterable = keyStoreCredentialResolverDelegate.resolve(criteriaSet);
		
		assertNotNull(iterable);
		
		Iterator<Credential> iterator = iterable.iterator();

		assertFalse(iterator.hasNext());
		
	}
	
	@Test
	public void testResolveSingleUnknownKey() throws Exception {
		
		assertNotNull(keyStoreCredentialResolverDelegate);
		
		EntityIDCriteria criteria = new EntityIDCriteria("foo");
		
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(criteria);
		
		Credential credential = keyStoreCredentialResolverDelegate.resolveSingle(criteriaSet);
		
		assertNull(credential);

	}
}
*/