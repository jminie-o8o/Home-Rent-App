package com.nextsquad.house.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nextsquad.house.domain.house.HouseImage;
import com.nextsquad.house.repository.HouseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class FileUploadService {
    private final AmazonS3Client s3Client;
    private final String bucket = "house-image-bucket";

    public List<String> uploadFile(List<MultipartFile> multipartFiles) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            String imageUrl = uploadSingleFile(multipartFiles.get(i));
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    public String uploadSingleFile(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String key = String.valueOf(UUID.randomUUID());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        return s3Client.getUrl(bucket, key).toString();
    }


}
