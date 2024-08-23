package com.sparta.nbcampspringjpatask.dto;

import com.sparta.nbcampspringjpatask.entity.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CommentSelectDto {
    private Long id;
    private String content;
    private String userNm;
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;
    private String createdAt;
    private String modifiedAt;

    public CommentSelectDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNm = comment.getUser().getName();
        this.scheduleId = comment.getSchedule().getId();
        this.scheduleTitle = comment.getSchedule().getTitle();
        this.scheduleContent = comment.getSchedule().getContent();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.modifiedAt = comment.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }
}
