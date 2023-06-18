package com.studytime.web.controller;

import com.studytime.exception.StudyTimeException;
import com.studytime.web.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validException (MethodArgumentNotValidException e){

        Map<String, String> validation = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            validation.put(error.getField(), error.getDefaultMessage());
        }

        return ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(validation)
                .build();
    }
    @ExceptionHandler(StudyTimeException.class)
    public ResponseEntity<ErrorResponse> exception(StudyTimeException e){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(e.getStatusCode())
                .body(errorResponse);
    }
}
