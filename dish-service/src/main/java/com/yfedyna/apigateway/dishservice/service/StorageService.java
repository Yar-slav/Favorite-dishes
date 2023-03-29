package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.LInksToImagesDto;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadFile(MultipartFile file, Long dishId);

    LInksToImagesDto generateLinksForDownloadImages(Long dishId, Long userIdByToken);

    void deleteAllFilesByDishId(Long dishId);
}
