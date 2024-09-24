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
 * IP转换区域地址解析
 * 
 * <p>
 * 依次顺序为Local(本地) ->OnLine(在线解析) ->OffLine(离线解析) -> unknown(未知)
 * </p>
 * 
 * @author Crystal.Sea
 *
 */
public class IpLocationParser extends AbstractIpLocation  implements IpLocation{

	IpLocation ipRegionLocal = new IpLocationLocal();
	
	IpLocation ipLocationOnLine;
	
	IpLocation ipLocationOffLine;
	
	boolean isIpLocation;

	
	public IpLocationParser() {
	}


	public IpLocationParser(boolean isIpLocation,IpLocation ipLocationOnLine, IpLocation ipLocationOffLine) {
		super();
		this.ipLocationOnLine  = ipLocationOnLine;
		this.ipLocationOffLine = ipLocationOffLine;
		this.isIpLocation = isIpLocation;
	}

	/**
	 * ip转换区域地址
	 */
	@Override
	public Region region(String ipAddress) {
		Region region = null;
		if( isIpLocation ){//true 需要转换，否则跳过
			//本地转换
			region = ipRegionLocal.region(ipAddress);
			//在线转换
			if(ipLocationOnLine != null && region == null) {
				region = ipLocationOnLine.region(ipAddress);
			}
			//离线转换
			if(ipLocationOffLine != null && region == null) {
				region = ipLocationOffLine.region(ipAddress);
			}
		}
		//不转换或者未找到返回unknown
		return region == null ? new Region("unknown") : region;
	}

	
}
