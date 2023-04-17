package com.yfedyna.userservice.controller;

import com.yfedyna.userservice.dto.LoginResponse;
import com.yfedyna.userservice.service.AuthService;
import com.yfedyna.userservice.dto.UserLoginRequest;
import com.yfedyna.userservice.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/auth")
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
