package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.dto.IngredientResponseDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.ImportantIngredient;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import com.yfedyna.apigateway.dishservice.model.Product;
import com.yfedyna.apigateway.dishservice.repository.IngredientRepository;
import com.yfedyna.apigateway.dishservice.service.IngredientService;
import com.yfedyna.apigateway.dishservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final ProductService productService;
    private final IngredientRepository ingredientRepository;
    @Override
    public Ingredient mapToIngredient(IngredientRequestDto ingredientRequestDto, Product product) {
        return Ingredient.builder()
                .amount(ingredientRequestDto.getAmount())
                .product(product)
                .importantIngredient(ImportantIngredient.valueOf(ingredientRequestDto.getImportantIngredient()))
                .build();
    }

    @Override
    public IngredientResponseDto mapToIngredientResponseDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .amount(ingredient.getAmount())
                .productName(ingredient.getProduct().getName())
                .importantIngredient(String.valueOf(ingredient.getImportantIngredient()))
                .build();
    }

    @Override
    public void deleteAllIngredientsByDishId(Long dishId) {
        ingredientRepository.deleteAllByDish_Id(dishId);
    }


    @Override
    public Dish saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish){
        List<Ingredient> ingredients = new ArrayList<>();
        for(IngredientRequestDto ingredientRequestDto: dishRequestDtoIngredients){
            String productName = ingredientRequestDto.getProductName();
            Product product = productService.saveProduct(productName);

            Ingredient ingredient = mapToIngredient(ingredientRequestDto, product);
            ingredient.setDish(dish);
            ingredients.add(ingredient);
        }
        checkingUniquenessOfTheIngredientsInDish(ingredients);

        ingredientRepository.saveAll(ingredients);
        dish.setIngredients(ingredients);
        return dish;
    }

    private static void checkingUniquenessOfTheIngredientsInDish(List<Ingredient> ingredients) {
        Set<Ingredient> ingredientSet = new HashSet<>(ingredients);
        if (ingredientSet.size() < ingredients.size()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ingredient with this product already exist");
        }
    }
}
