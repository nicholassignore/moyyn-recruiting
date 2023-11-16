package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PdfTest {
    @Test
    void should_create_pdf() throws IOException {
        Candidate candidate = new Candidate();
        candidate.setAge(25);
        candidate.setMarried(true);
        candidate.setFirstName("mickey");
        candidate.setLastName("mouse");
        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("spring");
        skills.add("react");
        candidate.setSkills(skills);

        PDFBoxService pdfService = new PDFBoxService();
        PDDocument document = pdfService.getPersonalDocument(candidate);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);

        document.save("mickeymouse.pdf");
        log.info("text : {} ", text);

        String expected = "Name: mickey mouse\n" +
                "Age: 25\n" +
                "Married: true\n" +
                "Skills: java,spring,react\n";
        Assertions.assertEquals(expected, text, "Extracted text was not as expected");


    }

    @Test
    public void should_process_pdf_file() throws IOException {
        Path pdfPath = Paths.get("src/test/resources/mickeymouse.pdf");
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "mickeymouse.pdf", "application/pdf", pdf);
        byte[] pdfBytes = mockFile.getBytes();
        String contentType = mockFile.getContentType();
        Assertions.assertEquals("application/pdf", contentType);

        PDFBoxService pdfService = new PDFBoxService();
        Candidate candidate = pdfService.processPDF(pdfBytes);
        log.info("candidate: {}", candidate);

        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("spring");
        skills.add("react");
        Assertions.assertEquals("mickey", candidate.getFirstName());
        Assertions.assertEquals("mouse", candidate.getLastName());
        Assertions.assertEquals(true, candidate.getMarried());
        Assertions.assertEquals(25, candidate.getAge());
        Assertions.assertEquals(skills, candidate.getSkills());

    }

}
