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
 

package org.dromara.maxkey.provision.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProvisioningRunnerThread  extends Thread{
    private static final Logger _logger = LoggerFactory.getLogger(ProvisioningRunnerThread.class);
    
    ProvisioningRunner runner;

    public ProvisioningRunnerThread(ProvisioningRunner runner) {
        super();
        this.runner = runner;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(60 * 1000);
                _logger.trace("Provisioning start ...");
                runner.provisions();
                _logger.trace("Provisioning end , wait for next .");
            } catch (InterruptedException e) {
                _logger.error("InterruptedException", e);
            }
        }
    }
}
