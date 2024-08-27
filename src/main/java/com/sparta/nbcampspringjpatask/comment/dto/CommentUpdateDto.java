package com.sparta.nbcampspringjpatask.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateDto {
    @Size(max=500, min=1)
    @NotBlank
    private String content;
}
