package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Override
    public Candidate convertPdfToCandidate(MultipartFile file) throws IOException {

        // grab the PDF content in byte array
        byte[] pdfBytes = file.getBytes();

        // create a PDFBox service object
        PDFBoxService pdfService = new PDFBoxService();

        Candidate candidate = pdfService.processPDF(pdfBytes);
        candidate.setFileName(file.getOriginalFilename());
        candidate.setFileType(file.getContentType());

        return candidate;
    }
}