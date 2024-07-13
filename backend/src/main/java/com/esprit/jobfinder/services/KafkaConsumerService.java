package com.esprit.jobfinder.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "my_topic_name", groupId = "my-group-id")
    public void listen(String message) {
        System.out.println("Received message in group 'my-group-id': " + message);
       
    }
}
