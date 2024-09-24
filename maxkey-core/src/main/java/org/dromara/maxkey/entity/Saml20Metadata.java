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
 

package org.dromara.maxkey.entity;

import java.io.Serializable;
import org.dromara.mybatis.jpa.entity.JpaEntity;

/**
 * Saml20Metadata.
 * @author Crystal.Sea
 *
 */
public class Saml20Metadata extends JpaEntity implements Serializable {
    private static final long serialVersionUID = -403743150268165622L;
    
    public static  final class ContactPersonType {
        public static  final  String TECHNICAL = "technical";
        public static  final  String SUPPORT = "support";
        public static  final  String ADMINISTRATIVE = "administrative";
        public static final  String BILLING = "billing";
        public static final  String OTHER = "other";
    }

    private String orgName;
    private String orgDisplayName;
    private String orgURL;
    private String contactType;
    private String company;
    private String givenName;
    private String surName;
    private String emailAddress;
    private String telephoneNumber;


    public Saml20Metadata() {
        super();

    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDisplayName() {
        return orgDisplayName;
    }

    public void setOrgDisplayName(String orgDisplayName) {
        this.orgDisplayName = orgDisplayName;
    }

    public String getOrgURL() {
        return orgURL;
    }

    public void setOrgURL(String orgURL) {
        this.orgURL = orgURL;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Saml20Metadata [orgName=");
        builder.append(orgName);
        builder.append(", orgDisplayName=");
        builder.append(orgDisplayName);
        builder.append(", orgURL=");
        builder.append(orgURL);
        builder.append(", contactType=");
        builder.append(contactType);
        builder.append(", company=");
        builder.append(company);
        builder.append(", givenName=");
        builder.append(givenName);
        builder.append(", surName=");
        builder.append(surName);
        builder.append(", emailAddress=");
        builder.append(emailAddress);
        builder.append(", telephoneNumber=");
        builder.append(telephoneNumber);
        builder.append("]");
        return builder.toString();
    }

}
