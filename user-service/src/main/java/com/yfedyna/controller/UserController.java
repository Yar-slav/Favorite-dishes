package com.yfedyna.controller;

import com.yfedyna.dto.UserInfoResponse;
import com.yfedyna.model.UserEntity;
import com.yfedyna.service.AuthService;
import com.yfedyna.service.UserService;
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
}
