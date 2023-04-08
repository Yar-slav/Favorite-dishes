package com.storageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LInksToImagesDto {
    private long dishId;
    private List<URL> imageUrls;
}
