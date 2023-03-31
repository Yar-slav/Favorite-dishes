package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByName(String name);

    Optional<Dish> findById(Long id);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "ingredients")
    Page<Dish> findAll(Pageable pageable);

//    Page<DishResponseDto> findAllBy(Pageable pageable);

//    @Query(value = "SELECT d.id, d.name, d.type, d.description, d.userId, i.product.name, i.amount, img.id, img.image FROM Dish d " +
//            "LEFT JOIN Ingredient i ON d.id = i.dish.id " +
//            "LEFT JOIN Product p ON i.product.id = p.id " +
//            "LEFT JOIN Image img ON d.id = img.dish.id")
//    Page<Dish> findAllSQL(Pageable pageable);



}
