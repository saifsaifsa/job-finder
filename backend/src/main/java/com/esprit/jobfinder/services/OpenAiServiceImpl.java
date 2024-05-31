package com.esprit.jobfinder.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.esprit.jobfinder.payload.request.PromptGpt4;
import com.esprit.jobfinder.payload.response.GptResponse;

@Service
public class OpenAiServiceImpl {

    @Value("${openai.api.url}")
    private  String OPENAI_API_URL ;

    @Value("${openai.api.key}")
    private  String AUTH_TOKEN ; 

    @Autowired
    private RestTemplate restTemplate;

    public GptResponse callOpenAI(PromptGpt4 prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + AUTH_TOKEN);

        HttpEntity<PromptGpt4> entity = new HttpEntity<>(prompt, headers);

        ResponseEntity<GptResponse> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, GptResponse.class);

        return response.getBody();
    }
}
