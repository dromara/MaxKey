/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "MXK_INSTITUTIONS")
public class Institutions extends JpaEntity implements Serializable {
	private static final long serialVersionUID = -2375872012431214098L;

	@Id
	@Column
	@GeneratedValue
	private String id;
	@Column
	private String name;
	@Column
	private String fullName;
	@Column
	private String division;
	@Column
	private String country;
	@Column
	private String region;
	@Column
	private String locality;
	@Column
	private String street;
	@Column
	private String address;
	@Column
	private String contact;
	@Column
	private String postalCode;
	@Column
	private String phone;
	@Column
	private String fax;
	@Column
	private String email;

	@Column
	private String description;


	@Column
	private String logo;
	@Column
	private String domain;
	@Column
	private String frontTitle;
	@Column
	private String consoleDomain;
	@Column
	private String consoleTitle;
	
	@Column
	private String defaultUri;


	public Institutions() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getFrontTitle() {
		return frontTitle;
	}

	public void setFrontTitle(String frontTitle) {
		this.frontTitle = frontTitle;
	}

	public String getConsoleDomain() {
		return consoleDomain;
	}

	public void setConsoleDomain(String consoleDomain) {
		this.consoleDomain = consoleDomain;
	}

	public String getConsoleTitle() {
		return consoleTitle;
	}

	public void setConsoleTitle(String consoleTitle) {
		this.consoleTitle = consoleTitle;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultUri() {
		return defaultUri;
	}

	public void setDefaultUri(String defaultUri) {
		this.defaultUri = defaultUri;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Institutions [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", division=");
		builder.append(division);
		builder.append(", country=");
		builder.append(country);
		builder.append(", region=");
		builder.append(region);
		builder.append(", locality=");
		builder.append(locality);
		builder.append(", street=");
		builder.append(street);
		builder.append(", address=");
		builder.append(address);
		builder.append(", contact=");
		builder.append(contact);
		builder.append(", postalCode=");
		builder.append(postalCode);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", fax=");
		builder.append(fax);
		builder.append(", email=");
		builder.append(email);
		builder.append(", description=");
		builder.append(description);
		builder.append(", logo=");
		builder.append(logo);
		builder.append(", domain=");
		builder.append(domain);
		builder.append(", frontTitle=");
		builder.append(frontTitle);
		builder.append(", consoleDomain=");
		builder.append(consoleDomain);
		builder.append(", consoleTitle=");
		builder.append(consoleTitle);
		builder.append(", defaultUri=");
		builder.append(defaultUri);
		builder.append("]");
		return builder.toString();
	}

}
