package org.maxkey.domain;

import java.io.Serializable;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;


/**
 * @author Crystal.Sea
 * 
 */
public class Logs extends JpaBaseDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6560201093784960493L;
	String id;
	String serviceName;
	String message;
	String content;
	String messageType;
	String operateType;
	String username;
	String code;
	String createdBy;
	String createdDate;
	String modifiedBy;
	String modifiedDate;
	
	String startDate;
	String endDate;
	
	public Logs() {
		super();
	}

	public Logs(String serviceName, String code, String message,
			String content, String messageType, String operateType,
			String createdBy, String username, String cname) {
		super();
		this.serviceName = serviceName;
		this.code = code;
		this.message = message;
		this.content = content;
		this.messageType = messageType;
		this.operateType = operateType;
		this.createdBy = createdBy;
		this.username = username;

	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



}
