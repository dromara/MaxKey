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

package org.maxkey.persistence.mq.thread;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.maxkey.pretty.PrettyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;

/**
 * RocketMQ Provisioning Thread for send message
 *
 */
public class RocketMQProvisioningThread extends Thread{

	private static final Logger _logger = LoggerFactory.getLogger(RocketMQProvisioningThread.class);
			
	RocketMQTemplate rocketMQTemplate;
    
    String topic ;
    
    String msg;
    
    public RocketMQProvisioningThread(
    						RocketMQTemplate rocketMQTemplate, 
                            String topic, 
                            String msg) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.topic = topic;
        this.msg = msg;
    }

    @Override
    public void run() {
    	_logger.debug("send message \n{}" , PrettyFactory.getJsonPretty().format(msg));
    	rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msg).build());
        _logger.debug("send to Message Queue finished .");
    }
}