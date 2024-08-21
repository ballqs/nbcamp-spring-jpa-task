package com.sparta.nbcampspringjpatask.repository;

import com.sparta.nbcampspringjpatask.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
