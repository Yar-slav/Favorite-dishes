package com.yfedyna.dishservice.mapper;

import com.yfedyna.dishservice.dto.IngredientRequestDto;
import com.yfedyna.dishservice.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface IngredientMapper {

    Ingredient toIngredient(IngredientRequestDto ingredientRequestDto);
}
