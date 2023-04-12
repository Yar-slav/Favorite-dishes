package com.yfedyna.dishservice.mapper;

import com.yfedyna.dishservice.dto.ProductRequestDto;
import com.yfedyna.dishservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequestDto productRequestDto);
}
