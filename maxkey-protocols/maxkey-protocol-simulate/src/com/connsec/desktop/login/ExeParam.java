package com.connsec.desktop.login;

/**
 * @author Crystal.Sea
 *
 */
public class ExeParam {

	private String programPath;
	
	//PARAMETER,SIMULATION
	private String parameter;
	
	private String preUsername;
	
	private String usernameType;//PARAMETER,SIMULATION
	
	private String usernameParameter;
	
	private String username;
	
	private String prePassword;
	
	private String passwordType;//PARAMETER,SIMULATION
	
	private String passwordParameter;
	
	private String password;
	
	
	private String preSubmit;
	//Enter or key
	private String submitType;
	
	private String submitKey;

	public ExeParam() {
		
	}
	
	
	public String getProgramPath() {
		return programPath;
	}

	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getPreUsername() {
		return preUsername;
	}

	public void setPreUsername(String preUsername) {
		this.preUsername = preUsername;
	}

	public String getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(String usernameType) {
		this.usernameType = usernameType;
	}

	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPrePassword() {
		return prePassword;
	}

	public void setPrePassword(String prePassword) {
		this.prePassword = prePassword;
	}

	public String getPasswordType() {
		return passwordType;
	}

	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}

	public String getPasswordParameter() {
		return passwordParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPreSubmit() {
		return preSubmit;
	}

	public void setPreSubmit(String preSubmit) {
		this.preSubmit = preSubmit;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public String getSubmitKey() {
		return submitKey;
	}

	public void setSubmitKey(String submitKey) {
		this.submitKey = submitKey;
	}

	@Override
	public String toString() {
		return "ExeParam [programPath=" + programPath + ", parameter="
				+ parameter + ", preUsername=" + preUsername
				+ ", usernameType=" + usernameType + ", usernameParameter="
				+ usernameParameter + ", username=" + username
				+ ", prePassword=" + prePassword + ", passwordType="
				+ passwordType + ", passwordParameter=" + passwordParameter
				+ ", password=" + password + ", preSubmit=" + preSubmit
				+ ", submitType=" + submitType + ", submitKey=" + submitKey
				+ "]";
	}
	
}
