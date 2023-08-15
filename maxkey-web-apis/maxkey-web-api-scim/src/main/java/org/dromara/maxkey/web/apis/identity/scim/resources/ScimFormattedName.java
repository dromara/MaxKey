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
 

package org.dromara.maxkey.web.apis.identity.scim.resources;

import java.io.Serializable;

public class ScimFormattedName  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2482724471550531523L;
    private  String formatted;
    private  String familyName;
    private  String givenName;
    private  String middleName;
    private  String honorificPrefix;
    private  String honorificSuffix;
    public String getFormatted() {
        return formatted;
    }
    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getGivenName() {
        return givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getHonorificPrefix() {
        return honorificPrefix;
    }
    public void setHonorificPrefix(String honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }
    public String getHonorificSuffix() {
        return honorificSuffix;
    }
    public void setHonorificSuffix(String honorificSuffix) {
        this.honorificSuffix = honorificSuffix;
    }
    public ScimFormattedName(String formatted, String familyName, String givenName, String middleName, String honorificPrefix,
            String honorificSuffix) {
        super();
        this.formatted = formatted;
        this.familyName = familyName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.honorificPrefix = honorificPrefix;
        this.honorificSuffix = honorificSuffix;
    }
    public ScimFormattedName() {
    }

    
}
