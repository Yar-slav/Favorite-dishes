package com.yfedyna.userservice.service;

import com.yfedyna.userservice.dto.LoginResponse;
import com.yfedyna.userservice.dto.UserLoginRequest;
import com.yfedyna.userservice.dto.UserRegistrationRequest;
import com.yfedyna.userservice.model.UserEntity;

public interface AuthService {

    void register(UserRegistrationRequest userRegistrationRequestDto);

    LoginResponse login(UserLoginRequest request);

    UserEntity getUserEntityByToken(String authHeader);
}
