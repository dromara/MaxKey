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
 

package org.maxkey.connector.receiver;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.maxkey.connector.OrganizationConnector;
import org.maxkey.domain.Organizations;
import org.maxkey.identity.kafka.KafkaIdentityAction;
import org.maxkey.identity.kafka.KafkaIdentityTopic;
import org.maxkey.identity.kafka.KafkaMessage;
import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrgsTopicReceiver {
    private static final Logger _logger = LoggerFactory.getLogger(KafkaOrgsTopicReceiver.class);
   
    @Autowired
    OrganizationConnector organizationConnector;
    
    @KafkaListener(topics = {KafkaIdentityTopic.ORG_TOPIC})
    public void listen(ConsumerRecord<?, ?> record) {
        try {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
    
            if (kafkaMessage.isPresent()) {
    
                Object message = kafkaMessage.get();
    
                _logger.debug("----------------- record =" + record);
                _logger.debug("------------------ message =" + message);
                
                KafkaMessage receiverMessage = JsonUtils.gson2Object(message.toString(), KafkaMessage.class);
                Organizations org = JsonUtils.gson2Object(receiverMessage.getContent().toString(),Organizations.class);
                
                if(receiverMessage.getActionType().equalsIgnoreCase(KafkaIdentityAction.CREATE_ACTION)) {
                    organizationConnector.create(org);
                }else if(receiverMessage.getActionType().equalsIgnoreCase(KafkaIdentityAction.UPDATE_ACTION)) {
                    organizationConnector.update(org);
                }else if(receiverMessage.getActionType().equalsIgnoreCase(KafkaIdentityAction.DELETE_ACTION)) {
                    organizationConnector.delete(org);
                }else{
                    _logger.info("Other Action ");
                }
            }
        }catch(Exception e) {
            
        }

    }
}
