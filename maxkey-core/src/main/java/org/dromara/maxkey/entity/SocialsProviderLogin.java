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
 

package org.dromara.maxkey.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Crystal.Sea
 *
 */

public class SocialsProviderLogin implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2672107566766342357L;
	
	List<SocialsProvider> providers = new ArrayList<SocialsProvider>();
	
	String qrScan = null;

	public SocialsProviderLogin(List<SocialsProvider> socialSignOnProviders) {
		super();
		this.providers = socialSignOnProviders;
	}

	public String getQrScan() {
		return qrScan;
	}

	public void setQrScan(String qrScan) {
		this.qrScan = qrScan;
	}

	public List<SocialsProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<SocialsProvider> providers) {
		this.providers = providers;
	}
}
