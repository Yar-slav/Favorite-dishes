package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.DishFilterDto;
import com.yfedyna.apigateway.dishservice.dto.DishRequestDto;
import com.yfedyna.apigateway.dishservice.dto.DishResponseDto;
import com.yfedyna.apigateway.dishservice.mapper.DishMapper;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import com.yfedyna.apigateway.dishservice.service.*;
import com.yfedyna.apigateway.dishservice.util.DishFiltering;
import com.yfedyna.apigateway.dishservice.util.DishValidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    private final ProductService productService;
    private final IngredientService ingredientService;
    private final ImageService imageService;

    private final DishMapper dishMapper;
    private final DishValidate dishValidate;

    private final DishFiltering dishFiltering;

    @Transactional
    @Override
    public void createDish(DishRequestDto dishRequestDto, Long userId) {
//        dishValidate.checkIfDishExist(dishRequestDto.getName()); // подумати чизалишати перевірку на унікальність імені
        dishValidate.validateDishRequestDto(dishRequestDto);
        Dish dish = dishMapper.toDish(dishRequestDto, userId);
        dish = dishRepository.save(dish);
        ingredientService.saveAllIngredients(dishRequestDto.getIngredients(), dish);
    }

    @Override
    public DishResponseDto getDishById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        return dishMapper.toDishResponseDto(dish);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DishResponseDto> getAllDishes(Pageable pageable, DishFilterDto dishFilterDto, Long userId) {
        dishValidate.validateDishFilterDto(dishFilterDto);

        List<Dish> dishes = dishRepository.findAllSQL(pageable,
                dishFiltering.getDishTypes(dishFilterDto.getTypes()),
                dishFilterDto.getMyProducts(),
                userId,
                dishFilterDto.isMyDishes()
        ).stream().toList();
        dishes = dishFiltering.getRandomDishIfIsRandomTrue(dishFilterDto.isRandom(), dishes);
        return dishes.stream()
                .map(dishMapper::toDishResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public DishResponseDto updateDish(Long dishId, DishRequestDto dishRequestDto, Long userId) {
        dishValidate.validateDishRequestDto(dishRequestDto);
        Dish dish = findDishById(dishId);
        dishValidate.checkDishOwner(userId, dish.getUserId());

        dish = getNewDish(dishRequestDto, dish);
        ingredientService.deleteAllIngredientsByDishId(dish.getId());
        dish = ingredientService.saveAllIngredients(dishRequestDto.getIngredients(), dish);
        productService.deleteProductWithoutIngredient();

        return dishMapper.toDishResponseDto(dish);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteById(Long dishId, Long userIdByToken) {
        Dish dish = findDishById(dishId);
        // TODO: 4/7/23  delete image from storage
//        imageService.deleteAllImagesFromDbByDishId(dishId);
        ingredientService.deleteAllIngredientsByDishId(dishId);
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

    private static Dish getNewDish(DishRequestDto dishRequestDto, Dish dish) {
        return dish.toBuilder()
                .name(dishRequestDto.getName())
                .type(DishType.valueOf(dishRequestDto.getType()))
                .description(dishRequestDto.getDescription())
                .build();
    }
}
