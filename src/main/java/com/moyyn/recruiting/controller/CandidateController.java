package com.moyyn.recruiting.controller;


import com.moyyn.recruiting.chatgpt.beans.ChatGptResponse;
import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.services.BotService;
import com.moyyn.recruiting.services.CandidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@AllArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final BotService botService;

    @PostMapping("/jackson")
    public Candidate uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return candidateService.toCandidate(file);
    }

    @PostMapping("/chatgpt")
    public String uploadFileChatGpt(@RequestParam("file") MultipartFile file) throws Exception {
        Candidate candidate = candidateService.toCandidate(file);
        String domanda = "can you translate the string '" + candidate + "' to a json string ?";
        log.info("domanda : {} ", domanda);
        ChatGptResponse chatGptResponse = botService.askChatGpt(domanda);
        return chatGptResponse.getChoices().get(0).getText();
    }

}