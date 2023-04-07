package com.yfedyna.apigateway.dishservice.util;

import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DishFiltering {

    public List<DishType> getDishTypes(List<String> types) {
        if (types == null || types.isEmpty()) {
            return null;
        }
        List<DishType> dishTypes = new ArrayList<>();
        for (String type : types) {
            try {
                dishTypes.add(DishType.valueOf(type.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "Type not found. Please choose one of these: " + types);
            }
        }
        return dishTypes;
    }

//    public List<DishType> getDishTypes(List<String> types) {
//        List<DishType> dishTypes = new ArrayList<>();
//        for (String type : types) {
//            try {
//                dishTypes.add(DishType.valueOf(type.toUpperCase()));
//            } catch (IllegalArgumentException e) {
//                throw new ResponseStatusException(HttpStatusCode.valueOf(404),
//                        "Type not found. Please choose one of these: " + types);
//            }
//        }
//        return dishTypes;
//    }

    public List<Dish> getRandomDishIfIsRandomTrue(boolean isRandom, List<Dish> dishes) {
        if (isRandom) {
            Dish dish = dishes.get(new Random().nextInt(dishes.size()));
            dishes = new ArrayList<>(List.of(dish));
        }
        return dishes;
    }

}
