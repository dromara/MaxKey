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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractCache .
 * @author Crystal
 * @version 1.0
 * @Date 2015/11/12
 */
public abstract class AbstractCache extends Thread {

    protected static final Logger _logger = LoggerFactory.getLogger(AbstractCache.class);

    private int interval = 30000 / 1000;

    public AbstractCache() {

    }

    public abstract void business();

    @Override
    public void run() {
        while (true) {
            _logger.debug("Cache Thread Start run " + getName());
            _logger.info("Cache Thread Start run " + this.getClass());
            try {

                business();

                _logger.debug("Cache Thread " + getName() + " Finished . ");
                _logger.info("Cache Thread " + this.getClass() + " Finished . ");

                _logger.debug("Cache Thread sleep " + (interval * 1000) + " minute . ");
                sleep(interval * 1000);
            } catch (InterruptedException e) {
                _logger.error(e.getMessage(), e);
            }

        }
    }

    @Override
    public void start() {
        this.run();
    }

    /**
     * constructor.
     * @param name String
     */
    public AbstractCache(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

}
