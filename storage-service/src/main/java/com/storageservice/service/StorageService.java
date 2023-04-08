package com.storageservice.service;

import com.storageservice.dto.LInksToImagesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    void addDishImages(List<MultipartFile> files, Long dishId);

    LInksToImagesDto generateLinksForDownloadImages(Long dishId, String token);

    void deleteAllFilesByDishId(Long dishId);

    void updateDishImage(List<MultipartFile> files, Long dishId);
}

