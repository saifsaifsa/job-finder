package com.esprit.jobfinder.payload.request;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromptGpt4 {

    // defult gpt-4 model with anotation
    private String model ;
    private List<Message> messages;

    public void initPromptGpt4(String prompt) {
        this.model = "gpt-4";
        Message message = new Message("user", "Ses valeurs représentent les réponses des employés sur un questionnaire. Conclure en 3 points  direct sans titre 1,2,3:"+prompt);

        this.messages = List.of(message);
    }


}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Message {
    private String role;
    private String content;

}