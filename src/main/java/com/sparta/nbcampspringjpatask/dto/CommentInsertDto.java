package com.sparta.nbcampspringjpatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentInsertDto {
    @Size(max=500, min=1)
    @NotBlank
    private String content;
    @Positive
    @NotNull
    private Long userId;
    @Positive
    @NotNull
    private Long scheduleId;
}
