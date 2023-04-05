package com.yfedyna.apigateway.dishservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DishType type;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "dish")
//    @BatchSize(size = 100)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "dish")
//    @BatchSize(size = 100)
    private List<Ingredient> ingredients = new ArrayList<>();
}
