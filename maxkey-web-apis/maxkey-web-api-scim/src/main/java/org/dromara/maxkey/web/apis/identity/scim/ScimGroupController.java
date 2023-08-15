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
 

package org.dromara.maxkey.web.apis.identity.scim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.dromara.maxkey.entity.Roles;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.RoleMemberService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.util.StringUtils;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimGroup;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMemberRef;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMeta;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimParameters;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/idm/SCIM/v2/Groups")
public class ScimGroupController {
	final static Logger _logger = LoggerFactory.getLogger(ScimGroupController.class);
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	RoleMemberService roleMemberService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue get(@PathVariable String id,
                                   @RequestParam(required = false) String attributes) {
    	_logger.debug("ScimGroup id {} , attributes {}", id , attributes);
    	Roles role    = rolesService.get(id);
    	ScimGroup  scimGroup = role2ScimGroup(role);
    	List<UserInfo>  userList = roleMemberService.queryMemberByRoleId(id);
    	if(userList != null && userList.size() > 0) {
    		Set<ScimMemberRef> members = new HashSet<ScimMemberRef>();
    		for (UserInfo user : userList) {
    			members.add(new ScimMemberRef(user.getDisplayName(),user.getId()));
    		}
    		scimGroup.setMembers(members);
    	}
        return new MappingJacksonValue(scimGroup);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MappingJacksonValue create(@RequestBody  ScimGroup scimGroup,
                                      @RequestParam(required = false) String attributes,
                                      UriComponentsBuilder builder) throws IOException {
    	_logger.debug("ScimGroup content {} , attributes {}", scimGroup , attributes);
    	Roles  role =scimGroup2Role(scimGroup);
    	rolesService.insert(role);
        return get(role.getId(),attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MappingJacksonValue  replace(@PathVariable String id,
                                        @RequestBody ScimGroup scimGroup,
                                        @RequestParam(required = false) String attributes)
                                        		throws IOException {
    	_logger.debug("ScimGroup content {} , attributes {}", scimGroup , attributes);
    	Roles  role =scimGroup2Role(scimGroup);
    	rolesService.update(role);
        return get(role.getId(),attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("ScimGroup id {} " , id);
    	rolesService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public MappingJacksonValue searchWithGet(@ModelAttribute ScimParameters requestParameters) {
        return searchWithPost(requestParameters);
    }

    @RequestMapping(value = "/.search", method = RequestMethod.POST)
    public MappingJacksonValue searchWithPost(@ModelAttribute ScimParameters requestParameters) {
    	requestParameters.parse();
    	_logger.debug("requestParameters {} ",requestParameters);
    	Roles queryModel = new Roles();
    	queryModel.setPageSize(requestParameters.getCount());
    	queryModel.calculate(requestParameters.getStartIndex()); 
        
        JpaPageResults<Roles> orgResults = rolesService.queryPageResults(queryModel);
        List<ScimGroup> resultList = new ArrayList<ScimGroup>();
        for(Roles group : orgResults.getRows()) {
        	resultList.add(role2ScimGroup(group));
        }
        ScimSearchResult<ScimGroup> scimSearchResult = 
        		new ScimSearchResult<ScimGroup>(
        				resultList,
        				orgResults.getRecords(),
        				queryModel.getPageSize(),
        				requestParameters.getStartIndex());  
        return new MappingJacksonValue(scimSearchResult);
    }
    
    public ScimGroup role2ScimGroup(Roles group) {
    	ScimGroup scimGroup = new ScimGroup();
    	scimGroup.setId(group.getId());
    	scimGroup.setExternalId(group.getId());
    	scimGroup.setDisplayName(group.getRoleName());
    	
    	ScimMeta meta = new ScimMeta("Group");
        if(StringUtils.isNotBlank(group.getCreatedDate())){
        	meta.setCreated(
        			DateUtils.parse(group.getCreatedDate(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
        }
        if(StringUtils.isNotBlank(group.getModifiedDate())){
        	meta.setLastModified(
        			DateUtils.parse(group.getModifiedDate(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
        }
        scimGroup.setMeta(meta);
        
    	return scimGroup;
    }
    
    public Roles scimGroup2Role(ScimGroup scimGroup) {
    	Roles role = new Roles();
    	role.setId(scimGroup.getId());
    	role.setRoleName(scimGroup.getDisplayName());
    	return role;
    }
}
