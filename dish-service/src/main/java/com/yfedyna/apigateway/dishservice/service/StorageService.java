package com.yfedyna.apigateway.dishservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadFile(MultipartFile file, Long dishId);

    byte[] downloadFile(String fileName, Long dishId);

    String deleteFile(String fileName);

    void deleteAllFilesByDishId(Long dishId);
}
