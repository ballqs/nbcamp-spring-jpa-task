package com.sparta.nbcampspringjpatask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {
    private int status;
    private T body;
    private String msg;
}