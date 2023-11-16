package com.moyyn.recruiting.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.webresources.FileResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class ResumeControllerTest {
    @Autowired
    private MockMvc mvc;


    @Test
    public void shouldSaveUploadedFile() throws Exception {
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "mickeymouse.pdf", "application/pdf", pdf);
        MvcResult result = this.mvc.perform(multipart("/").file(mockFile))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        log.info(content);
        Assertions.assertEquals("{\"fileName\":\"file\",\"fileType\":\"application/pdf\",\"firstName\":\"mickey\",\"lastName\":\"mouse\",\"age\":25,\"married\":true,\"skills\":[\"java\",\"spring\",\"react\"]}",content);
    }


}