package com.yfedyna.dishservice.service;

import com.yfedyna.dishservice.dto.DishRequest;

public interface DishService {
    void createDish(DishRequest dishRequest, Long userIdByToken);
}
