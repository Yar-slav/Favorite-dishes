package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.model.Product;
import com.yfedyna.apigateway.dishservice.repository.ProductRepository;
import com.yfedyna.apigateway.dishservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public Product getProductByName(String name){
        Optional<Product> productByName = productRepository.findByName(name);
        return productByName.orElseGet(() -> Product.builder()
                .name(name)
                .build());
    }

    @Override
    public Product saveProduct(String productName) {
        Product product = getProductByName(productName);
        return productRepository.save(product);
    }

    @Override
    public void deleteProductWithoudIngredient(){

        List<Long> productsWithoutIngredients = productRepository.findProductsWithoutIngredients();
        productRepository.deleteAllByIdIn(productsWithoutIngredients);
        log.info("product {}", productsWithoutIngredients);
    }
}
