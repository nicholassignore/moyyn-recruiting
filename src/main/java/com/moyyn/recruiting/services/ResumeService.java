package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    Candidate toCandidate(MultipartFile file) throws Exception;

}
