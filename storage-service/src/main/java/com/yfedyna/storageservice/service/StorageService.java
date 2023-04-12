package com.yfedyna.storageservice.service;

import com.yfedyna.storageservice.dto.LInksToImagesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    void addDishImages(List<MultipartFile> files, Long dishId, String token);

    LInksToImagesDto generateLinksForDownloadImages(Long dishId, String token);

    void deleteAllFilesByDishId(Long dishId, String token);

    void updateDishImage(List<MultipartFile> files, Long dishId, String token);
}

