package com.sparta.nbcampspringjpatask.dto;

import com.sparta.nbcampspringjpatask.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ScheduleSelectDto {
    private Long id;
    private String title;
    private String content;
    private String userNms;
    private String createdAt;
    private String modifiedAt;


    public ScheduleSelectDto (Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.userNms = schedule.getScheduleMappingList().stream().map(mapping -> mapping.getUser().getName()).collect(Collectors.joining(","));
        this.createdAt = schedule.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.modifiedAt = schedule.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }
}
