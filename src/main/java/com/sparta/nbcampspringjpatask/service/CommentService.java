package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.dto.CommentInsertDto;
import com.sparta.nbcampspringjpatask.dto.CommentSelectDto;
import com.sparta.nbcampspringjpatask.dto.CommentUpdateDto;
import com.sparta.nbcampspringjpatask.entity.Comment;
import com.sparta.nbcampspringjpatask.entity.Schedule;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.repository.CommentRepository;
import com.sparta.nbcampspringjpatask.repository.UserRepository;
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
        Schedule schedule = scheduleService.findById(id);
        User user = userService.findById(commentInsertDto.getUserId());

        Comment comment = new Comment(commentInsertDto , user);
        comment.setSchedule(schedule);

        return new CommentSelectDto(commentRepository.save(comment));
    }

    public CommentSelectDto selectComment(Long id) {
        return new CommentSelectDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<CommentSelectDto> selectAllComment() {
        return commentRepository.findAll().stream().map(CommentSelectDto::new).toList();
    }

    public CommentSelectDto updateComment(Long id , CommentUpdateDto commentUpdateDto) {
        Comment comment = findById(id);
        comment.update(commentUpdateDto);
        return new CommentSelectDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NullPointerException("선택한 댓글은 존재하지 않습니다."));
    }
}
