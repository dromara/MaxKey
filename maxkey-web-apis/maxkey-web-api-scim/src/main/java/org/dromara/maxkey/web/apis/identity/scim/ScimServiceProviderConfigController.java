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

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/idm/SCIM/v2/ServiceProviderConfig")
public class ScimServiceProviderConfigController {

    public static final int MAX_RESULTS = 500;
    public static final int MAX_RESULTS_LIMIT = 5000;
    
    @RequestMapping
    @ResponseBody
    public ServiceProviderConfig getConfig() {
        return ServiceProviderConfig.INSTANCE;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static final class ServiceProviderConfig {

        public static final ServiceProviderConfig INSTANCE = new ServiceProviderConfig();
        public static final String SCHEMA = "urn:ietf:params:scim:schemas:core:2.0:ServiceProviderConfig";
        public final Supported patch = new Supported(false);
        public final Supported bulk = new BulkSupported(false);
        public final Supported filter = new FilterSupported(true, MAX_RESULTS);
        public final Supported changePassword = new Supported(false);
        public final Supported sort = new Supported(true);
        public final Supported etag = new Supported(false);
        public final Supported xmlDataFormat = new Supported(false);
        public final AuthenticationSchemes authenticationSchemes = new AuthenticationSchemes(
                new AuthenticationSchemes.AuthenticationScheme("Oauth2 Bearer",
                        "OAuth2 Bearer access token is used for authorization.", "http://tools.ietf.org/html/rfc6749",
                        "http://oauth.net/2/"));
        public Set<String> schemas = new HashSet<>();

        private ServiceProviderConfig() {
            schemas.add(SCHEMA);
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Schemas {
            public final Set<String> schemas = new HashSet<>();

            public Schemas(String coreSchema) {
                schemas.add(coreSchema);
            }
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Supported {
            public final boolean supported;

            public Supported(boolean b) {
                supported = b;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class FilterSupported extends Supported {
            public final Integer maxResults;

            public FilterSupported(boolean b, Integer maxResults) {
                super(b);
                this.maxResults = maxResults;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class BulkSupported extends Supported {
            public final Integer maxOperations;
            public final Integer maxPayloadSize;

            public BulkSupported(boolean b) {
                super(b);
                this.maxOperations = null;
                this.maxPayloadSize = null;
            }

        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        static class AuthenticationSchemes {
            public AuthenticationScheme[] authenticationSchemes;

            public AuthenticationSchemes(AuthenticationScheme... authenticationScheme) {
                this.authenticationSchemes = authenticationScheme;

            }

            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            public static class AuthenticationScheme {
                public final String name;
                public final String description;
                public final String specUrl;
                public final String documentationUrl;

                AuthenticationScheme(String name, String description, String specUrl, String documentationUrl) {
                    this.name = name;
                    this.description = description;
                    this.specUrl = specUrl;
                    this.documentationUrl = documentationUrl;
                }
            }
        }
    }
}
