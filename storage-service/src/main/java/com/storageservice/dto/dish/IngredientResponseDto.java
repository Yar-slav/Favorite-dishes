package com.storageservice.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponseDto {
    private ProductResponseDto product;
    private String amount;
    private String statusIngredient;
}
