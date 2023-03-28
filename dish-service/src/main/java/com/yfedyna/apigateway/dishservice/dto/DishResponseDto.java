package com.yfedyna.apigateway.dishservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishResponseDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private List<ImageResponseDto> images = new ArrayList<>();
    private Long userId;
}
