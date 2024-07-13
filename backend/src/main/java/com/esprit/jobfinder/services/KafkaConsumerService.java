package com.esprit.jobfinder.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final EmailService emailService;
    public KafkaConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }



   // @KafkaListener(topics = "my_topic_name", groupId = "my-group-id")
    public void listen(String message) {
        System.out.println("Received message in group 'my-group-id': " + message);
        // Extracting offer title, user full name, and user email from the message
        String[] parts = message.split(";");
        String offerTitle = parts[0];
        String userFullName = parts[1];
        String userEmail = parts[2];

        // Sending the congratulatory message
        String congratsMessage = "Congratulations, " + userFullName + "! You have successfully submitted to the offer: " + offerTitle;
        emailService.sendSimpleMessage(userEmail, "successfull! Submit to the offer: " + offerTitle, congratsMessage);
       
    }
}
