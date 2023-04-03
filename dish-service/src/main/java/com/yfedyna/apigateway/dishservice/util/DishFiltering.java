package com.yfedyna.apigateway.dishservice.util;

import com.yfedyna.apigateway.dishservice.dto.DishFilterDto;
import com.yfedyna.apigateway.dishservice.model.*;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DishFiltering {

    private final DishRepository dishRepository;

    public List<Dish> getAllMyDishesOrAllDishes(Pageable pageable, boolean isMyDishes, Long userId) {
        List<Dish> dishes;
        if (isMyDishes) {
            dishes = dishRepository.findAllByUserId(pageable, userId).stream().toList();
        } else {
            dishes = dishRepository.findAll(pageable).stream().toList();
        }
        return dishes;
    }

    public List<Dish> getDishesByMyProductList(DishFilterDto dishFilterDto, List<Dish> dishes) {
        List<String> myProducts = dishFilterDto.getMyProducts();
        dishes = dishes.stream()
                .filter(dish -> myProducts == null
                        || myProducts.isEmpty()
                        || myProducts.containsAll(getProductsName(dish)))
                .toList();
        return dishes;
    }

    private List<String> getProductsName(Dish dish) {
        return dish.getIngredients().stream()
                .filter(ingredient -> ingredient.getImportantIngredient()
                        .equals(ImportantIngredient.ESSENTIAL))
                .map(Ingredient::getProduct)
                .map(Product::getName)
                .toList();
    }

    public List<Dish> sortByTypes(List<String> types, List<Dish> dishes) {
        return dishes.stream()
                .filter(dish -> types.isEmpty() || getDishTypes(types)
                        .contains(dish.getType()))
                .toList();
    }

    private static List<DishType> getDishTypes(List<String> types) {
        return types.stream()
                .map(DishType::valueOf)
                .toList();
    }

    public List<Dish> getRandomDishIfIsRandomTrue(boolean isRandom, List<Dish> dishes) {
        if (isRandom) {
            Dish dish = dishes.get(new Random().nextInt(dishes.size() - 1));
            dishes = new ArrayList<>(List.of(dish));
        }
        return dishes;
    }

}
