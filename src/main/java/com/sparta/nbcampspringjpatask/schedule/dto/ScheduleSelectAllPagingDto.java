package com.sparta.nbcampspringjpatask.schedule.dto;

import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleSelectAllPagingDto {
    private String title;
    private String content;
    private int commentCount;
    private String createAt;
    private String modifiedAt;

    public ScheduleSelectAllPagingDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = schedule.getCommentList().size();
        this.createAt = schedule.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.modifiedAt = schedule.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }
}
