package com.yfedyna.apigateway.userservice.controller;

import com.yfedyna.apigateway.userservice.dto.LoginResponse;
import com.yfedyna.apigateway.userservice.service.AuthService;
import com.yfedyna.apigateway.userservice.dto.UserLoginRequest;
import com.yfedyna.apigateway.userservice.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(
            @Valid @RequestBody UserRegistrationRequest requestDto) {
        authService.register(requestDto);
    }

    @GetMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody UserLoginRequest request) {
        return authService.login(request);
    }
}
