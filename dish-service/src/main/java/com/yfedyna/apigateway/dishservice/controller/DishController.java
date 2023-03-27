package com.yfedyna.apigateway.dishservice.controller;

import com.yfedyna.apigateway.dishservice.dto.DishRequest;
import com.yfedyna.apigateway.dishservice.service.DishService;
import com.yfedyna.apigateway.dishservice.service.security.Roles;
import com.yfedyna.apigateway.dishservice.service.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;
    private final Security security;

    @PostMapping
    public void addDish(@RequestBody DishRequest dishRequest,
                        @RequestHeader("Authorization") String authHeader
    ) {
        Long userIdByToken = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        dishService.createDish(dishRequest, userIdByToken);

    }
}
