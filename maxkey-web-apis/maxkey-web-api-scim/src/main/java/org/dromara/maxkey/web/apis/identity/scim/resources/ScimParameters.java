/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.web.apis.identity.scim.ScimServiceProviderConfigController;

public class ScimParameters {
	int startIndex = 1;
	int count = ScimServiceProviderConfigController.MAX_RESULTS;
	String ﬁlter;
	String sortBy;
	String sortOrder = "ascending";
	String attributes;

	public ScimParameters() {
	}

	public void parse() {
		if(startIndex == -1) {
    		count = ScimServiceProviderConfigController.MAX_RESULTS_LIMIT;
    	}
		
		if(startIndex <= 0) {
			startIndex = 1;
		}
		
		if(count > ScimServiceProviderConfigController.MAX_RESULTS
				&& count != ScimServiceProviderConfigController.MAX_RESULTS_LIMIT) {
    		count = ScimServiceProviderConfigController.MAX_RESULTS;
    	}
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getﬁlter() {
		return ﬁlter;
	}

	public void setﬁlter(String ﬁlter) {
		this.ﬁlter = ﬁlter;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "ScimParameters [count=" + count + ", startIndex=" + startIndex + ", ﬁlter=" + ﬁlter + ", sortBy="
				+ sortBy + ", sortOrder=" + sortOrder + ", attributes=" + attributes + "]";
	}

}
