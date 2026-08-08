package org.dromara.maxkey.entity.dto;

import java.util.List;
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
 
import java.util.Map;

public class DashboardVo {

	String  instId;
    Integer dayCount;
    Integer monthCount;
    Integer newUsers;
    
    Integer onlineUsers;
    Integer activeUsers;
    
    Integer totalUsers;
    Integer totalDepts;
    Integer totalApps;
    Integer totalGroups;
    
    List<Map<String, Object>> reportMonth;
    List<Map<String, Object>> reportDayHour;
    
    List<Map<String, Object>> reportProvince;
    
    List<Map<String, Object>> reportCountry;
    
    List<Map<String, Object>> reportBrowser;
    
    List<Map<String, Object>>reportApp;

    public DashboardVo() {
    	
    }
    
    public DashboardVo(String  instId) {
    	this.instId = instId;
    }
    
	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

	public Integer getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}

	public Integer getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(Integer newUsers) {
		this.newUsers = newUsers;
	}

	public Integer getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(Integer onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public Integer getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(Integer activeUsers) {
		this.activeUsers = activeUsers;
	}

	public Integer getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getTotalDepts() {
		return totalDepts;
	}

	public void setTotalDepts(Integer totalDepts) {
		this.totalDepts = totalDepts;
	}

	public Integer getTotalApps() {
		return totalApps;
	}

	public void setTotalApps(Integer totalApps) {
		this.totalApps = totalApps;
	}

	public Integer getTotalGroups() {
		return totalGroups;
	}

	public void setTotalGroups(Integer totalGroups) {
		this.totalGroups = totalGroups;
	}

	public List<Map<String, Object>> getReportMonth() {
		return reportMonth;
	}

	public void setReportMonth(List<Map<String, Object>> reportMonth) {
		this.reportMonth = reportMonth;
	}

	public List<Map<String, Object>> getReportDayHour() {
		return reportDayHour;
	}

	public void setReportDayHour(List<Map<String, Object>> reportDayHour) {
		this.reportDayHour = reportDayHour;
	}

	public List<Map<String, Object>> getReportProvince() {
		return reportProvince;
	}

	public void setReportProvince(List<Map<String, Object>> reportProvince) {
		this.reportProvince = reportProvince;
	}

	public List<Map<String, Object>> getReportCountry() {
		return reportCountry;
	}

	public void setReportCountry(List<Map<String, Object>> reportCountry) {
		this.reportCountry = reportCountry;
	}

	public List<Map<String, Object>> getReportBrowser() {
		return reportBrowser;
	}

	public void setReportBrowser(List<Map<String, Object>> reportBrowser) {
		this.reportBrowser = reportBrowser;
	}

	public List<Map<String, Object>> getReportApp() {
		return reportApp;
	}

	public void setReportApp(List<Map<String, Object>> reportApp) {
		this.reportApp = reportApp;
	}
    
}
