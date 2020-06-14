package org.maxkey.connector.receiver;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.maxkey.connector.OrganizationConnector;
import org.maxkey.identity.kafka.KafkaIdentityTopic;
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

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();

            _logger.info("----------------- record =" + record);
            _logger.info("------------------ message =" + message);
        }

    }
}
