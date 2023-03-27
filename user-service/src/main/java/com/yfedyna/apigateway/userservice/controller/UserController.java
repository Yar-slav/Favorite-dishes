package com.yfedyna.apigateway.userservice.controller;

import com.yfedyna.apigateway.userservice.dto.UserInfoResponse;
import com.yfedyna.apigateway.userservice.model.UserEntity;
import com.yfedyna.apigateway.userservice.service.AuthService;
import com.yfedyna.apigateway.userservice.service.UserService;
import com.yfedyna.apigateway.userservice.dto.ValidationTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping()
    public UserInfoResponse getUserInfo(@RequestHeader("Authorization") String authHeader) {
        UserEntity userEntity = authService.getUserEntityByToken(authHeader);
        return userService.getUserInfo(userEntity);
    }

    @GetMapping("/validate-auth-token")
    public ValidationTokenDto validationTokenDto(
            @RequestHeader("Authorization") String authHeader){
        UserEntity userEntity = authService.getUserEntityByToken(authHeader);
        return userService.validateAuthToken(userEntity);
    }
}
