package com.yfedyna.service;

import com.yfedyna.dto.UserInfoResponse;
import com.yfedyna.model.UserEntity;

public interface UserService {

    UserInfoResponse getUserInfo(UserEntity userEntity);
}
