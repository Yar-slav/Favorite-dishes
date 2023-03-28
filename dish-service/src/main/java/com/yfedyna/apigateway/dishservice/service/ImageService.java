package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.model.Dish;

public interface ImageService {

    void saveImageToDb(String originFileName, Dish dish);

    void deleteAllByDishId(Long dishId);
}
