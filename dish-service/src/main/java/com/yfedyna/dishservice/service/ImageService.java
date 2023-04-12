package com.yfedyna.dishservice.service;

import com.yfedyna.dishservice.model.Dish;

import java.util.List;

public interface ImageService {

    void saveImages(List<String> imagesName, Dish dish);

    void deleteAllImagesFromDbByDishId(Long dishId);
}
