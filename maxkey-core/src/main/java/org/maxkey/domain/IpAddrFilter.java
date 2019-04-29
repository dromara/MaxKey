package org.maxkey.domain;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;

public class IpAddrFilter extends JpaBaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2308650344165845812L;
	
	
	public static class FILTER{
		public static  int WHITELIST=1;
		public static  int BLACKLIST=2;
		
	}
	
	String id;
	private String ipAddr;
	
	private int filter;
	private String description;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getFilter() {
		return filter;
	}
	public void setFilter(int filter) {
		this.filter = filter;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "IpAddrFilter [ipAddr=" + ipAddr + ", filter=" + filter + "]";
	}
	
	
	
}
