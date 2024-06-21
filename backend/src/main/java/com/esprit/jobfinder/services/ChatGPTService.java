package com.yourapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.json.JSONObject;

@Service
public class ChatGPTService {

    private final String API_URL = "https://api.openai.com/v1/engines/davinci/completions";
    private final String API_KEY = "YOUR_API_KEY";

    public String generateQuizQuestion(String topic) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("prompt", "Generate a quiz question on the topic: " + topic);
        requestBody.put("max_tokens", 100);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);

        return response.getBody();
    }
}
