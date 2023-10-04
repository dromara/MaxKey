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
package org.dromara.maxkey.ip2location;

import java.io.IOException;
import java.net.InetAddress;

import org.springframework.core.io.ClassPathResource;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class Geoip2Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ClassPathResource resource = new ClassPathResource("/geoip2/GeoLite2-City.mmdb");
		String ip = "117.155.70.59";
	
        if (!resource.getFile().exists()) {
            System.out.println("Error: Invalid GeoLite2-City.mmdb file, filePath：" + resource.getFile().getPath());
            
        }
        // 读取数据库内容
        DatabaseReader reader = null;
        try {
            reader = new DatabaseReader.Builder(resource.getFile()).build();
            //解析IP地址
            InetAddress ipAddress = InetAddress.getByName(ip);
            // 获取查询结果
            CityResponse response = reader.city(ipAddress);
            // 获取国家信息
            String country = response.getCountry().getNames().get("zh-CN");
            // 获取省份
            String state = response.getMostSpecificSubdivision().getNames().get("zh-CN");
            //查询不到时保持与ip2region方式的返回结果一致
            if (state == null){
            	state = "0";
            }
            // 获取城市
            String city = response.getCity().getNames().get("zh-CN");
            if (city == null){
            	city = "0";
            }
            String[] resu = {state,city};
            System.out.println(" " +country+" " +state +" " +city);
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
	}

}
