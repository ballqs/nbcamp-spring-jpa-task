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

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private List<ScheduleMapping> scheduleMappingList = new ArrayList<>();


    public User(UserInsertDto userInsertDto) {
        this.name = userInsertDto.getName();
        this.email = userInsertDto.getEmail();
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.name = userUpdateDto.getName();
    }
}
