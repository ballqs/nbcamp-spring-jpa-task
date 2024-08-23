package com.sparta.nbcampspringjpatask.controller;

import com.sparta.nbcampspringjpatask.dto.ResponseDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> illegalArgumentHandle(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(HttpStatus.UNAUTHORIZED.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ResponseDto<String>> nullPointerHandle(NullPointerException e){
        return ResponseEntity.badRequest().body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<ResponseDto<String>> duplicateKeyHandle(DuplicateKeyException e){
        return ResponseEntity.badRequest().body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , e.getMessage()));
    }
}
