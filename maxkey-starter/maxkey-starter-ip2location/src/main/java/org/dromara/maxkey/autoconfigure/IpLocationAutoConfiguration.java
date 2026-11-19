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
 

package org.dromara.maxkey.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.ip2location.IpLocation;
import org.dromara.maxkey.ip2location.IpLocationParser;
import org.dromara.maxkey.ip2location.provider.GeoIP2V4;
import org.dromara.maxkey.ip2location.provider.Ip2regionV2;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.maxmind.geoip2.DatabaseReader;

/**
 * 
 * @author Crystal.Sea
 *
 */

@AutoConfiguration
public class IpLocationAutoConfiguration {
    static final  Logger _logger = LoggerFactory.getLogger(IpLocationAutoConfiguration.class);
    
    /**
     * 加载Ip2Region离线库数据 version 2.7.0
     * @return Ip2regionV2
     * @throws Exception
     */
    public Ip2regionV2 ip2regionV2() throws Exception {
        _logger.debug("IpRegion OffLine init...");
        ClassPathResource resource = new ClassPathResource("/ip2region/ip2region.xdb");
        byte[] dbBinStr = StreamUtils.copyToByteArray(resource.getInputStream());
        _logger.debug("ip2region length {}",dbBinStr.length);
        Searcher searcher = Searcher.newWithBuffer(dbBinStr);
        return new Ip2regionV2(searcher);
    }
    
    
    /**
     * 加载GeoIP2离线库数据 version 4.0.1
     * @return GeoIp2V4
     * @throws Exception
     */
    public GeoIP2V4 geoIP2() throws Exception {
        _logger.debug("GeoIP2 OffLine init...");
        ClassPathResource resource = new ClassPathResource("/geoip2/GeoLite2-City.mmdb");
        DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getInputStream()).build();
        return new GeoIP2V4(databaseReader);
    }
    
    /**
     * builder offline provider IpLocation
     * @param provider
     * @return IpLocation
     */
    public IpLocation builderIpLocationProvider(String provider) {
        IpLocation ipLocation = null;
        try {
            if(StringUtils.isBlank(provider)|| "none".equalsIgnoreCase(provider)) {
                //do nothing
                _logger.debug("IpLocation Provider none");
            }else if("Ip2Region".equalsIgnoreCase(provider)){
            	ipLocation = ip2regionV2();
                _logger.debug("IpLocation Provider Ip2Region");
            }else if("GeoIp2".equalsIgnoreCase(provider)){
            	ipLocation = geoIP2();
                _logger.debug("IpLocation Provider GeoIp2");
            }
        }catch(Exception e) {
            _logger.error("builder  IpLocation error", e);
        }
        return ipLocation;
    }
    

    
    /**
     * IP转换区域地址解析
     * @param isIplocation 是否转换
     * @param provider 离线转换实现提供商none/Ip2Region/GeoIp2
     * @return IpLocationParser
     * @throws Exception
     */
    @Bean 
    IpLocationParser ipLocationParser(
            @Value("${maxkey.login.iplocation:true}") boolean isIplocation,
            @Value("${maxkey.login.iplocation.provider:Ip2Region}") String provider) throws Exception {
        return new IpLocationParser(
                isIplocation,
                null,
                builderIpLocationProvider(provider)
        );
    }
    

}
