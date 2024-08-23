package com.sparta.nbcampspringjpatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleUpdateDto {
    @Size(max=255, min=1)
    @NotBlank
    private String title;
    @Size(max=500, min=1)
    @NotBlank
    private String content;
    @NotBlank
    private List<Long> userList;
}
