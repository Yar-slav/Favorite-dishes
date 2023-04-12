package com.yfedyna.dishservice.repository;

import com.yfedyna.dishservice.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    void deleteAllByDish_Id(Long dishId);

}
