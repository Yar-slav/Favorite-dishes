package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.*;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import com.yfedyna.apigateway.dishservice.service.DishService;
import com.yfedyna.apigateway.dishservice.service.IngredientService;
import com.yfedyna.apigateway.dishservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ProductService productService;
    private final IngredientService ingredientService;


    @Transactional
    @Override
    public void createDish(DishRequestDto dishRequestDto, Long userId) {
//        checkIfDishExist(dishRequest.getName()); // подумати чизалишати перевірку на унікальність імені
        List<IngredientRequestDto> dishRequestDtoIngredients = dishRequestDto.getIngredients();

        Dish dish = mapToDish(dishRequestDto);
        dish.setUserId(userId);
        dish = dishRepository.save(dish);

        ingredientService.saveAllIngredients(dishRequestDtoIngredients, dish);

    }

    @Override
    public DishResponseDto getDishById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        return mapToDishResponseDto(dish);
    }

    @Override
    public List<DishResponseDto> getAllDishes(Pageable pageable, String authHeader) {
        return dishRepository.findAll(pageable).stream()
                .map(this::mapToDishResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto, Long userId) {
        Dish dish = findDishById(id);
        // TODO: 3/29/23 get ingredients from dishRequestDto and sent to dish
        dish.toBuilder()
                .name(dishRequestDto.getName())
                .type(DishType.valueOf(dishRequestDto.getType()))
                .description(dishRequestDto.getDescription())
//                .ingredients()
                .build();
        dishRepository.save(dish);
        return mapToDishResponseDto(dish);
    }

    @Override
    public void deleteById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        dishRepository.delete(dish);
    }

    @Override
    public Dish findDishById(Long id) {
        Optional<Dish> dishOptional = dishRepository.findById(id);
        if (dishOptional.isPresent()) {
            return dishOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Dish not found");
        }
    }

    private DishResponseDto mapToDishResponseDto(Dish dish) {
        List<ImageResponseDto> imageResponseDtoList = new ArrayList<>();
        for (int i = 0; i < dish.getImages().size(); i++) {
            ImageResponseDto imageResponseDto = ImageResponseDto.builder()
                    .id(dish.getImages().get(i).getId())
                    .image(dish.getImages().get(i).getImage())
                    .build();
            imageResponseDtoList.add(imageResponseDto);
        }

        List<IngredientResponseDto> ingredientRequestDtoList = new ArrayList<>();
        for (int i = 0; i < dish.getIngredients().size(); i++) {
            Ingredient ingredient = dish.getIngredients().get(i);
            IngredientResponseDto ingredientResponseDto = ingredientService.mapToIngredientResponseDto(ingredient);
            ingredientRequestDtoList.add(ingredientResponseDto);
        }
        return DishResponseDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .type(String.valueOf(dish.getType()))
                .description(dish.getDescription())
                .images(imageResponseDtoList)
                .ingredients(ingredientRequestDtoList)
                .userId(dish.getUserId())
                .build();
    }

    private Dish mapToDish(DishRequestDto dishRequestDto) {
        return Dish.builder()
                .name(dishRequestDto.getName())
                .type(DishType.valueOf(dishRequestDto.getType()))
                .description(dishRequestDto.getDescription())
                .build();
    }

    private void checkIfDishExist(String name) {
        if (dishRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Dish with this name already exist");
        }
    }
}
