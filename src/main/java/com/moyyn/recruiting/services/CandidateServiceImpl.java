package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.repositories.CandidateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.util.Arrays.asList;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final PDFBoxService pdfService;

    @Override
    public Candidate convertPdfToCandidate(MultipartFile file) throws IOException {

        // grab the PDF content in byte array
        byte[] pdfBytes = file.getBytes();

        Candidate candidate = pdfService.processPDF(pdfBytes);
        candidate.setFileName(file.getOriginalFilename());
        candidate.setFileType(file.getContentType());

        return candidate;
    }
}