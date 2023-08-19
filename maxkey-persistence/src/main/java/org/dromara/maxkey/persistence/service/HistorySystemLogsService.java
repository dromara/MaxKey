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
 

package org.dromara.maxkey.persistence.service;

import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.HistorySystemLogs;
import org.dromara.maxkey.entity.Organizations;
import org.dromara.maxkey.entity.Resources;
import org.dromara.maxkey.entity.RoleMember;
import org.dromara.maxkey.entity.GroupPermissions;
import org.dromara.maxkey.entity.GroupPrivileges;
import org.dromara.maxkey.entity.Roles;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.mapper.HistorySystemLogsMapper;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.mybatis.jpa.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HistorySystemLogsService  extends JpaService<HistorySystemLogs>{
	final static Logger _logger = LoggerFactory.getLogger(HistorySystemLogsService.class);
	
	public HistorySystemLogsService() {
		super(HistorySystemLogsMapper.class);
		
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public HistorySystemLogsMapper getMapper() {
		return (HistorySystemLogsMapper)super.getMapper();
	}
	
	public void insert(String topic,Object entity,String action,String result,UserInfo operator) {
		String message = "";
		if(entity != null) {
			if(entity instanceof UserInfo) {
				message = buildMsg((UserInfo)entity);
			}else if(entity instanceof Organizations) {
				message = buildMsg((Organizations)entity);
			}else if(entity instanceof ChangePassword) {
				message = buildMsg((ChangePassword)entity);
			}else if(entity instanceof Accounts) {
				message = buildMsg((Accounts)entity);
			}else if(entity instanceof Roles) {
				message = buildMsg((Roles)entity);
			}else if(entity instanceof RoleMember) {
				message = buildMsg((RoleMember)entity);
			}else if(entity instanceof GroupPermissions) {
				message = buildMsg((GroupPermissions)entity);
			}else if(entity instanceof Resources) {
				message = buildMsg((Resources)entity);
			}else if(entity instanceof Synchronizers) {
				message = buildMsg((Synchronizers)entity);
			}else if(entity instanceof SocialsProvider) {
				message = buildMsg((SocialsProvider)entity);
			}else if(entity instanceof GroupPrivileges) {
				message = buildMsg((GroupPrivileges)entity);
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
	
	public String buildMsg(GroupPermissions permission) {
		return new StringBuilder()
				.append(permission.getGroupName())
				.append("[")
				.append(permission.getAppName())
				.append("]")
				.toString();
	}
	
	public String buildMsg(GroupPrivileges privilege) {
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
