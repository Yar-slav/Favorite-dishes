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
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(name = "uc_product_name", columnNames = {"name"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "product")
    private List<Ingredient> ingredients = new ArrayList<>();
}
