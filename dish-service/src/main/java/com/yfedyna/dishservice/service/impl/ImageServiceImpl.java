package com.yfedyna.dishservice.service.impl;

import com.yfedyna.dishservice.model.Dish;
import com.yfedyna.dishservice.model.Image;
import com.yfedyna.dishservice.repository.ImageRepository;
import com.yfedyna.dishservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public void saveImages(List<String> imagesName, Dish dish) {
        List<Image> imageList = imagesName.stream()
                .map(image -> getImage(image, dish))
                .toList();
        imageRepository.saveAll(imageList);
    }

    @Override
    public void deleteAllImagesFromDbByDishId(Long dishId) {
        imageRepository.deleteAllByDish_Id(dishId);
    }

    private static Image getImage(String imageName, Dish dish) {
        return Image.builder()
                .dish(dish)
                .image(imageName)
                .build();
    }

}
