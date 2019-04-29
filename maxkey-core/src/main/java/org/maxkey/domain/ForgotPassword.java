package org.maxkey.domain;


public class ForgotPassword extends ChangePassword{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218071580331822219L;

	/**
	 * 1 for email
	 * 2 for mobile sms code
	 * 3 for answer question
	 */
	private int type;
	
	private String email;
	
	private String mobile;
	
	private String smsCode;
	
	/**
	 * 
	 */
	public ForgotPassword() {

	}
	
	/**
	 * 
	 */
	public ForgotPassword(String email) {
		this.email=email;
		this.type=1;

	}
	
	public ForgotPassword(String mobile,String smsCode) {
		this.mobile=mobile;
		this.smsCode=smsCode;
		this.type=2;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ForgotPassword [email=" + email + "]";
	}

	
}
