package com.yfedyna.apigateway.dishservice.controller;

import com.yfedyna.apigateway.dishservice.dto.DishRequestDto;
import com.yfedyna.apigateway.dishservice.dto.DishResponseDto;
import com.yfedyna.apigateway.dishservice.service.DishService;
import com.yfedyna.apigateway.dishservice.service.security.Roles;
import com.yfedyna.apigateway.dishservice.service.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;
    private final Security security;

    @PostMapping
    public void addDish(
            @RequestBody DishRequestDto dishRequestDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        dishService.createDish(dishRequestDto, userId);
    }



    @GetMapping("/{id}")
    public DishResponseDto getDishById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        return dishService.getDishById(id, userId);
    }

    @GetMapping
    public List<DishResponseDto> getAllDishes(
            Pageable pageable,
            @RequestHeader("Authorization") String authHeader
    ) {
        return dishService.getAllDishes(pageable, authHeader);
    }

    @PutMapping("/{id}")
    public DishResponseDto update(
            @PathVariable Long id,
            @RequestBody DishRequestDto dishRequestDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        return dishService.updateDish(id, dishRequestDto, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = security.getUserIdByToken(authHeader, Set.of(Roles.USER));
        dishService.deleteById(id, userId);
    }

}
