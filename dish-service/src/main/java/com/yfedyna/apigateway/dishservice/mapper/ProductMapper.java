package com.yfedyna.apigateway.dishservice.mapper;

import com.yfedyna.apigateway.dishservice.dto.ProductRequestDto;
import com.yfedyna.apigateway.dishservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequestDto productRequestDto);
}
