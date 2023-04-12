package com.yfedyna.storageservice.controller;

import com.yfedyna.storageservice.dto.LInksToImagesDto;
import com.yfedyna.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage/image")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/{dishId}")
    public void addDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
        storageService.addDishImages(files, dishId, authHeader);
    }

    @PutMapping("/{dishId}")
    public void updateDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
        storageService.updateDishImage(files, dishId, authHeader);
    }

    @GetMapping("/{dishId}")
    public LInksToImagesDto download(
            @PathVariable Long dishId,
            @RequestHeader("Authorization") String authHeader
    ) {
        return storageService.generateLinksForDownloadImages(dishId, authHeader);

    }

    @DeleteMapping("/{dishId}")
    public void delete(
            @PathVariable Long dishId,
            @RequestHeader("Authorization") String authHeader
    ){
        storageService.deleteAllFilesByDishId(dishId, authHeader);
    }
}
