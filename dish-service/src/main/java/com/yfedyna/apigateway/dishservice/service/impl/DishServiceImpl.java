package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.*;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.model.Image;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import com.yfedyna.apigateway.dishservice.service.DishService;
import com.yfedyna.apigateway.dishservice.service.IngredientService;
import com.yfedyna.apigateway.dishservice.service.ProductService;
import com.yfedyna.apigateway.dishservice.util.DishFiltering;
import com.yfedyna.apigateway.dishservice.util.DishValidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ProductService productService;
    private final IngredientService ingredientService;
    private final DishValidate dishValidate;

    private final DishFiltering dishFiltering;


    @Transactional
    @Override
    public void createDish(DishRequestDto dishRequestDto, Long userId) {
//        dishValidate.checkIfDishExist(dishRequestDto.getName()); // подумати чизалишати перевірку на унікальність імені
        dishValidate.validateDishRequestDto(dishRequestDto);
        Dish dish = mapToDish(dishRequestDto);
        dish.setUserId(userId);
        dish = dishRepository.save(dish);
        ingredientService.saveAllIngredients(dishRequestDto.getIngredients(), dish);
    }

    @Override
    public DishResponseDto getDishById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        return mapToDishResponseDto(dish);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DishResponseDto> getAllDishes(Pageable pageable, DishFilterDto dishFilterDto, Long userId) {
        dishValidate.validateDishFilterDto(dishFilterDto);
        List<Dish> dishes;
        dishes = dishFiltering.getAllMyDishesOrAllDishes(pageable, dishFilterDto.isMyDishes(), userId);
        dishes = dishFiltering.getDishesByMyProductList(dishFilterDto, dishes);
        dishes = dishFiltering.sortByTypes(dishFilterDto.getTypes(), dishes);
        dishes = dishFiltering.getRandomDishIfIsRandomTrue(dishFilterDto.isRandom(), dishes);
        return dishes.stream()
                .map(this::mapToDishResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public DishResponseDto updateDish(Long dishId, DishRequestDto dishRequestDto, Long userId) {
        dishValidate.validateDishRequestDto(dishRequestDto);
        Dish dish = findDishById(dishId);
        dishValidate.checkDishOwner(userId, dish.getUserId());

        dish = getNewDish(dishRequestDto, dish);
        dish = dishRepository.save(dish);

        ingredientService.deleteAllIngredientsByDishId(dish.getId());
        dish = ingredientService.saveAllIngredients(dishRequestDto.getIngredients(), dish);
        productService.deleteProductWithoutIngredient();
        return mapToDishResponseDto(dish);
    }

    @Transactional(readOnly = true)
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

    private Dish mapToDish(DishRequestDto dishRequestDto) {
        return Dish.builder()
                .name(dishRequestDto.getName())
                .type(DishType.valueOf(dishRequestDto.getType()))
                .description(dishRequestDto.getDescription())
                .build();
    }

    private DishResponseDto mapToDishResponseDto(Dish dish) {
        List<ImageResponseDto> imageResponseDtoList = getImageResponseDtoList(dish.getImages());
        List<IngredientResponseDto> ingredientRequestDtoList = getIngredientResponseDtoList(dish.getIngredients());
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

    private static List<ImageResponseDto> getImageResponseDtoList(List<Image> images) {
        List<ImageResponseDto> imageResponseDtoList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i).getImage();
            ImageResponseDto imageResponseDto = ImageResponseDto.builder()
                    .id(images.get(i).getId())
                    .image(image)
                    .build();
            imageResponseDtoList.add(imageResponseDto);
        }
        return imageResponseDtoList;
    }

    private List<IngredientResponseDto> getIngredientResponseDtoList(List<Ingredient> ingredientList) {
        List<IngredientResponseDto> ingredientRequestDtoList = new ArrayList<>();
        for (int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            IngredientResponseDto ingredientResponseDto = ingredientService.mapToIngredientResponseDto(ingredient);
            ingredientRequestDtoList.add(ingredientResponseDto);
        }
        return ingredientRequestDtoList;
    }

    private static Dish getNewDish(DishRequestDto dishRequestDto, Dish dish) {
        return dish.toBuilder()
                .name(dishRequestDto.getName())
                .type(DishType.valueOf(dishRequestDto.getType()))
                .description(dishRequestDto.getDescription())
                .build();
    }
}
