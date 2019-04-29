package org.maxkey.domain.userinfo;

public class EnterpriseEntity {
	private String employeeNumber;
	private String costCenter;
	private String organization;
	private String division;
	private String departmentId;
	private String department;
	private String title;
	private String managerId;
	private String manager;
	private String assistantId;
	private String assistant;
	/**
	 * 
	 */
	public EnterpriseEntity() {
		
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@Override
	public String toString() {
		return "EnterpriseEntity [employeeNumber=" + employeeNumber
				+ ", costCenter=" + costCenter + ", organization="
				+ organization + ", division=" + division
				+ ", departmentId=" + departmentId + ", department="
				+ department + ", title=" + title + ", managerId="
				+ managerId + ", manager=" + manager + ", assistantId="
				+ assistantId + ", assistant=" + assistant + "]";
	}
}
