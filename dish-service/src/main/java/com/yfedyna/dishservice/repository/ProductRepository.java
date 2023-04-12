package com.yfedyna.dishservice.repository;

import com.yfedyna.dishservice.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByName(String name);


    @Query("SELECT p.id FROM Product p WHERE NOT EXISTS (SELECT i FROM Ingredient i WHERE i.product = p)")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Long> findProductsWithoutIngredients();

    void deleteAllByIdIn(List<Long> products);





}
