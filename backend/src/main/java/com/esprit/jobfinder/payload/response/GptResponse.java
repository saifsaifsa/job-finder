package com.esprit.jobfinder.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String system_fingerprint;
    public String getGptResponse() {
        return this.choices.get(0).getMessage().getContent();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Choice {
    private int index;
    private Message message;
    private String logprobs;
    private String finish_reason;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Message {
    private String role;
    private String content;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}