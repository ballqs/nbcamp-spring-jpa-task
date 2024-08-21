package com.sparta.nbcampspringjpatask.entity;

import com.sparta.nbcampspringjpatask.dto.CommentInsertDto;
import com.sparta.nbcampspringjpatask.dto.CommentUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @Column(name = "user_nm" , nullable = false)
    private String user_nm;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(CommentInsertDto commentInsertDto) {
        this.content = commentInsertDto.getContent();
        this.user_nm = commentInsertDto.getUser_nm();
    }

    public void update(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
        this.user_nm = commentUpdateDto.getUser_nm();
    }
}
