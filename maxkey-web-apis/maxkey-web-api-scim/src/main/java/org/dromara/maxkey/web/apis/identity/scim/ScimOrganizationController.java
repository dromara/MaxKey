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
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMeta;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimOrganization;
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

/**
 * This Controller is used to manage Organization
 * <p>
 * http://tools.ietf.org/html/draft-ietf-scim-core-schema-00#section-6
 * <p>
 * it is based on the SCIM 2.0 API Specification:
 * <p>
 * http://tools.ietf.org/html/draft-ietf-scim-api-00#section-3
 */
@RestController
@RequestMapping(value = "/api/idm/SCIM/v2/Organizations")
public class ScimOrganizationController {
	static final  Logger _logger = LoggerFactory.getLogger(ScimOrganizationController.class);
	
	@Autowired
	OrganizationsService organizationsService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue get(@PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("ScimOrganization id {} , attributes {}", id , attributes);
    	Organizations	org = organizationsService.get(id);
    	ScimOrganization 	scimOrg = org2ScimOrg(org);
        
        return new MappingJacksonValue(scimOrg);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MappingJacksonValue create(@RequestBody  ScimOrganization scimOrg,
                                      @RequestParam(required = false) String attributes,
                                      UriComponentsBuilder builder) throws IOException {
    	_logger.debug("ScimOrganization content {} , attributes {}", scimOrg , attributes);
        Organizations createOrg = scimOrg2Org(scimOrg);
        organizationsService.insert(createOrg);
        return get(createOrg.getId(), attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MappingJacksonValue replace(@PathVariable String id,
                                       @RequestBody ScimOrganization scimOrg,
                                       @RequestParam(required = false) String attributes)throws IOException {
    	_logger.debug("ScimOrganization content {} , attributes {}", scimOrg , attributes);
    	Organizations updateOrg = scimOrg2Org(scimOrg);
    	organizationsService.update(updateOrg);
        return get(id, attributes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("ScimOrganization id {}", id );
    	organizationsService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public MappingJacksonValue searchWithGet(@ModelAttribute ScimParameters requestParameters) {
        return searchWithPost(requestParameters);
    }

    @RequestMapping(value = "/.search", method = RequestMethod.POST)
    public MappingJacksonValue searchWithPost(@ModelAttribute ScimParameters requestParameters) {
    	requestParameters.parse();
    	_logger.debug("requestParameters {} ",requestParameters);
        Organizations queryModel = new Organizations();
        queryModel.setPageSize(requestParameters.getCount());
        queryModel.calculate(requestParameters.getStartIndex()); 
        
        JpaPageResults<Organizations> orgResults = organizationsService.fetchPageResults(queryModel);
        List<ScimOrganization> resultList = new ArrayList<ScimOrganization>();
        for(Organizations org : orgResults.getRows()) {
        	resultList.add(org2ScimOrg(org));
        }
        ScimSearchResult<ScimOrganization> scimSearchResult = 
        		new ScimSearchResult<ScimOrganization>(
        				resultList,
        				orgResults.getRecords(),
        				queryModel.getPageSize(),
        				requestParameters.getStartIndex());  
        
        return new MappingJacksonValue(scimSearchResult);
    }
    
    public ScimOrganization org2ScimOrg(Organizations org) {
    	ScimOrganization 	scimOrg = new ScimOrganization();
        scimOrg.setId(org.getId());
        scimOrg.setCode(org.getOrgCode());
        scimOrg.setName(org.getOrgName());
        scimOrg.setDisplayName(org.getOrgName());
        scimOrg.setFullName(org.getFullName());
        scimOrg.setType(org.getType());
        scimOrg.setLevel(org.getLevel());
        scimOrg.setDivision(org.getDivision());
        scimOrg.setSortOrder(org.getSortOrder());
        scimOrg.setCodePath(org.getCodePath());
        scimOrg.setNamePath(org.getNamePath());
        scimOrg.setDescription(org.getDescription());
        
        scimOrg.setParentId(org.getParentId());
        scimOrg.setParent(org.getParentId());
        //scimOrg.setParentCode(org.getParentId());
        scimOrg.setParentName(org.getParentName());
        
        scimOrg.setParentName(org.getParentName());
        if(StringUtils.isNotBlank(org.getSortOrder())) {
        	scimOrg.setOrder(Long.parseLong(org.getSortOrder()));
        }else {
        	scimOrg.setOrder(1);
        }
        scimOrg.setExternalId(org.getId());
        
        ScimMeta meta = new ScimMeta("Organization");
        
        if(org.getCreatedDate()!= null){
        	meta.setCreated(org.getCreatedDate());
        }
        if(org.getModifiedDate()!= null){
        	meta.setLastModified(org.getModifiedDate());
        }
        scimOrg.setMeta(meta);
        return scimOrg;
    }
    
    public Organizations scimOrg2Org(ScimOrganization 	scimOrg) {
    	Organizations org = new Organizations();
    	org.setId(scimOrg.getId());
    	org.setOrgCode(scimOrg.getCode());
    	org.setFullName(scimOrg.getFullName());
    	org.setOrgName(StringUtils.isNotBlank(scimOrg.getName()) ? scimOrg.getName():scimOrg.getDisplayName());
    	org.setParentId(StringUtils.isNotBlank(scimOrg.getParentId())? scimOrg.getParentId():scimOrg.getParent());
    	org.setParentCode(scimOrg.getParentCode());
    	org.setParentName(scimOrg.getParentName());
    	org.setSortOrder(StringUtils.isNotBlank(scimOrg.getSortOrder() )?scimOrg.getSortOrder():scimOrg.getOrder()+"");
    	org.setLevel(scimOrg.getLevel());
    	org.setType(scimOrg.getType());
    	org.setDivision(scimOrg.getDivision());
    	org.setDescription(scimOrg.getDescription());
    	return org;
    }
}
