package com.sparta.nbcampspringjpatask.controller;

import com.sparta.nbcampspringjpatask.dto.*;
import com.sparta.nbcampspringjpatask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ResponseDto<UserSelectDto>> createUser(@RequestBody UserInsertDto userInsertDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.createUser(userInsertDto) , "성공적으로 등록완료했습니다."));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserSelectDto>> selectUser(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.selectUser(id) , "성공적으로 조회완료했습니다."));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDto<List<UserSelectDto>>> selectAllUser() {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.selectAllUser() , "성공적으로 조회완료했습니다."));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserSelectDto>> updateUser(@PathVariable Long id , @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.updateUser(id , userUpdateDto) , "성공적으로 수정완료했습니다."));
    }

    // 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDto<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "" , "성공적으로 삭제완료했습니다."));
    }
}
