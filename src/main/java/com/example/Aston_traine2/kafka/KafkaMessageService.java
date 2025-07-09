package com.example.Aston_traine2.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public KafkaMessageService(KafkaTemplate<String, String> kafkaTemplate,
                               @Value("${app.kafka.topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendUserEvent(String eventType, String email) {
        String message = String.format("{\"event\": \"%s\", \"email\": \"%s\"}", eventType, email);
        kafkaTemplate.send(topicName, message);
    }
}

