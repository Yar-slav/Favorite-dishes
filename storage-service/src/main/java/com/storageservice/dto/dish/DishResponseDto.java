package com.storageservice.dto.dish;

import com.storageservice.dto.ImageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<ImageResponseDto> images;
    private List<IngredientResponseDto> ingredients;
    private Long userId;
}
