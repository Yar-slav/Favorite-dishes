package com.yfedyna.apigateway.dishservice.service.impl;

import com.yfedyna.apigateway.dishservice.dto.DishRequest;
import com.yfedyna.apigateway.dishservice.dto.DishResponseDto;
import com.yfedyna.apigateway.dishservice.dto.ImageResponseDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.DishType;
import com.yfedyna.apigateway.dishservice.repository.DishRepository;
import com.yfedyna.apigateway.dishservice.service.DishService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

//    private final StorageService storageService;

    // чому не працює debug якщо стоїть @Transactional
    @Transactional
    @Override
    public void createDish(DishRequest dishRequest, Long userId) {
//        checkIfDishExist(dishRequest.getName()); // подумати чизалишати перевірку на унікальність імені
        Dish dish = mapToDish(dishRequest);
        dish.setUserId(userId);
        dishRepository.save(dish);
    }

    @Override
    public DishResponseDto getDishById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        return getDishResponseDto(dish);
    }

    @Override
    public List<DishResponseDto> getAllDishes(Pageable pageable, String authHeader) {
        return dishRepository.findAll(pageable).stream()
                .map(DishServiceImpl::getDishResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public DishResponseDto updateDish(Long id, DishRequest dishRequest, Long userId) {
        Dish dish = findDishById(id);

        dish = dish.toBuilder()
                .name(dishRequest.getName())
                .type(DishType.valueOf(dishRequest.getType()))
                .description(dishRequest.getDescription())
                .build();
        dishRepository.save(dish);
        return getDishResponseDto(dish);
    }

    @Override
    public void deleteById(Long id, Long userIdByToken) {
        Dish dish = findDishById(id);
        dishRepository.delete(dish);
    }

    @Override
    public Dish findDishById(Long id) {
        Optional<Dish> dishOptional = dishRepository.findById(id);
        if(dishOptional.isPresent()) {
            return dishOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Dish not found");
        }
    }

    private static DishResponseDto getDishResponseDto(Dish dish) {
        List<ImageResponseDto> imageResponseDtoList = new ArrayList<>();
        for (int i = 0; i < dish.getImages().size(); i++) {
            ImageResponseDto imageResponseDto = ImageResponseDto.builder()
                    .id(dish.getImages().get(i).getId())
                    .image(dish.getImages().get(i).getImage())
                    .build();
            imageResponseDtoList.add(imageResponseDto);
        }
        return DishResponseDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .type(String.valueOf(dish.getType()))
                .description(dish.getDescription())
                .images(imageResponseDtoList)
                .userId(dish.getUserId())
                .build();
    }

    private static Dish mapToDish(DishRequest dishRequest) {
        return Dish.builder()
                .name(dishRequest.getName())
                .type(DishType.valueOf(dishRequest.getType()))
                .description(dishRequest.getDescription())
                .build();
    }

    private void checkIfDishExist(String name) {
        if(dishRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Dish with this name already exist");
        }
    }
}
