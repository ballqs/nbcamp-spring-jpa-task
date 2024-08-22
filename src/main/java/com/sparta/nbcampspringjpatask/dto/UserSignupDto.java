package com.sparta.nbcampspringjpatask.dto;

import com.sparta.nbcampspringjpatask.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
public class UserSignupDto {
    private Long id;
    private String name;
    private String email;
    private String createdAt;
    private String modifiedAt;
    private String token;

    public UserSignupDto(User user , String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.modifiedAt = user.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.token = token;
    }
}
