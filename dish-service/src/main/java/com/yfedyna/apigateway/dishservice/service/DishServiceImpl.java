package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import com.yfedyna.apigateway.dishservice.dto.DishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Transactional
    @Override
    public void createDish(DishRequest dishRequest, Long userId) {
        checkIfDishExist(dishRequest.getName());
        Dish dish = mapToDish(dishRequest);
        dish.setUserId(userId);
        dishRepository.save(dish);
    }

    private static Dish mapToDish(DishRequest dishRequest) {
        return Dish.builder()
                .name(dishRequest.getName())
                .type(DishType.valueOf(dishRequest.getType()))
                .description(dishRequest.getDescription())
                .image(dishRequest.getImage())
                .build();
    }

    private void checkIfDishExist(String name) {
        if(dishRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Dhis with this name already exist");
        }
    }
}
