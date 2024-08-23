package com.sparta.nbcampspringjpatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserInsertDto {
    @Size(max=255, min=1)
    @NotBlank
    private String name;
    @Size(max=255, min=1)
    @NotBlank
    private String pw;
    @Size(max=255, min=1)
    @NotBlank
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
