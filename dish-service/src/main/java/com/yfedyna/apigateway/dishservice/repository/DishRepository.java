package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByName(String name);

    Optional<Dish> findById(Long id);

    @Query(value = "SELECT DISTINCT d FROM Dish d " +
            "LEFT JOIN FETCH Ingredient i ON d.id = i.dish.id " +
            "LEFT JOIN FETCH Product p ON i.product.id = p.id " +
            "LEFT JOIN FETCH Image img ON d.id = img.dish.id " +

            // дозволяє використовувати параметр types для вибору страв певного типу.
            // Якщо параметр не заданий (NULL), то виводяться страви будь-якого типу.
            "WHERE (:types IS NULL OR (d.type IN :types AND d.type IS NOT NULL)) " +
//            "WHERE (d.type IN :types AND d.type IS NOT NULL) " +

            // обмежує вибірку лише на інгредієнти, позначені як важливі.
            "AND i.importantIngredient = 'ESSENTIAL' " +

            // перевіряє, що для даної страви не існує іншого інгредієнта, який позначений як ESSENTIAL,
            // і не міститься у myProducts.
            "AND NOT EXISTS (SELECT i2 FROM Ingredient i2 WHERE i2.dish.id = d.id " +
            "AND i2.importantIngredient = 'ESSENTIAL' " +
            "AND i2.product.name NOT IN :myProducts) " +

            // якщо isMyDishes = true то витягує страви у яких поле userId дорівнює переданому значенню userId.
            // Інакше будуть витягнуті всі страви
            "AND (:isMyDishes = false OR d.userId = :userId)"
    )
    Page<Dish> findAllSQL(Pageable pageable,
                          @Param("types") List<DishType> types,
                          @Param("myProducts") List<String> myProducts,
                          @Param("userId") Long userId,
                          @Param("isMyDishes") boolean isMyDishes
    );
}
