package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.dto.IngredientResponseDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.Ingredient;
import com.yfedyna.apigateway.dishservice.model.Product;
import com.yfedyna.apigateway.dishservice.repository.IngredientRepository;
import com.yfedyna.apigateway.dishservice.service.IngredientService;
import com.yfedyna.apigateway.dishservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .build();
    }

    @Override
    public IngredientResponseDto mapToIngredientResponseDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .amount(ingredient.getAmount())
                .productName(ingredient.getProduct().getName())
                .build();
    }

    @Override
    public void saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish){
        List<Ingredient> ingredients = new ArrayList<>();
        for(IngredientRequestDto ingredientRequestDto: dishRequestDtoIngredients){
            String productName = ingredientRequestDto.getProductName();
            Product product = productService.saveProduct(productName);

            Ingredient ingredient = mapToIngredient(ingredientRequestDto, product);
            ingredient.setDish(dish);
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);
    }
}
