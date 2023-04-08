package com.storageservice.service.DishService;

import com.storageservice.dto.dish.DishResponseDto;

public interface DishService {
    DishResponseDto getDishById(Long dishId, String token);
}
