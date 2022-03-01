package org.maxkey.web.ipregion;

public class IpRegionIp360Response {

	int errno;
	String errmsg;
	String data;
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IpRegionIp360Response [errno=");
		builder.append(errno);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	
}
