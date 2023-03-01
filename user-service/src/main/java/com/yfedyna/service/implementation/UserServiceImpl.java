package com.yfedyna.service.implementation;

import com.yfedyna.dto.UserInfoResponse;
import com.yfedyna.model.UserEntity;
import com.yfedyna.service.AuthService;
import com.yfedyna.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserInfoResponse getUserInfo(UserEntity user) {
        return UserInfoResponse.builder()
                .firstname(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .build();
    }
}
