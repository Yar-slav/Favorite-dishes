package com.yfedyna.apigateway.dishservice.dto;

import com.yfedyna.apigateway.dishservice.model.DishType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishFilterDto {

    List<String> types;
    List<String> myProducts;
    boolean random;
    boolean myDishes;

    public List<String> getTypes() {
        List<String> typesList = new ArrayList<>();
        try{
            if (types == null){
                return typesList;
            }
            for (String type: types) {
                String value = String.valueOf(DishType.valueOf(type));
                typesList.add(value);
            }
            return typesList;
        } catch (IllegalArgumentException e) {
            List<String> types = Arrays.stream(DishType.values())
                    .map(Enum::toString)
                    .toList();
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "Type not found. Please choose one of these: " + types);
        }
    }
}
