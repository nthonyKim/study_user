package com.study.user.exception;

public class WrongArgumentException extends RuntimeException {
    public WrongArgumentException(String msg){
        super(msg);
    }
}
