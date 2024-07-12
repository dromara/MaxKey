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
package org.dromara.maxkey.ip2location.offline;

import org.dromara.maxkey.ip2location.AbstractIpLocation;
import org.dromara.maxkey.ip2location.IpLocation;
import org.dromara.maxkey.ip2location.Region;
import org.lionsoul.ip2region.xdb.Searcher;


/**
 * 基于ip2region离线库ip查询
 * 
 * <p>
 * 官方文档：https://gitee.com/lionsoul/ip2region Apache-2.0
 * </p>
 * 
 * <p>
 * Ip2region (2.0 - xdb) 是一个离线 IP 数据管理框架和定位库，支持亿级别的数据段，10微秒级别的查询性能，提供了许多主流编程语言的 xdb 数据管理引擎的实现。
 * </p>
 * 
 * @author Crystal.Sea
 *
 */
public class Ip2regionV2 extends AbstractIpLocation implements IpLocation{
	
	Searcher searcher;;
	
	public Ip2regionV2(Searcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public Region region(String ipAddress) {
		try {
			String regionAddr = searcher.search(ipAddress);
			if(regionAddr.indexOf("内网IP")>-1) {
				return new Region("内网IP");
			}
			String[] regionAddrs =regionAddr.split("\\|");
			return new Region(regionAddrs[0],regionAddrs[2],regionAddrs[3],regionAddrs[0]+regionAddrs[2]+regionAddrs[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
