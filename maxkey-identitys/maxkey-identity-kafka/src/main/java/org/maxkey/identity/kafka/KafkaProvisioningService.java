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
 

package org.maxkey.identity.kafka;

import java.util.UUID;

import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProvisioningService {
    
    private static final Logger _logger = LoggerFactory.getLogger(KafkaProvisioningService.class);
    
    @Autowired
    protected ApplicationConfig applicationConfig;
    
    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    /**
     * send  msg to kafka
     * @param topic kafka TOPIC
     * @param content msg Object
     * @param actionType CREATE UPDATE DELETE
     */
    public void send(String topic,Object content,String actionType) {
        //config.identity.kafkasupport , if true 
        if(applicationConfig.isKafkaSupport()) {
            KafkaMessage message = new KafkaMessage();
            //message id uuid
            message.setMsgId(UUID.randomUUID().toString());
            message.setActionType(actionType);
            message.setTopic(topic);
            //send to kafka time
            message.setSendTime(DateUtils.getCurrentDateTimeAsString());
            //content Object to json message content
            message.setContent(JsonUtils.gson2Json(content));
            String msg = JsonUtils.gson2Json(message);
            _logger.info("send  message = {}", msg);
            //通过线程发送Kafka消息
            KafkaProvisioningThread thread = 
                    new  KafkaProvisioningThread(kafkaTemplate,topic,msg);
            
            thread.start();
        }
    }
    
    
    
    /**
     * KafkaProvisioningThread for send message
     *
     */
    class KafkaProvisioningThread extends Thread{

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
            
            kafkaTemplate.send(topic, msg);
            
        }

    }
    
}
