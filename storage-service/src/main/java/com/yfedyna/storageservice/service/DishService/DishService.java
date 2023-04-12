package com.yfedyna.storageservice.service.DishService;

import com.yfedyna.storageservice.dto.dish.DishResponseDto;

public interface DishService {
    DishResponseDto getDishById(Long dishId, String token);
}
