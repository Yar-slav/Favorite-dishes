package com.yfedyna.dishservice.service;

import com.yfedyna.dishservice.dto.IngredientRequestDto;
import com.yfedyna.dishservice.model.Dish;

import java.util.List;

public interface IngredientService {

    Dish saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish);

    void deleteAllIngredientsByDishId(Long dishId);

}

