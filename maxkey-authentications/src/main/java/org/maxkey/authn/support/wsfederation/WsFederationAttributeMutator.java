
package org.maxkey.authn.support.wsfederation;

import java.util.Map;

/**
 * This interface provides a mechanism to alter the SAML attributes before they
 * are added the WsFederationCredentials and returned to Sec
 */
public interface WsFederationAttributeMutator {
    /**
     * modifyAttributes manipulates the attributes before they are assigned to the credential.
     *
     * @param attributes the attribute returned by the IdP.
     */
    void modifyAttributes(Map<String, Object> attributes,String upnSuffix);
}
