package org.maxkey.domain.userinfo;

public class ImsEntity{
	private String aim;
	private String gtalk;
	private String icq;
	private String xmpp;
	private String skype;
	private String qq;
	private String yahoo;
	private String sinaweibo;
	private String weixin;
	
	/**
	 * 
	 */
	public ImsEntity() {
		
	}
	public String getAim() {
		return aim;
	}
	public void setAim(String aim) {
		this.aim = aim;
	}
	public String getGtalk() {
		return gtalk;
	}
	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}
	public String getIcq() {
		return icq;
	}
	public void setIcq(String icq) {
		this.icq = icq;
	}
	public String getXmpp() {
		return xmpp;
	}
	public void setXmpp(String xmpp) {
		this.xmpp = xmpp;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getYahoo() {
		return yahoo;
	}
	public void setYahoo(String yahoo) {
		this.yahoo = yahoo;
	}
	public String getSinaweibo() {
		return sinaweibo;
	}
	public void setSinaweibo(String sinaweibo) {
		this.sinaweibo = sinaweibo;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	@Override
	public String toString() {
		return "Ims [aim=" + aim + ", gtalk=" + gtalk + ", icq=" + icq
				+ ", xmpp=" + xmpp + ", skype=" + skype + ", qq=" + qq
				+ ", yahoo=" + yahoo + ", sinaweibo=" + sinaweibo
				+ ", weixin=" + weixin + "]";
	}	
}
