package com.study.user.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String msg){
        super(msg);
    }
}
