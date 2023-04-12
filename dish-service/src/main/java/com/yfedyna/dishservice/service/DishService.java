package com.yfedyna.dishservice.service;

import com.yfedyna.dishservice.dto.DishFilterDto;
import com.yfedyna.dishservice.dto.DishRequestDto;
import com.yfedyna.dishservice.dto.DishResponseDto;
import com.yfedyna.dishservice.model.Dish;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishService {
    void createDish(DishRequestDto dishRequestDto, Long userId);

    DishResponseDto getDishById(Long dishId, Long userId);

    List<DishResponseDto> getAllDishes(Pageable pageable, DishFilterDto dishFilterDto, Long userId);

    DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto, Long userId);

    void deleteById(Long id, Long userId);

    Dish findDishById(Long id);
//    Optional<Dish> findDishById(Long id);
}
