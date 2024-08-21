package com.sparta.nbcampspringjpatask.entity;

import com.sparta.nbcampspringjpatask.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    @Column(name = "user_nm" , nullable = false)
    private String user_nm;

    public Schedule(ScheduleInsertDto scheduleInsertDto) {
        this.title = scheduleInsertDto.getTitle();
        this.content = scheduleInsertDto.getContent();
        this.user_nm = scheduleInsertDto.getUser_nm();
    }

    public void update(ScheduleUpdateDto scheduleUpdateDto) {
        this.title = scheduleUpdateDto.getTitle();
        this.content = scheduleUpdateDto.getContent();
        this.user_nm = scheduleUpdateDto.getUser_nm();
    }
}
