package org.dromara.maxkey.ip2location;
/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
 


/**
 * IpRegion转换接口
 * 
 * @author Crystal.Sea
 *
 */
public interface IpLocation {
	public static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36";
	
	public static final int  TIMEOUT 		= 5000;
	
	public Region region(String ipAddress);
	
	public String getLocation(String region);
	
	public int getFailCount();
	
	public int plusFailCount() ;
	
}
