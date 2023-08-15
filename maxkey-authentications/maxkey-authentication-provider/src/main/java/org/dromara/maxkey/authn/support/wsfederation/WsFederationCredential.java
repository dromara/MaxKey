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
 


package org.dromara.maxkey.authn.support.wsfederation;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * This class represents the basic elements of the WsFederation token.
 *
 */
public final class WsFederationCredential {
    private final Logger _logger = LoggerFactory.getLogger(WsFederationCredential.class);

    private String audience;
    private String authenticationMethod;
    private String id;
    private String issuer;
    private DateTime issuedOn;
    private DateTime notBefore;
    private DateTime notOnOrAfter;
    private DateTime retrievedOn;
    private Map<String, Object> attributes;

    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    public void setAuthenticationMethod(final String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getAudience() {
        return this.audience;
    }

    public void setAudience(final String audience) {
        this.audience = audience;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public DateTime getIssuedOn() {
        return this.issuedOn;
    }

    public void setIssuedOn(final DateTime issuedOn) {
        this.issuedOn = issuedOn;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    public DateTime getNotBefore() {
        return this.notBefore;
    }

    public void setNotBefore(final DateTime notBefore) {
        this.notBefore = notBefore;
    }

    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    public void setNotOnOrAfter(final DateTime notOnOrAfter) {
        this.notOnOrAfter = notOnOrAfter;
    }

    public DateTime getRetrievedOn() {
        return this.retrievedOn;
    }

    public void setRetrievedOn(final DateTime retrievedOn) {
        this.retrievedOn = retrievedOn;
    }

    /**
     * toString produces a human readable representation of the WsFederationCredential.
     *
     * @return a human readable representation of the WsFederationCredential
     */
    @Override
    public String toString() {
        String attributeList = "";

        for (String attr : this.attributes.keySet()) {
            attributeList += "  " + attr + ": " + (attributes.get(attr)).toString() + "\n";
        }

        final String readable = "ID: %s\nIssuer: %s\nAudience: %s\nAudience Method: %s\nIssued On: %s\n"
                + "Valid After: %s\nValid Before: %s\nAttributes:\n%s";

        return String.format(readable, this.id, this.issuer, this.audience, this.authenticationMethod,
                this.issuedOn.toString(), this.notBefore.toString(), this.notOnOrAfter.toString(), attributeList);
    }

    /**
     * isValid validates the credential.
     *
     * @param expectedAudience the audience that the token was issued to (CAS Server)
     * @param expectedIssuer   the issuer of the token (the IdP)
     * @param timeDrift        the amount of acceptable time drift
     * @return true if the credentials are valid, otherwise false
     */
    public boolean isValid(final String expectedAudience, final String expectedIssuer, final int timeDrift) {
        if (!this.getAudience().equalsIgnoreCase(expectedAudience)) {
           _logger.warn(".isValid: audience is invalid: {}", this.getAudience());
            return false;
        }

        if (!this.getIssuer().equalsIgnoreCase(expectedIssuer)) {
           _logger.warn(".isValid: issuer is invalid: {}", this.getIssuer());
            return false;
        }

        if (this.getIssuedOn().isBefore(this.getRetrievedOn().minusMillis(timeDrift))
                || this.getIssuedOn().isAfter(this.getRetrievedOn().plusMillis(timeDrift))) {
           _logger.warn(".isValid: Ticket outside of drift.");
            return false;
        }

        if (this.getRetrievedOn().isAfter(this.getNotOnOrAfter())) {
           _logger.warn(".isValid: ticket is too late.");
            return false;
        }

       _logger.debug(".isValid: credential is valid.");
        return true;
    }
}
