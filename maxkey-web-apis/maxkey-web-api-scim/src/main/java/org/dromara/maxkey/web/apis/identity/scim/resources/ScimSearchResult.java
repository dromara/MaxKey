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
 

package org.dromara.maxkey.web.apis.identity.scim.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScimSearchResult <T>{

	public static class Constants{
		public static final String FILTER		=	"filter";
		public static final String SORTBY		=	"sortBy";
		public static final String COUNT		=	"count";
		public static final String STARTINDEX	=	"startIndex";
		
		
	}
    public static final String SCHEMA = "urn:ietf:params:scim:api:messages:2.0:ListResponse";
    public static final int MAX_RESULTS = 100;
    private long totalResults;
    private long itemsPerPage;
    private long startIndex;
    private Set<String> schemas = new HashSet<>(Collections.singletonList(SCHEMA));
    
    @JsonProperty("Resources")
    private List<T> resources = new ArrayList<>();

    /**
     * Default constructor for Jackson
     */
    ScimSearchResult() {
    }

    public ScimSearchResult(List<T> resources, long totalResults, long itemsPerPage, long startIndex) {
        this.resources = resources;
        this.totalResults = totalResults;
        this.itemsPerPage = itemsPerPage;
        this.startIndex = startIndex;
    }


    /**
     * gets a list of found {@link ScimUser}s or {@link ScimGroup}s
     *
     * @return a list of found resources
     */
    @JsonProperty("Resources")
    public List<T> getResources() {
        return resources;
    }

    /**
     * The total number of results returned by the list or query operation. This may not be equal to the number of
     * elements in the Resources attribute of the list response if pagination is requested.
     *
     * @return the total result
     */
    public long getTotalResults() {
        return totalResults;
    }

    /**
     * Gets the schemas of the search result
     *
     * @return the search result schemas
     */
    public Set<String> getSchemas() {
        return schemas;
    }

    /**
     * The number of Resources returned in a list response page.
     *
     * @return items per page
     */
    public long getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * The 1-based index of the first result in the current set of list results.
     *
     * @return the start index of the actual page
     */
    public long getStartIndex() {
        return startIndex;
    }
}
