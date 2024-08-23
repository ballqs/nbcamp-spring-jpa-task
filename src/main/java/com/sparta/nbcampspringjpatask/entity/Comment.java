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

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id" , nullable = false)
    private Schedule schedule;

    public Comment(CommentInsertDto commentInsertDto , User user) {
        this.content = commentInsertDto.getContent();
        this.user = user;
    }

    public void update(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
    }
}
