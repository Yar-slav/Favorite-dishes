package com.yfedyna.apigateway.dishservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(
            name = "dish_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_image_dish"))
    private Dish dish;

    @Lob
    @Column(name = "image")
    private String image;
}
