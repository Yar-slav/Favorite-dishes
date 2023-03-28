package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.Image;
import com.yfedyna.apigateway.dishservice.repository.ImageRepository;
import com.yfedyna.apigateway.dishservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public void saveImageToDb(String originFileName, Dish dish) {
        imageRepository.save(Image.builder()
                        .dish(dish)
                        .image(originFileName)
                .build());
    }

    @Override
    public void deleteAllByDishId(Long dishId) {
        imageRepository.deleteAllByDish_Id(dishId);
    }
}
