package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.IngredientRequestDto;
import com.yfedyna.apigateway.dishservice.dto.ProductRequestDto;
import com.yfedyna.apigateway.dishservice.mapper.IngredientMapper;
import com.yfedyna.apigateway.dishservice.mapper.ProductMapper;
import com.yfedyna.apigateway.dishservice.model.Dish;
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

    private final IngredientMapper ingredientMapper;
    private final ProductMapper productMapper;

    @Override
    public void deleteAllIngredientsByDishId(Long dishId) {
        ingredientRepository.deleteAllByDish_Id(dishId);
    }


    @Override
    public Dish saveAllIngredients(List<IngredientRequestDto> dishRequestDtoIngredients, Dish dish){
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientRequestDto ingredientRequestDto: dishRequestDtoIngredients) {
            ProductRequestDto productRequestDto = ingredientRequestDto.getProduct();
            Product product = productMapper.toProduct(productRequestDto);
            product = productService.saveProduct(product);

            Ingredient ingredient = ingredientMapper.toIngredient(ingredientRequestDto);
            ingredient.setProduct(product);
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
