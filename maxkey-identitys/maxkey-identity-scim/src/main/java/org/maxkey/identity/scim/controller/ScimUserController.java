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
 

package org.maxkey.identity.scim.controller;

import java.io.IOException;
import java.util.Map;

import org.maxkey.identity.scim.resources.ScimSearchResult;
import org.maxkey.identity.scim.resources.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
@RequestMapping(value = "/identity/scim/v2/Users")
public class ScimUserController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue getUser(@PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
        User user = null;
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MappingJacksonValue> create(@RequestBody  User user,
                                                      @RequestParam(required = false) String attributes,
                                                      UriComponentsBuilder builder) throws IOException {
        User createdUser = null;
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MappingJacksonValue> replace(@PathVariable String id,
                                                       @RequestBody User user,
                                                       @RequestParam(required = false) String attributes)
            throws IOException {
        User createdUser = null;
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
        //tokenService.revokeAllTokensOfUser(id);
       
    }

    @RequestMapping(method = RequestMethod.GET)
    public MappingJacksonValue searchWithGet(@RequestParam Map<String, String> requestParameters) {
        return searchWithPost(requestParameters);
    }

    @RequestMapping(value = "/.search", method = RequestMethod.POST)
    public MappingJacksonValue searchWithPost(@RequestParam Map<String, String> requestParameters) {
        ScimSearchResult<User> scimSearchResult = null;
        /*
                requestParameters.get("filter"),
                requestParameters.get("sortBy"),
                requestParameters.getOrDefault("sortOrder", "ascending"),             // scim default
                Integer.parseInt(requestParameters.getOrDefault("count", "" + ScimServiceProviderConfigController.MAX_RESULTS)),
                Integer.parseInt(requestParameters.getOrDefault("startIndex", "1")); // scim default
*/
        String attributes = (requestParameters.containsKey("attributes") ? requestParameters.get("attributes") : "");
        return null;
    }
}
