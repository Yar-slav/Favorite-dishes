package com.yfedyna.apigateway.userservice.service.implementation;

import com.yfedyna.apigateway.userservice.model.UserEntity;
import com.yfedyna.apigateway.userservice.dto.UserInfoResponse;
import com.yfedyna.apigateway.userservice.dto.ValidationTokenDto;
import com.yfedyna.apigateway.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public ValidationTokenDto validateAuthToken(UserEntity userEntity) {
        return ValidationTokenDto.builder()
                .roles(Set.of(String.valueOf(userEntity.getUserRole())))
                .userId(userEntity.getId())
                .build();
    }
}
