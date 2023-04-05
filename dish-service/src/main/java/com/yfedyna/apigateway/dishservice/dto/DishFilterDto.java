package com.yfedyna.apigateway.dishservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishFilterDto {

    List<String> types;
    List<String> myProducts;
    boolean random;
    boolean myDishes;
}
