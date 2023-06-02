package com.example.project.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResDTO<T> {
    private Integer code;
    private String message;
    private T data;
}
