package com.sparta.nbcampspringjpatask.comment.controller;

import com.sparta.nbcampspringjpatask.comment.dto.CommentInsertDto;
import com.sparta.nbcampspringjpatask.comment.dto.CommentSelectDto;
import com.sparta.nbcampspringjpatask.comment.dto.CommentUpdateDto;
import com.sparta.nbcampspringjpatask.common.dto.ResponseDto;
import com.sparta.nbcampspringjpatask.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<ResponseDto<CommentSelectDto>> createComment(@Valid @RequestBody CommentInsertDto commentInsertDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.createComment(commentInsertDto) , "성공적으로 등록완료했습니다."));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentSelectDto>> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.getComment(id) , "성공적으로 조회완료했습니다."));
    }

    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentSelectDto>>> getComments() {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.getComments() , "성공적으로 조회완료했습니다."));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentSelectDto>> updateComment(@PathVariable Long id , @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.updateComment(id , commentUpdateDto) , "성공적으로 수정완료했습니다."));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<String>> removeComment(@PathVariable Long id) {
        commentService.removeComment(id);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "" , "성공적으로 삭제완료했습니다."));
    }
}
