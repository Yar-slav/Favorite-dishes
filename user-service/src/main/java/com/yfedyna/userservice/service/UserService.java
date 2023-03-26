package com.yfedyna.userservice.service;

import com.yfedyna.userservice.dto.UserInfoResponse;
import com.yfedyna.userservice.dto.ValidationTokenDto;
import com.yfedyna.userservice.model.UserEntity;

public interface UserService {

    UserInfoResponse getUserInfo(UserEntity userEntity);

    ValidationTokenDto validateAuthToken(UserEntity userEntity);
}
