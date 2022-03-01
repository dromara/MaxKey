package org.maxkey.web.ipregion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpRegionIp138Response {

	String ct;
	String prov;
	String city;
	String area;
	String yunyin;
	
	public IpRegionIp138Response() {
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getYunyin() {
		return yunyin;
	}
	public void setYunyin(String yunyin) {
		this.yunyin = yunyin;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ct)
			   .append(prov)
			   .append(city)
			   .append(area)
			   .append(" ")
			   .append(yunyin);
		return builder.toString();
	}
	
	
}
