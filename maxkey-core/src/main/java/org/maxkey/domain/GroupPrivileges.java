package org.maxkey.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.maxkey.domain.apps.Applications;


/*
   ID                   varchar(40)                    not null,
   ROLEID               varchar(40)                    null,
   MENUID                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
public class GroupPrivileges extends Applications implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8634166407201007340L;
	@NotEmpty
	private String groupId;
	@NotEmpty
	private String appId;
	
	public GroupPrivileges(){
		super();
	}
	
	
	/**
	 * @param groupId
	 * @param appId
	 */
	public GroupPrivileges(String groupId, String appId) {
		super();
		this.groupId = groupId;
		this.appId = appId;
	}


	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupApp [groupId=" + groupId + ", appId=" + appId + "]";
	}
	

}
