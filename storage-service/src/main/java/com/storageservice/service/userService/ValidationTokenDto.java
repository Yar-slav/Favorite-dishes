package com.storageservice.service.userService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationTokenDto {
    private Set<String> roles;
    private Long userId;
}
