package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.model.Skill;
import com.moyyn.recruiting.repositories.CandidateRepository;
import com.moyyn.recruiting.repositories.SkillRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

@Service
@Slf4j
public class PDFBoxService {
    public static final PDFont BOLD = PDType1Font.HELVETICA_BOLD;
    public static final PDFont PLAIN = PDType1Font.HELVETICA;
    public static final int X = 50;
    private float Y;

    private final SkillRepository skillRepository;
    private final CandidateRepository candidateRepository;

    public PDFBoxService(SkillRepository skillRepository, CandidateRepository candidateRepository) {
        this.skillRepository = skillRepository;
        this.candidateRepository = candidateRepository;
    }

    // given candidate ---> creates the PDF document
    public PDDocument getPersonalDocument(Candidate candidate) throws IOException {
        Y = 700;
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        updateY(-15);
        addHeaderAndWrappedText("Name: ", candidate.getFirstName() + " " + candidate.getLastName(), contentStream);
        addHeaderAndWrappedText("Age: ", String.valueOf(candidate.getAge()), contentStream);
        addHeaderAndWrappedText("Married: ", String.valueOf(candidate.getMarried()), contentStream);

        StringJoiner joiner = new StringJoiner(",");
        for (Skill skill : candidate.getSkills().stream().toList()) {
            joiner.add(skill.getName());
        }
        String skillsCommaSeparated = joiner.toString();

        addHeaderAndWrappedText("Skills: ", skillsCommaSeparated, contentStream);

        contentStream.close();
        return document;
    }


    private void addHeaderAndWrappedText(String strHeader, String text, PDPageContentStream contentStream) throws IOException {
        updateY(-15);
        contentStream.beginText();
        contentStream.newLineAtOffset(X, Y);
        contentStream.setFont(BOLD, 12);
        contentStream.showText(strHeader);
        contentStream.setFont(PLAIN, 12);
        String[] wrappedText = WordUtils.wrap(text, 75).split("\\r?\\n");
        String string;
        for (int i = 0; i < wrappedText.length; i++) {
            if (i != 0) {
                updateY(-15);
                contentStream.beginText();
                contentStream.newLineAtOffset(X, Y);
            }
            string = wrappedText[i];
            contentStream.showText(string);
            contentStream.endText();
        }
    }

    private void updateY(int i) {
        Y += i;
    }

    // given PDF ---> crates the Candidate
    public Candidate processPDF(byte[] pdf) throws IOException {
        try (PDDocument document = PDDocument.load(pdf)) {

            Candidate candidate = new Candidate();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);

                // PDF opened  line by line
                String[] lines = pdfFileInText.split("\\r?\\n");

                // extract values and put into candidate object
                for (String line : lines) {
                    log.info("linea: <{}>", line);
                    if (line.startsWith("Name")) {
                        String name = extractFirstName(line);
                        String cognome = extractLastName(line);
                        candidate.setFirstName(name);
                        candidate.setLastName(cognome);
                    } else if (line.startsWith("Age")) {
                        Integer age = extractAge(line);
                        candidate.setAge(age);
                    } else if (line.startsWith("Married")) {
                        Boolean married = extractMarried(line);
                        candidate.setMarried(married);
                    } else if (line.startsWith("Skills")) {  // spring,java,react
                        List<String> skills = extractSkills(line);

                        Set<Skill> skillSet = new HashSet<>();
                        for (int i = 0; i < skills.size(); i++) {

                            String name = skills.get(i);

                            Optional<Skill> skill = skillRepository.findByName(name);
                            if (skill.isEmpty()){
                                skillSet.add(new Skill(skills.get(i)));
                            } else {
                                Skill skilltrovato = skill.get();
                                skillSet.add(skilltrovato);
                            }

                        }
                        candidate.setSkills(skillSet);
                    }
                }
            }

            candidateRepository.save(candidate);  // save ID = NULL  vs. update ID = 32

            return candidate;
        }
    }

    private List<String> extractSkills(String line) {
        String[] split = line.split(" ");
        String[] skills = split[1].split(",");
        return Arrays.asList(skills);
    }

    private Boolean extractMarried(String line) {
        String[] split = line.split(" ");
        return Boolean.parseBoolean(split[1]);
    }

    private Integer extractAge(String line) {
        String[] split = line.split(" ");
        return Integer.parseInt(split[1]);
    }

    private String extractLastName(String line) {
        String[] split = line.split(" ");
        return split[2];
    }

    private String extractFirstName(String line) {
        String[] split = line.split(" ");
        return split[1];
    }
}
