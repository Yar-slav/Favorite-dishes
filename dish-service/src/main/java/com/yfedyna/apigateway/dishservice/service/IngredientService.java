package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.model.Dish;

import java.util.List;

public interface IngredientService {

    Dish saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish);

    void deleteAllIngredientsByDishId(Long dishId);

}

