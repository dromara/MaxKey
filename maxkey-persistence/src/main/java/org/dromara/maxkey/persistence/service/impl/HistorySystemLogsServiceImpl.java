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
 

package org.dromara.maxkey.persistence.service.impl;

import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ChangePassword;

import java.util.Date;

import org.dromara.maxkey.entity.Access;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.history.HistorySystemLogs;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Permission;
import org.dromara.maxkey.entity.permissions.Resources;
import org.dromara.maxkey.entity.permissions.RoleMember;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.mapper.HistorySystemLogsMapper;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HistorySystemLogsServiceImpl  extends JpaServiceImpl<HistorySystemLogsMapper,HistorySystemLogs> implements HistorySystemLogsService{
	static final Logger _logger = LoggerFactory.getLogger(HistorySystemLogsServiceImpl.class);

	public void insert(String topic,Object entity,String action,String result,UserInfo operator) {
		String message = "";
		if(entity != null) {
			if(entity instanceof UserInfo userInfo) {
				message = buildMsg(userInfo);
			}else if(entity instanceof Organizations organization) {
				message = buildMsg(organization);
			}else if(entity instanceof ChangePassword changePassword) {
				message = buildMsg(changePassword);
			}else if(entity instanceof Accounts account) {
				message = buildMsg(account);
			}else if(entity instanceof Roles role) {
				message = buildMsg(role);
			}else if(entity instanceof RoleMember roleMember) {
				message = buildMsg(roleMember);
			}else if(entity instanceof Access access) {
				message = buildMsg(access);
			}else if(entity instanceof Resources resource) {
				message = buildMsg(resource);
			}else if(entity instanceof Synchronizers synchronizer) {
				message = buildMsg(synchronizer);
			}else if(entity instanceof SocialsProvider socialsProvider) {
				message = buildMsg(socialsProvider);
			}else if(entity instanceof Permission permission) {
				message = buildMsg(permission);
			}else if(entity instanceof String) {
				message = entity.toString();
			}
			
		}
		
		insert(topic,message,action,result,operator, entity);
	}
	
	public void insert(String topic,String message,String action,String result,UserInfo operator,Object entity) {
		HistorySystemLogs systemLog = new HistorySystemLogs();
		systemLog.setId(systemLog.generateId());
		systemLog.setTopic(topic);
		systemLog.setMessage(message);
		systemLog.setMessageAction(action);
		systemLog.setMessageResult(result);
		systemLog.setUserId(operator.getId());
		systemLog.setUsername(operator.getUsername());
		systemLog.setDisplayName(operator.getDisplayName());
		systemLog.setInstId(operator.getInstId());
		systemLog.setJsonCotent(JsonUtils.gsonToString(entity));
		systemLog.setExecuteTime(new Date());
		_logger.trace("System Log {}" ,systemLog);
		getMapper().insert(systemLog);
	}
	
	public String buildMsg(UserInfo userInfo) {
		return new StringBuilder()
				.append(userInfo.getDisplayName())
				.append("[")
				.append(userInfo.getUsername())
				.append("]")
				.toString();
	}
	
	public String buildMsg(Organizations org) {
		return new StringBuilder()
				.append(org.getOrgName())
				.append("[")
				.append(org.getOrgCode())
				.append("]")
				.toString();
	}
	
	public String buildMsg(Accounts account) {
		return new StringBuilder()
				.append(account.getRelatedUsername())
				.append("[")
				.append(account.getDisplayName()).append(",")
				.append(account.getUsername()).append(",")
				.append(account.getAppName())
				.append("]")
				.toString();
	}
	
	public String buildMsg(ChangePassword changePassword) {
		return new StringBuilder()
				.append(changePassword.getDisplayName())
				.append("[")
				.append(changePassword.getUsername())
				.append("]")
				.toString();
	}
	
	public String buildMsg(Roles g) {
		return new StringBuilder()
				.append(g.getRoleName())
				.toString();
	}
	
	public String buildMsg(RoleMember rm) {
		return new StringBuilder()
				.append(rm.getRoleName())
				.append("[")
				.append(rm.getUsername()).append(",")
				.append(rm.getDisplayName())
				.append("]")
				.toString();
	}
	
	public String buildMsg(Access permission) {
		return new StringBuilder()
				.append(permission.getGroupName())
				.append("[")
				.append(permission.getAppName())
				.append("]")
				.toString();
	}
	
	public String buildMsg(Permission privilege) {
		return new StringBuilder()
				.append(privilege.getGroupId())
				.append("[")
				.append(privilege.getResourceId())
				.append("]")
				.toString();
	}
	
	
	public String buildMsg(Resources r) {
		return new StringBuilder()
				.append(r.getResourceName())
				.append("[")
				.append(r.getResourceType())
				.append("]")
				.toString();
	}
	
	
	public String buildMsg(Synchronizers s) {
		return new StringBuilder()
				.append(s.getName())
				.append("[")
				.append(s.getSourceType()).append(",")
				.append(s.getScheduler()).append(",")
				.append("]")
				.toString();
	}
	
	public String buildMsg(SocialsProvider s) {
		return new StringBuilder()
				.append(s.getProviderName())
				.append("[")
				.append(s.getProvider())
				.append("]")
				.toString();
	}
	
}
