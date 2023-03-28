package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.dto.DishRequest;
import com.yfedyna.apigateway.dishservice.dto.DishResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DishService {
    void createDish(DishRequest dishRequest, List<MultipartFile> files, Long userIdByToken);

    DishResponseDto getDishById(Long dishId, Long userIdByToken);

    List<DishResponseDto> getAllDishes(Pageable pageable, String authHeader);

    DishResponseDto updateDish(Long id, DishRequest dishRequest, List<MultipartFile> files, Long userIdByToken);

    void deleteById(Long id, Long userIdByToken);
}
