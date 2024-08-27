package com.sparta.nbcampspringjpatask.schedulemapping.entity;

import com.sparta.nbcampspringjpatask.common.entity.Timestamped;
import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
import com.sparta.nbcampspringjpatask.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "schedule_mapping")
public class ScheduleMapping extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id" , nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    public void update(Schedule schedule , User user) {
        this.schedule = schedule;
        this.user = user;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
