package com.moyyn.recruiting.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ResumeServiceImplTest {



    @Test
    void saveAttachment() throws Exception {
        MultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello, world!".getBytes());


//        assertEquals("test.txt", resume.getFileName());
//        assertEquals("text/plain", resume.getFileType());
    }




}