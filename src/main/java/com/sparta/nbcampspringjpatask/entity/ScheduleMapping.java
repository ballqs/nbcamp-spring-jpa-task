package com.sparta.nbcampspringjpatask.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "schedule_mapping")
public class ScheduleMapping extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(Schedule schedule , User user) {
        this.schedule = schedule;
        this.user = user;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
