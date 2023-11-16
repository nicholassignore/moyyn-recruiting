package com.moyyn.recruiting.services;

import com.moyyn.recruiting.controller.CandidateController;
import com.moyyn.recruiting.model.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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

@Slf4j
@SpringBootTest
class CandidateServiceImplTest {

    @Autowired
    CandidateController candidateController;


    @Test
    void should_create_candidate() throws Exception {
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "mickeymouse.pdf", "application/pdf", pdf);
        Candidate candidate = candidateController.uploadFile(mockFile);

        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("spring");
        skills.add("react");
        Assertions.assertEquals("mickey", candidate.getFirstName());
        Assertions.assertEquals("mouse", candidate.getLastName());
        Assertions.assertEquals(true, candidate.getMarried());
        Assertions.assertEquals(25, candidate.getAge());
        Assertions.assertEquals(skills, candidate.getSkills());
        log.info("candidate:{} ", candidate);

    }


}