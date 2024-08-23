package com.sparta.nbcampspringjpatask.entity;

import com.sparta.nbcampspringjpatask.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title" , nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @Column(name = "weather" , nullable = false)
    private String weather;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @OneToMany(mappedBy = "schedule" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<ScheduleMapping> scheduleMappingList = new ArrayList<>();

    public Schedule(ScheduleInsertDto scheduleInsertDto , User user , String weather) {
        this.title = scheduleInsertDto.getTitle();
        this.content = scheduleInsertDto.getContent();
        this.user = user;
        this.weather = weather;
    }

    public void update(ScheduleUpdateDto scheduleUpdateDto) {
        this.title = scheduleUpdateDto.getTitle();
        this.content = scheduleUpdateDto.getContent();
    }

    public void addScheduleMappingList(ScheduleMapping scheduleMapping) {
        this.scheduleMappingList.add(scheduleMapping);
        scheduleMapping.addSchedule(this); // 외래 키(연관 관계) 설정
    }
}
