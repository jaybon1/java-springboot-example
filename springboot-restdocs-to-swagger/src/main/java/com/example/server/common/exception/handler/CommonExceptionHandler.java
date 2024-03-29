package com.example.server.common.exception.handler;


import com.example.server.common.exception.BadRequestException;
import com.example.server.common.exception.EntityNotFoundException;
import com.example.server.common.dto.ResDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BindException.class)
    public HttpEntity<?> handleBindException(BindException exception){
        Map<String,String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message("요청 데이터가 유효하지 않습니다.")
                        .data(errorMap)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public HttpEntity<?> handleEntityNotFoundException(){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message("해당하는 DB 데이터가 없습니다.")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public HttpEntity<?> handleBadRequestException(Exception exception){

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<?> handleException(Exception exception){
        String message = "에러가 발생했습니다. 관리자에게 문의하세요.";

        if (exception.getMessage() != null && !exception.getMessage().equals("")){
            message = exception.getMessage();
        }

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message(message)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
