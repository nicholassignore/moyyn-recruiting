package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.model.Skill;
import com.moyyn.recruiting.repositories.CandidateRepository;
import com.moyyn.recruiting.repositories.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
class PdfTest extends TestUtils{

    @Mock
    CandidateRepository candidateRepository;

    @Mock
    SkillRepository skillRepository;

    @BeforeEach
    public void before(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_create_pdf() throws IOException {
        Candidate candidate = new Candidate();
        candidate.setAge(25);
        candidate.setMarried(true);
        candidate.setFirstName("mickey");
        candidate.setLastName("mouse");

        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("java"));
        skills.add(new Skill("spring"));
        skills.add(new Skill("react"));
        candidate.setSkills(skills);

        // mickey mouse :  oggetto "java", oggetto "spring", "oggetto react"


        StringJoiner joiner = new StringJoiner(",");
        for (Skill skill : skills.stream().toList()) {
            joiner.add(skill.getName());
        }
        String skillsCommaSeparated = joiner.toString();



        PDFBoxService pdfService = new PDFBoxService(null, null);
        PDDocument document = pdfService.getPersonalDocument(candidate);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);

        document.save("src/test/resources/mickeymouse.pdf");
        log.info("text : {} ", text);

        String expected = "Name: mickey mouse\n" +
                "Age: 25\n" +
                "Married: true\n" +
                "Skills: " +skillsCommaSeparated +"\n";

        Assertions.assertEquals(expected, text, "Extracted text was not as expected");
    }


    @Test
    void should_create_goofy_pdf() throws IOException {
        Candidate candidate = new Candidate();
        candidate.setAge(25);
        candidate.setMarried(true);
        candidate.setFirstName("goofy");
        candidate.setLastName("goofy");
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("java"));
        skills.add(new Skill("spring"));
        skills.add(new Skill("react"));
        candidate.setSkills(skills);

        StringJoiner joiner = new StringJoiner(",");
        for (Skill skill : skills.stream().toList()) {
            joiner.add(skill.getName());
        }
        String skillsCommaSeparated = joiner.toString();

        PDFBoxService pdfService = new PDFBoxService(skillRepository, candidateRepository);
        PDDocument document = pdfService.getPersonalDocument(candidate);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);

        document.save("src/test/resources/goofy.pdf");
        log.info("text : {} ", text);

        String expected = "Name: goofy goofy\n" +
                "Age: 25\n" +
                "Married: true\n" +
                "Skills: " +skillsCommaSeparated +"\n";

        Assertions.assertEquals(expected, text, "Extracted text was not as expected");
    }
    /*
        Parse PDF and extract candidate values
     */
    @Test
    void should_process_pdf_file() throws IOException {

        String first = "src/test/resources/mickeymouse.pdf";
        String originalFilename = "mickeymouse.pdf";

        Candidate candidate = createCandidateFromFile(first, originalFilename);

        Assertions.assertEquals("mickey", candidate.getFirstName());
        Assertions.assertEquals("mouse", candidate.getLastName());
        Assertions.assertEquals(true, candidate.getMarried());
        Assertions.assertEquals(25, candidate.getAge());

        Set<Skill> skills = candidate.getSkills();

        Assertions.assertEquals(3,skills.size());
    }

    @Test
    void should_process_pdf_file_2() throws IOException {

        String first = "src/test/resources/goofy.pdf";
        String originalFilename = "goofy.pdf";

        Candidate candidate = createCandidateFromFile(first, originalFilename);

        Assertions.assertEquals("goofy", candidate.getFirstName());
        Assertions.assertEquals("goofy", candidate.getLastName());
        Assertions.assertEquals(true, candidate.getMarried());
        Assertions.assertEquals(25, candidate.getAge());

        Set<Skill> skills = candidate.getSkills();

        Assertions.assertEquals(3,skills.size());
    }

    private  Candidate createCandidateFromFile(String first, String originalFilename) throws IOException {
        Path pdfPath = Paths.get(first);
        byte[] pdf = Files.readAllBytes(pdfPath);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", originalFilename, "application/pdf", pdf);
        byte[] pdfBytes = mockFile.getBytes();
        String contentType = mockFile.getContentType();
        Assertions.assertEquals("application/pdf", contentType);

        PDFBoxService pdfService = new PDFBoxService(skillRepository, candidateRepository);
        Candidate candidate = pdfService.processPDF(pdfBytes);
        log.info("candidate: {}", candidate);
        return candidate;
    }
}
