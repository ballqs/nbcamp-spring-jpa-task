package com.sparta.nbcampspringjpatask.schedule.entity;

import com.sparta.nbcampspringjpatask.comment.entity.Comment;
import com.sparta.nbcampspringjpatask.schedulemapping.entity.ScheduleMapping;
import com.sparta.nbcampspringjpatask.common.entity.Timestamped;
import com.sparta.nbcampspringjpatask.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends Timestamped {
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

    @OneToMany(mappedBy = "schedule" , cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    private List<ScheduleMapping> scheduleMappingList = new ArrayList<>();

    public Schedule(String title , String content , User user , String weather) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.weather = weather;
    }

    public void update(String title , String content) {
        this.title = title;
        this.content = content;
    }

    public void addScheduleMappingList(ScheduleMapping scheduleMapping) {
        this.scheduleMappingList.add(scheduleMapping);
        scheduleMapping.addSchedule(this); // 외래 키(연관 관계) 설정
    }
}
