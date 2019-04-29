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
*/

package com.connsec.spring;

import static org.junit.Assert.*;

import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.connsec.spring.User;

public class UserTest {

	User user;
	User equivalentUser;
	User notEquivalentUser;
	
	@Before
	public void before() {
		
		user = new User("same", "authenticationResponseIssuingEntityName", "authenticationAssertionIssuingEntityName", "responseID","assertionID", new DateTime(), new DateTime(), new DateTime(), Collections.EMPTY_LIST);
		equivalentUser =  new User("same", "other", "other", "other", "other", new DateTime().minusMinutes(5), new DateTime().minusMinutes(5), new DateTime().minusMinutes(5), Collections.EMPTY_LIST);
		notEquivalentUser =  new User("other", "other", "other", "other", "other", new DateTime().minusMinutes(5), new DateTime().minusMinutes(5), new DateTime().minusMinutes(5), Collections.EMPTY_LIST);

	}
	
	
	@Test
	public void testEquals() throws Exception {
		assertEquals(user, equivalentUser);
	}
	
	@Test
	public void testNotEquals() throws Exception {
		assertFalse( user.equals(notEquivalentUser));

	}
	
	@Test
	public void testHashCode() throws Exception {
		assertTrue(user.hashCode() ==  equivalentUser.hashCode());
		assertFalse(user.hashCode() ==  notEquivalentUser.hashCode());
	}
	

}
