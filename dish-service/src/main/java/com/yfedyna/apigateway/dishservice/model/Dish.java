package com.yfedyna.apigateway.dishservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "dishes",
        uniqueConstraints = {@UniqueConstraint(name = "uc_name", columnNames = {"name"})})
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

    @Column(name = "image")
    private String image;

    @Column(name = "user_id")
    private Long userId;
}
