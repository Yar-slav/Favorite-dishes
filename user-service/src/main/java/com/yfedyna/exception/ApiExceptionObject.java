package com.yfedyna.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiExceptionObject {

    private final String httpStatus;
    private final String message;
    private final String timestamp;
}
