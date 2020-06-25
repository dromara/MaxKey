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
    
    public void send(String topic,Object content,String actionType) {
        if(applicationConfig.isKafkaSupport()) {
            KafkaMessage message = new KafkaMessage();
            message.setMsgId(UUID.randomUUID().toString());
            message.setActionType(actionType);
            message.setTopic(topic);
            message.setSendTime(DateUtils.getCurrentDateTimeAsString());
            message.setContent(JsonUtils.gson2Json(content));
            String msg = JsonUtils.gson2Json(message);
            _logger.info("send  message = {}", msg);
            //通过线程发送Kafka消息
            KafkaProvisioningThread thread = 
                    new  KafkaProvisioningThread(kafkaTemplate,topic,msg);
            
            thread.start();
        }
    }
    
    
    
    class KafkaProvisioningThread extends Thread{

        KafkaTemplate<String, String> kafkaTemplate;
        
        String topic ;
        
        String msg;
        
        public KafkaProvisioningThread(KafkaTemplate<String, String> kafkaTemplate, String topic, String msg) {
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
