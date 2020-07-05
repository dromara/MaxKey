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
 

package org.maxkey.cache;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheFactory.
 * @author Crystal.Sea
 *
 */
public class CacheFactory {
    protected static final Logger _logger = LoggerFactory.getLogger(CacheFactory.class);

    private ArrayList<AbstractCache> cache;

    
    /**
     *  CacheFactory.
     */
    public CacheFactory() {

    }
    
    /**
     * start Cache.
     */
    public void start() {

        for (AbstractCache cacheable : cache) {
            _logger.info("Cache " + cacheable.getClass());
            new Thread(cacheable).start();

        }

    }

    public ArrayList<AbstractCache> getCache() {
        return cache;
    }

    public void setCache(ArrayList<AbstractCache> cache) {
        this.cache = cache;
    }

}
