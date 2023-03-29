package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByName(String name);

    Page<Dish> findAll(Pageable pageable);
}
