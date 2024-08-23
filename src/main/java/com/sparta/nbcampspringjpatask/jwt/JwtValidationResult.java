package com.sparta.nbcampspringjpatask.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtValidationResult {
    private final String message;
    private final int statusCode;
}
