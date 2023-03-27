package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.DishRequest;

public interface DishService {
    void createDish(DishRequest dishRequest, Long userIdByToken);
}
