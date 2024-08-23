package com.sparta.nbcampspringjpatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleInsertDto {
    @Size(max=255, min=1)
    @NotBlank
    private String title;
    @Size(max=500, min=1)
    @NotBlank
    private String content;
    @Positive
    @NotNull
    private Long userId;
    @NotBlank
    private List<Long> userList;
}
