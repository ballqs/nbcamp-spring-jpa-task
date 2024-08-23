package com.sparta.nbcampspringjpatask.dto;

import lombok.Getter;

@Getter
public class UserInsertDto {
    private String name;
    private String pw;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
