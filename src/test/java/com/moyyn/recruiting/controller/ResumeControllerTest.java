package com.moyyn.recruiting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyyn.recruiting.model.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class ResumeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_test_jackson_endpoint() throws Exception {

        // file to upload on HTTP
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "pdf", "mickeymouse.pdf", "application/pdf", pdf);

        // execute the PST call
        MvcResult result = this.mvc.perform(multipart("/jackson").file(mockFile))
                .andExpect(status().isOk()).andReturn();

        // Assert the return
        String content = result.getResponse().getContentAsString();
        log.info(content);

        ObjectMapper mapper = new ObjectMapper();
        Candidate candidate = mapper.readValue(content, Candidate.class);

        Assertions.assertEquals("mickey", candidate.getFirstName());
        //Assertions.assertEquals("{\"fileName\":\"mickeymouse.pdf\",\"fileType\":\"application/pdf\",\"firstName\":\"mickey\",\"lastName\":\"mouse\",\"age\":25,\"married\":true,\"skills\":[\"java\",\"spring\",\"react\"]}", content);
    }


    /*
    @Test
    public void should_test_chatGPT_endpoint() throws Exception {

        // file to upload on HTTP
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "pdf", "mickeymouse.pdf", "application/pdf", pdf);

        // execute the PST call
        MvcResult result = this.mvc.perform(multipart("/chatgpt").file(mockFile))
                .andExpect(status().isOk()).andReturn();

        // Assert the return
        String content = result.getResponse().getContentAsString();
        log.info(content);
        Assertions.assertEquals("{\n" +
                "    \"fileName\": \"mickeymouse.pdf\",\n" +
                "    \"fileType\": \"application/pdf\",\n" +
                "    \"firstName\": \"mickey\",\n" +
                "    \"lastName\": \"mouse\",\n" +
                "    \"age\": 25,\n" +
                "    \"married\": true,\n" +
                "    \"skills\": [\"java\", \"spring\", \"react\"]\n" +
                "}", content.trim());
    }
*/
}