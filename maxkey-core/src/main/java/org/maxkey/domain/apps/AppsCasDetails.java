package org.maxkey.domain.apps;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "APPS_CAS_DETAILS")  
public class AppsCasDetails extends Apps {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4272290765948322084L;
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="uuid")
	private String id;
	@Column
	private String service;
	@Column
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
