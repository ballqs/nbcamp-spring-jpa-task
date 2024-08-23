package com.sparta.nbcampspringjpatask.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleInsertDto {
    private String title;
    private String content;
    private Long userId;
    private List<Long> userList;
}
