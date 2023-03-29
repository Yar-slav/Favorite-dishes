package com.yfedyna.apigateway.dishservice.controller;

import com.yfedyna.apigateway.dishservice.dto.LInksToImagesDto;
import com.yfedyna.apigateway.dishservice.service.StorageService;
import com.yfedyna.apigateway.dishservice.service.security.Roles;
import com.yfedyna.apigateway.dishservice.service.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/dish/image")
@RequiredArgsConstructor
public class StorageController {
    private final Security security;

    private final StorageService storageService;

    @PostMapping("/{dishId}")
    public void addDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        storageService.addDishImage(files, dishId, userId);
    }

    @PutMapping("/{dishId}")
    public void updateDishImage(
            @PathVariable Long dishId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        storageService.updateDishImage(files, dishId, userId);
    }

    @GetMapping("/{dishId}")
    public LInksToImagesDto download(
            @PathVariable Long dishId,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        return storageService.generateLinksForDownloadImages(dishId, userId);

    }
}
