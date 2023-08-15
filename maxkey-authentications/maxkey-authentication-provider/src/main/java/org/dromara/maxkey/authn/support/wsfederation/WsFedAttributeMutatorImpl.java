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

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This will remove the @example.org from the upn local accounts. Other IdP should
 * have the upn un-altered to prevent users collusions in CAS-based applications.
 * 
 */
public class WsFedAttributeMutatorImpl implements WsFederationAttributeMutator {
    private static final Logger _logger = LoggerFactory.getLogger(WsFedAttributeMutatorImpl.class);

    @Override
    public void modifyAttributes(Map<String, Object> attributes,String upnSuffix) {
        if ( attributes.containsKey("upn") ) {
            attributes.put("upn", attributes.get("upn").toString().replace("@"+upnSuffix, ""));
            _logger.debug(String.format("modifyAttributes: upn modified (%s)", attributes.get("upn").toString()));
        }else {
            _logger.warn("modifyAttributes: upn attribute not found");
        }
        
        attributeMapping(attributes, "surname", "LastName");
        attributeMapping(attributes, "givenname", "FirstName");
        attributeMapping(attributes, "Group", "Groups");
        attributeMapping(attributes, "employeeNumber", "UDC_IDENTIFIER");
    }

    private void attributeMapping(Map<String, Object> attributes, String oldName, String newName) {
        if ( attributes.containsKey(oldName) ) {
            _logger.debug(String.format("attributeRemapping: %s -> %s (%s)", oldName, newName, attributes.get(oldName)));
            attributes.put(newName, attributes.get(oldName));
            attributes.remove(oldName);
        } else { 
            _logger.debug(String.format("attributeRemapping: attribute not found (%s)", oldName));
        }
    }
    
}
