package com.yfedyna.dishservice.service;

import com.yfedyna.dishservice.model.Product;

public interface ProductService {
    Product getProductByName(String name);

    Product saveProduct(Product product);

    void deleteProductWithoutIngredient();
}
