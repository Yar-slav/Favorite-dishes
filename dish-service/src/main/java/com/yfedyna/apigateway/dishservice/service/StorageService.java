package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.LInksToImagesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    void addDishImage(List<MultipartFile> files, Long dishId, Long userId);

    LInksToImagesDto generateLinksForDownloadImages(Long dishId, Long userId);

    void deleteAllFilesByDishId(Long dishId);

    void updateDishImage(List<MultipartFile> files, Long dishId, Long userId);
}

