package com.moyyn.recruiting.controller;


import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.services.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    // for uploading the SINGLE file to the database
    @PostMapping("/single/base")
    public Candidate uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        return resumeService.toCandidate(file);


    }

}