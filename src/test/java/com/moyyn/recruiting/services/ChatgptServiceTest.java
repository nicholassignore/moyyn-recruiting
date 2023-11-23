package com.moyyn.recruiting.services;

import com.moyyn.recruiting.chatgpt.beans.ChatGptResponse;
import com.moyyn.recruiting.model.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ChatgptServiceTest extends TestUtils {

    @Autowired
    private BotService botService;


    //@Test
    public void should_convert_candidate_to_json() {

        Candidate candidate = new Candidate();
        candidate.setAge(29);
        candidate.setMarried(true);
        candidate.setFirstName("mickey");
        candidate.setLastName("mouse");
        candidate.setFileName("file.pdf");
        candidate.setFileType("application/pdf");
        createSetSkills(candidate);

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
