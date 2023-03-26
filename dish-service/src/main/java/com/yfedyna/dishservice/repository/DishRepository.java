package com.yfedyna.dishservice.repository;

import com.yfedyna.dishservice.model.Dish;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Long> {

    Optional<Dish> findByName(String name);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByName(String name);
}
