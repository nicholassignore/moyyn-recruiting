package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
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
import java.util.List;

@Service
@Slf4j
public class PDFBoxService {
    public static final PDFont BOLD = PDType1Font.HELVETICA_BOLD;
    public static final PDFont PLAIN = PDType1Font.HELVETICA;
    public static final int X = 50;
    private float Y;

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
        String joined2 = String.join(",", candidate.getSkills());
        addHeaderAndWrappedText("Skills: ", joined2, contentStream);

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
                    } else if (line.startsWith("Skills")) {
                        List<String> skills = extractSkills(line);
                        candidate.setSkills(skills);
                    }
                }
            }
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
