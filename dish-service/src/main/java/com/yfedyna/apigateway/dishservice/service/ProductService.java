package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.model.Product;

public interface ProductService {
    Product getProductByName(String name);

    Product saveProduct(Product product);

    void deleteProductWithoutIngredient();
}
