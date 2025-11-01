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
 

package org.dromara.maxkey.persistence.service.impl;

import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.entity.apps.AppsFormBasedDetails;
import org.dromara.maxkey.persistence.mapper.AppsFormBasedDetailsMapper;
import org.dromara.maxkey.persistence.service.AppsFormBasedDetailsService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Repository
public class AppsFormBasedDetailsServiceImpl  extends JpaServiceImpl<AppsFormBasedDetailsMapper,AppsFormBasedDetails> implements AppsFormBasedDetailsService{

    protected static final   Cache<String, AppsFormBasedDetails> detailsCache = 
            Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(200000)
                .build();

    public  AppsFormBasedDetails  getAppDetails(String id,boolean cached) {
        AppsFormBasedDetails details = null;
        if(cached) {
            details = detailsCache.getIfPresent(id);
            if(details == null) {
                details = getMapper().getAppDetails(id);
                detailsCache.put(id, details);
            }
        }else {
            details = getMapper().getAppDetails(id);
        }
        return details;
    }
}
