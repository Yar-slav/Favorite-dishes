package com.yfedyna.dishservice.mapper;

import com.yfedyna.dishservice.dto.DishRequestDto;
import com.yfedyna.dishservice.dto.DishResponseDto;
import com.yfedyna.dishservice.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "userId", source = "userId")
    Dish toDish(DishRequestDto dishRequestDto, Long userId);

    DishResponseDto toDishResponseDto(Dish dish);


}
