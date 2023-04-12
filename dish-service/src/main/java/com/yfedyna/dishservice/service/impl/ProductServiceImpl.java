package com.yfedyna.dishservice.service.impl;

import com.yfedyna.dishservice.model.Product;
import com.yfedyna.dishservice.repository.ProductRepository;
import com.yfedyna.dishservice.service.ProductService;
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
    public Product getProductByName(String name) {
        Optional<Product> productByName = productRepository.findByName(name);
        return productByName.orElseGet(() -> Product.builder()
                .name(name)
                .build());
    }

    @Override
    public Product saveProduct(Product product) {
        Product productForSave = getProductByName(product.getName());
        return productRepository.save(productForSave);
    }

    @Override
    public void deleteProductWithoutIngredient() {
        List<Long> productsWithoutIngredients = productRepository.findProductsWithoutIngredients();
        productRepository.deleteAllByIdIn(productsWithoutIngredients);
    }
}
