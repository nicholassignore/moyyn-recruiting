package com.moyyn.recruiting.controller;

import com.moyyn.recruiting.beans.ResponseClass;
import com.moyyn.recruiting.model.Resume;
import com.moyyn.recruiting.services.ResumeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/api/files")
public class ResumeController {
    @Autowired
    private ResumeService fileService;

    // for uploading the SINGLE file to the database
    @PostMapping("/single/base")
    public ResponseClass uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        Resume attachment = null;
        String downloadURl = "";
        attachment = fileService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        log.info("url = {}", downloadURl);
        return new ResponseClass(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    //for uploading the MULTIPLE files to the database
    @PostMapping("/multiple/base")
    public List<ResponseClass> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
        List<ResponseClass> responseList = new ArrayList<>();
        for (MultipartFile file : files) {
            Resume attachment = fileService.saveAttachment(file);
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId())
                    .toUriString();
            ResponseClass response = new ResponseClass(attachment.getFileName(),
                    downloadUrl,
                    file.getContentType(),
                    file.getSize());
            responseList.add(response);
        }
        return responseList;
    }
    //for retrieving  all the  files uploaded 
    @GetMapping("/all")
    public ResponseEntity<List<ResponseClass>> getAllFiles() {
        List<Resume> products = fileService.getAllFiles();
        List<ResponseClass> responseClasses = products.stream().map(product -> {
            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(product.getId())
                    .toUriString();
            return new ResponseClass(product.getFileName(),
                    downloadURL,
                    product.getFileType(),
                    product.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(responseClasses);
    }
}