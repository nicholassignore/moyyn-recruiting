package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Override
    public Candidate toCandidate(MultipartFile file) throws Exception {

        byte[] pdfBytes = file.getBytes();
        PDFBoxService pdfService = new PDFBoxService();
        Candidate candidate = pdfService.processPDF(pdfBytes);
        candidate.setFileName(file.getName());
        candidate.setFileType(file.getContentType());
        return candidate;
    }
}