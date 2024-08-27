package com.sparta.nbcampspringjpatask.common.exception;

import com.sparta.nbcampspringjpatask.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> illegalArgumentHandle(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(HttpStatus.UNAUTHORIZED.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ResponseDto<String>> IllegalStateHandle(IllegalStateException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ResponseDto<String>> nullPointerHandle(NullPointerException e){
        return ResponseEntity.badRequest().body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ResponseDto<String>> noSuchElementHandle(NoSuchElementException e){
        return ResponseEntity.badRequest().body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<String>> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        List<String> msgList = new ArrayList<>();
        for (int i = 0; i < e.getBindingResult().getFieldErrors().size(); i++) {
            String msg = e.getBindingResult().getFieldErrors().get(i).getField() + "는 " + e.getBindingResult().getFieldErrors().get(i).getDefaultMessage();
            msgList.add(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value() , "" , String.join(" , " , msgList)));
    }
}
