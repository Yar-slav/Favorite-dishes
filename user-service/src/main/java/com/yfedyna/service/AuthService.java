package com.yfedyna.service;

import com.yfedyna.dto.LoginResponse;
import com.yfedyna.dto.UserLoginRequest;
import com.yfedyna.dto.UserRegistrationRequest;
import com.yfedyna.model.UserEntity;

public interface AuthService {

    void register(UserRegistrationRequest userRegistrationRequestDto);

    LoginResponse login(UserLoginRequest request);

    UserEntity getUserEntityByToken(String authHeader);
}
