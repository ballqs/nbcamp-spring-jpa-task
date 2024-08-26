package com.sparta.nbcampspringjpatask.repository;

import com.sparta.nbcampspringjpatask.entity.Comment;
import com.sparta.nbcampspringjpatask.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("선택한 댓글은 존재하지 않습니다."));
    }
}
