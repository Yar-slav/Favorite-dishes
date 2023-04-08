package com.storageservice.service.DishService;

import com.storageservice.dto.dish.DishResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final WebClient.Builder webClientBuilder;



    @Override
    public DishResponseDto getDishById(Long dishId, String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://dish-service/dish/" + dishId)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(DishResponseDto.class)
                .block();

    }
}
