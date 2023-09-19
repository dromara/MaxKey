/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.plugin.maxkey.service;

import com.google.gson.annotations.SerializedName;

public class MaxkeyUser {

    private String userId;

    private String name;

    private String displayName;

    private String department;

    private String departmentId;

    private String gender;

    private String phoneNumber;

    private String email;

    private String region;

    private Address address;

    /**
     * Gets userId.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     *
     * @param userId the userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Gets clientId.
     *
     * @return the clientId
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets displayName.
     *
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets displayName.
     *
     * @param displayName the displayName
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets department.
     *
     * @param department the department
     */
    public void setDepartment(final String department) {
        this.department = department;
    }

    /**
     * Gets departmentId.
     *
     * @return the departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets departmentId.
     *
     * @param departmentId the departmentId
     */
    public void setDepartmentId(final String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(final String gender) {
        this.gender = gender;
    }

    /**
     * Gets phoneNumber.
     *
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets gender.
     *
     * @param phoneNumber the phoneNumber
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets region.
     *
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets region.
     *
     * @param region the region
     */
    public void setRegion(final String region) {
        this.region = region;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(final Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MaxkeyUser{"
                + "userId='" + userId + '\''
                + ", name='" + name + '\''
                + ", displayName='" + displayName + '\''
                + ", department='" + department + '\''
                + ", departmentId='" + departmentId + '\''
                + ", gender='" + gender + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", email='" + email + '\''
                + ", region='" + region + '\''
                + ", address=" + address.toString()
                + '}';
    }

    public static class Address {

        private String country;

        @SerializedName("street_address")
        private String streetAddress;
        
        private String formatted;
        
        private String locality;
        
        private String region;

        @SerializedName("postal_code")
        private String postalCode;

        /**
         * Gets country.
         *
         * @return the country
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets streetAddress.
         *
         * @param country the streetAddress
         */
        public void setCountry(final String country) {
            this.country = country;
        }

        /**
         * Gets Street_address.
         *
         * @return the streetAddress
         */
        public String getStreetAddress() {
            return streetAddress;
        }

        /**
         * Sets streetAddress.
         *
         * @param streetAddress the streetAddress
         */
        public void setStreetAddress(final String streetAddress) {
            this.streetAddress = streetAddress;
        }

        /**
         * Gets formatted.
         *
         * @return the formatted
         */
        public String getFormatted() {
            return formatted;
        }

        /**
         * Sets formatted.
         *
         * @param formatted the formatted
         */
        public void setFormatted(final String formatted) {
            this.formatted = formatted;
        }

        /**
         * Gets formatted.
         *
         * @return the formatted
         */
        public String getLocality() {
            return locality;
        }

        /**
         * Sets locality.
         *
         * @param locality the locality
         */
        public void setLocality(final String locality) {
            this.locality = locality;
        }

        /**
         * Gets region.
         *
         * @return the region
         */
        public String getRegion() {
            return region;
        }

        /**
         * Sets region.
         *
         * @param region the region
         */
        public void setRegion(final String region) {
            this.region = region;
        }

        /**
         * Gets postalCode.
         *
         * @return the postalCode
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * Sets postalCode.
         *
         * @param postalCode the postalCode
         */
        public void setPostalCode(final String postalCode) {
            this.postalCode = postalCode;
        }

        @Override
        public String toString() {
            return "Address{" 
                    + "country='" + country + '\''
                    + ", streetAddress='" + streetAddress + '\''
                    + ", formatted='" + formatted + '\''
                    + ", locality='" + locality + '\''
                    + ", region='" + region + '\''
                    + ", postalCode='" + postalCode + '\''
                    + '}';
        }
    }
}
