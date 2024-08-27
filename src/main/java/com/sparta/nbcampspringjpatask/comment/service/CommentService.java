package com.sparta.nbcampspringjpatask.comment.service;

import com.sparta.nbcampspringjpatask.comment.dto.CommentInsertDto;
import com.sparta.nbcampspringjpatask.comment.dto.CommentSelectDto;
import com.sparta.nbcampspringjpatask.comment.dto.CommentUpdateDto;
import com.sparta.nbcampspringjpatask.comment.entity.Comment;
import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
import com.sparta.nbcampspringjpatask.user.entity.User;
import com.sparta.nbcampspringjpatask.comment.repository.CommentRepository;
import com.sparta.nbcampspringjpatask.schedule.service.ScheduleService;
import com.sparta.nbcampspringjpatask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;
    private final UserService userService;

    public CommentSelectDto createComment(CommentInsertDto commentInsertDto) {
        Long id = commentInsertDto.getScheduleId();
        Schedule schedule = scheduleService.getSchduleEntity(id);
        Long userId = commentInsertDto.getUserId();
        User user = userService.getUserEntity(userId);

        Comment comment = new Comment(commentInsertDto.getContent() , user , schedule);

        return new CommentSelectDto(commentRepository.save(comment));
    }

    public CommentSelectDto getComment(Long id) {
        return new CommentSelectDto(commentRepository.findByIdOrElseThrow(id));
    }

    @Transactional(readOnly = true)
    public List<CommentSelectDto> getComments() {
        return commentRepository.findAll().stream().map(CommentSelectDto::new).toList();
    }

    public CommentSelectDto updateComment(Long id , CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        comment.update(commentUpdateDto.getContent());
        return new CommentSelectDto(comment);
    }

    public void removeComment(Long id) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment getCommentEntity(Long id) {
        return commentRepository.findByIdOrElseThrow(id);
    }
}
