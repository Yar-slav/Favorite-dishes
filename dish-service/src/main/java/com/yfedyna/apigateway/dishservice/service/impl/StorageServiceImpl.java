package com.yfedyna.apigateway.dishservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.yfedyna.apigateway.dishservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public String uploadFile(MultipartFile file, Long dishId) {
        String folderName = bucketName + "/dishId_" + dishId;
        File fileObj = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(folderName, fileName, fileObj));
        return fileName;
    }

    @Override
    public byte[] downloadFile(String fileName, Long dishId) {
//        String folderName = getBucketDish(dishId);
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String fileName) {
//        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed...";
    }

    @Override
    public void deleteAllFilesByDishId(Long dishId) {
        String folderName = "dishId_" + dishId + "/";
        ObjectListing objectListing = s3Client.listObjects(bucketName, folderName);
        while (true) {
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, summary.getKey()));
            }

            if (objectListing.isTruncated()) {
                objectListing = s3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
        s3Client.deleteObject(bucketName, folderName);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertFile;
    }
}
