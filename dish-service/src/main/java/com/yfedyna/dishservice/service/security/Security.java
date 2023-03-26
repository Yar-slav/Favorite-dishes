package com.yfedyna.dishservice.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class Security {

//    private final DiscoveryUrlService urlService;
    private final WebClient webClient;

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
        return webClient
                .get()
//                .uri(urlService.getUserServiceUrl() + "validate-auth-token")
                .uri("http://localhost:8091/user/validate-auth-token")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        error -> Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(401), "The token not valid or expired")))
                .bodyToMono(ValidationTokenDto.class)
                .block();
    }
}
