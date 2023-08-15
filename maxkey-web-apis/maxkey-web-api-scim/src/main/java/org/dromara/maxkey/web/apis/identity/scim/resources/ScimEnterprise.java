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
 

package org.dromara.maxkey.web.apis.identity.scim.resources;

import java.io.Serializable;

public class ScimEnterprise implements Serializable {
    private static final long serialVersionUID = -204619629148409697L;
    
    private  String employeeNumber;
    private  String costCenter;
    private  String organization;
    private  String division;
    private  String departmentId;
    private  String department;
    private  ScimManager manager;
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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
    public ScimManager getManager() {
        return manager;
    }
    public void setManager(ScimManager manager) {
        this.manager = manager;
    }
    public ScimEnterprise() {
    }
    
    
}
