package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByName(String name);

    Optional<Dish> findById(Long id);
//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "ingredients")
    Page<Dish> findAll(Pageable pageable);

//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "ingredients")
    Page<Dish> findAllByUserId(Pageable pageable, Long userId);

//    Page<DishResponseDto> findAllBy(Pageable pageable);

//    @Query(value = "SELECT d FROM Dish d " +
//            "LEFT JOIN FETCH Ingredient i ON d.id = i.dish.id " +
//            "LEFT JOIN FETCH Product p ON i.product.id = p.id " +
//            "LEFT JOIN FETCH Image img ON d.id = img.dish.id")
//    Page<Dish> findAllSQL(Pageable pageable);



}
