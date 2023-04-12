package com.yfedyna.dishservice.dto;

import jakarta.validation.Valid;
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


    @Valid
    private ProductRequestDto product;

    private String amount;

    @NotBlank(message = "Status of ingredient cannot be empty or null")
    private String statusIngredient;
}
