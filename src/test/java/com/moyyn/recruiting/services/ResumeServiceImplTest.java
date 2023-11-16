package com.moyyn.recruiting.services;

import com.moyyn.recruiting.controller.ResumeController;
import com.moyyn.recruiting.model.Candidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ResumeServiceImplTest {


    @Autowired
    ResumeController resumeController;

    @Test
    void saveAttachment() throws Exception {
        MultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello, world!".getBytes());


//        assertEquals("test.txt", resume.getFileName());
//        assertEquals("text/plain", resume.getFileType());
    }

    @Test
    void should_create_candidate() throws Exception {
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "mickeymouse.pdf", "application/pdf", pdf);
        Candidate candidate = resumeController.uploadFile(mockFile);

        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("spring");
        skills.add("react");
        Assertions.assertEquals("mickey",candidate.getFirstName());
        Assertions.assertEquals("mouse",candidate.getLastName());
        Assertions.assertEquals(true,candidate.getMarried());
        Assertions.assertEquals(25,candidate.getAge());
        Assertions.assertEquals(skills,candidate.getSkills());

    }



}