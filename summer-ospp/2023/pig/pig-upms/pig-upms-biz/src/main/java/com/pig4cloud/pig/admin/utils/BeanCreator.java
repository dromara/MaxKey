package com.pig4cloud.pig.admin.utils;

import com.pig4cloud.pig.admin.api.entity.SysUser;

import java.util.Map;

public class BeanCreator {

	private static final String DEFAULT_PASSWORD = "pigmax123456";

	public static SysUser createSysUserByMap(Map<String, Object> map) {
		SysUser sysUser = new SysUser();
		String username = (String) map.get("username");
		String phone = (String) map.get("mobile");
		String deptId = (String) map.get("departmentId");
		sysUser.setUsername(username);
		sysUser.setPhone(phone);
		sysUser.setDeptId(Long.parseLong(deptId));
		sysUser.setPassword(DEFAULT_PASSWORD);
		return sysUser;
	}

}
