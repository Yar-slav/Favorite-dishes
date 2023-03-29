package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.DishRequestDto;
import com.yfedyna.apigateway.dishservice.dto.DishResponseDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishService {
    void createDish(DishRequestDto dishRequestDto, Long userId);

    DishResponseDto getDishById(Long dishId, Long userId);

    List<DishResponseDto> getAllDishes(Pageable pageable, String authHeader);

    DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto, Long userId);

    void deleteById(Long id, Long userId);

    Dish findDishById(Long id);
}
