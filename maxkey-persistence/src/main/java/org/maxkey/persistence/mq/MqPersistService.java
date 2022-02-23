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
 

package org.maxkey.persistence.mq;

import java.util.UUID;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.persistence.mq.thread.KafkaProvisioningThread;
import org.maxkey.persistence.mq.thread.RocketMQProvisioningThread;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqPersistService {
    private static final Logger _logger = LoggerFactory.getLogger(MqPersistService.class);
    
    @Autowired
    protected ApplicationConfig applicationConfig;
    
    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    /**
     * send  msg to kafka
     * @param topic kafka TOPIC
     * @param content msg Object
     * @param actionType CREATE UPDATE DELETE
     */
    public void send(String topic,Object content,String actionType) {
        //maxkey.server.message.queue , if not none , Kafka , RocketMQ
        if(applicationConfig.isMessageQueueSupport()) {
            MqMessage message = 
            		new MqMessage(
            				UUID.randomUUID().toString(),	//message id as uuid
            				topic,	//TOPIC
            				actionType,	//action of content
            				DateUtils.getCurrentDateTimeAsString(),	//send time
            				content 	//content Object to json message content
            				);
            String msg = JsonUtils.gson2Json(message);
            //sand msg to MQ topic
            Thread thread = null;
            if(applicationConfig.getMessageQueue().equalsIgnoreCase("Kafka")) {
            	_logger.trace("Kafka message...");
            	thread = new  KafkaProvisioningThread(kafkaTemplate,topic,msg);
            }else if(applicationConfig.getMessageQueue().equalsIgnoreCase("RocketMQ")) {
            	_logger.trace("RocketMQ message...");
            	thread = new  RocketMQProvisioningThread(rocketMQTemplate,topic,msg);
            }
            thread.start();
        }
    }
}
