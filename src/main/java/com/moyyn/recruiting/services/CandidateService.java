package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CandidateService {
    Candidate convertPdfToCandidate(MultipartFile file) throws IOException;
}
