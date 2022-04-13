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
 

package org.maxkey.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.SystemUtils;
import org.joda.time.DateTime;
import org.maxkey.constants.ContentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MetadataEndpoint {

	@RequestMapping(value = "/metadata/version",produces = ContentType.TEXT_PLAIN_UTF8, method={RequestMethod.GET})
	@ResponseBody
	public String  metadata(HttpServletRequest request,HttpServletResponse response) {
		StringBuffer version =
				new StringBuffer("---------------------------------------------------------------------------------\n");
				  version.append("+                                MaxKey \n");
				  version.append("+                      Single   Sign   On ( SSO ) \n");
				  version.append("+                           Version "); 
				  version.append(WebContext.properties.getProperty("application.formatted-version")+"\n");
				  version.append("+\n");
				  version.append(String.format("+                 %sCopyright 2018 - %s https://www.maxkey.top/\n",
	        			    (char)0xA9 , new DateTime().getYear()
	        			));
				  version.append("+                 Licensed under the Apache License, Version 2.0 \n");

			        
				  version.append("---------------------------------------------------------------------------------\n");
				  version.append("+                                JAVA    \n");
				  version.append(String.format("+                 %s java version %s, class %s\n",
			                        SystemUtils.JAVA_VENDOR,
			                        SystemUtils.JAVA_VERSION,
			                        SystemUtils.JAVA_CLASS_VERSION
			                    ));
				  version.append(String.format("+                 %s (build %s, %s)\n",
			                        SystemUtils.JAVA_VM_NAME,
			                        SystemUtils.JAVA_VM_VERSION,
			                        SystemUtils.JAVA_VM_INFO
			                    ));
				  version.append("---------------------------------------------------------------------------------\n");
		return version.toString();
	}
}
