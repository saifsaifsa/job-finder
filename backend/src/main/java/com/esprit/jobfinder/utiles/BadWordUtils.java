package com.esprit.jobfinder.utiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BadWordUtils {

    private static final Set<String> badWords = new HashSet<>();

    static {
        try {
            loadBadWords();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load bad words");
        }
    }

    private static void loadBadWords() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("bad-words.json");
        JsonNode jsonNode = objectMapper.readTree(resource.getInputStream());
        jsonNode.get("badWords").forEach(wordNode -> badWords.add(wordNode.asText()));
    }

    public static boolean containsBadWord(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        for (String badWord : badWords) {
            if (input.toLowerCase().contains(badWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
