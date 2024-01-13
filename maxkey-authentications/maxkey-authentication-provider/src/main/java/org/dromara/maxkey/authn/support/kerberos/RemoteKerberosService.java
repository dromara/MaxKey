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
 

package org.dromara.maxkey.authn.support.kerberos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dromara.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteKerberosService  implements KerberosService{
	private static Logger _logger = LoggerFactory.getLogger(RemoteKerberosService.class);
	List<KerberosProxy> kerberosProxys;

	@Override
	public List<KerberosProxy> getKerberosProxys() {
		return kerberosProxys;
	}

	public void setKerberosProxys(List<KerberosProxy> kerberosProxys) {
		this.kerberosProxys = kerberosProxys;
	}
	
	@Override
	public  String buildKerberosProxys(){
		List<Map<String,String>>userDomainUrlList=new ArrayList<Map<String,String>>();
		for (KerberosProxy kerberosProxy :kerberosProxys){
			Map<String,String> userDomainUrl =new HashMap<String,String>();
			userDomainUrl.put("userDomain", kerberosProxy.getUserdomain());
			userDomainUrl.put("redirectUri", kerberosProxy.getRedirectUri());
			userDomainUrlList.add(userDomainUrl);
		}
		_logger.debug(""+userDomainUrlList);
		String userDomainUrlJson=JsonUtils.toString(userDomainUrlList);
		_logger.debug("userDomain Url Json "+userDomainUrlJson);
		return userDomainUrlJson;
	}
}
