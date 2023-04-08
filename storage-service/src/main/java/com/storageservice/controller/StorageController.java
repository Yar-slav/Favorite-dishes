package com.storageservice.controller;

import com.storageservice.dto.LInksToImagesDto;
import com.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dish/image")
@RequiredArgsConstructor
public class StorageController {
//    private final Security security;

    private final StorageService storageService;

    @PostMapping("/{dishId}")
    public void addDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
//        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        storageService.addDishImages(files, dishId);
    }

    @PutMapping("/{dishId}")
    public void updateDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
        storageService.updateDishImage(files, dishId);
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
        storageService.deleteAllFilesByDishId(dishId);
    }
}
