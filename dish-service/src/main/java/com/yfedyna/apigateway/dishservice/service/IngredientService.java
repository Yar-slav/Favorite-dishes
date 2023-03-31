package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.dto.IngredientResponseDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import com.yfedyna.apigateway.dishservice.model.Product;

import java.util.List;

public interface IngredientService {

    Ingredient mapToIngredient(IngredientRequestDto ingredientRequestDto, Product product);

    Dish saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish);

    IngredientResponseDto mapToIngredientResponseDto(Ingredient ingredient);

    void deleteAllIngredientsByDishId(Long dishId);

}

