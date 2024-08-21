package com.sparta.nbcampspringjpatask.dto;

import lombok.Getter;

@Getter
public class CommentInsertDto {
    private String content;
    private Long userId;
    private Long scheduleId;
}
