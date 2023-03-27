package com.yfedyna.apigateway.dishservice.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class Security {
    private final WebClient.Builder webClientBuilder;

    public Long getUserIdByToken(String token, Set<Roles> roles) {
        if (token == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "You are not logged");
        }
        ValidationTokenDto rolesAndUser = getRolesAndUser(token);
        Set<String> tokenRoles = rolesAndUser.getRoles();
        if (tokenRoles.stream().noneMatch(roles.toString()::contains)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "You dont have permissions for this action");
        }
        return rolesAndUser.getUserId();
    }

    public ValidationTokenDto getRolesAndUser(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service/user/validate-auth-token")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(401), "The token not valid or expired")))
                .bodyToMono(ValidationTokenDto.class)
                .block();
    }
}
