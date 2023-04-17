package com.yfedyna.storageservice.service.client;

import com.yfedyna.storageservice.dto.dish.DishResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "dish-service")
public interface DishClient {

    @GetMapping("/dishes/{dishId}")
    DishResponseDto getDishById(
            @PathVariable Long dishId,
            @RequestHeader("Authorization") String authHeader
    );
}
