package com.yfedyna.apigateway.dishservice.mapper;

import com.yfedyna.apigateway.dishservice.dto.StorageService.ImageResponseDto;
import com.yfedyna.apigateway.dishservice.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageResponseDto toImageResponseDto(Image image);
}
