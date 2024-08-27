package com.sparta.nbcampspringjpatask.schedule.dto;

import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
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
    private String weather;
    private Long userId;
    private String userNm;
    private String userEmail;
    private String refUserIds;
    private String refUserNms;
    private String refUserEmails;
    private String createdAt;
    private String modifiedAt;

    public ScheduleSelectDto (Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.weather = schedule.getWeather();
        this.userId = schedule.getUser().getId();
        this.userNm = schedule.getUser().getName();
        this.userEmail = schedule.getUser().getEmail();
        this.refUserIds = schedule.getScheduleMappingList().stream().map(mapping -> Long.toString(mapping.getUser().getId())).collect(Collectors.joining(","));
        this.refUserNms = schedule.getScheduleMappingList().stream().map(mapping -> mapping.getUser().getName()).collect(Collectors.joining(","));
        this.refUserEmails = schedule.getScheduleMappingList().stream().map(mapping -> mapping.getUser().getEmail()).collect(Collectors.joining(","));
        this.createdAt = schedule.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.modifiedAt = schedule.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }
}
