package com.nextsquad.house.controller;

import com.nextsquad.house.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/images")
    public List<String> uploadImages(@RequestParam List<MultipartFile> images) throws IOException {
        return fileUploadService.uploadFile(images);
    }
}
