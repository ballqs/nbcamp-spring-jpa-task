package com.sparta.nbcampspringjpatask.dto;

import lombok.Getter;

@Getter
public class CommentInsertDto {
    private String content;
    private String user_nm;
    private Long scheduleId;
}
