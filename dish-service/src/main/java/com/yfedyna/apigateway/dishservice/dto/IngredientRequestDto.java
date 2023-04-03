package com.yfedyna.apigateway.dishservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientRequestDto {

    @NotBlank(message = "Product name cannot be empty or null")
    private String productName;

    private String amount;

    @NotBlank(message = "Important ingredient cannot be empty or null")
    private String importantIngredient;
}
