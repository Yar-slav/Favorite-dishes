package com.yfedyna.apigateway.dishservice.service;

import com.yfedyna.apigateway.dishservice.model.Product;

public interface ProductService {
    Product getProductByName(String name);

    Product saveProduct(String productName);

    void deleteProductWithoudIngredient();
}
