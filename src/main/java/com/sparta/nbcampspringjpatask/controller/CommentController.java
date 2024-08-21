package com.sparta.nbcampspringjpatask.controller;

import com.sparta.nbcampspringjpatask.dto.CommentInsertDto;
import com.sparta.nbcampspringjpatask.dto.CommentSelectDto;
import com.sparta.nbcampspringjpatask.dto.CommentUpdateDto;
import com.sparta.nbcampspringjpatask.dto.ResponseDto;
import com.sparta.nbcampspringjpatask.service.CommentService;
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
    public ResponseEntity<ResponseDto<CommentSelectDto>> createComment(@RequestBody CommentInsertDto commentInsertDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.createComment(commentInsertDto) , "성공적으로 등록완료했습니다."));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentSelectDto>> selectComment(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.selectComment(id) , "성공적으로 조회완료했습니다."));
    }

    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentSelectDto>>> selectAllComment() {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.selectAllComment() , "성공적으로 조회완료했습니다."));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentSelectDto>> updateComment(@PathVariable Long id , @RequestBody CommentUpdateDto commentUpdateDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), commentService.updateComment(id , commentUpdateDto) , "성공적으로 수정완료했습니다."));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<String>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "" , "성공적으로 삭제완료했습니다."));
    }
}
