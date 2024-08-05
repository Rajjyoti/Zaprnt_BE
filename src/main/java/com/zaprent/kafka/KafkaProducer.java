package com.zaprent.kafka;

import com.zaprnt.beans.kafka.BulkProductUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.zaprnt.beans.kafka.Constants.PRODUCT_UPDATE_TOPIC;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, BulkProductUpdateEvent> kafkaTemplate;

    public void publishEventForProductUpdate(BulkProductUpdateEvent event) {
        kafkaTemplate.send(PRODUCT_UPDATE_TOPIC, event);
    }
}

