package com.yfedyna.apigateway.userservice.service;

import com.yfedyna.apigateway.userservice.model.UserEntity;
import com.yfedyna.apigateway.userservice.dto.UserInfoResponse;
import com.yfedyna.apigateway.userservice.dto.ValidationTokenDto;

public interface UserService {

    UserInfoResponse getUserInfo(UserEntity userEntity);

    ValidationTokenDto validateAuthToken(UserEntity userEntity);
}
