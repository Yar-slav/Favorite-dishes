package com.yfedyna.apigateway.dishservice.util;

import com.yfedyna.apigateway.dishservice.dto.DishFilterDto;
import com.yfedyna.apigateway.dishservice.dto.DishRequestDto;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DishValidate {

    private final DishRepository dishRepository;

    public void validateDishRequestDto(DishRequestDto dishRequestDto){
       validateDishType(dishRequestDto.getType());
    }

    public void validateDishFilterDto(DishFilterDto dishFilterDto) {
        validateDishTypes(dishFilterDto.getTypes());
    }

    public void validateDishType(String typeRequest){
        List<String> types = Arrays.stream(DishType.values())
                .map(Enum::toString)
                .toList();
        if(!types.contains(typeRequest)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "Type not found. Please choose one of these: " + types);
        }
    }

    public void validateDishTypes(List<String> types) {
        if(types != null){
            for(String type: types){
                validateDishType(type);
            }
        }
    }

    public void checkDishOwner(Long userId, Long dishId) {
        if(!dishId.equals(userId)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "You can not update this dish");
        }
    }

    public void checkIfDishExist(String name) {
        if (dishRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Dish with this name already exist");
        }
    }


}
