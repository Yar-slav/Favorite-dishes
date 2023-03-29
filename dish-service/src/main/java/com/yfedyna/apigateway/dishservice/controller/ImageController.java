package com.yfedyna.apigateway.dishservice.controller;

import com.yfedyna.apigateway.dishservice.dto.LInksToImagesDto;
import com.yfedyna.apigateway.dishservice.service.impl.StorageServiceImpl;
import com.yfedyna.apigateway.dishservice.service.security.Roles;
import com.yfedyna.apigateway.dishservice.service.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/dish/image")
@RequiredArgsConstructor
public class ImageController {
    private final Security security;

    private final StorageServiceImpl storageServiceImpl;

    @GetMapping("/{dishId}")
    public LInksToImagesDto download(
            @PathVariable Long dishId,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userIdByToken = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        return storageServiceImpl.generateLinksForDownloadImages(dishId, userIdByToken);

    }
}
