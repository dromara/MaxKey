package org.maxkey.web.ipregion;

public interface IpRegion {
	public static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36";
	
	public static final int  TIMEOUT 		= 5000;
	
	public String region(String ipAddress);
	
	public String getLocation(String region);
	
	public int getFailCount();
	
	public int plusFailCount() ;
	
}
