package com.nextsquad.house.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.nextsquad.house.repository.HouseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class FileUploadService {
    private final AmazonS3Client s3Client;
    private final HouseImageRepository houseImageRepository;
    private String bucket = "house-image-bucket";

    public void uploadFile(MultipartFile multipartFile) {

    }





}
