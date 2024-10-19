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

import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.GroupMemberService;
import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimGroup;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMemberRef;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMeta;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimParameters;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimSearchResult;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
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
	static final  Logger _logger = LoggerFactory.getLogger(ScimGroupController.class);
	
	@Autowired
	GroupsService groupsService;
	
	@Autowired
	GroupMemberService groupMemberService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue get(@PathVariable String id,
                                   @RequestParam(required = false) String attributes) {
    	_logger.debug("ScimGroup id {} , attributes {}", id , attributes);
    	Groups group    = groupsService.get(id);
    	ScimGroup  scimGroup = group2ScimGroup(group);
    	List<UserInfo>  userList = groupMemberService.queryMemberByGroupId(id);
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
    	Groups  group =scimGroup2Role(scimGroup);
    	groupsService.insert(group);
        return get(group.getId(),attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MappingJacksonValue  replace(@PathVariable String id,
                                        @RequestBody ScimGroup scimGroup,
                                        @RequestParam(required = false) String attributes)
                                        		throws IOException {
    	_logger.debug("ScimGroup content {} , attributes {}", scimGroup , attributes);
    	Groups  group =scimGroup2Role(scimGroup);
    	groupsService.update(group);
        return get(group.getId(),attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("ScimGroup id {} " , id);
    	groupsService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public MappingJacksonValue searchWithGet(@ModelAttribute ScimParameters requestParameters) {
        return searchWithPost(requestParameters);
    }

    @RequestMapping(value = "/.search", method = RequestMethod.POST)
    public MappingJacksonValue searchWithPost(@ModelAttribute ScimParameters requestParameters) {
    	requestParameters.parse();
    	_logger.debug("requestParameters {} ",requestParameters);
    	Groups queryModel = new Groups();
    	queryModel.setPageSize(requestParameters.getCount());
    	queryModel.calculate(requestParameters.getStartIndex()); 
        
        JpaPageResults<Groups> orgResults = groupsService.fetchPageResults(queryModel);
        List<ScimGroup> resultList = new ArrayList<ScimGroup>();
        for(Groups group : orgResults.getRows()) {
        	resultList.add(group2ScimGroup(group));
        }
        ScimSearchResult<ScimGroup> scimSearchResult = 
        		new ScimSearchResult<ScimGroup>(
        				resultList,
        				orgResults.getRecords(),
        				queryModel.getPageSize(),
        				requestParameters.getStartIndex());  
        return new MappingJacksonValue(scimSearchResult);
    }
    
    public ScimGroup group2ScimGroup(Groups group) {
    	ScimGroup scimGroup = new ScimGroup();
    	scimGroup.setId(group.getId());
    	scimGroup.setExternalId(group.getId());
    	scimGroup.setDisplayName(group.getGroupName());
    	
    	ScimMeta meta = new ScimMeta("Group");
        if(group.getCreatedDate()!= null){
        	meta.setCreated(group.getCreatedDate());
        }
        if(group.getModifiedDate()!= null){
        	meta.setLastModified(group.getModifiedDate());
        }
        scimGroup.setMeta(meta);
        
    	return scimGroup;
    }
    
    public Groups scimGroup2Role(ScimGroup scimGroup) {
    	Groups group = new Groups();
    	group.setId(scimGroup.getId());
    	group.setGroupName(scimGroup.getDisplayName());
    	return group;
    }
}
