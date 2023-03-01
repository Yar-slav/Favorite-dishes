package com.yfedyna.controller;

import com.yfedyna.dto.LoginResponse;
import com.yfedyna.dto.UserLoginRequest;
import com.yfedyna.dto.UserRegistrationRequest;
import com.yfedyna.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
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
