package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    void deleteAllByDish_Id(Long dishId);

}
