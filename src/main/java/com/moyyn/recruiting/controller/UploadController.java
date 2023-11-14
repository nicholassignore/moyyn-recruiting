package com.moyyn.recruiting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    @PostMapping("/single/upload")
    public ResponseEntity<String> fileUploading(@RequestParam("file") MultipartFile file) {
        // Code to save the file to a database or disk
        return ResponseEntity.ok("Successfully uploaded the file");
    }
}
