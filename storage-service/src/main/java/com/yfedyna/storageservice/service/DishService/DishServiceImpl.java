package com.yfedyna.storageservice.service.DishService;

import com.yfedyna.storageservice.dto.dish.DishResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
                .onStatus(HttpStatusCode::isError,
                        ex -> Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(404), "Dish not found!")))
                .bodyToMono(DishResponseDto.class)
                .block();
    }
}
