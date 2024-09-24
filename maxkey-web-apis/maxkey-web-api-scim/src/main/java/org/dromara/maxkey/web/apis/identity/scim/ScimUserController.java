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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimEnterprise;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimFormattedName;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimGroupRef;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimManager;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMeta;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimParameters;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimSearchResult;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimUser;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimUserEmail;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimUserPhoneNumber;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimOrganizationEmail.UserEmailType;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimOrganizationPhoneNumber.UserPhoneNumberType;
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

/**
 * This Controller is used to manage User
 * <p>
 * http://tools.ietf.org/html/draft-ietf-scim-core-schema-00#section-6
 * <p>
 * it is based on the SCIM 2.0 API Specification:
 * <p>
 * http://tools.ietf.org/html/draft-ietf-scim-api-00#section-3
 */
@RestController
@RequestMapping(value = "/api/idm/SCIM/v2/Users")
public class ScimUserController {
	static final  Logger _logger = LoggerFactory.getLogger(ScimUserController.class);
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	GroupsService groupsService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue get(@PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("ScimUser id {} , attributes {}", id , attributes);
        UserInfo userInfo = userInfoService.get(id);
        ScimUser scimUser = userInfo2ScimUser(userInfo);
        return new MappingJacksonValue(scimUser);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MappingJacksonValue create(@RequestBody  ScimUser user,
                                      @RequestParam(required = false) String attributes,
                                      UriComponentsBuilder builder) throws IOException {
    	_logger.debug("ScimUser {} , attributes {}", user , attributes);
    	UserInfo userInfo = scimUser2UserInfo(user);
    	userInfoService.insert(userInfo);
        return get(userInfo.getId(),attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MappingJacksonValue replace(@PathVariable String id,
                                       @RequestBody ScimUser user,
                                       @RequestParam(required = false) String attributes)
            throws IOException {
    	_logger.debug("ScimUser {} , attributes {}", user , attributes);
    	UserInfo userInfo = scimUser2UserInfo(user);
    	userInfoService.update(userInfo);
        return get(id,attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("ScimUser id {} ", id );
    	userInfoService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public MappingJacksonValue searchWithGet(@ModelAttribute ScimParameters requestParameters) {
        return searchWithPost(requestParameters);
    }

    @RequestMapping(value = "/.search", method = RequestMethod.POST)
    public MappingJacksonValue searchWithPost(@ModelAttribute ScimParameters requestParameters) {
    	requestParameters.parse();
    	_logger.debug("requestParameters {} ",requestParameters);
    	UserInfo queryModel = new UserInfo();
    	queryModel.setPageSize(requestParameters.getCount());
    	queryModel.calculate(requestParameters.getStartIndex()); 
        
        JpaPageResults<UserInfo> orgResults = userInfoService.fetchPageResults(queryModel);
        List<ScimUser> resultList = new ArrayList<ScimUser>();
        for(UserInfo user : orgResults.getRows()) {
        	resultList.add(userInfo2ScimUser(user));
        }
        ScimSearchResult<ScimUser> scimSearchResult = 
        		new ScimSearchResult<ScimUser>(
        				resultList,
        				orgResults.getRecords(),
        				queryModel.getPageSize(),
        				requestParameters.getStartIndex());  
        return new MappingJacksonValue(scimSearchResult);
    }
    
    public ScimUser userInfo2ScimUser(UserInfo userInfo) {
    	ScimUser scimUser =new ScimUser();
    	scimUser.setId(userInfo.getId());
    	scimUser.setExternalId(userInfo.getId());
    	scimUser.setDisplayName(userInfo.getDisplayName());
    	scimUser.setUserName(userInfo.getUsername());
    	scimUser.setName(new ScimFormattedName(
    									userInfo.getFormattedName(),
    									userInfo.getFamilyName(),
    									userInfo.getGivenName(),
    									userInfo.getMiddleName(),
    									userInfo.getHonorificPrefix(),
    									userInfo.getHonorificSuffix()
    						)
    					);
    	scimUser.setNickName(userInfo.getNickName());
    	scimUser.setTitle(userInfo.getJobTitle());
    	scimUser.setUserType(userInfo.getUserType());
    	
    	ScimEnterprise enterprise = new ScimEnterprise();
    	enterprise.setDepartmentId(userInfo.getDepartmentId());
    	enterprise.setDepartment(userInfo.getDepartment());
    	enterprise.setCostCenter(userInfo.getCostCenter());
    	enterprise.setManager(new ScimManager(userInfo.getManagerId(),userInfo.getManager()));
    	enterprise.setDivision(userInfo.getDivision());
    	enterprise.setEmployeeNumber(userInfo.getEmployeeNumber());
    	scimUser.setEnterprise(enterprise);
    	
    	List<String> organizationsList=new  ArrayList<String>(); 
    	organizationsList.add(userInfo.getDepartmentId());
    	scimUser.setOrganization(organizationsList);
    	
    	List<String> groupsList=new  ArrayList<String>(); 
    	List<ScimGroupRef> groups = new  ArrayList<ScimGroupRef>(); 
    	for(Groups group : groupsService.queryByUserId(userInfo.getId())){
    		groupsList.add(group.getId());
    		groups.add(new ScimGroupRef(group.getId(),group.getGroupName()));
    		
    	}
    	scimUser.setGroup(groupsList);
    	scimUser.setGroups(groups);
    	
    	scimUser.setTimezone(userInfo.getTimeZone());
    	scimUser.setLocale(userInfo.getLocale());
    	scimUser.setPreferredLanguage(userInfo.getPreferredLanguage());
    	scimUser.setActive(userInfo.getStatus() == ConstsStatus.ACTIVE);
    	
    	List<ScimUserEmail> emails = new ArrayList<ScimUserEmail>(); 
    	if(StringUtils.isNotBlank(userInfo.getEmail())){
    		emails.add(new ScimUserEmail(userInfo.getEmail(),UserEmailType.OTHER,true));
    	}
    	if(StringUtils.isNotBlank(userInfo.getWorkEmail())){
    		emails.add(new ScimUserEmail(userInfo.getEmail(),UserEmailType.WORK,false));
    	}
    	if(StringUtils.isNotBlank(userInfo.getHomeEmail())){
    		emails.add(new ScimUserEmail(userInfo.getEmail(),UserEmailType.HOME,false));
    	}
    	
    	if(emails.size() > 0) {
    		scimUser.setEmails(emails);
    	}
    	
    	List<ScimUserPhoneNumber> phoneNumbers = new ArrayList<ScimUserPhoneNumber>(); 
    	if(StringUtils.isNotBlank(userInfo.getMobile())){
    		phoneNumbers.add(new ScimUserPhoneNumber(userInfo.getMobile(),UserPhoneNumberType.MOBILE,true));
    	}
    	if(StringUtils.isNotBlank(userInfo.getWorkPhoneNumber())){
    		phoneNumbers.add(new ScimUserPhoneNumber(userInfo.getWorkPhoneNumber(),UserPhoneNumberType.WORK,false));
    	}
    	
    	if(StringUtils.isNotBlank(userInfo.getHomePhoneNumber())){
    		phoneNumbers.add(new ScimUserPhoneNumber(userInfo.getHomePhoneNumber(),UserPhoneNumberType.HOME,false));
    	}
    	
    	if(phoneNumbers.size() > 0) {
    		scimUser.setPhoneNumbers(phoneNumbers);
    	}
    	
        ScimMeta meta = new ScimMeta("User");
        if(userInfo.getCreatedDate()!= null){
        	meta.setCreated(userInfo.getCreatedDate());
        }
        if(userInfo.getModifiedDate()!= null){
        	meta.setLastModified(userInfo.getModifiedDate());
        }
        scimUser.setMeta(meta);
    	return scimUser;
    }
    
    public UserInfo scimUser2UserInfo(ScimUser scimUser) {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setId(scimUser.getId());
    	userInfo.setUsername(scimUser.getUserName());
    	return userInfo;
    }
}
