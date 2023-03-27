package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Long> {

    Optional<Dish> findByName(String name);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByName(String name);
}
