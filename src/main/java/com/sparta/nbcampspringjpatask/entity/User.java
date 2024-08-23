package com.sparta.nbcampspringjpatask.entity;

import com.sparta.nbcampspringjpatask.dto.UserInsertDto;
import com.sparta.nbcampspringjpatask.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "email" , nullable = false)
    private String email;
    @Column(name = "pw" , nullable = false)
    private String pw;
    @Column(name = "role" , nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<ScheduleMapping> scheduleMappingList = new ArrayList<>();

    public User(String name , String email , String pw , UserRoleEnum role) {
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.role = role;
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.name = userUpdateDto.getName();
    }
}
