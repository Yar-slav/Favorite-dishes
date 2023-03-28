package com.yfedyna.apigateway.dishservice.repository;

import com.yfedyna.apigateway.dishservice.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    void deleteAllByDish_Id(Long dishId);
}
