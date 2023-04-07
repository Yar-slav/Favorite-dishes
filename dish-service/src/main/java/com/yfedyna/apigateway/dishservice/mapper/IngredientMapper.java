package com.yfedyna.apigateway.dishservice.mapper;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class, ImageMapper.class})
public interface IngredientMapper {

    List<Ingredient> toIngredients(List<IngredientRequestDto> ingredientRequestDtos);

    Ingredient toIngredient(IngredientRequestDto ingredientRequestDto);
}
