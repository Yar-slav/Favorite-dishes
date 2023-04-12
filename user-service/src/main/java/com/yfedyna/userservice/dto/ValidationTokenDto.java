package com.yfedyna.userservice.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ValidationTokenDto {
    private Set<String> roles;
    private Long userId;
}
