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

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * 
 * The principal that is attached the the SAMLAuthenticationToken.
 * 
 * Note that there is no relation to Spring Security's User or UserDetails, except for the code
 * that is used to sort the User's GrantedAuthorities
 * 
 * @author jcox
 *
 */
public class User implements Principal, Serializable {

	private final String name;
	private final String authenticationResponseIssuingEntityName;
	private final String authenticationAssertionIssuingEntityName;
	private final String authenticationResponseID;
	private final String authenticationAssertionID;
	private final DateTime authenticationResponseIssueInstant;
	private final DateTime authenticationAssertionIssueInstant;
	private final DateTime authenticationIssueInstant;
	
	private final Set<GrantedAuthority>  authorities;
	
	public User(String name, String authenticationResponseIssuingEntityName,
			String authenticationAssertionIssuingEntityName,
			String authenticationResponseID, 
			String authenticationAssertionID,
			DateTime authenticationResponseIssueInstant,
			DateTime authenticationAssertionIssueInstant,
			DateTime authenticationIssueInstant,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = name;
		this.authenticationResponseIssuingEntityName = authenticationResponseIssuingEntityName;
		this.authenticationAssertionIssuingEntityName = authenticationAssertionIssuingEntityName;
		this.authenticationResponseID = authenticationResponseID;
		this.authenticationAssertionID = authenticationAssertionID;
		this.authenticationResponseIssueInstant = authenticationResponseIssueInstant;
		this.authenticationAssertionIssueInstant = authenticationAssertionIssueInstant;
		this.authenticationIssueInstant = authenticationIssueInstant;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public String getAuthenticationResponseIssuingEntityName() {
		return authenticationResponseIssuingEntityName;
	}

	public String getAuthenticationAssertionIssuingEntityName() {
		return authenticationAssertionIssuingEntityName;
	}

	public DateTime getAuthenticationResponseIssueInstant() {
		return authenticationResponseIssueInstant;
	}

	public DateTime getAuthenticationAssertionIssueInstant() {
		return authenticationAssertionIssueInstant;
	}

	public DateTime getAuthenticationIssueInstant() {
		return authenticationIssueInstant;
	}

	public String getAuthenticationResponseID() {
		return authenticationResponseID;
	}

	public String getAuthenticationAssertionID() {
		return authenticationAssertionID;
	}

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

	@Override
	public String getName() {
		return name;
	}

	/** 
	 * Returns true if this object's name is equal to the name of the passed in arg.
	 * 
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		  
		if (obj == null) { return false; }
		  if (obj == this) { return true; }
		  
		  if (obj.getClass() != getClass()) {
		     return false;
		   }
		   User rhs = (User) obj;
		   return new EqualsBuilder()
		                .append(name, rhs.name)
		                .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(517, 43).
	       append(name).toHashCode();
	       
	}

	@Override
	public String toString() {
		return "User [name=" + name
				+ ", authenticationResponseIssuingEntityName="
				+ authenticationResponseIssuingEntityName
				+ ", authenticationAssertionIssuingEntityName="
				+ authenticationAssertionIssuingEntityName
				+ ", authenticationResponseID=" + authenticationResponseID
				+ ", authenticationAssertionID=" + authenticationAssertionID
				+ ", authenticationResponseIssueInstant="
				+ authenticationResponseIssueInstant
				+ ", authenticationAssertionIssueInstant="
				+ authenticationAssertionIssueInstant
				+ ", authenticationIssueInstant=" + authenticationIssueInstant
				+ ", authorities=" + authorities + "]";
	}
	
	//Taken From Spring Security's User impl
	  private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
	        SortedSet<GrantedAuthority> sortedAuthorities =
	            new TreeSet<GrantedAuthority>(new AuthorityComparator());

	        for (GrantedAuthority grantedAuthority : authorities) {
	            sortedAuthorities.add(grantedAuthority);
	        }

	        return sortedAuthorities;
	    }

		//Taken From Spring Security's User impl
	    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
	        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
	       
	            if (g2.getAuthority() == null) {
	                return -1;
	            }

	            if (g1.getAuthority() == null) {
	                return 1;
	            }

	            return g1.getAuthority().compareTo(g2.getAuthority());
	        }
	    }
	
}
