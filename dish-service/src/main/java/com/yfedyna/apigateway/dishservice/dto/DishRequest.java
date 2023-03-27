package com.yfedyna.apigateway.dishservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishRequest {

    private String name;
    private String type;
    private String description;
    private String image;
}
