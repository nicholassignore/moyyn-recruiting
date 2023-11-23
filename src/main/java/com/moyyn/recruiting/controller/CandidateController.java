package com.moyyn.recruiting.controller;


import com.moyyn.recruiting.chatgpt.beans.ChatGptResponse;
import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.services.BotService;
import com.moyyn.recruiting.services.CandidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // POST http://localhost:8080/jackson
    // rest post that uses Jackson in spring boot to return the Candidate json
    @PostMapping("/jackson")
    public ResponseEntity<Candidate> uploadFile(@RequestParam("pdf") MultipartFile pdf) throws Exception {
        Candidate candidate = candidateService.convertPdfToCandidate(pdf);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    // rest post that uses Chatgpt  String json
    // POST http://localhost:8080//chatgpt
    @PostMapping("/chatgpt")
    public ResponseEntity<String> uploadFileChatGpt(@RequestParam("pdf") MultipartFile pdf) throws Exception {
        Candidate candidate = candidateService.convertPdfToCandidate(pdf);

        // prepare question for ChatGPT
        String domanda = "can you translate the string '" + candidate + "' to a json string ?";
        log.info("domanda : {} ", domanda);

        // Ask chatGPT to convert Candidate to json string
        ChatGptResponse chatGptResponse = botService.askChatGpt(domanda);

        return new ResponseEntity<>(chatGptResponse.getChoices().get(0).getText(), HttpStatus.OK);
    }

}