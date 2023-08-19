/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.entity;

import java.io.Serializable;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_USERINFO_ADJUNCT")
public class UserInfoAdjoint extends JpaEntity  implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8634054312223379561L;

    @Id
    @Column
    @GeneratedValue
    String id;
    
    protected String displayName;
    @Column
    protected String userId;
    
 // for work
    @Column
    protected String workCountry;
    @Column
    protected String workRegion;// province;
    @Column
    protected String workLocality;// city;
    @Column
    protected String workStreetAddress;
    @Column
    protected String workAddressFormatted;
    @Column
    protected String workEmail;
    @Column
    protected String workPhoneNumber;
    @Column
    protected String workPostalCode;
    @Column
    protected String workFax;
    
    @Column
    protected String costCenter;
    @Column
    protected String organization;
    @Column
    protected String division;
    @Column
    protected String departmentId;
    @Column
    protected String department;
    @Column
    protected String jobTitle;
    @Column
    protected String jobLevel;
    @Column
    protected String managerId;
    @Column
    protected String manager;
    @Column
    protected String assistantId;
    @Column
    protected String assistant;
    @Column
    protected String entryDate;
    @Column
    protected String quitDate;
    
	@Column
	private String instId;

	private String instName;
	
    public UserInfoAdjoint() {
        super();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getWorkCountry() {
        return workCountry;
    }
    public void setWorkCountry(String workCountry) {
        this.workCountry = workCountry;
    }
    public String getWorkRegion() {
        return workRegion;
    }
    public void setWorkRegion(String workRegion) {
        this.workRegion = workRegion;
    }
    public String getWorkLocality() {
        return workLocality;
    }
    public void setWorkLocality(String workLocality) {
        this.workLocality = workLocality;
    }
    public String getWorkStreetAddress() {
        return workStreetAddress;
    }
    public void setWorkStreetAddress(String workStreetAddress) {
        this.workStreetAddress = workStreetAddress;
    }
    public String getWorkAddressFormatted() {
        return workAddressFormatted;
    }
    public void setWorkAddressFormatted(String workAddressFormatted) {
        this.workAddressFormatted = workAddressFormatted;
    }
    public String getWorkEmail() {
        return workEmail;
    }
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }
    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }
    public String getWorkPostalCode() {
        return workPostalCode;
    }
    public void setWorkPostalCode(String workPostalCode) {
        this.workPostalCode = workPostalCode;
    }
    public String getWorkFax() {
        return workFax;
    }
    public void setWorkFax(String workFax) {
        this.workFax = workFax;
    }
    public String getCostCenter() {
        return costCenter;
    }
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getJobLevel() {
        return jobLevel;
    }
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }
    public String getManagerId() {
        return managerId;
    }
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    public String getManager() {
        return manager;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }
    public String getAssistantId() {
        return assistantId;
    }
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }
    public String getAssistant() {
        return assistant;
    }
    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }
    public String getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    public String getQuitDate() {
        return quitDate;
    }
    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }
    
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInfoAdjoint [id=");
        builder.append(id);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", workCountry=");
        builder.append(workCountry);
        builder.append(", workRegion=");
        builder.append(workRegion);
        builder.append(", workLocality=");
        builder.append(workLocality);
        builder.append(", workStreetAddress=");
        builder.append(workStreetAddress);
        builder.append(", workAddressFormatted=");
        builder.append(workAddressFormatted);
        builder.append(", workEmail=");
        builder.append(workEmail);
        builder.append(", workPhoneNumber=");
        builder.append(workPhoneNumber);
        builder.append(", workPostalCode=");
        builder.append(workPostalCode);
        builder.append(", workFax=");
        builder.append(workFax);
        builder.append(", costCenter=");
        builder.append(costCenter);
        builder.append(", organization=");
        builder.append(organization);
        builder.append(", division=");
        builder.append(division);
        builder.append(", departmentId=");
        builder.append(departmentId);
        builder.append(", department=");
        builder.append(department);
        builder.append(", jobTitle=");
        builder.append(jobTitle);
        builder.append(", jobLevel=");
        builder.append(jobLevel);
        builder.append(", managerId=");
        builder.append(managerId);
        builder.append(", manager=");
        builder.append(manager);
        builder.append(", assistantId=");
        builder.append(assistantId);
        builder.append(", assistant=");
        builder.append(assistant);
        builder.append(", entryDate=");
        builder.append(entryDate);
        builder.append(", quitDate=");
        builder.append(quitDate);
        builder.append("]");
        return builder.toString();
    }
    
    
}
