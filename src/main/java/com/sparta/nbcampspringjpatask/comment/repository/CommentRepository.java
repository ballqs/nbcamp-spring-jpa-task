package com.sparta.nbcampspringjpatask.comment.repository;

import com.sparta.nbcampspringjpatask.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("선택한 댓글은 존재하지 않습니다."));
    }
}
