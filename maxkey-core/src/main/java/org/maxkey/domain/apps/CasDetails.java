package org.maxkey.domain.apps;


public class CasDetails extends Applications {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4272290765948322084L;
	private String service;
	
	private String validation;
	
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the validation
	 */
	public String getValidation() {
		return validation;
	}
	/**
	 * @param validation the validation to set
	 */
	public void setValidation(String validation) {
		this.validation = validation;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CASDetails [service=" + service + ", validation=" + validation
				+ "]";
	}
	
	
	
}
