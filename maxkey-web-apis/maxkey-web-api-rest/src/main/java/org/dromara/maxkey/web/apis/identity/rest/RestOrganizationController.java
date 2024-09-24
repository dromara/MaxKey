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
 

package org.dromara.maxkey.web.apis.identity.rest;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value={"/api/idm/Organization"})
public class RestOrganizationController {
	static final  Logger _logger = LoggerFactory.getLogger(RestOrganizationController.class);
	
    @Autowired
    OrganizationsService organizationsService;
    
    @GetMapping(value = "/{id}")
    public Organizations getUser(@PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("Organizations id {} , attributes {}", id , attributes);
        return organizationsService.get(id);
    }

    @PostMapping
    public Organizations create(@RequestBody  Organizations org,
                                                      @RequestParam(required = false) String attributes,
                                                      UriComponentsBuilder builder) {
    	_logger.debug("Organizations content {} , attributes {}", org , attributes);
        Organizations loadOrg = organizationsService.get(org.getId());
        if(loadOrg == null) {
            organizationsService.insert(org);
        }else {
            organizationsService.update(org);
        }
        return org;
    }

    @PutMapping(value = "/{id}")
    public Organizations replace(@PathVariable String id,
                                                       @RequestBody Organizations org,
                                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("Organizations id {} , content {} , attributes {}", id , org , attributes);
        Organizations loadOrg = organizationsService.get(id);
        if(loadOrg == null) {
            organizationsService.insert(org);
        }else {
            organizationsService.update(org);
        }

        return org;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("Organizations id {} ", id );
        organizationsService.delete(id);
       
    }
    
    @GetMapping(value = { "/.search" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<Organizations>> search(@ModelAttribute Organizations org) {
    	if(StringUtils.isBlank(org.getInstId())){
    		org.setInstId("1");
    	}
		_logger.debug("Organizations {}" , org);
		return new Message<>(organizationsService.fetchPageResults(org));
	}


}
