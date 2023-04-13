package com.yfedyna.dishservice.repository;

import com.yfedyna.dishservice.model.Dish;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByName(String name);

    List<Dish> findAllByIdIn(List<Long> dishIdList);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Dish> findById(Long id);

    @Query(value = "SELECT DISTINCT d.id  " +
            "FROM dishes as d " +
            "LEFT JOIN ingredients as i ON d.id = i.dish_id " +
            "LEFT JOIN products as p ON i.product_id = p.id " +
            "LEFT JOIN images as img ON d.id = img.dish_id " +

            // дозволяє використовувати параметр types для вибору страв певного типу.
            // Якщо параметр не заданий (NULL), то виводяться страви будь-якого типу.
            "WHERE (:types IS NULL OR d.type IN (:types)) " +

            // обмежує вибірку лише на інгредієнти, позначені як важливі.
            "AND i.status_ingredient = 'ESSENTIAL' " +

            // перевіряє, що для даної страви не існує іншого інгредієнта, який позначений як ESSENTIAL,
            // і не міститься у myProducts.
            "AND NOT EXISTS (SELECT * FROM ingredients i2 " +
            "LEFT JOIN dishes d2 ON i2.dish_id = d2.id " +
            "LEFT JOIN  products p2 ON i2.product_id = p2.id " +
            "WHERE d2.id = d.id AND p2.name NOT IN (:myProducts)) " +

            // якщо isMyDishes = true то витягує страви у яких поле userId дорівнює переданому значенню userId.
            // Інакше будуть витягнуті всі страви
            "AND (:isMyDishes = false OR d.user_id = :userId) "
            , nativeQuery = true)
    Page<Long> findAllSQL(Pageable pageable,
                          @Param("types") List<String> types,
                          @Param("myProducts") List<String> myProducts,
                          @Param("userId") Long userId,
                          @Param("isMyDishes") boolean isMyDishes
    );
}
