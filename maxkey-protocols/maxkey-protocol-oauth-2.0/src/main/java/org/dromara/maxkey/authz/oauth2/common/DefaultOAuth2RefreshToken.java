/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.authz.oauth2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

/**
 * An OAuth 2 refresh token.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
public class DefaultOAuth2RefreshToken implements Serializable, OAuth2RefreshToken {

    private static final long serialVersionUID = 8349970621900575838L;

    private String value;

    /**
     * Create a new refresh token.
     */
    @JsonCreator
    public DefaultOAuth2RefreshToken(String value) {
        this.value = value;
    }
    
    /**
     * Default constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private DefaultOAuth2RefreshToken() {
        this(null);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.oauth2.common.IFOO#getValue()
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultOAuth2RefreshToken)) {
            return false;
        }

        DefaultOAuth2RefreshToken that = (DefaultOAuth2RefreshToken) o;

        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
