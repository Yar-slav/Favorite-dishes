package com.yfedyna.dishservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishRequestDto {

    @NotBlank(message = "Name cannot be empty or null")
    private String name;

    @NotBlank(message = "Type cannot be empty or null")
    private String type;
    private String description;

    @Valid
    @NotEmpty(message = "Ingredients cannot be empty or null. You need add at least one ingredient")
    private List<IngredientRequestDto> ingredients;
}
