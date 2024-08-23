package com.sparta.nbcampspringjpatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    @Size(max=255, min=1)
    @NotBlank
    private String email;
    @Size(max=255, min=1)
    @NotBlank
    private String pw;
}
