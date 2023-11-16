package com.moyyn.recruiting.services;

import com.moyyn.recruiting.chatgpt.beans.ChatGptResponse;
import com.moyyn.recruiting.model.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class ChatgptServiceTest {

    @Autowired
    private BotService botService;

    @Test
    public void should_convert_candidate_to_json() {

        Candidate candidate = new Candidate();
        candidate.setAge(29);
        candidate.setMarried(true);
        candidate.setFirstName("mickey");
        candidate.setLastName("mouse");
        candidate.setFileName("file.pdf");
        candidate.setFileType("application/pdf");
        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("spring");
        skills.add("react");
        candidate.setSkills(skills);

        String domanda = "can you translate the string '" + candidate + "' to a json string ?";

        log.info("domanda : {} ", domanda);

        ChatGptResponse chatGptResponse = botService.askChatGpt(domanda);

        Assertions.assertEquals("{\n" +
                "    \"fileName\": \"file.pdf\",\n" +
                "    \"fileType\": \"application/pdf\",\n" +
                "    \"firstName\": \"mickey\",\n" +
                "    \"lastName\": \"mouse\",\n" +
                "    \"age\": 29,\n" +
                "    \"married\": true,\n" +
                "    \"skills\": [\"java\", \"spring\", \"react\"]\n" +
                "}", chatGptResponse.getChoices().get(0).getText().trim());

    }


}
