package org.maxkey.domain.userinfo;

public class ContactEntity {
	private String country;
	private String region;
	private String locality;
	private String streetAddress;
	private String addressFormatted;
	private String email;
	private String phoneNumber;
	private String postalCode;
	private String fax;
	/**
	 * 
	 */
	public ContactEntity() {
		
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
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getAddressFormatted() {
		return addressFormatted;
	}
	public void setAddressFormatted(String addressFormatted) {
		this.addressFormatted = addressFormatted;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Override
	public String toString() {
		return "Contact [country=" + country + ", region=" + region
				+ ", locality=" + locality + ", streetAddress="
				+ streetAddress + ", addressFormatted=" + addressFormatted
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", postalCode=" + postalCode + ", fax=" + fax + "]";
	}	
		
		
	}