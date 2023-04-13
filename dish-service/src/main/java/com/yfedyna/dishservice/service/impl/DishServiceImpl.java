package com.yfedyna.dishservice.service.impl;

import com.yfedyna.dishservice.dto.DishFilterDto;
import com.yfedyna.dishservice.dto.DishRequestDto;
import com.yfedyna.dishservice.dto.DishResponseDto;
import com.yfedyna.dishservice.mapper.DishMapper;
import com.yfedyna.dishservice.model.Dish;
import com.yfedyna.dishservice.model.DishType;
import com.yfedyna.dishservice.repository.DishRepository;
import com.yfedyna.dishservice.service.DishService;
import com.yfedyna.dishservice.service.ImageService;
import com.yfedyna.dishservice.service.IngredientService;
import com.yfedyna.dishservice.service.ProductService;
import com.yfedyna.dishservice.util.DishValidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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

    @Transactional
    @Override
    public void createDish(DishRequestDto dishRequestDto, Long userId) {
//        dishValidate.checkIfDishExist(dishRequestDto.getName()); // подумати чизалишати перевірку на унікальність імені
        dishValidate.validateDishRequestDto(dishRequestDto);
        Dish dish = dishMapper.toDish(dishRequestDto, userId);
        dish = dishRepository.save(dish);
        ingredientService.saveAllIngredients(dishRequestDto.getIngredients(), dish);
    }

    @Transactional()
    @Override
    public DishResponseDto getDishById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        return dishMapper.toDishResponseDto(dish);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DishResponseDto> getAllDishes(Pageable pageable, DishFilterDto dishFilterDto, Long userId) {
        dishValidate.validateDishFilterDto(dishFilterDto);
        List<Long> dishIdList = getDishIdListByFilter(pageable, dishFilterDto, userId);
        dishIdList = getRandomDishIfRandomTrue(dishFilterDto.isRandom(), dishIdList);
        return dishRepository.findAllByIdIn(dishIdList).stream()
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

    @Transactional
    @Override
    public void deleteById(Long dishId, Long userId) {
        Dish dish = findDishById(dishId);
        imageService.deleteAllImagesFromDbByDishId(dishId);
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

    private List<Long> getDishIdListByFilter(Pageable pageable, DishFilterDto dishFilterDto, Long userId) {
        return dishRepository.findAllSQL(pageable,
                getDishTypes(dishFilterDto.getTypes()),
                dishFilterDto.getMyProducts(),
                userId,
                dishFilterDto.isMyDishes()
        ).stream().toList();
    }

    private static List<String> getDishTypes(List<String> types) {
        if (types == null || types.isEmpty()) {
            return null;
        }
        return types;
    }

    private static List<Long> getRandomDishIfRandomTrue(boolean isRandom, List<Long> dishIdList) {
        if(isRandom) {
            Long randomDishId = dishIdList.get(new Random().nextInt(dishIdList.size()));
            dishIdList = Collections.singletonList(randomDishId);
        }
        return dishIdList;
    }
}
