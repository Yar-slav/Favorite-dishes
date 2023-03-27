package com.yfedyna.apigateway.userservice.service;

import com.yfedyna.apigateway.userservice.dto.LoginResponse;
import com.yfedyna.apigateway.userservice.dto.UserLoginRequest;
import com.yfedyna.apigateway.userservice.dto.UserRegistrationRequest;
import com.yfedyna.apigateway.userservice.model.UserEntity;

public interface AuthService {

    void register(UserRegistrationRequest userRegistrationRequestDto);

    LoginResponse login(UserLoginRequest request);

    UserEntity getUserEntityByToken(String authHeader);
}
