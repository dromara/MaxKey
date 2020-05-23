package org.maxkey.authn.support.httpheader;

public class HttpHeaderConfig {
	String headerName;
	boolean enable;
	
	
	/**
	 * 
	 */
	public HttpHeaderConfig() {

	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
    public HttpHeaderConfig(String headerName, boolean enable) {
        super();
        this.headerName = headerName;
        this.enable = enable;
    }
	
	
}
