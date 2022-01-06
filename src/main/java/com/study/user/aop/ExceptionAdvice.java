package com.study.user.aop;

import com.study.user.dto.ResponseDTO;
import com.study.user.exception.NotFoundException;
import com.study.user.exception.WrongArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({NotFoundException.class, WrongArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO NotFoundException(Exception e) {
        return ResponseDTO.builder()
                .messageKey(e.getMessage())
                .result(false)
                .build();
    }
}
