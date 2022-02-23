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

import org.maxkey.pretty.PrettyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka Provisioning Thread for send message
 *
 */
public class KafkaProvisioningThread extends Thread{
	private static final Logger _logger = LoggerFactory.getLogger(KafkaProvisioningThread.class);
	
    KafkaTemplate<String, String> kafkaTemplate;
    
    String topic ;
    
    String msg;
    
    public KafkaProvisioningThread(
                            KafkaTemplate<String, String> kafkaTemplate, 
                            String topic, 
                            String msg) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.msg = msg;
    }

    @Override
    public void run() {
    	_logger.debug("send message \n{}" , PrettyFactory.getJsonPretty().format(msg));
        kafkaTemplate.send(topic, msg);
        _logger.debug("send to Message Queue finished .");
    }
}
